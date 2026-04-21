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

        Card[] cards = data.getPossibleCards();
        data.setPossibleCards(cards);
        assertNotNull(data.getPossibleCards());
        assertNotNull(data.getShieldCard());
        assertNotNull(data.getHero());
    }
}