package Core;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

import Entities.Action;

public class GameManagerTest {

    @Test
    public void testEstadoInicial() {
        GameManager manager = new GameManager();
        
        assertEquals(GameState.MAP, manager.getState());
        assertFalse(manager.isGameEnded());
        assertTrue(manager.getGameData() != null); 
    }

    @Test
    public void testSetGameOver() {
        GameManager manager = new GameManager();
        
        manager.setGameOver();
        
        assertTrue(manager.isGameEnded());
    }

    @Test
    public void testSetState() {
        GameManager manager = new GameManager();
        
        manager.setState(GameState.BATTLE_CARD);
        
        assertEquals(GameState.BATTLE_CARD, manager.getState());
    }

    @Test
    public void testUpdateMapActionQuit() {
        GameManager manager = new GameManager();
        
        // Simula uma Action do tipo QUIT
        Action quitAction = new Action() {
            @Override
            public ActionType getActionType() {
                return ActionType.QUIT;
            }
        };

        manager.updateMap(quitAction);

        assertTrue(manager.getGameData().isGameOver());
        assertTrue(manager.getGameData().isGameClosed());
    }

    @Test
    public void testUpdateMapInvalidRoomIndex() {
        GameManager manager = new GameManager();
        
        // Simula uma Action de escolher sala com valor inválido
        Action invalidRoomAction = new Action() {
            @Override
            public ActionType getActionType() {
                return ActionType.CHOOSE_ROOM;
            }
            
            @Override
            public Integer getInputInt() {
                return 7; // Maior que 6 ativa a validação de ação inválida
            }
        };

        manager.updateMap(invalidRoomAction);

        assertTrue(manager.getGameData().isActionInvalid());
    }
}