import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.util.Stack;

public final class GameData {
    // Class with the single purpose on storing data
    private Hero hero = new Hero("Hero", 10, 3, 0);
    int[] enemy_damage_range = {4, 8};
    private final Enemy enemy = new Enemy("Rat", 20, 0, enemy_damage_range);
    private DamageCard strike = new DamageCard("Golpe", 1, 3, enemy);
    private ShieldCard defend = new ShieldCard("Escudo", 1, 2);


    private int buy_pile_size = 20;
    private int hand_size = 5;
    private Card[] possible_cards = {strike, defend};
    private ArrayList<Integer> player_hand = new ArrayList<>();
    private Stack<Integer> buy_pile = new Stack<>();
    private Stack<Integer> discard_pile = new Stack<>();

    private boolean invalid_action = false;
    private boolean battle_over = false;
    private int battle_rounds = 1;


    public GameData() {
        generateRandomBuyPile();
        buyRoundCards();
    }



    public Hero getHero() {
        return this.hero;
    }


    public Enemy getEnemy() {
        return this.enemy;
    }


    public DamageCard getDamageCard() {
        return this.strike;
    }


    public ShieldCard getShieldCard() {
        return this.defend;
    }

    
    public boolean isBattle_over() {
        return this.battle_over;
    }

    
    public void setBattle_over(boolean game_over) {
        this.battle_over = game_over;
    }


    public int getBattle_rounds() {
        return this.battle_rounds;
    }


    public void addBattle_round() {
        this.battle_rounds += 1;
    }


    public Card[] getPossible_cards() {
        return possible_cards;
    }


    public void setPossible_cards(Card[] possible_cards) {
        this.possible_cards = possible_cards;
    }


    public Stack<Integer> getBuy_pile() {
        return this.buy_pile;
    }


        public boolean isAction_invalid() {
        return invalid_action;
    }


    public void setInvalid_action(boolean card_failed_use) {
        this.invalid_action = card_failed_use;
    }


    public void generateRandomBuyPile() {
        Random generator = new Random();
        for (int i = 0; i < this.buy_pile_size; i++) {
            int random_card_index = generator.nextInt(0, possible_cards.length);
            this.buy_pile.push(random_card_index);  
        }
    }


    public Stack<Integer> getDiscard_pile() {
        return this.discard_pile;
    }


    public void discardCard(int position) {
        int card = player_hand.remove(position);
        this.discard_pile.push(card);
    }


    public void discardHand() {
        this.discard_pile.addAll(player_hand);
        this.player_hand.clear();
    }


    public void buyCard() {
        if (!this.buy_pile.isEmpty()) {
            this.player_hand.add(this.buy_pile.pop());
        }
    }


    public void buyRoundCards() {
        for (int i = 0; i < hand_size; i++) {
            buyCard();
        }
    }


    public void resetBuyPile() {
        this.buy_pile.addAll(discard_pile);
        Collections.shuffle(this.buy_pile);
        this.discard_pile.clear();
    }


    public ArrayList<Integer> getPlayer_hand() {
        return this.player_hand;
    }
}