import java.util.ArrayList;

/**
 * Main controller for the game logic (Game Loop and Battle Rules).
 * <p>
 * The {@code GameManager} is responsible for processing player actions, updating 
 * the game state in {@link GameData}, coordinating turns, and managing the lifecycle 
 * of status effects (Buffs/Debuffs) using the Observer pattern.
 * </p>
 */
public class GameManager {
    private final GameData data = new GameData();
    private final ArrayList<StatusEffect> effectSubscribers = new ArrayList<>();
    private final Hero hero = data.getHero();
    private final ArrayList<Integer> player_hand = data.getPlayerHand();
    private GameState state = GameState.CHOOSING_CARD;
    private boolean gameEnded = false;


    /**
     * Processes the current action and updates the overall state.
     * Must be called on every user interaction or interface cycle.
     * @param action The requested action for the current turn (e.g., Use Card, Skip Turn).
     */
    public void update(Action action) {
        updateEffects(action);
        Action.ActionType actionType = action.getActionType();
        this.data.setInvalidAction(false);
        
        if (data.isBattleOver()) {
            this.gameEnded = true;
            return;
        }

        switch(actionType) {
            case CHOOSE_CARD -> {
                handleCardChoose(action);
            }
            case CHOOSE_TARGET -> {
                handleTargetChoose(action);
            }
            case BACK -> {
                this.state = GameState.CHOOSING_CARD;
            }
            case SKIP -> {
                turnSkip();
            }
            case QUIT -> {
                this.data.setBattleOver(true);
            }
            case INVALID -> {
                this.data.setInvalidAction(true);
            }
        }
        
        this.data.getEnemies().removeIf(enemy -> !enemy.isAlive());
        checkBattleOver();
    }


    public boolean isGameEnded() {
        return this.gameEnded;
    }


    /** @return The {@link GameData} instance managed by this controller. */
    public GameData getGameData() {
        return this.data;
    }


    /**
     * Resolves the End Turn logic for the player.
     * <p>
     * The following sequence of events occurs:
     * <ol>
     * <li>Discards the player's remaining hand.</li>
     * <li>If the draw pile is empty, shuffles the discard pile back in.</li>
     * <li>Draws a new hand for the next turn.</li>
     * <li>Resets shields (armor does not carry over from one turn to the next).</li>
     * <li>The enemy executes its programmed action.</li>
     * <li>The hero's energy is restored.</li>
     * </ol>
     * </p>
     */
    private void turnSkip() {
        this.data.discardHand();
        if (this.data.getBuyPile().isEmpty()) {
            this.data.resetBuyPile();
        }
        this.data.setInvalidAction(false);
        this.data.buyRoundCards();
        for (Enemy enemy : data.getEnemies()) {
            enemy.setShield(0);
            enemy.executeAction(hero, effectSubscribers);
        }
        this.hero.setEnergy(3);
        this.hero.setShield(0);
        this.data.addBattleRound();
    }


    /**
     * Attempts to execute the use of a card based on the player's action.
     * * @param action The action object containing the index of the card in hand.
     */
    private void handleCardChoose(Action action) {
        Integer cardIndex = action.getCardUsedIndex();

        if (!isCardChoosen(action)) {
            return;
        }

        if (isCardInvalid(cardIndex)) {
            this.data.setInvalidAction(true);
            return;
        }
        
        Card card = getCardFromIndex(cardIndex);
        
        if (card.requiresTarget()) {
            this.state = GameState.TARGETING;
            return;
        }

        handleCardUse(cardIndex, hero);
        this.data.addBattleRound();
        }


    private void handleTargetChoose(Action action) {
        int targetIndex = action.getTargetIndex();

        if (isTargetInvalid(targetIndex)) {
            this.data.setInvalidAction(true);
            return;
        }

        Enemy target = data.getEnemies().get(targetIndex);

        handleCardUse(action.getCardUsedIndex(), target);
        this.state = GameState.CHOOSING_CARD;
        this.data.addBattleRound();
    }


    private void handleCardUse(int cardIndex, Entity target) {
        Card card = getCardFromIndex(cardIndex);
        if (card.useCard(hero, target)) {
            this.data.discardCard(cardIndex);
            handleEffectCards(card);
        }
    }


    /**
     * Checks if the played card applies any Status Effect (Buff/Debuff).
     * If it does and it's not yet active, subscribes the effect to the system.
     * * @param card The card that was just played.
     */
    private void handleEffectCards(Card card) {
        if (!(card instanceof EffectCard effectCard)) return;

        StatusEffect effect = effectCard.getEffect();

        if (notSubscribed(effect)) {
            subscribe(effect);
        }
    }


    /**
     * Translates the index from the player's hand to the actual Card instance in the catalog.
     * * @param cardIndex The card's position in the interface/player's hand.
     * @return The corresponding {@link Card} object.
     */
    private Card getCardFromIndex(int cardIndex) {
        int card_type = this.player_hand.get(cardIndex);
        Card card = this.data.getPossibleCards()[card_type];
        return card;
    }

    
    /**
     * Validates if the card choice is possible (prevents OutOfBounds or playing from an empty hand).
     * * @param cardIndex The chosen index.
     * @return {@code true} if it's an invalid play.
     */
    private boolean isCardInvalid(int cardIndex) {
        return (player_hand.size() <= cardIndex || player_hand.isEmpty() || cardIndex < 0);
    }


    private boolean isCardChoosen(Action action) {
        return (action.getCardUsedIndex() != null);
    }


    private boolean isTargetInvalid(int targetIndex) {
        return (targetIndex < 0 || targetIndex >= data.getEnemies().size());
    }


    private void checkBattleOver() {
        if (!hero.isAlive() || data.getEnemies().isEmpty()) {
            this.data.setBattleOver(true);
        }
    }


    private void subscribe(StatusEffect effect) {
        this.effectSubscribers.add(effect);
    }


    public void unsubscribe(StatusEffect effect) {
        this.effectSubscribers.remove(effect);
    }


    private boolean notSubscribed(StatusEffect effect) {
        return !effectSubscribers.contains(effect);
    }


    /**
     * Notifies all active effects that an action has occurred.
     * Effects with zero duration are removed from the owning entity.
     * * @param action The recent action that might trigger the effect.
     */
    private void notifyEffects(Action action) {
        for (StatusEffect effectSubscriber : effectSubscribers) {
            effectSubscriber.beNotified(action, this.data);
            if (effectSubscriber.getAmount() == 0) {
                effectSubscriber.getOwner().getEffects().remove(effectSubscriber);
            }
        }
    }

    
    /**
     * Triggers notifications for the effects and clears the ones that have expired.
     * * @param action The action of the current turn.
     */
    private void updateEffects(Action action)  {
        notifyEffects(action);
        effectSubscribers.removeIf(effect -> effect.getAmount() == 0);
    }

    public GameState getState() {
        return state;
    }
}
