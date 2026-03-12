import java.util.Scanner;

public class InputSystem {
    private final Scanner inputReader = new Scanner(System.in);

    
    public Action readInput() {
        // Reads player input and returns an action (enum class)
        try {
            System.out.print("Escolha: ");
            int input = inputReader.nextInt();

            switch (input) {
                case 1 -> {
                    return Action.ATTACK;
                }
                case 2 -> {
                    return Action.DEFEND;
                }
                case 3 -> {
                    return Action.SKIP;
                }
                default -> {
                    return Action.SKIP;
                }
            }
        }
        catch (java.util.InputMismatchException e){
            return Action.SKIP;
        }
    }

}
