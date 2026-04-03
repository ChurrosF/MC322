import java.io.IOException;
import java.util.ArrayList;

import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.screen.Screen;

public class Renderer {
    private final TerminalManager terminalManager = TerminalManager.getInstance();

    // Getting Window constants from TerminalManager
    private final int HEIGHT = terminalManager.getHeight();
    private final int WIDTH = terminalManager.getWidth();

    // Renderer position and length constants
    private final int VERTICAL_BAR_SIZE = 35;
    private final int[] VERTICAL_BAR_POSITION = {1, VERTICAL_BAR_SIZE};

    private final int[] HP_BAR_POSITION = {4, 38};
    private final int[] SHIELD_COUNTER_POSITION = {4, 63}; 
    private final int[] ENERGY_BAR_POSITION = {1, 2};
    private final int[] NO_ENERGY_WARNING_POSITION = {1, 15};

    private final int[] BUY_PILE_POSITION = {3, 2};
    private final int[] DISCARD_PILE_POSITION = {4, 2};

    private final int[] HERO_POSITION = {6, 38};
    private final int[] ENEMY_POSITION = {9, 77};
    
    
    // Initializing screen and textGraphics from Lanterna Lib
    private final Screen screen = terminalManager.getScreen();
    private final TextGraphics textGraphics = terminalManager.getTextGraphics();


    private void place_text(int[] position, String text) {
        // Place any text (single or multi line) in a given position (line, column)
        int line = position[0];
        int column = position[1];

        String[] lines = text.split("\n");

        for (int i = 0; i < lines.length; i++) {
            textGraphics.putString(column, line + i, lines[i]);
        }
    }

    
    private void place_text(int[] position, String text, TextColor color) {
        textGraphics.setForegroundColor(color);
        place_text(position, text);
        textGraphics.setForegroundColor(TextColor.ANSI.WHITE);
    }


    private void placeBorders() {
        // Places borders on game screen (frame)

        textGraphics.drawLine(1, 0, WIDTH  - 2, 0, '═');
        textGraphics.drawLine(1, HEIGHT - 1, WIDTH  - 2, HEIGHT - 1, '═');
        textGraphics.drawLine(0, 1, 0, HEIGHT - 2, '║');
        textGraphics.drawLine(WIDTH - 1, 1, WIDTH - 1, HEIGHT - 2, '║');

        textGraphics.putString(0, 0, "╔");
        textGraphics.putString(WIDTH - 1, 0, "╗");
        textGraphics.putString(0, HEIGHT - 1, "╚");
        textGraphics.putString(WIDTH - 1, HEIGHT - 1, "╝");
    }


    private String create_hp_bar(int cur_hp, int max_hp) {
        int white_health_bars = Math.round(((float) cur_hp / max_hp) * 10);
        String hp_bar = "Vida: [" + "█".repeat(white_health_bars) + "░".repeat(10-white_health_bars) + "]" + " " + cur_hp + "/" + max_hp;
        return hp_bar;
    }


    private void placeHeroSprite(GameData gameData, int[] position) {
        int line = position[0];
        int column = position[1];

        String heroSprite = gameData.getHero().getHero_sprite();
        ArrayList<StatusEffect> effects = gameData.getHero().getEffects();

        place_text(new int[] {line - 4, column}, "Cavaleiro (Player):");
        place_text(new int[] {line, column + 3}, heroSprite);
        for (int i = 0; i < effects.size(); i++) {
            StatusEffect effect = effects.get(i);
            place_text(new int[] {line + 10 + i, column - 1}, effect.getName() + ": " + effect.getAmount() + " Acúmulos");
        }
    }


    private void placeEnemySprite(GameData gameData, int[] position) {
        // Places enemy sprite with info
        int line = position[0];
        int column = position[1];

        // Getting Enemy Data
        int enemyLife = gameData.getEnemy().getLife();
        int enemyShield = gameData.getEnemy().getShield();
        int enemyMaxLife = gameData.getEnemy().getMaxLife();
        int enemyRoundDamage = gameData.getEnemy().getRoundDamage();
        int enemyShieldtoAdd = gameData.getEnemy().getShieldToAdd();
        int enemyPoisonAmount = gameData.getEnemy().getPoisonAmount();

        EnemyAction enemyAction = gameData.getEnemy().getEnemyAction();
        String shieldCounter = "(+" + enemyShield + ")";
        ArrayList<StatusEffect> effects = gameData.getEnemy().getEffects();

        String ratSprite = gameData.getEnemy().getEnemySprite();

        String ratHpBarSprite = create_hp_bar(enemyLife, enemyMaxLife);

        place_text(new int[] {line - 7, column - 2}, "Aranha Maligna:");
        place_text(new int[] {line - 5, column - 2}, ratHpBarSprite, TextColor.ANSI.RED_BRIGHT);
        if (enemyShield > 0) {
            place_text(new int[] {line - 5, column + 23}, shieldCounter);
        }

        switch (enemyAction) {
            case ATTACK -> {
                place_text(new int[] {line - 4, column - 2}, String.format("Turno Seguinte: %d DMG", enemyRoundDamage));
            }
            case DEFEND -> {
                place_text(new int[] {line - 4, column - 2}, String.format("Turno Seguinte: %d SHD", enemyShieldtoAdd));
            }
            case POISON -> {
                place_text(new int[] {line - 4, column - 2}, String.format("Turno Seguinte: %d PSN", enemyPoisonAmount));
            }
        }
        for (int i = 0; i < effects.size(); i++) {
            StatusEffect effect = effects.get(i);
            place_text(new int[] {line + 7 + i, column - 2}, effect.getName() + ": " + effect.getAmount() + " Acúmulos");
        }
        place_text(new int[] {line + 2, column}, ratSprite);
    }


