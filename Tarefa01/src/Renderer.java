public class Renderer {
    private final int HEIGHT = 18;
    private final int WIDTH = 120;
    private final int VERTICAL_BAR_SIZE = 35;
    private StringBuilder frame = new StringBuilder();


    private void place_text(int[] position, String text) {
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


    private void place_hero_sprite(GameData gameData, int[] position) {
        int line = position[0];
        int column = position[1];

        String hero_sprite = gameData.getHero().getHero_sprite();

        place_text(new int[] {line - 4, column}, "Cavaleiro:");
        place_text(new int[] {line, column}, hero_sprite);
    }


    private void place_enemy_sprite(GameData gameData, int[] position) {
        int line = position[0];
        int column = position[1];

        // Getting Enemy Data
        int enemy_life = gameData.getEnemy().getLife();
        String rat_sprite = gameData.getEnemy().getEnemy_sprite();
        String rat_hp_bar_sprite = "Vida: [" + "█".repeat(enemy_life) + "░".repeat(20-enemy_life) + "]" + " " + enemy_life + "/20";

        place_text(new int[] {line - 7, column - 2}, "Grande Rato Nv. 1:");
        place_text(new int[] {line - 5, column - 2}, rat_hp_bar_sprite);
        place_text(new int[] {line, column}, rat_sprite);
    }


    private void place_hero_info(GameData gameData) {
        // Getting Hero Data
        int hero_life = gameData.getHero().getLife();
        int hero_shield = gameData.getHero().getShield();
        int hero_energy = gameData.getHero().getEnergy();
        
        String hero_hp_bar_sprite = "Vida: [" + "█".repeat(hero_life) + "░".repeat(10-hero_life) + "]" + " " + hero_life + "/10";
        String shield_bar_sprite = "Escudo: " + hero_shield;
        String energy_bar_sprite = "Energia: " + "■ ".repeat(hero_energy) + hero_energy + "/3";
        String vertical_bar = "║\n".repeat(HEIGHT - 2);

        place_text(new int[] {4, 38}, hero_hp_bar_sprite);
        place_text(new int[] {2, 2}, shield_bar_sprite);
        place_text(new int[] {1, 2}, energy_bar_sprite);
        place_text(new int[] {1, VERTICAL_BAR_SIZE}, vertical_bar);
    }


    private void place_card_UI(GameData gameData) {
        int damage_card_damage = gameData.getDamageCard().getDamage();
        int damage_card_cost = gameData.getDamageCard().getCost();
        String damage_card_name = gameData.getDamageCard().getName();

        int shield_card_defense = gameData.getShieldCard().getShield();
        int shield_card_cost = gameData.getShieldCard().getCost();
        String shield_card_name = gameData.getShieldCard().getName();

        place_text(new int[] {3, 1}, "=".repeat(VERTICAL_BAR_SIZE - 1));
        place_text(new int[] {4, 1}, "(1) " + "Carta " + damage_card_name +"  |" + " DMG:" + damage_card_damage + " CUSTO:" + damage_card_cost);
        place_text(new int[] {5, 1}, "-".repeat(VERTICAL_BAR_SIZE - 1));
        place_text(new int[] {6, 1}, "(2) " +  "Carta " + shield_card_name +" |" + " SHD:" + shield_card_defense + " CUSTO:" + shield_card_cost);
        place_text(new int[] {7, 1}, "-".repeat(VERTICAL_BAR_SIZE - 1));

        place_text(new int[] {HEIGHT - 3, 1}, "=".repeat(VERTICAL_BAR_SIZE - 1));
        place_text(new int[] {HEIGHT - 2, 1}, "(3) PASSAR TURNO (+3 ENERGIA)");
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
        if (gameData.getHero().isAlive()) {
            System.out.println("\nVOCÊ GANHOU!\n");
        }
        else {
            System.out.println("\nVOCÊ FOI DERROTADO...\n");
        }
    }


    public void render(GameData gameData) {
        clearScreen();
        place_borders();
        placeBattleScreen(gameData);
        System.out.print(frame);
        if (gameData.isBattle_over()) {
            drawEndScreen(gameData);
        }
    }
}