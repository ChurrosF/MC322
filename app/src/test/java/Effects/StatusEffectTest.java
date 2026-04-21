package Effects;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

import Core.GameData;
import Entities.Action;
import Entities.Hero;

class StatusEffectTest {
    @Test
    void testPoisonStackingAndDamage() {
        Hero hero = new Hero("Hero", 20, 3, 0);
        PoisonEffect p1 = new PoisonEffect("Veneno", hero, 2);
        
        // Simula a aplicação de veneno duas vezes (acumulando 2 + 3 = 5)
        hero.applyEffect(hero, p1, 2);
        hero.applyEffect(hero, p1, 3);

        assertEquals(5, hero.getEffects().get(0).getAmount(), "O acúmulo deve ser a soma (5).");

        // Simula o fim do turno (SKIP)
        Action skip = new Action();
        skip.setActionType(Action.ActionType.SKIP);
        
        p1.beNotified(skip, new GameData());

        assertEquals(15, hero.getLife(), "Dano deve ser igual aos acúmulos antes do decréscimo.");
        assertEquals(4, p1.getAmount(), "Acúmulo deve cair para 4 após o dano.");
    }
}