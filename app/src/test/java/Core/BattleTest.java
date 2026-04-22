package Core;

import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

import Entities.Action;

public class BattleTest {

    
    @Test
    public void testUpdateActionQuit() {
        GameManager manager = new GameManager();
        Battle battle = new Battle(manager.getGameData(), manager);
        
        // Simula a Action de sair
        Action quitAction = new Action() {
            @Override
            public ActionType getActionType() {
                return ActionType.QUIT;
            }
        };
        
        battle.update(quitAction);
        
        assertTrue(manager.getGameData().isGameOver());
        assertTrue(manager.getGameData().isGameClosed());
    }

    @Test
    public void testUpdateActionInvalid() {
        GameManager manager = new GameManager();
        Battle battle = new Battle(manager.getGameData(), manager);
        
        // Simula uma Action desconhecida/inválida
        Action invalidAction = new Action() {
            @Override
            public ActionType getActionType() {
                return ActionType.INVALID;
            }
        };
        
        battle.update(invalidAction);
        
        assertTrue(manager.getGameData().isActionInvalid());
    }
}