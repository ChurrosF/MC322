package Entities;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

class ActionTest {
    @Test
    void testActionData() {
        Action action = new Action();
        
        // Testa todos os setters
        action.setActionType(Action.ActionType.CHOOSE_CARD);
        action.setInputInt(2);
        action.setTargetIndex(1);

        // Testa todos os getters
        assertEquals(Action.ActionType.CHOOSE_CARD, action.getActionType());
        assertEquals(2, action.getInputInt());
        assertEquals(1, action.getTargetIndex());
    }
}