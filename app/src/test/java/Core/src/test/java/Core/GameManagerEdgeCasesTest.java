package Core;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

import Entities.Action;

class GameManagerEdgeCasesTest {
    @Test
    void testBackActionAndInvalidTargets() {
        GameManager gm = new GameManager();
        
        // Prepara para forçar o estado TARGETING
        gm.getGameData().getPlayerHand().clear();
        gm.getGameData().getPlayerHand().add(0); // Coloca um ataque
        Action chooseCard = new Action();
        chooseCard.setActionType(Action.ActionType.CHOOSE_CARD);
        chooseCard.setCardUsedIndex(0);
        gm.update(chooseCard);
        
        assertEquals(GameState.TARGETING, gm.getState());

        // 1. Testa a ação BACK (Cancelar uso da carta)
        Action back = new Action();
        back.setActionType(Action.ActionType.BACK);
        gm.update(back);
        
        assertEquals(GameState.CHOOSING_CARD, gm.getState(), "Deve retornar ao estado normal.");

        // 2. Testa escolher um alvo inválido
        gm.update(chooseCard); // Vai para TARGETING novamente
        Action invalidTarget = new Action();
        invalidTarget.setActionType(Action.ActionType.CHOOSE_TARGET);
        invalidTarget.setTargetIndex(99); // Índice de inimigo absurdo
        
        gm.update(invalidTarget);
        
        assertTrue(gm.getGameData().isActionInvalid(), "Alvo inválido deve ativar a flag de erro.");
    }
}