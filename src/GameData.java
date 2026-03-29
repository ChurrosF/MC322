import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.util.Stack;

public final class GameData {
    // Class with the single purpose on storing data
    private Hero hero = new Hero("Hero", 10, 3, 0);
    int[] enemyDamageRange = {4, 8};
    private final Enemy enemy = new Enemy("Rat", 20, 0, enemyDamageRange);
    private DamageCard strike = new DamageCard("Golpe", 1, 3, enemy);
    private ShieldCard defend = new ShieldCard("Escudo", 1, 2);


    private int buyPileSize = 20;
    private int handSize = 5;
    private Card[] possible_cards = {strike, defend};
    private ArrayList<Integer> playerHand = new ArrayList<>();
    private Stack<Integer> buyPile = new Stack<>();
    private Stack<Integer> discardPile = new Stack<>();

    private boolean invalidAction = false;
    private boolean battleOver = false;
    private int battleRounds = 1;


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
        return this.battleOver;
    }

    
    public void setBattle_over(boolean game_over) {
        this.battleOver = game_over;
    }


    public int getBattle_rounds() {
        return this.battleRounds;
    }


    public void addBattle_round() {
        this.battleRounds += 1;
    }


    public Card[] getPossible_cards() {
        return possible_cards;
    }


    public void setPossible_cards(Card[] possible_cards) {
        this.possible_cards = possible_cards;
    }


    public Stack<Integer> getBuy_pile() {
        return this.buyPile;
    }


        public boolean isAction_invalid() {
        return invalidAction;
    }


    public void setInvalidAction(boolean card_failed_use) {
        this.invalidAction = card_failed_use;
    }


    public void generateRandomBuyPile() {
        Random generator = new Random();
        for (int i = 0; i < this.buyPileSize; i++) {
            int random_card_index = generator.nextInt(0, possible_cards.length);
            this.buyPile.push(random_card_index);  
        }
    }


    public Stack<Integer> getDiscardPile() {
        return this.discardPile;
    }


    public void discardCard(int position) {
        int card = playerHand.remove(position);
        this.discardPile.push(card);
    }


    public void discardHand() {
        this.discardPile.addAll(playerHand);
        this.playerHand.clear();
    }


    public void buyCard() {
        if (!this.buyPile.isEmpty()) {
            this.playerHand.add(this.buyPile.pop());
        }
    }


    public void buyRoundCards() {
        for (int i = 0; i < handSize; i++) {
            buyCard();
        }
    }


    public void resetBuyPile() {
        this.buyPile.addAll(discardPile);
        Collections.shuffle(this.buyPile);
        this.discardPile.clear();
    }


    public ArrayList<Integer> getPlayerHand() {
        return this.playerHand;
    }
}