    private void placeHeroInfo(GameData gameData) {
        // Places hero info (hp, shield, energy) on frame
        
        // Getting Hero Data
        int hero_life = gameData.getHero().getLife();
        int hero_max_life = gameData.getHero().getMaxLife();
        int hero_shield = gameData.getHero().getShield();
        int hero_energy = gameData.getHero().getEnergy();
        int buy_pile_size = gameData.getBuy_pile().size();
        int discard_pile_size = gameData.getDiscardPile().size();
        
        String hero_hp_bar_sprite = create_hp_bar(hero_life, hero_max_life);
        String shield_counter = "(+" + hero_shield + ")";
        String energy_bar_sprite = "Energia: " + "■ ".repeat(hero_energy) + hero_energy + "/3";
        String vertical_bar = "║\n".repeat(HEIGHT - 2);


        

        place_text(HP_BAR_POSITION, hero_hp_bar_sprite, TextColor.ANSI.RED_BRIGHT);

        if (hero_shield > 0) {
            place_text(SHIELD_COUNTER_POSITION, shield_counter, TextColor.ANSI.BLUE_BRIGHT);
        }

        place_text(ENERGY_BAR_POSITION, energy_bar_sprite, TextColor.ANSI.YELLOW_BRIGHT);


        if (hero_energy == 0) { 
            place_text(NO_ENERGY_WARNING_POSITION, "| Sem energia!");
        }

        

        place_text(BUY_PILE_POSITION, "Pilha de Compra: x" + buy_pile_size);
        place_text(DISCARD_PILE_POSITION, "Pilha de Descarte: x" + discard_pile_size);
        place_text(VERTICAL_BAR_POSITION, vertical_bar);
    }


    private void place_card(int[] position, Card card, int index) {
        place_text(new int[] {position[0], position[1]}, "(" + (index + 1) + ") " + card.getDescription());
        if (index < 4) {
            place_text(new int[] {position[0] + 1, position[1]}, "-".repeat(VERTICAL_BAR_SIZE - 1));
        }
    }

    
    private void placeCardUI(GameData gameData) {
        int hand_size = gameData.getPlayerHand().size();

        int DECK_TEXT_LINE = 6;
        
        place_text(new int[] {DECK_TEXT_LINE - 1, 1}, "=".repeat(VERTICAL_BAR_SIZE - 1));
        place_text(new int[] {DECK_TEXT_LINE, 1}, "               Mão:");
        place_text(new int[] {DECK_TEXT_LINE + 1, 1}, "=".repeat(VERTICAL_BAR_SIZE - 1));
        
        int start_line = 8;
        int line = 0;
        for (int i = 0; i < hand_size; i++) {

            int card_index = gameData.getPlayerHand().get(i);
            Card card = gameData.getPossible_cards()[card_index];
            place_card(new int[] {line + start_line, 1}, card, i);
            line += 2;
        }


        place_text(new int[] {HEIGHT - 3, 1}, "=".repeat(VERTICAL_BAR_SIZE - 1));
        place_text(new int[] {HEIGHT - 2, 1}, "(P) Passar Turno (+3 energia)");
        if (gameData.isAction_invalid()) {
            place_text(new int[] {HEIGHT - 2, 37}, "AVISO: AÇÃO INVÁLIDA");
        }
    }


    public void placeBattleScreen(GameData gameData) {
        


        placeHeroSprite(gameData, HERO_POSITION);
        placeEnemySprite(gameData, ENEMY_POSITION);
        placeHeroInfo(gameData);
        placeCardUI(gameData);
    }


    public void drawEndScreen(GameData gameData) {
        // Directly prints end screen
        if (!gameData.getHero().isAlive()) {
            System.out.println("\n--- VOCÊ FOI DERROTADO... ---\n");
        }
        else if (!gameData.getEnemy().isAlive()) {
            System.out.println("\n--- VOCÊ GANHOU! ---\n");
        }
        else {
            System.out.println("\n--- JOGO ENCERRADO ---\n");
        }
    }


    public void render(GameData gameData) {
        // Places text (sprites, UI) on frame and prints it
        try {
            screen.clear();
            screen.setCursorPosition(null);
            placeBorders();
            placeBattleScreen(gameData);
            screen.refresh();
            if (gameData.isBattle_over()) {
                drawEndScreen(gameData);
                screen.close();
            }  
        }
        catch (IOException e) {
        }
    }
}