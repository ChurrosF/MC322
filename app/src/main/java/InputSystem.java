import java.io.IOException;

import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import com.googlecode.lanterna.screen.Screen;

public class InputSystem {
    private final Screen screen = TerminalManager.getInstance().getScreen();
    private final Action action = new Action();

    
    public Action readInput(GameState state) {
        try { 
            KeyStroke key = screen.readInput();
            switch (state) {
                case GameState.CHOOSING_CARD -> CardChooseAction(key);
                case GameState.TARGETING -> TargetChooseAction(key);
            }
        }
        catch (IOException e){
            System.err.println("(InputSystem) Error when trying to read input from player:" + e);
        }

        return this.action;
    }


    private void CardChooseAction(KeyStroke key) {
        if (isKeySpecial(key)) {
            handleSpecialInput(key);
        }
        else if (isKeyNumeric(key)) {
            CardChooseNumericalInput(key);
        }
        else {
            CardChooseAlphabeticInput(key);
        }
    }


    private void TargetChooseAction(KeyStroke key) {
        if (!isKeyNumeric(key)) {
            if (key.getKeyType() == KeyType.Escape) {
                this.action.setCardUsedIndex(null);
                this.action.setActionType(Action.ActionType.BACK);
            }
            else {
                this.action.setActionType(Action.ActionType.INVALID);
            }
            return;
        }

        TargetingNumericalInput(key);
    }


    private void CardChooseAlphabeticInput(KeyStroke key) {
        String inputStr = key.getCharacter().toString();

        
        switch (inputStr.toUpperCase()) {
                case "Q" -> this.action.setActionType(Action.ActionType.QUIT);
                case "P" -> this.action.setActionType(Action.ActionType.SKIP);
                default -> this.action.setActionType(Action.ActionType.INVALID);
        }
    }


    private void CardChooseNumericalInput(KeyStroke key) {
        String inputStr = key.getCharacter().toString();
        int cardInt = Integer.parseInt(inputStr);

        if (cardInt <= 5 || cardInt > 0) {
                this.action.setActionType(Action.ActionType.CHOOSE_CARD);
                this.action.setCardUsedIndex(cardInt - 1);
                return;
        }

        this.action.setActionType(Action.ActionType.SKIP);
    }


    private void TargetingNumericalInput(KeyStroke key) {
        String inputStr = key.getCharacter().toString();
        int targetInt = Integer.parseInt(inputStr);

        if (targetInt > 0 || targetInt <= 3) {
            this.action.setActionType(Action.ActionType.CHOOSE_TARGET);
            this.action.setTargetIndex(targetInt - 1);
            return;
        }

        this.action.setActionType(Action.ActionType.INVALID);
    }


    private void handleSpecialInput(KeyStroke key) {
        switch (key.getKeyType()) {
            case KeyType.Enter -> this.action.setActionType(Action.ActionType.SKIP);
            case KeyType.EOF -> this.action.setActionType(Action.ActionType.QUIT);
            default -> this.action.setActionType(Action.ActionType.INVALID);
        }
    }


    private boolean isKeySpecial(KeyStroke key) {
        return !(key.getKeyType() == KeyType.Character);
    }


    private boolean isKeyNumeric(KeyStroke key) {
        if (key.getCharacter() != null) {
            return Character.isDigit(key.getCharacter());
        }
        return false;
    }
}