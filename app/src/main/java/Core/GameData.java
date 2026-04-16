package Core;
import java.util.ArrayList;
import java.util.Collections;
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
    private final Hero hero = new Hero("Hero", 20, 3, 0);
    private final ArrayList<Enemy> enemies = new ArrayList<>();

    // Catálogo global de cartas existentes no jogo
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

    /** Array com todas as instâncias únicas de cartas (Flyweight). */
    private Card[] possible_cards = {lightAttack, heavyAttack, superHeavyAttack, partialDefense, totalDefense, poison, strength, energyRegen, manaCard};

    private final ArrayList<Integer> playerHand = new ArrayList<>();
    private final Stack<Integer> buyPile = new Stack<>();
    private final Stack<Integer> discardPile = new Stack<>();

    private boolean invalidAction = false;
    private boolean battleOver = false;
    private int battleRounds = 1;

    /**
     * Construtor padrão do gerenciador de dados.
     * Ao iniciar a batalha, gera os inimigos, o deck inicial aleatório e compra 
     * a primeira mão de cartas para o herói.
     */
    public GameData() {
        this.heroCurrentFloor = 0;
        this.enemies.add(new Enemy("Thug Spider", 20, 0, new int[] {3, 7}));
        this.enemies.add(new Enemy("Tuff Spider", 15, 0, new int[] {3, 6}));
        generateRandomBuyPile();
        buyRoundCards();
    }

    // ... Getters e Setters básicos
    public Hero getHero() { return this.hero; }
    
    public ArrayList<Integer> getPlayerHand() { return this.playerHand; }
    public ArrayList<Enemy> getEnemies() { return enemies; }

    public boolean isBattleOver() { return this.battleOver; }
    public void setBattleOver(boolean gameOver) { this.battleOver = gameOver; }

    public int getBattleRounds() { return this.battleRounds; }
    public void addBattleRound() { this.battleRounds += 1; }

    public Card[] getPossibleCards() { return possible_cards; }
    public void setPossibleCards(Card[] possible_cards) { this.possible_cards = possible_cards; }

    public Stack<Integer> getBuyPile() { return this.buyPile; }
    public Stack<Integer> getDiscardPile() { return this.discardPile; }

    public int getHeroCurrentFloor() { return heroCurrentFloor; }
    public void setHeroCurrentFloor(int heroCurrentFloor) { this.heroCurrentFloor = heroCurrentFloor; }

    public int getHeroCurrentFloorPosition() { return heroCurrentFloorPosition; }
    public void setHeroCurrentFloorPosition(int heroCurrentFloorPosition) { this.heroCurrentFloorPosition = heroCurrentFloorPosition; }
    
    /** @return {@code true} se o jogador tentou realizar uma ação bloqueada/inválida. */
    public boolean isActionInvalid() { return invalidAction; }
    public void setInvalidAction(boolean card_failed_use) { this.invalidAction = card_failed_use; }

    /**
     * Gera o baralho inicial (Pilha de Compra) sorteando cartas do catálogo.
     */
    public void generateRandomBuyPile() {
        Random generator = new Random();
        for (int i = 0; i < this.buyPileSize; i++) {
            int random_card_index = generator.nextInt(0, possible_cards.length);
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
}