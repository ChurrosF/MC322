package Core;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

import Entities.Action;

class GameManagerTest {
    @Test
    void testGameManagerFlow() {
        GameManager gm = new GameManager();
        
        // 1. Testa passar o turno
        Action skip = new Action();
        skip.setActionType(Action.ActionType.SKIP);
        gm.update(skip);
        assertEquals(2, gm.getGameData().getBattleRounds(), "Passar o turno deve incrementar o round.");

        // 2. Testa ação inválida (carta fora do limite)
        Action invalidCard = new Action();
        invalidCard.setActionType(Action.ActionType.CHOOSE_CARD);
        invalidCard.setCardUsedIndex(99); // Índice absurdo
        gm.update(invalidCard);
        assertTrue(gm.getGameData().isActionInvalid(), "Ação fora do limite deve ser marcada como inválida.");

        // 3. Testa desistir
        Action quit = new Action();
        quit.setActionType(Action.ActionType.QUIT);
        gm.update(quit);
        
        // CORREÇÃO AQUI: Verifica a flag do GameData, que é atualizada no mesmo ciclo
        assertTrue(gm.getGameData().isBattleOver(), "A batalha deve ser marcada como finalizada após QUIT.");
    }
}