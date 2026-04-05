import java.io.IOException;
import java.util.ArrayList;

import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.screen.Screen;


/**
 * The rendering engine for the game's user interface.
 * <p>
 * This class acts as the "View" component in the game's architecture. It is strictly 
 * responsible for reading the state from {@link GameData} and drawing the corresponding 
 * visual elements (ASCII art, UI borders, health bars, and text) onto the terminal screen.
 * </p>
 * <p>
 * <b>Layout System:</b> Positions are generally stored as integer arrays in the 
 * format {@code [line/y, column/x]} to map onto the Lanterna terminal grid.
 * </p>
 */
public class Renderer {
    private final TerminalManager terminalManager = TerminalManager.getInstance();

    // Getting Window constants from TerminalManager
    private final int HEIGHT = terminalManager.getHeight();
    private final int WIDTH = terminalManager.getWidth();

    // ==========================================
    // UI LAYOUT COORDINATES [Line, Column]
    // ==========================================
    
    /** Height of the vertical separator line dividing the UI (Cards) from the Battle Screen. */
    private final int VERTICAL_BAR_SIZE = 35;
    private final int[] VERTICAL_BAR_POSITION = {1, VERTICAL_BAR_SIZE};

    private final int[] HP_BAR_POSITION = {4, 38};
    private final int[] SHIELD_COUNTER_POSITION = {4, 63}; 
    private final int[] ENERGY_BAR_POSITION = {1, 2};
    private final int[] NO_ENERGY_WARNING_POSITION = {1, 15};

    private final int[] BUY_PILE_POSITION = {3, 2};
    private final int[] DISCARD_PILE_POSITION = {4, 2};

    private final int[] HERO_POSITION = {6, 38};
    private final int[] ENEMY_POSITION = {9, 110};
    
    
    // ==========================================
    // LANTERNA GRAPHICS CONTEXT
    // ==========================================
    private final Screen screen = terminalManager.getScreen();
    private final TextGraphics textGraphics = terminalManager.getTextGraphics();


    /**
     * Prints a text string at a specific coordinate on the screen.
     * Supports multiline strings by splitting the text at {@code \n} and 
     * incrementing the line (Y-axis) for each subsequent substring.
     *
     * @param position An array containing {@code [line, column]}.
     * @param text     The text to be drawn.
     */
    private void placeText(int[] position, String text) {
        int line = position[0];
        int column = position[1];

        String[] lines = text.split("\n");

        for (int i = 0; i < lines.length; i++) {
            textGraphics.putString(column, line + i, lines[i]);
        }
    }


    /**
     * Prints a text string at a specific coordinate using a custom color.
     * After drawing, resets the foreground color back to standard White to prevent color bleeding.
     *
     * @param position An array containing {@code [line, column]}.
     * @param text     The text to be drawn.
     * @param color    The Lanterna {@link TextColor} to apply to the text.
     */
    private void placeText(int[] position, String text, TextColor color) {
        textGraphics.setForegroundColor(color);
        placeText(position, text);
        textGraphics.setForegroundColor(TextColor.ANSI.WHITE);
    }


    /**
     * Draws the outer decorative borders of the game window.
     * Uses double-line ASCII characters (═, ║, ╔, ╗, ╚, ╝).
     */
    private void placeBorders() {
        textGraphics.drawLine(1, 0, WIDTH  - 2, 0, '═');
        textGraphics.drawLine(1, HEIGHT - 1, WIDTH  - 2, HEIGHT - 1, '═');
        textGraphics.drawLine(0, 1, 0, HEIGHT - 2, '║');
        textGraphics.drawLine(WIDTH - 1, 1, WIDTH - 1, HEIGHT - 2, '║');

        textGraphics.putString(0, 0, "╔");
        textGraphics.putString(WIDTH - 1, 0, "╗");
        textGraphics.putString(0, HEIGHT - 1, "╚");
        textGraphics.putString(WIDTH - 1, HEIGHT - 1, "╝");
    }


    /**
     * Generates a visual ASCII health bar string based on current and maximum health.
     *
     * @param cur_hp Current health points.
     * @param max_hp Maximum health points.
     * @return A formatted string representing the health bar.
     */
    private String createHpBar(int cur_hp, int max_hp) {
        int white_health_bars = Math.round(((float) cur_hp / max_hp) * 10);
        String hp_bar = "Vida: [" + "█".repeat(white_health_bars) + "░".repeat(10-white_health_bars) + "]" + " " + cur_hp + "/" + max_hp;
        return hp_bar;
    }


