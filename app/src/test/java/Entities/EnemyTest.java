package Entities;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

import Effects.StatusEffect;

class EnemyTest {
    @Test
    void testEnemyActions() {
        Hero hero = new Hero("Hero", 20, 3, 0);
        Enemy enemy = new Enemy("Spider", 15, 0, new int[]{2, 3});
        ArrayList<StatusEffect> globalEffects = new ArrayList<>();

        // Testa defesa
        enemy.defend();
        assertTrue(enemy.getShield() > 0);

        // Testa envenenar o herói
        enemy.poisonHero(hero, globalEffects);
        assertEquals(1, hero.getEffects().size(), "Herói deve ter recebido o efeito.");
        assertEquals(1, globalEffects.size(), "Efeito deve ir para a lista global.");

        // Testa ataque
        int initialLife = hero.getLife();
        enemy.attackHero(hero);
        assertTrue(hero.getLife() < initialLife, "Herói deve perder vida no ataque.");
    }
}