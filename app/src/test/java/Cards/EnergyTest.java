package Cards;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

import Entities.Enemy;
import Entities.Hero;

class EnergyTest {
    @Test
    void testInsufficientEnergy() {
        Hero hero = new Hero("Hero", 20, 1, 0); // Só 1 de energia
        Enemy target = new Enemy("Spider", 10, 0, new int[]{2, 5});
        DamageCard heavyAttack = new DamageCard("Ataque Pesado", 2, 6);

        boolean result = heavyAttack.useCard(hero, target);

        assertFalse(result, "Não deve permitir uso sem energia.");
        assertEquals(1, hero.getEnergy());
    }

    @Test
    void testEnergyRegenCard() {
        Hero hero = new Hero("Hero", 20, 1, 0); // Começa com 1
        EnergyRegenCard regen = new EnergyRegenCard("Ganhar Energia", 0, 2);

        // Alvo pode ser o próprio herói para evitar null
        boolean result = regen.useCard(hero, hero);

        assertTrue(result);
        assertEquals(3, hero.getEnergy(), "Deve gastar 0 e ganhar 2 (1+2=3).");
    }
}