public class App {
    public static void main(String[] args) throws Exception {
        GameManager gameManager = new GameManager();
        Renderer renderer = new Renderer();
        InputSystem inputSystem = new InputSystem();

        renderer.place_borders();
        while (!gameManager.gameEnded()) {
            renderer.render(gameManager.getGameData());
            Action action = inputSystem.readInput();
            gameManager.update(action);
        }
        renderer.drawEndScreen(gameManager.getGameData());
    }
}
