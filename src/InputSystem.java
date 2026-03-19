import java.util.Scanner;

public class InputSystem {
    private final Scanner inputReader = new Scanner(System.in);

    
    public Action readInput() {
        // Reads player input and returns an action (enum class)
        Action action = new Action();
        action.setAction_type(Action.ActionType.SKIP);
        try {
            System.out.print("Escolha: ");
            String input_str = inputReader.nextLine();
            int input_int = Integer.parseInt(input_str);

            if (input_int <= 5 || input_int > 0) {
                action.setAction_type(Action.ActionType.CARD);
                action.setCard_used_index(input_int - 1);
                return action;
            }

            action.setAction_type(Action.ActionType.SKIP);
            return action;
        }
        catch (java.lang.NumberFormatException e){
            return action;
        }
    }
}