import java.util.ArrayList;
import java.util.Stack;

public class GameManager {
    private final GameData data = new GameData();
    private boolean game_ended = false;

    Hero hero = data.getHero();
    Enemy enemy = data.getEnemy();
    DamageCard strike = data.getDamageCard();
    ShieldCard defend = data.getShieldCard();
    ArrayList<Integer> player_hand = data.getPlayer_hand();
    Stack<Integer> buy_pile = data.getBuy_pile();
    Stack<Integer> discard_pile = data.getDiscard_pile();


    public void update(Action action) {
        // Logic to be update every frame

        boolean battle_over = data.isBattle_over();
        Action.ActionType actionType = action.getAction_type();

        if (data.getBattle_rounds() == 1) {
            data.generateRandomBuyPile();
            data.buyRoundCards();
        }

        if (!battle_over) {
            // Battle logic
            switch(actionType) {
                case CARD -> {
                    System.out.println(discard_pile);
                    System.out.println(buy_pile);
                    System.out.println(player_hand);

                    int card_index = action.getCard_used_index() - 1;
                    System.out.print(card_index);
                    
                    if (player_hand.size() < card_index || player_hand.isEmpty()) {
                        this.data.setCard_failed_use(true);
                    }
                    else {
                        int card_type = this.player_hand.get(card_index);
                        Card card = this.data.getPossible_cards()[card_type];
                        card.useCard(this.hero);
                        this.data.discardCard(card_index);
                        this.data.addBattle_round();
                    }
                }
                case SKIP -> {
                    System.out.println("eu sou o fim de jogo");
                    endTurn();
                }
            }
  
            if (!hero.isAlive()) {
                this.data.setBattle_over(true);
            }
        }
        else {
            this.game_ended = true;
        }
    }


    public boolean isGame_Ended() {
        return this.game_ended;
    }


    public GameData getGameData() {
        return this.data;
    }


    public void endTurn() {
        this.data.discardHand();
        if (this.data.getBuy_pile().isEmpty()) {
            this.data.resetBuyPile();
        }
        this.data.buyRoundCards();
        this.enemy.attackHero(1, hero);
        this.hero.setEnergy(3);
        this.hero.setShield(0);
        this.data.addBattle_round();
    }



}
