public class Renderer {
    private final int HEIGHT = 18;
    private final int WIDTH = 120;
    private StringBuilder frame = new StringBuilder();


    public void place_text(int[] vector, String text, StringBuilder frame) {
        int start_pos = vector[0] * (WIDTH + 2) + (vector[1]) - 3;
        int count = 0;
        int cur_line = vector[0];

        for (int i = start_pos; count < text.length(); i++) {
            
            char letter = text.charAt(count); 
            count++;

            if (letter == '\n') {
                i = (cur_line + 1) * (WIDTH + 1) + vector[1] - 1;
                cur_line++;
            }
            else {
                frame.setCharAt(i, letter);
            }      
        }
    }


    public void place_borders() {
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


    public void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }


    // public void drawBattleScreen(GameData gameData) {
    //     Hero hero = gameData.getHero();
    //     Enemy enemy = gameData.getEnemy();

    //     if (gameData.getBattle_rounds() == 1) {
    //         System.out.println("Uma batalha formidável contra um rato começou!");
    //     }

    //     System.out.println();
    //     System.out.println("=-=");
    //     System.out.printf("Herói (%d/10 de vida) (%d de escudo)\n", hero.getLife(), hero.getShield());
    //     System.out.println("vs");
    //     System.out.printf("Rato (%d/20 de vida)\n\n", enemy.getLife());
    //     System.out.printf("%d/3 de Energia disponível\n", hero.getEnergy());
    //     System.out.println("1 - Usar carta de dano");
    //     System.out.println("2 - Usar carta de escudo");
    //     System.out.println("3 - Encerrar turno\n");
        
    //     if (hero.getEnergy() == 0) {
    //         System.out.println("Você está sem energia!\n");
    //     }
    //     System.out.print("Escolha: ");
    // }

    public void placeBattleScreen(GameData gameData) {
        int hero_life = gameData.getHero().getLife();
        int hero_shield = gameData.getHero().getShield();
        int hero_energy = gameData.getHero().getEnergy();

        int enemy_life = gameData.getEnemy().getLife();

        String hero_hp_bar_sprite = "Vida: [" + "█".repeat(hero_life) + "░".repeat(10-hero_life) + "]" + "(" + hero_life + "/10)";
        String shield_bar_sprite = "Escudo: " + hero_shield;
        String energy_bar_sprite = "Energia: " + "□".repeat(hero_life);
        String hero_sprite = """
  |
    |
    + \\
    \\.G_.*=.
    `(H'/.|
    .>' (_--.
_=/d   ,^\\
~~ \\)-'   '
    / |
    '  '  
""";

        String rat_hp_bar_sprite = "Vida: [" + "█".repeat(enemy_life) + "░".repeat(20-enemy_life) + "]" + "(" + enemy_life + "/20)";
        String rat_sprite = """
        _
  ,-(_)-\"\"\"\"\"--,,
<  "             ";===""==,.
 `-../ )__... (  ,'        "==
   ==="    ,,==="
                """;


        place_text(new int[] {2, 15}, hero_hp_bar_sprite, this.frame);
        place_text(new int[] {3, 14}, shield_bar_sprite, this.frame);
        place_text(new int[] {4, 13}, energy_bar_sprite, this.frame);
        place_text(new int[] {5, 20}, hero_sprite, this.frame);

        place_text(new int[] {2, 70}, rat_hp_bar_sprite, this.frame);
        place_text(new int[] {6, 72}, rat_sprite, this.frame);
    }

    public void drawEndScreen(GameData gameData) {
        if (gameData.getHero().isAlive()) {
            System.out.println("Você derrotou o inimigo!");
        }
        else {
            System.out.println("Você foi derrotado! =( ");
        }
    }


    public void render(GameData gameData) {
        clearScreen();
        placeBattleScreen(gameData);
        System.out.println(frame);
    }
}
