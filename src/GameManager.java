import java.util.ArrayList;

public class GameManager {
    private final GameData data = new GameData();
    private final ArrayList<StatusEffect> effectSubscribers = new ArrayList<>();
    private boolean gameEnded = false;

    Hero hero = data.getHero();
    Enemy enemy = data.getEnemy();
    ArrayList<Integer> player_hand = data.getPlayerHand();
    boolean battle_over = data.isBattle_over();


    public void update(Action action) {
        // Logic to be update every frame

        // Notifies effects the current action and unsubscribes expired effects
        updateEffects(action);
        Action.ActionType actionType = action.getAction_type();
        
        if (!battle_over) {
            // Battle logic
            switch(actionType) {
                case CARD -> {
                    int card_index = action.getCard_used_index();


                    if (player_hand.size() <= card_index || player_hand.isEmpty()) {
                        this.data.setInvalidAction(true);
                    }
                    else {
                        this.data.setInvalidAction(false);
                        int card_type = this.player_hand.get(card_index);
                        Card card = this.data.getPossible_cards()[card_type];

                        if (card.useCard(this.hero)) {
                            this.data.discardCard(card_index);

                            if (card instanceof EffectCard effectCard) {
                                StatusEffect effect = effectCard.getEffect();

                                if (notSubscribed(effect)) {
                                    subscribe(effect);
                                }
                            }
                        }
                        this.data.addBattle_round();
                    }
                }
                case SKIP -> {
                    endTurn();
                }
                case QUIT -> {
                    this.data.setBattle_over(true);
                }
                case INVALID -> {
                    this.data.setInvalidAction(true);
                }
            }
  
            if (!hero.isAlive() || !enemy.isAlive()) {
                this.data.setBattle_over(true);
            }
        }
        else {
            this.gameEnded = true;
        }
    }


    public boolean isGame_Ended() {
        return this.gameEnded;
    }


    public GameData getGameData() {
        return this.data;
    }


    private void endTurn() {
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


    private void subscribe(StatusEffect effect) {
        this.effectSubscribers.add(effect);
    }


    public void unsubscribe(StatusEffect effect) {
        this.effectSubscribers.remove(effect);
    }


    private void notifyEffects(Action action) {
        for (StatusEffect effectSubscriber : effectSubscribers) {
            effectSubscriber.beNotified(action, this.data);
            if (effectSubscriber.getAmount() == 0) {
                effectSubscriber.owner.getEffects().remove(effectSubscriber);
            }
        }
    }

    
    private void updateEffects(Action action)  {
        notifyEffects(action);
        effectSubscribers.removeIf(effect -> effect.getAmount() == 0);
    }


    private boolean notSubscribed(StatusEffect effect) {
        return !effectSubscribers.contains(effect);
    }

}
