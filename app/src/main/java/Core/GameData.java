package Core;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.Stack;

import Cards.Card;
import Cards.DamageCard;
import Cards.EnergyRegenCard;
import Cards.ManaCard;
import Cards.PoisonCard;
import Cards.ShieldCard;
import Cards.StrengthCard;
import Entities.Enemy;
import Entities.Hero;
import Map.Map;

/**
 * Repositório central para os dados e estado da partida de "Slay the Tuff Rat".
 * <p>
 * Esta classe age como o componente "Model" puro na arquitetura do jogo. Ela armazena 
 * as entidades principais (Herói e Inimigos), o estado de fim de batalha, e gerencia toda 
 * a lógica estrutural do DeckBuilder (pilha de compra, mão do jogador e pilha de descarte).
 * </p>
 * <p>
 * <b>Nota de Arquitetura:</b> Para otimização de memória, as pilhas e a mão do jogador 
 * não armazenam objetos do tipo {@code Card}. Em vez disso, armazenam números Inteiros 
 * que representam os índices do array {@code possible_cards} (Abordagem do padrão Flyweight).
 * </p>
 */
public final class GameData {
    private final Hero hero = new Hero("Hero", 30, 3, 0);
    private final Map map = new Map(7, 15, 6);
    private ArrayList<Enemy> enemies = new ArrayList<>();

    private final DamageCard lightAttack = new DamageCard("Ataque Leve", 1, 3);
    private final DamageCard heavyAttack = new DamageCard("Ataque Pesado", 2, 6);
    private final DamageCard superHeavyAttack = new DamageCard("Bomba Nuclear", 3, 9);
    private final ShieldCard partialDefense = new ShieldCard("Defesa Parcial", 1, 2);
    private final ShieldCard totalDefense = new ShieldCard("Defesa Total", 2, 5);
    private final PoisonCard poison = new PoisonCard("Dardo Venenoso", 2, 3);
    private final StrengthCard strength = new StrengthCard("Focar Ataque", 1, 2);
    private final EnergyRegenCard energyRegen = new EnergyRegenCard("Ganhar Energia", 0, 1);
    private final ManaCard manaCard = new ManaCard("Usar Mana", 3, 2);

    private final int buyPileSize = 20;
    private final int handSize = 5;

    private int heroCurrentFloor;
    private int heroCurrentFloorPosition;

    private ArrayList<Card> obtainableCards = new ArrayList<>(List.of(superHeavyAttack, totalDefense, energyRegen, manaCard));
    private ArrayList<Card> currentCards = new ArrayList<>(List.of(lightAttack, heavyAttack, partialDefense, poison, strength));

    private final ArrayList<Integer> playerHand = new ArrayList<>();
    private final Stack<Integer> buyPile = new Stack<>();
    private final Stack<Integer> discardPile = new Stack<>();

    private boolean invalidAction = false;
    private boolean battleOver = false;
    private boolean gameOver = false;
    private boolean gameClosed = false;
    private int battleRounds = 1;

    /**
     * Construtor padrão do gerenciador de dados.
     * Ao iniciar a batalha, gera os inimigos, o deck inicial aleatório e compra 
     * a primeira mão de cartas para o herói.
     */
    public GameData() {
        this.heroCurrentFloor = -1;
        generateRandomBuyPile();
        buyRoundCards();
    }

    // ... Getters e Setters básicos
    public Hero getHero() { return this.hero; }
    
    public ArrayList<Integer> getPlayerHand() { return this.playerHand; }
    public ArrayList<Enemy> getEnemies() { return enemies; }
    public void setEnemies(ArrayList<Enemy> enemies) { this.enemies = enemies; }

    public boolean isBattleOver() { return this.battleOver; }
    public void setBattleOver(boolean gameOver) { this.battleOver = gameOver; }

    public int getBattleRounds() { return this.battleRounds; }
    public void addBattleRound() { this.battleRounds += 1; }

    public ArrayList<Card> getCurrentCards() { return currentCards; }
    public void setCurrentCards(ArrayList<Card> possible_cards) { this.currentCards = possible_cards; }

    public Stack<Integer> getBuyPile() { return this.buyPile; }
    public Stack<Integer> getDiscardPile() { return this.discardPile; }

    public int getHeroCurrentFloor() { return heroCurrentFloor; }
    public void setHeroCurrentFloor(int heroCurrentFloor) { this.heroCurrentFloor = heroCurrentFloor; }

    public int getHeroCurrentFloorPosition() { return heroCurrentFloorPosition; }
    public void setHeroCurrentFloorPosition(int heroCurrentFloorPosition) { this.heroCurrentFloorPosition = heroCurrentFloorPosition; }

    public Map getMap() { return this.map; }
    
    
    /** @return {@code true} se o jogador tentou realizar uma ação bloqueada/inválida. */
    public boolean isActionInvalid() { return invalidAction; }
    public void setInvalidAction(boolean card_failed_use) { this.invalidAction = card_failed_use; }

    /**
     * Gera o baralho inicial (Pilha de Compra) sorteando cartas do catálogo.
     */
    public void generateRandomBuyPile() {
        Random generator = new Random();
        for (int i = 0; i < this.buyPileSize; i++) {
            int random_card_index = generator.nextInt(0, currentCards.size());
            this.buyPile.push(random_card_index);  
        }
    }

    /**
     * Descarta uma carta específica da mão para a pilha de descarte.
     * @param position O índice da carta na lista da mão do jogador.
     */
    public void discardCard(int position) {
        int card = playerHand.remove(position);
        this.discardPile.push(card);
    }

    /**
     * Joga todas as cartas restantes na mão do jogador na pilha de descarte.
     */
    public void discardHand() {
        this.discardPile.addAll(playerHand);
        this.playerHand.clear();
    }

    /**
     * Puxa a carta do topo da pilha de compra e a coloca na mão do jogador.
     */
    public void buyCard() {
        if (!this.buyPile.isEmpty()) {
            this.playerHand.add(this.buyPile.pop());
        }
    }

    /**
     * Compra cartas consecutivamente até encher a mão para o início do turno.
     */
    public void buyRoundCards() {
        for (int i = 0; i < handSize; i++) {
            buyCard();
        }
    }

    /**
     * Pega todas as cartas da pilha de descarte, embaralha e as devolve
     * para a pilha de compra.
     */
    public void resetBuyPile() {
        this.buyPile.addAll(discardPile);
        Collections.shuffle(this.buyPile);
        this.discardPile.clear();
    }

    public boolean isGameOver() {
        return gameOver;
    }

    public void setGameOver(boolean gameOver) {
        this.gameOver = gameOver;
    }


    public boolean isGameClosed() {
        return gameClosed;
    }

    public void setGameClosed(boolean gameClosed) {
        this.gameClosed = gameClosed;
    }

    public ArrayList<Card> getObtainableCards() {
        return obtainableCards;
    }

    public void setObtainableCards(ArrayList<Card> obtainableCards) {
        this.obtainableCards = obtainableCards;
    }
}