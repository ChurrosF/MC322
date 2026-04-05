import java.io.IOException;

import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import com.googlecode.lanterna.screen.Screen;

public class InputSystem {
    private final  Screen screen = TerminalManager.getInstance().getScreen();

    
    public Action readInput(GameData data) {
        // Reads player input and returns an action
        Action action = new Action();
        action.setActionType(Action.ActionType.SKIP);


        try { 
            KeyStroke key = screen.readInput();
            convertInputToAction(key, action);

            // NOVO: Lógica de 2 passos (Escolhe a carta -> Escolhe o alvo)
            if (action.getActionType() == Action.ActionType.CARD) {
                int cardIndex = action.getCardUsedIndex();
                
                // Checa se o jogador não digitou uma carta fora da mão
                if (cardIndex >= 0 && cardIndex < data.getPlayerHand().size()) {
                    int cardTypeId = data.getPlayerHand().get(cardIndex);
                    Card card = data.getPossibleCards()[cardTypeId];
                    
                    if (card.requiresTarget()) {
                        // Carta exige alvo, então espera o próximo input do teclado
                        KeyStroke targetKey = screen.readInput();
                        if (isKeyNumeric(targetKey)) {
                            int targetInt = Integer.parseInt(targetKey.getCharacter().toString());
                            // O jogador digita 1, mas o índice da lista é 0
                            action.setTargetIndex(targetInt - 1); 
                        } else {
                            action.setActionType(Action.ActionType.INVALID);
                        }
                    }
                } else {
                    action.setActionType(Action.ActionType.INVALID);
                }
            }
        }
        catch (IOException e){
        }

        return action;
    }


    private void convertInputToAction(KeyStroke key, Action action) {
        if (isKeySpecial(key)) {
            handleSpecialInput(key, action);
        }
        else if (isKeyNumeric(key)) {
            handleNumericalInput(key, action);
        }
        else {
            handleAlphabeticInput(key, action);
        }
    }


    private void handleAlphabeticInput(KeyStroke key, Action action) {
        String inputStr = key.getCharacter().toString();
        switch (inputStr.toUpperCase()) {
                case "Q" -> action.setActionType(Action.ActionType.QUIT);
                case "P" -> action.setActionType(Action.ActionType.SKIP);
                default -> action.setActionType(Action.ActionType.INVALID);
        }
    }


    private void handleNumericalInput(KeyStroke key, Action action) {
        String inputStr = key.getCharacter().toString();
        int inputInt = Integer.parseInt(inputStr);

        if (inputInt <= 5 || inputInt > 0) {
                action.setActionType(Action.ActionType.CARD);
                action.setCardUsedIndex(inputInt - 1);
                return;
            }

        action.setActionType(Action.ActionType.SKIP);
    }


    private void handleSpecialInput(KeyStroke key, Action action) {
        switch (key.getKeyType()) {
            case KeyType.Enter -> action.setActionType(Action.ActionType.SKIP);
            case KeyType.EOF -> action.setActionType(Action.ActionType.QUIT);
            default -> action.setActionType(Action.ActionType.INVALID);
        }
    }


    private boolean isKeySpecial(KeyStroke key) {
        return !(key.getKeyType() == KeyType.Character);
    }


    private boolean isKeyNumeric(KeyStroke key) {
        return Character.isDigit(key.getCharacter());
    }
}