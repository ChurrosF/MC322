public class App {
    public static void main(String[] args) throws Exception {
        GameManager gameManager = new GameManager();
        Renderer renderer = new Renderer();
        InputSystem inputSystem = new InputSystem();

        while (true) {
            renderer.render(gameManager.getGameData());
            if (gameManager.getGameData().isBattle_over()) {
                break;
            }
            Action action = inputSystem.readInput();
            gameManager.update(action);
            }
        }
    }