    /**
     * Renders the Hero's ASCII sprite, title, and current active status effects.
     *
     * @param gameData The central data repository containing the hero's state.
     * @param position The base coordinates {@code [line, column]} to anchor the drawing.
     */
    private void placeHeroSprite(GameData gameData, int[] position) {
        int line = position[0];
        int column = position[1];

        String heroSprite = gameData.getHero().getHero_sprite();
        ArrayList<StatusEffect> effects = gameData.getHero().getEffects();

        placeText(new int[] {line - 4, column}, "Cavaleiro (Player):");
        placeText(new int[] {line, column + 3}, heroSprite);
        for (int i = 0; i < effects.size(); i++) {
            StatusEffect effect = effects.get(i);
            placeText(new int[] {line + 10 + i, column - 1}, effect.getName() + ": " + effect.getAmount() + " Acúmulos");
        }
    }


    /**
     * Renders the Enemy's ASCII sprite, health bar, shield, intended next action, and status effects.
     *
     * @param enemy The specific enemy instance to extract stats from.
     * @param position The base coordinates {@code [line, column]} to anchor the drawing.
     * @param index The array position of this enemy.
     */
    private void placeEnemySprite(Enemy enemy, int[] position, int index) {
        // Places enemy sprite with info
        int line = position[0];
        int column = position[1];

        // Getting Enemy Data
        int enemyLife = enemy.getLife();
        int enemyShield = enemy.getShield();
        int enemyMaxLife = enemy.getMaxLife();
        int enemyRoundDamage = enemy.getRoundDamage();
        int enemyShieldtoAdd = enemy.getShieldToAdd();
        int enemyPoisonAmount = enemy.getPoisonAmount();

        EnemyAction enemyAction = enemy.getEnemyAction();
        String shieldCounter = "(+" + enemyShield + ")";
        ArrayList<StatusEffect> effects = enemy.getEffects();

        String ratSprite = enemy.getEnemySprite();
        String ratHpBarSprite = createHpBar(enemyLife, enemyMaxLife);

        // Coloquei o índice (index + 1) do lado do nome pro jogador saber qual número apertar pra mirar a carta nele
        placeText(new int[] {line - 7, column - 2}, enemy.getName() + " (" + (index + 1) + "):");
        placeText(new int[] {line - 5, column - 2}, ratHpBarSprite, TextColor.ANSI.RED_BRIGHT);
        
        if (enemyShield > 0) {
            placeText(new int[] {line - 5, column + 23}, shieldCounter, TextColor.ANSI.BLUE_BRIGHT);
        }

        switch (enemyAction) {
            case ATTACK -> {
                placeText(new int[] {line - 4, column - 2}, String.format("Turno Seguinte: %d DMG", enemyRoundDamage));
            }
            case DEFEND -> {
                placeText(new int[] {line - 4, column - 2}, String.format("Turno Seguinte: %d SHD", enemyShieldtoAdd));
            }
            case POISON -> {
                placeText(new int[] {line - 4, column - 2}, String.format("Turno Seguinte: %d PSN", enemyPoisonAmount));
            }
        }
        for (int i = 0; i < effects.size(); i++) {
            StatusEffect effect = effects.get(i);
            placeText(new int[] {line + 7 + i, column - 2}, effect.getName() + ": " + effect.getAmount() + " Acúmulos");
        }
        placeText(new int[] {line + 2, column}, ratSprite);
    }


    /**
     * Renders the top HUD elements for the hero, including health, shield, energy, 
     * and the size of the draw/discard piles. Also draws the vertical UI separator.
     *
     * @param gameData The data model to extract hero statistics from.
     */
    private void placeHeroInfo(GameData gameData) {
        // Getting Hero Data
        int hero_life = gameData.getHero().getLife();
        int hero_max_life = gameData.getHero().getMaxLife();
        int hero_shield = gameData.getHero().getShield();
        int hero_energy = gameData.getHero().getEnergy();
        int buy_pile_size = gameData.getBuyPile().size();
        int discard_pile_size = gameData.getDiscardPile().size();
        
        String hero_hp_bar_sprite = createHpBar(hero_life, hero_max_life);
        String shield_counter = "(+" + hero_shield + ")";
        String energy_bar_sprite = "Energia: " + "■ ".repeat(hero_energy) + hero_energy + "/3";
        String vertical_bar = "║\n".repeat(HEIGHT - 2);


        placeText(HP_BAR_POSITION, hero_hp_bar_sprite, TextColor.ANSI.RED_BRIGHT);

        if (hero_shield > 0) {
            placeText(SHIELD_COUNTER_POSITION, shield_counter, TextColor.ANSI.BLUE_BRIGHT);
        }

        placeText(ENERGY_BAR_POSITION, energy_bar_sprite, TextColor.ANSI.YELLOW_BRIGHT);


        if (hero_energy == 0) { 
            placeText(NO_ENERGY_WARNING_POSITION, "| Sem energia!");
        }

        placeText(BUY_PILE_POSITION, "Pilha de Compra: x" + buy_pile_size);
        placeText(DISCARD_PILE_POSITION, "Pilha de Descarte: x" + discard_pile_size);
        placeText(VERTICAL_BAR_POSITION, vertical_bar);
    }


