package Core;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

class GameDataTest {
    @Test
    void testPileManagement() {
        GameData data = new GameData();
        
        // Testa descarte
        int initialHandSize = data.getPlayerHand().size();
        data.discardCard(0);
        assertEquals(initialHandSize - 1, data.getPlayerHand().size());
        assertEquals(1, data.getDiscardPile().size());

        // Testa limpar a mão
        data.discardHand();
        assertTrue(data.getPlayerHand().isEmpty());
        assertTrue(data.getDiscardPile().size() > 1);

        // Testa resetar a pilha de compras
        data.getBuyPile().clear(); // Força a pilha a ficar vazia
        data.resetBuyPile();
        assertTrue(data.getDiscardPile().isEmpty());
        assertFalse(data.getBuyPile().isEmpty());
    }
}