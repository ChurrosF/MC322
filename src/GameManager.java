import java.util.ArrayList;
import java.util.Stack;

public class GameManager {
    private final GameData data = new GameData();
    private ArrayList<StatusEffect> effectSubscribers = new ArrayList<StatusEffect>();
    private boolean gameEnded = false;

    Hero hero = data.getHero();
    Enemy enemy = data.getEnemy();
    ArrayList<Integer> player_hand = data.getPlayerHand();
    Stack<Integer> buyPile = data.getBuy_pile();
    Stack<Integer> discardPile = data.getDiscardPile();


    public void update(Action action) {
        // Logic to be update every frame

        // Notifies to GM the effects
        this.notify_effects(action);

        boolean battle_over = data.isBattle_over();
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
                            if (!enemy.isAlive()) {
                                this.data.setBattle_over(true);
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
  
            if (!hero.isAlive()) {
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


    public void endTurn() {
        this.data.discardHand();
        if (this.data.getBuy_pile().isEmpty()) {
            this.data.resetBuyPile();
        }
        this.data.setInvalidAction(false);
        this.data.buyRoundCards();
        this.enemy.attackHero(hero);
        this.hero.setEnergy(3);
        this.hero.setShield(0);
        this.data.addBattle_round();
    }


    public void subscribe(StatusEffect effect) {
        effectSubscribers.add(effect);
    }


    public void unsubscribe(StatusEffect effect) {
        effectSubscribers.remove(effect);
    }


    public void notify_effects(Action action) {
        for (StatusEffect effectSubscriber : effectSubscribers) {
            effectSubscriber.beNotified(action);
            if (effectSubscriber.amount == 0) {
                unsubscribe(effectSubscriber);
            }
        }
    }
}
