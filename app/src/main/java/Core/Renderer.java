package Core;
import java.io.IOException;
import java.util.ArrayList;

import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.screen.Screen;

import Cards.Card;
import Effects.StatusEffect;
import Entities.Enemy;
import Entities.EnemyAction;
import Map.Map;
import Map.Room;


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

    private final int VERTICAL_BAR_SIZE = RendererConfig.VERTICAL_BAR_SIZE;
    private final int[] VERTICAL_BAR_POSITION = {1, VERTICAL_BAR_SIZE};

    private final int[] HP_BAR_POSITION = RendererConfig.HP_BAR_POSITION;
    private final int[] SHIELD_COUNTER_POSITION = RendererConfig.SHIELD_COUNTER_POSITION; 
    private final int[] ENERGY_BAR_POSITION = RendererConfig.ENERGY_BAR_POSITION;
    private final int[] NO_ENERGY_WARNING_POSITION = RendererConfig.NO_ENERGY_WARNING_POSITION;

    private final int DECK_TEXT_LINE = RendererConfig.DECK_TEXT_LINE;
    private final int[] BUY_PILE_POSITION = RendererConfig.BUY_PILE_POSITION;
    private final int[] DISCARD_PILE_POSITION = RendererConfig.DISCARD_PILE_POSITION;

    private final int[] HERO_POSITION = RendererConfig.HERO_POSITION;
    private final int[] ENEMIES_POSITION = RendererConfig.ENEMY_POSITION;
    
    
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
        textGraphics.drawLine(1, HEIGHT - 3, WIDTH  - 2, HEIGHT - 3, '═');
        textGraphics.drawLine(0, 1, 0, HEIGHT - 2, '║');
        textGraphics.drawLine(WIDTH - 1, 1, WIDTH - 1, HEIGHT - 2, '║');

        textGraphics.putString(0, 0, "╔");
        textGraphics.putString(WIDTH - 1, 0, "╗");
        textGraphics.putString(0, HEIGHT - 1, "╚");
        textGraphics.putString(WIDTH - 1, HEIGHT - 1, "╝");
    }


    /**
     * Generates a visual ASCII health bar string based on current and maximum health.
     * <p>
     * Example Output: {@code Vida: [█████░░░░░] 10/20}
     * </p>
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

        placeText(new int[] {line - 4, column}, "Cavaleiro (Jogador):");
        placeText(new int[] {line, column + 3}, heroSprite);
        for (int i = 0; i < effects.size(); i++) {
            StatusEffect effect = effects.get(i);
            placeText(new int[] {line + 10 + i, column}, effect.getName() + ": " + effect.getAmount() + " Acúmulos");
        }
    }


    /**
     * Renders the Enemy's ASCII sprite, health bar, shield, intended next action, and status effects.
     *
     * @param gameData The central data repository containing the enemy's state.
     * @param position The base coordinates {@code [line, column]} to anchor the drawing.
     */
    private void placeEnemySprite(Enemy enemy, int[] position, int index) {
        // Places enemy sprite with info
        int line = position[0];
        int column = position[1];

        // Getting Enemy Data
        String enemyName = enemy.getName();
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

        placeText(new int[] {line - 7, column - 2}, enemyName + " (" + (index + 1) + ")");
        placeText(new int[] {line - 5, column - 2}, ratHpBarSprite, TextColor.ANSI.RED_BRIGHT);
        if (enemyShield > 0) {
            placeText(new int[] {line - 5, column + 22}, shieldCounter, TextColor.ANSI.BLUE_BRIGHT);
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


    private void placeEnemies(GameData gameData, int position[]) {
        ArrayList<Enemy> enemies = gameData.getEnemies();
        for (int i = 0; i < enemies.size(); i++) {
            int[] cur_position = {position[0], position[1] + i * 29};
            placeEnemySprite(enemies.get(i), cur_position, i);
        }
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
        String vertical_bar = "║\n".repeat(HEIGHT - 4);


        

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


        placeText(new int[] {HEIGHT - 5, 1}, "=".repeat(VERTICAL_BAR_SIZE - 1));
        placeText(new int[] {HEIGHT - 4, 1}, "(P) Passar Turno (+3 energia)");
        if (gameData.isActionInvalid()) {
            placeText(new int[] {HEIGHT - 4, 38}, "AVISO: AÇÃO INVÁLIDA");
        }
    }


    /**
     * Orchestrates the rendering of all battle screen components.
     * Calls individual methods to compose the final frame.
     *
     * @param gameData The snapshot of the current game state to render.
     */
    private void placeBattleScreen(GameData gameData) {
        placeBorders();
        placeHeroSprite(gameData, HERO_POSITION);
        placeEnemies(gameData, ENEMIES_POSITION);
        placeHeroInfo(gameData);
        placeCardUI(gameData);
    }


/**
     * Tela do mapa simplificada seguindo a lógica de duplo laço for.
     */
    private void placeMapScreen(GameData gameData) {
        placeBorders();

        Map map = gameData.getMap();
        int currentFloor = gameData.getHeroCurrentFloor();
        int currentPosition = gameData.getHeroCurrentFloorPosition();
        
        ArrayList<Room> nextRooms = getPossibleNextRooms(map, currentFloor, currentPosition);
        int nextFloor = currentFloor + 1;

        int startX = 22 ;
        int startY = HEIGHT - 5;
        
        int choiceCounter = 1;
        
        for (int i = map.getHeight() - 1; i >= 0; i--) {
            for (int j = 0; j < map.getMaxWidth(); j++) {
                Room room = map.getFloors()[i][j];
                
                String roomSymbol = "(R)";
                TextColor roomColor = TextColor.ANSI.WHITE;

                if (room != null) {
                    if (i == nextFloor && nextRooms.contains(room)) {
                        roomSymbol = "(" + choiceCounter + ")";
                        choiceCounter++;
                        roomColor = TextColor.ANSI.YELLOW_BRIGHT;
                    } 
                    else if (i == currentFloor && j == currentPosition) {
                        roomSymbol = "(A)"; 
                        roomColor = TextColor.ANSI.BLUE_BRIGHT;
                    } 
                    else if (room.isVisited()) {
                        roomSymbol = "(V)";
                        roomColor = TextColor.ANSI.GREEN;
                    }
                }
                placeRoomAndPaths(map, room, i, j, startX, startY, roomSymbol, roomColor);
            }
        }
        placeBossRoom(currentFloor);
    }

    
    private void placeRoomAndPaths(Map map, Room room, int floor, int floorPosition, int startX, int startY, String roomSymbol, TextColor roomColor) {
        int lineY = startY - (floor * 2);
        int columnX = startX + (floorPosition * 6);

        if (room == null) {
            return;
        }

        placeText(new int[]{lineY, columnX + 1}, roomSymbol, roomColor);

        int pathY = lineY - 1;
        Room[][] floors = map.getFloors();

        if (room.hasCenterChild()) {
            Room child = floors[floor + 1][floorPosition];
            TextColor color = isPathVisited(room, child) ? TextColor.ANSI.GREEN : TextColor.ANSI.WHITE;
            placeText(new int[]{pathY, columnX + 2}, "|", color);
        }
        if (room.hasRightChild()) {
            Room child = floors[floor + 1][floorPosition + 1];
            TextColor color = isPathVisited(room, child) ? TextColor.ANSI.GREEN : TextColor.ANSI.WHITE;
            placeText(new int[]{pathY, columnX + 5}, "/", color);
        }
        if (room.hasLeftChild()) {
            Room child = floors[floor + 1][floorPosition - 1];
            TextColor color = isPathVisited(room, child) ? TextColor.ANSI.GREEN : TextColor.ANSI.WHITE;
            
            placeText(new int[]{pathY, columnX - 1}, "\\", color);
        }
        }

    private void placeBossRoom(int currentFloor) {
        TextColor color = currentFloor == 6 ? TextColor.ANSI.GREEN : TextColor.ANSI.WHITE;
        placeText(new int[] {2, WIDTH / 2}, "[BOSS]", color);
    }


    private ArrayList<Room> getPossibleNextRooms(Map map, int currentFloor, int currentPosition) {
        ArrayList<Room> nextRooms = new ArrayList<>();

        if (currentFloor == -1) {
            nextRooms.addAll(map.getStartRooms());
            return nextRooms;
        }

        Room currentRoom = map.getFloors()[currentFloor][currentPosition];
        if (currentRoom != null) {
            for (Room next: currentRoom.getNextRooms()) {
                if (next != null) {
                    nextRooms.add(next); 
                }
            }
        }
        
        return nextRooms;
    }


    private boolean isPathVisited(Room fromRoom, Room toRoom) {
        return (fromRoom != null && fromRoom.isVisited()) && (toRoom != null && toRoom.isVisited());
    }


    private void placeContextBar(GameState state, GameData data) {
        if (null != state) switch (state) {
            case BATTLE_CARD -> placeText(new int [] {HEIGHT - 2, WIDTH / 2 - 24}, "------------ ESPERANDO AÇÃO DO JOGADOR ------------");
            case BATTLE_TARGETING -> placeText(new int [] {HEIGHT - 2, WIDTH / 2 - 24}, "----------------- ESCOLHA O ALVO ------------------");
            case MAP -> {
                if (data.getHeroCurrentFloor() == 6) {
                     placeText(new int [] {HEIGHT - 2, WIDTH / 2 - 26}, "----- APERTE QUALQUER TECLA PARA ENTRAR NA SALA DO BOSS FINAL -----");
                }
                else {
                    placeText(new int [] {HEIGHT - 2, WIDTH / 2 - 24}, "----------------- ESCOLHA A SALA ------------------");
                }
            }
            default -> {
            }
        }
    }


    /**
     * Resolves the endgame state and prints the corresponding victory or defeat message
     * to the standard console out (System.out) after the Lanterna screen closes.
     *
     * @param gameData The game state containing the final health metrics.
     */
    private void printEndScreen(GameData gameData) {
        if (gameData.isGameClosed()) {
            System.out.println("\n--- JOGO ENCERRADO ---\n");
        }
        else if (!gameData.getHero().isAlive()) {
            System.out.println("\n--- VOCÊ FOI DERROTADO... ---\n");
        }
        else if (gameData.getEnemies().isEmpty()) {
            System.out.println("\n--- VOCÊ GANHOU! ---\n");
        }
    }


    /**
     * The main rendering loop call. Clears the previous frame, builds the new UI 
     * based on the latest state, pushes it to the display buffer, and checks for game end.
     *
     * @param gameData The updated game state to be drawn on the screen.
     */
    public void render(GameData gameData, GameState state) {
        if (gameData.isGameOver()) {
            try {
                screen.close();
                printEndScreen(gameData);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return;
        }

        try {
            screen.clear();
            screen.setCursorPosition(null);
            
            
            switch (state) {
                case GameState.MAP -> placeMapScreen(gameData);
                default -> placeBattleScreen(gameData);
            }
            
            placeContextBar(state, gameData);
            screen.refresh();
        }
        catch (IOException e) {
            System.err.println("(Renderer) Erro na função render - " + e);
        }
    }
}