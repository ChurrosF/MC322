package Cards;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

import Entities.Hero;

class ManaAndStrengthCardTest {
    @Test
    void testManaCard() {
        Hero hero = new Hero("Hero", 20, 3, 0);
        ManaCard mana = new ManaCard("Mana Overflow", 1, 2);

        assertTrue(mana.useCard(hero, hero));
        assertEquals(2, hero.getEnergy(), "Deve gastar 1 de energia.");
        assertEquals(1, hero.getEffects().size(), "Efeito de mana deve ser aplicado.");
        assertFalse(mana.requiresTarget());
    }

    @Test
    void testStrengthCard() {
        Hero hero = new Hero("Hero", 20, 3, 0);
        StrengthCard str = new StrengthCard("Força", 2, 3);

        assertTrue(str.useCard(hero, hero));
        assertEquals(1, hero.getEnergy(), "Deve gastar 2 de energia.");
        assertEquals(1, hero.getEffects().size(), "Efeito de força deve ser aplicado.");
        assertFalse(str.requiresTarget());
    }

    @Test
    void testCardGetters() {
        DamageCard dmg = new DamageCard("Dano", 1, 5);
        assertEquals("Dano", dmg.getName());
        assertEquals(1, dmg.getCost());
        assertNotNull(dmg.getDescription());
        
        dmg.setBaseDamage(10);
        assertEquals(10, dmg.getBaseDamage());
    }
}