import java.util.Scanner;

public class InputSystem {
    private final Scanner inputReader = new Scanner(System.in);

    
    public Action readInput() {
        // Reads player input and returns an action (enum class)
        Action action = new Action();
        action.setAction_type(Action.ActionType.SKIP);
        try {
            System.out.print("Escolha: ");
            int input = inputReader.nextInt();

            if (input <= 5 || input > 0) {
                action.setAction_type(Action.ActionType.CARD);
                action.setCard_used_index(input);
                return action;
            }

            action.setAction_type(Action.ActionType.SKIP);
            return action;
        }
        catch (java.util.InputMismatchException e){
            return action;
        }
    }
}