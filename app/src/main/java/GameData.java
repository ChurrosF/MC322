import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.util.Stack;

/**
 * Central repository for the data and state of the game "Slay the Tuff Rat".
 * <p>
 * This class acts as the "Model" in the game's architecture, storing the 
 * main entities (Hero and Enemy), the battle state, and managing all the 
 * structural logic of the DeckBuilder (draw pile, player's hand, and discard pile).
 * </p>
 * <p>
 * <b>Architecture Note:</b> For memory optimization, the piles and the player's hand 
 * do not store objects of type {@code Card}. Instead, they store Integer numbers 
 * that represent the indices of the {@code possible_cards} array (Flyweight pattern approach).
 * </p>
 */
public final class GameData {
    private Hero hero = new Hero("Hero", 40, 3, 0);
    int[] enemyDamageRange = {3, 7};
    // Lista de inimigos
    private ArrayList<Enemy> enemies = new ArrayList<>();
    private DamageCard strike = new DamageCard("Golpe", 1, 3);
    private ShieldCard defend = new ShieldCard("Escudo", 1, 2);
    private PoisonCard poison = new PoisonCard("Veneno", 2);
    private StrengthCard strength = new StrengthCard("Força", 1);


    private int buyPileSize = 20;
    private int handSize = 5;
    private Card[] possible_cards = {strike, defend, poison, strength};
    private ArrayList<Integer> playerHand = new ArrayList<>();
    private Stack<Integer> buyPile = new Stack<>();
    private Stack<Integer> discardPile = new Stack<>();

    
    private boolean invalidAction = false;
    private boolean battleOver = false;
    private int battleRounds = 1;


    /**
     * Default constructor for the data manager.
     * Upon starting the battle, it generates the random initial deck and draws 
     * the first hand of cards for the hero.
     */
    public GameData() {

        enemies.add(new Enemy("Thug Rat", 20, 0, enemyDamageRange));
        enemies.add(new Enemy("Rat-a-tuili", 15, 0, enemyDamageRange));
        generateRandomBuyPile();
        buyRoundCards();
    }



    public Hero getHero() {
        return this.hero;
    }


    public ArrayList<Enemy> getEnemies() {
        return this.enemies;
    }
    
    public DamageCard getDamageCard() {
        return this.strike;
    }


    public ShieldCard getShieldCard() {
        return this.defend;
    }

    
    public boolean isBattleOver() {
        return this.battleOver;
    }

    
    public void setBattleOver(boolean gameOver) {
        this.battleOver = gameOver;
    }


    public int getBattleRounds() {
        return this.battleRounds;
    }


    public void addBattleRound() {
        this.battleRounds += 1;
    }


    public Card[] getPossibleCards() {
        return possible_cards;
    }


    public void setPossibleCards(Card[] possible_cards) {
        this.possible_cards = possible_cards;
    }


    public Stack<Integer> getBuyPile() {
        return this.buyPile;
    }


        public boolean isActionInvalid() {
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