package Cards;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

import Entities.Enemy;
import Entities.Hero;

class AllCardsTest {
    @Test
    void testShieldCard() {
        Hero hero = new Hero("Hero", 20, 3, 0);
        ShieldCard shield = new ShieldCard("Defesa", 1, 5);
        
        assertTrue(shield.useCard(hero, hero));
        assertEquals(5, hero.getShield());
        assertEquals(2, hero.getEnergy());
        assertFalse(shield.requiresTarget());
    }

    @Test
    void testPoisonCard() {
        Hero hero = new Hero("Hero", 20, 3, 0);
        Enemy target = new Enemy("Spider", 10, 0, new int[]{2, 3});
        PoisonCard poison = new PoisonCard("Dardo", 1, 3);
        
        assertTrue(poison.useCard(hero, target));
        assertEquals(1, target.getEffects().size());
        assertTrue(poison.requiresTarget());
    }
}