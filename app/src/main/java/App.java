import Core.GameData;
import Core.GameManager;
import Core.InputSystem;
import Core.Renderer;
import Entities.Action;
import Map.Map;
import States.StateType;

/**
 * The main entry point for the "Slay the Tuff Rat" application.
 * <p>
 * This class serves as the orchestrator of the entire game. It initializes the core 
 * subsystems (Model, View, Controller) and runs the main <b>Game Loop</b>. 
 * Because this is a turn-based RPG, the game loop is synchronous—it pauses execution 
 * to wait for the user's input before processing the next frame.
 * </p>
 */
public class App {
    /**
     * The main execution method that starts the game.
     * <p>
     * The execution sequence follows a classic Turn-Based Game Loop pattern:
     * <ol>
     * <li><b>Render:</b> Draws the current game state to the terminal screen.</li>
     * <li><b>Check State:</b> Evaluates if the battle has concluded (Win/Loss). If so, breaks the loop.</li>
     * <li><b>Input:</b> Blocks and waits for the player to press a valid key.</li>
     * <li><b>Update:</b> Passes the player's action to the GameManager to calculate damage, effects, and rules.</li>
     * </ol>
     *
     * @param args Command-line arguments (not utilized in this application).
     * @throws Exception If an unhandled interruption occurs during terminal initialization or input reading.
     */
    public static void main(String[] args) throws Exception {
        // Initialization of Core Subsystems
        Map map = new Map(5, 15, 3);
        map.printMap();
        GameManager gameManager = new GameManager();
        Renderer renderer = new Renderer();
        InputSystem inputSystem = new InputSystem();

        while (true) {
            GameData data = gameManager.getGameData();
            StateType state = gameManager.getState();
            renderer.render(data, state);
            if (gameManager.getGameData().isBattleOver()) {
                break;
            }
            Action action = inputSystem.readInput(state);
            gameManager.update(action);
            }
        }
    }
