public class Renderer {
    private final int HEIGHT = 15;
    private final int WIDTH = 90;
    private StringBuilder frame = new StringBuilder();


    public void place_text(int[] vector, String text) {
        int start_pos = vector[0] * (WIDTH + 2) + vector[1];
        this.frame.replace(start_pos, start_pos + text.length(), text);
    }


    public void draw_borders() {
        for (int i = 0; i < this.HEIGHT; i++) {
            for (int j = 0; j < this.WIDTH; j++) {

                if (i == 0 || i == this.HEIGHT - 1) {
                this.frame.append("-");
            }
                else if (j == 0 || j >= this.WIDTH - 1) {
                    this.frame.append("|");
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


    public void drawBattleScreen(GameData gameData) {
        Hero hero = gameData.getHero();
        Enemy enemy = gameData.getEnemy();

        if (gameData.getBattle_rounds() == 1) {
            System.out.println("Uma batalha formidável contra um rato começou!");
        }

        System.out.println();
        System.out.println("=-=");
        System.out.printf("Herói (%d/10 de vida) (%d de escudo)\n", hero.getLife(), hero.getShield());
        System.out.println("vs");
        System.out.printf("Rato (%d/20 de vida)\n\n", enemy.getLife());
        System.out.printf("%d/3 de Energia disponível\n", hero.getEnergy());
        System.out.println("1 - Usar carta de dano");
        System.out.println("2 - Usar carta de escudo");
        System.out.println("3 - Encerrar turno\n");
        
        if (hero.getEnergy() == 0) {
            System.out.println("Você está sem energia!\n");
        }
        System.out.print("Escolha: ");
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
        drawBattleScreen(gameData);
    }
}
