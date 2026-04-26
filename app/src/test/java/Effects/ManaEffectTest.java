package Effects;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

import Core.GameData;
import Entities.Action;
import Entities.Hero;

class ManaEffectTest {
    @Test
    void testManaRegenOnSkip() {
        Hero hero = new Hero("Hero", 20, 1, 0); // Energia inicial: 1
        ManaEffect mana = new ManaEffect("Mana", hero, 2); // 2 Acúmulos
        
        Action skip = new Action();
        skip.setActionType(Action.ActionType.SKIP);

        // Executa o pulo de turno
        mana.beNotified(skip, new GameData());

        assertEquals(3, hero.getEnergy(), "A energia deve somar 1 (base) + 2 (efeito) = 3.");
        assertEquals(1, mana.getAmount(), "O acúmulo de mana deve cair para 1.");
    }
}