    /**
     * Renders an individual card entry within the player's hand UI.
     *
     * @param position The coordinates to place the card text.
     * @param card     The card instance to extract the description from.
     * @param index    The integer index used for user input (e.g., pressing "1" for the first card).
     */
    private void placeCard(int[] position, Card card, int index) {
        placeText(new int[] {position[0], position[1]}, "(" + (index + 1) + ") " + card.getDescription());
        if (index < 4) {
            placeText(new int[] {position[0] + 1, position[1]}, "-".repeat(VERTICAL_BAR_SIZE - 1));
        }
    }

    
    /**
     * Renders the left sidebar containing the player's current hand of cards,
     * available actions (Skip Turn), and system warnings (Invalid Action).
     *
     * @param gameData The data model containing the player's hand array.
     */
    private void placeCardUI(GameData gameData) {
        int hand_size = gameData.getPlayerHand().size();

        int DECK_TEXT_LINE = 6;
        
        placeText(new int[] {DECK_TEXT_LINE - 1, 1}, "=".repeat(VERTICAL_BAR_SIZE - 1));
        placeText(new int[] {DECK_TEXT_LINE, 1}, "               Mão:");
        placeText(new int[] {DECK_TEXT_LINE + 1, 1}, "=".repeat(VERTICAL_BAR_SIZE - 1));
        
        int start_line = 8;
        int line = 0;
        for (int i = 0; i < hand_size; i++) {

            int card_index = gameData.getPlayerHand().get(i);
            Card card = gameData.getPossibleCards()[card_index];
            placeCard(new int[] {line + start_line, 1}, card, i);
            line += 2;
        }


        placeText(new int[] {HEIGHT - 3, 1}, "=".repeat(VERTICAL_BAR_SIZE - 1));
        placeText(new int[] {HEIGHT - 2, 1}, "(P) Passar Turno (+3 energia)");
        if (gameData.isActionInvalid()) {
            placeText(new int[] {HEIGHT - 2, 37}, "AVISO: AÇÃO INVÁLIDA");
        }
    }


    /**
     * Orchestrates the rendering of all battle screen components.
     * Calls individual methods to compose the final frame.
     *
     * @param gameData The snapshot of the current game state to render.
     */
    public void placeBattleScreen(GameData gameData) {
        placeHeroSprite(gameData, HERO_POSITION);
        
        // Pega a lista de inimigos vivos pra desenhar
        ArrayList<Enemy> enemies = gameData.getEnemies();
        int baseCol = ENEMY_POSITION[1];
        
        // Faz um loop por todos os inimigos. 
        // O offset 'baseCol - (i * 25)' serve pra empurrar cada monstro um pouco mais pra esquerda, 
        // senão os desenhos ficariam sobrepostos na mesma coluna.
        for (int i = 0; i < enemies.size(); i++) {
            int[] currentPos = {ENEMY_POSITION[0], baseCol - (i * 25)};
            placeEnemySprite(enemies.get(i), currentPos, i);
        }

        placeHeroInfo(gameData);
        placeCardUI(gameData);
    }


    /**
     * Resolves the endgame state and prints the corresponding victory or defeat message
     * to the standard console out (System.out) after the Lanterna screen closes.
     *
     * @param gameData The game state containing the final health metrics.
     */
    public void drawEndScreen(GameData gameData) {
        // Directly prints end screen
        if (!gameData.getHero().isAlive()) {
            System.out.println("\n--- VOCÊ FOI DERROTADO... ---\n");
        }
        // Atualizei a condição de vitória: agora o jogo acaba quando a lista de inimigos ficar vazia
        else if (gameData.getEnemies().isEmpty()) {
            System.out.println("\n--- VOCÊ GANHOU! ---\n");
        }
        else {
            System.out.println("\n--- JOGO ENCERRADO ---\n");
        }
    }


    /**
     * The main rendering loop call. Clears the previous frame, builds the new UI 
     * based on the latest state, pushes it to the display buffer, and checks for game end.
     *
     * @param gameData The updated game state to be drawn on the screen.
     */
    public void render(GameData gameData) {
        try {
            screen.clear();
            screen.setCursorPosition(null);
            placeBorders();
            placeBattleScreen(gameData);
            screen.refresh();
            if (gameData.isBattleOver()) {
                drawEndScreen(gameData);
                screen.close();
            }  
        }
        catch (IOException e) {
        }
    }
}