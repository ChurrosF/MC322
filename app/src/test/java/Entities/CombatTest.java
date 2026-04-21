package Entities;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import org.junit.jupiter.api.Test;

class CombatTest {
    @Test
    void testShieldAbsorption() {
        Hero hero = new Hero("Hero", 20, 3, 5); // Começa com 5 de escudo
        
        // Recebe 7 de dano. Deve tirar 5 do escudo e 2 da vida.
        hero.receiveDamage(7);

        assertEquals(0, hero.getShield(), "O escudo deve ser zerado.");
        assertEquals(18, hero.getLife(), "A vida deve cair apenas a diferença (2).");
    }

    @Test
    void testDeathLogic() {
        Enemy enemy = new Enemy("Spider", 5, 0, new int[]{1, 2});
        
        enemy.receiveDamage(10);

        assertFalse(enemy.isAlive(), "A entidade deve estar morta se vida <= 0.");
        assertEquals(0, enemy.getLife(), "A vida não deve ser negativa.");
    }
}