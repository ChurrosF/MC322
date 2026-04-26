package Cards;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

import Entities.Enemy;
import Entities.Hero;

class CardTest {
    @Test
    void testDamageCardLogic() {
        // Criando as entidades
        Hero hero = new Hero("Hero", 20, 3, 0);
        // Usamos um range fixo [5, 6] para evitar erros no Random do Enemy
        Enemy enemy = new Enemy("Spider", 10, 0, new int[]{5, 6});
        
        // Criando a carta (Custo 1, Dano 5)
        DamageCard attack = new DamageCard("Ataque", 1, 5);

        // Execução
        boolean success = attack.useCard(hero, enemy);

        // Verificações
        assertTrue(success, "A carta deveria ter sido usada com sucesso.");
        assertEquals(2, hero.getEnergy(), "O herói deveria ter 2 de energia restando.");
        assertEquals(5, enemy.getLife(), "O inimigo deveria ter 5 de vida restando.");
    }
}