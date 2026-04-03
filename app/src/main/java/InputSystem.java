import java.io.IOException;

import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.screen.Screen;

public class InputSystem {
    private final  Screen screen = TerminalManager.getInstance().getScreen();

    
    public Action readInput() {
        // Reads player input and returns an action (enum class)
        Action action = new Action();
        action.setAction_type(Action.ActionType.SKIP);
        String inputStr = "";

        try {
            KeyStroke key = screen.readInput();
            
            inputStr = key.getCharacter().toString();
            int inputInt = Integer.parseInt(inputStr);
            handleNumericalInput(inputInt, action);
            return action;
        }
        catch (java.lang.NumberFormatException | IOException e){
            handleAlphabeticInput(inputStr, action);
            return action;
        }
    }


    private Action handleAlphabeticInput(String inputStr, Action action) {
        switch (inputStr.toUpperCase()) {
                case "Q" -> action.setAction_type(Action.ActionType.QUIT);
                case "P" -> action.setAction_type(Action.ActionType.SKIP);
                default -> action.setAction_type(Action.ActionType.INVALID);
        }
        return action;
    }


    private Action handleNumericalInput(int inputInt, Action action) {
        if (inputInt <= 5 || inputInt > 0) {
                action.setAction_type(Action.ActionType.CARD);
                action.setCard_used_index(inputInt - 1);
                return action;
            }

        action.setAction_type(Action.ActionType.SKIP);
        return action;
    }
}