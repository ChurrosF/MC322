public class Renderer {
    private final int HEIGHT = 20;
    private final int WIDTH = 120;
    private final int VERTICAL_BAR_SIZE = 35;
    private StringBuilder frame = new StringBuilder();


    private void place_text(int[] position, String text) {
        // place any text (single or multi line) in a given position (line, column)
        int line = position[0];
        int row = position[1];
        int start_pos = line * (WIDTH + 1) + row;

        int count = 0;
        for (int i = start_pos; count < text.length(); i++) {
            
            char letter = text.charAt(count); 
            count++;
            

            if (letter == '\n') {
                i = (line + 1) * (WIDTH + 1) + position[1] - 1;
                line++;
            }
            else {
                this.frame.setCharAt(i, letter);
            }      
        }
    }


    private void place_borders() {
        // places borders on game screen (frame)
        for (int i = 0; i < this.HEIGHT; i++) {
            for (int j = 0; j < this.WIDTH; j++) {

                if (j == 0 && i == 0) {
                    this.frame.append("╔");
                }
                else if (j == 0 && i == this.HEIGHT - 1) {
                    this.frame.append("╚");
                }
                else if (j == this.WIDTH - 1 && i == 0) {
                    this.frame.append("╗");
                }
                else if (j == this.WIDTH - 1 && i ==  this.HEIGHT - 1) {
                    this.frame.append("╝");
                }
                else if (i == 0 || i == this.HEIGHT - 1) {
                    this.frame.append("═");
            }
                else if (j == 0 || j >= this.WIDTH - 1) {
                    this.frame.append("║");
                }
                else {
                    this.frame.append(" ");
                }
            }
            this.frame.append("\n");
        }
    }


    private String create_hp_bar(int cur_hp, int max_hp) {
        int white_health_bars = Math.round(((float) cur_hp / max_hp) * 10);
        String hp_bar = "Vida: [" + "█".repeat(white_health_bars) + "░".repeat(10-white_health_bars) + "]" + " " + cur_hp + "/" + max_hp;
        return hp_bar;
    }


    private void place_hero_sprite(GameData gameData, int[] position) {
        int line = position[0];
        int column = position[1];

        String hero_sprite = gameData.getHero().getHero_sprite();

        place_text(new int[] {line - 4, column}, "Cavaleiro (Player):");
        place_text(new int[] {line, column + 3}, hero_sprite);
    }


    private void place_enemy_sprite(GameData gameData, int[] position) {
        // places enemy sprite with info
        int line = position[0];
        int column = position[1];

        // Getting Enemy Data
        int enemy_life = gameData.getEnemy().getLife();
        int enemy_max_life = gameData.getEnemy().getMaxLife();
        int enemy_round_damage = gameData.getEnemy().getRound_damage();

        String rat_sprite = gameData.getEnemy().getEnemy_sprite();

        String rat_hp_bar_sprite = create_hp_bar(enemy_life, enemy_max_life);

        place_text(new int[] {line - 7, column - 2}, "Grande Rato:");
        place_text(new int[] {line - 5, column - 2}, rat_hp_bar_sprite);
        place_text(new int[] {line - 4, column - 2}, String.format("Turno Seguinte: %d DMG", enemy_round_damage));
        place_text(new int[] {line, column}, rat_sprite);
    }


    private void place_hero_info(GameData gameData) {
        // places hero info (hp, shield, energy) on frame
        
        // Getting Hero Data
        int hero_life = gameData.getHero().getLife();
        int hero_max_life = gameData.getHero().getMaxLife();
        int hero_shield = gameData.getHero().getShield();
        int hero_energy = gameData.getHero().getEnergy();
        int buy_pile_size = gameData.getBuy_pile().size();
        int discard_pile_size = gameData.getDiscard_pile().size();
        
        String hero_hp_bar_sprite = create_hp_bar(hero_life, hero_max_life);
        String shield_counter = "(+" + hero_shield + ")";
        String energy_bar_sprite = "Energia: " + "■ ".repeat(hero_energy) + hero_energy + "/3";
        String vertical_bar = "║\n".repeat(HEIGHT - 2);


        int[] HP_BAR_POSITION = {4, 38};
        int[] SHIELD_COUNTER_POSITION = {4, 63}; 
        int[] ENERGY_BAR_POSITION = {1, 2};
        int[] NO_ENERGY_WARNING_POSITION = {1, 15};

        place_text(HP_BAR_POSITION, hero_hp_bar_sprite);
        if (hero_shield > 0) {
            place_text(SHIELD_COUNTER_POSITION, shield_counter);
        }
        place_text(ENERGY_BAR_POSITION, energy_bar_sprite);


        if (hero_energy == 0) { 
            place_text(NO_ENERGY_WARNING_POSITION, "| Sem energia!");
        }

        int[] BUY_PILE_POSITION = {3, 2};
        int[] DISCARD_PILE_POSITION = {4, 2};
        int[] VERTICAL_BAR_POSITION = {1, VERTICAL_BAR_SIZE};

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

    
    private void place_card_UI(GameData gameData) {
        int hand_size = gameData.getPlayer_hand().size();

        int DECK_TEXT_LINE = 6;
        
        place_text(new int[] {DECK_TEXT_LINE - 1, 1}, "=".repeat(VERTICAL_BAR_SIZE - 1));
        place_text(new int[] {DECK_TEXT_LINE, 1}, "              Hand:");
        place_text(new int[] {DECK_TEXT_LINE + 1, 1}, "=".repeat(VERTICAL_BAR_SIZE - 1));
        
        int start_line = 8;
        int line = 0;
        for (int i = 0; i < hand_size; i++) {

            int card_index = gameData.getPlayer_hand().get(i);
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




    private void clearScreen() {
        this.frame.delete(0, frame.length());
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }


    public void placeBattleScreen(GameData gameData) {
        int[] HERO_POSITION = {6, 38};
        int[] ENEMY_POSITION = {9, 78};


        place_hero_sprite(gameData, HERO_POSITION);
        place_enemy_sprite(gameData, ENEMY_POSITION);
        place_hero_info(gameData);
        place_card_UI(gameData);
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
        // places text (sprites, UI) on frame and prints it
        clearScreen();
        place_borders();
        placeBattleScreen(gameData);
        System.out.print(frame);
        if (gameData.isBattle_over()) {
            drawEndScreen(gameData);
        }
    }
}