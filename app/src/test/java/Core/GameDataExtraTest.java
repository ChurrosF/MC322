package Core;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

import Cards.Card;

class GameDataExtraTest {
    @Test
    void testSettersAndGetters() {
        GameData data = new GameData();
        
        data.setBattleOver(true);
        assertTrue(data.isBattleOver());

        data.setInvalidAction(true);
        assertTrue(data.isActionInvalid());

        Card[] cards = data.getCurrentCards();
        data.setCurrentCards(cards);
        assertNotNull(data.getCurrentCards());
        assertNotNull(data.getHero());
    }
}