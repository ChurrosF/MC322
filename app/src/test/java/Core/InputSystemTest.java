package Core;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;

import Entities.Action;

public class InputSystemTest {

    /**
     * Função auxiliar usando Reflection para pegar a Action privada construída dentro do InputSystem.
     */
    private Action getInternalAction(InputSystem inputSystem) throws Exception {
        Field actionField = InputSystem.class.getDeclaredField("action");
        actionField.setAccessible(true);
        return (Action) actionField.get(inputSystem);
    }

    /**
     * Função auxiliar usando Reflection para invocar métodos privados (pulando a trava da tela).
     */
    private void invokeMethod(InputSystem inputSystem, String methodName, KeyStroke key) throws Exception {
        Method method = InputSystem.class.getDeclaredMethod(methodName, KeyStroke.class);
        method.setAccessible(true);
        method.invoke(inputSystem, key);
    }

    @Test
    public void testCardChooseAction_Quit() throws Exception {
        InputSystem inputSystem = new InputSystem();
        // Simula o usuário apertando a letra 'Q'
        KeyStroke key = new KeyStroke('q', false, false);

        invokeMethod(inputSystem, "CardChooseAction", key);

        Action action = getInternalAction(inputSystem);
        assertEquals(Action.ActionType.QUIT, action.getActionType());
    }

    @Test
    public void testCardChooseAction_Skip() throws Exception {
        InputSystem inputSystem = new InputSystem();
        // Simula o usuário apertando a letra 'P'
        KeyStroke key = new KeyStroke('p', false, false);

        invokeMethod(inputSystem, "CardChooseAction", key);

        Action action = getInternalAction(inputSystem);
        assertEquals(Action.ActionType.SKIP, action.getActionType());
    }

    @Test
    public void testCardChooseAction_Numeric() throws Exception {
        InputSystem inputSystem = new InputSystem();
        // Simula o usuário escolhendo a carta de número '2'
        KeyStroke key = new KeyStroke('2', false, false);

        invokeMethod(inputSystem, "CardChooseAction", key);

        Action action = getInternalAction(inputSystem);
        assertEquals(Action.ActionType.CHOOSE_CARD, action.getActionType());
        
        // A lógica do seu código subtrai 1 (cardInt - 1)
        assertEquals(1, action.getInputInt()); 
    }

    @Test
    public void testTargetChooseAction_Escape() throws Exception {
        InputSystem inputSystem = new InputSystem();
        // Simula o usuário apertando 'ESC'
        KeyStroke key = new KeyStroke(KeyType.Escape);

        invokeMethod(inputSystem, "TargetChooseAction", key);

        Action action = getInternalAction(inputSystem);
        assertEquals(Action.ActionType.BACK, action.getActionType());
        assertTrue(action.getInputInt() == null);
    }

    @Test
    public void testTargetChooseAction_Numeric() throws Exception {
        InputSystem inputSystem = new InputSystem();
        // Simula o usuário escolhendo o inimigo '3'
        KeyStroke key = new KeyStroke('3', false, false);

        invokeMethod(inputSystem, "TargetChooseAction", key);

        Action action = getInternalAction(inputSystem);
        assertEquals(Action.ActionType.CHOOSE_TARGET, action.getActionType());
        // A lógica do seu código subtrai 1 (targetInt - 1)
        assertEquals(2, action.getTargetIndex()); 
    }

    @Test
    public void testRoomChooseAction_Numeric() throws Exception {
        InputSystem inputSystem = new InputSystem();
        // Simula o usuário escolhendo a sala '5'
        KeyStroke key = new KeyStroke('5', false, false);

        invokeMethod(inputSystem, "RoomChooseAction", key);

        Action action = getInternalAction(inputSystem);
        assertEquals(Action.ActionType.CHOOSE_ROOM, action.getActionType());
        assertEquals(4, action.getInputInt());
    }

    @Test
    public void testRoomChooseAction_InvalidAlphabetic() throws Exception {
        InputSystem inputSystem = new InputSystem();
        // Simula o usuário apertando 'A' na tela de mapa (onde só deveria aceitar números)
        KeyStroke key = new KeyStroke('a', false, false);

        invokeMethod(inputSystem, "RoomChooseAction", key);

        Action action = getInternalAction(inputSystem);
        assertEquals(Action.ActionType.INVALID, action.getActionType());
    }

    @Test
    public void testCheckGameClose_EOF() throws Exception {
        InputSystem inputSystem = new InputSystem();
        // Simula um EOF (Sinal de encerramento de terminal)
        KeyStroke key = new KeyStroke(KeyType.EOF);

        invokeMethod(inputSystem, "checkGameClose", key);

        Action action = getInternalAction(inputSystem);
        assertEquals(Action.ActionType.QUIT, action.getActionType());
    }
}