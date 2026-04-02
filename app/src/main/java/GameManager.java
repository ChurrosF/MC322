import java.util.ArrayList;

public class GameManager {
    private final GameData data = new GameData();
    private final ArrayList<StatusEffect> effectSubscribers = new ArrayList<>();
    private boolean gameEnded = false;

    Hero hero = data.getHero();
    Enemy enemy = data.getEnemy();
    ArrayList<Integer> player_hand = data.getPlayerHand();
    boolean battleOver = data.isBattle_over();


    public void update(Action action) {
        // Logic to be update every frame

        updateEffects(action);
        Action.ActionType actionType = action.getAction_type();
        this.data.setInvalidAction(false);
        
        if (battleOver) {
            this.gameEnded = true;
            return;
        }

        switch(actionType) {
            case CARD -> {
                handleCardUse(action);
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
        
        checkBattleOver();
    }


    public boolean isGameEnded() {
        return this.gameEnded;
    }


    public GameData getGameData() {
        return this.data;
    }


    private void turnSkip() {
        this.data.discardHand();
        if (this.data.getBuy_pile().isEmpty()) {
            this.data.resetBuyPile();
        }
        this.data.setInvalidAction(false);
        this.data.buyRoundCards();
        this.enemy.setShield(0);
        this.enemy.executeAction(hero, effectSubscribers);
        this.hero.setEnergy(3);
        this.hero.setShield(0);
        this.data.addBattle_round();
    }


    private void handleCardUse(Action action) {
        int cardIndex = action.getCard_used_index();

        if (isCardInvalid(cardIndex)) {
            this.data.setInvalidAction(true);
            return;
        }
        
        Card card = getCardFromIndex(cardIndex);

        if (card.useCard(this.hero)) {
            this.data.discardCard(cardIndex);
            handleEffectCards(card);
        }
        this.data.addBattle_round();
        }


    private void handleEffectCards(Card card) {
        if (!(card instanceof EffectCard effectCard)) return;

        StatusEffect effect = effectCard.getEffect();

        if (notSubscribed(effect)) {
            subscribe(effect);
        }
    }


    private Card getCardFromIndex(int cardIndex) {
        int card_type = this.player_hand.get(cardIndex);
        Card card = this.data.getPossible_cards()[card_type];
        return card;
    }

    
    private boolean isCardInvalid(int cardIndex) {
        return (player_hand.size() <= cardIndex || player_hand.isEmpty());
    }


    private void checkBattleOver() {
        if (!hero.isAlive() || !enemy.isAlive()) {
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


    private void notifyEffects(Action action) {
        for (StatusEffect effectSubscriber : effectSubscribers) {
            effectSubscriber.beNotified(action, this.data);
            if (effectSubscriber.getAmount() == 0) {
                effectSubscriber.getOwner().getEffects().remove(effectSubscriber);
            }
        }
    }

    
    private void updateEffects(Action action)  {
        // Notifies effects of the current action and unsubscribes expired effects
        notifyEffects(action);
        effectSubscribers.removeIf(effect -> effect.getAmount() == 0);
    }
}
