package Effects;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

import Cards.DamageCard;
import Core.GameData;
import Entities.Action;

class StrengthTest {
    @Test
    void testStrengthIncreasesDamage() {
        GameData data = new GameData();
        // Limpa a mão aleatória e força a carta 0 (Ataque Leve) no índice 0
        data.getPlayerHand().clear();
        data.getPlayerHand().add(0); 
        
        StrengthEffect str = new StrengthEffect("Força", data.getHero(), 3);

        Action action = new Action();
        action.setActionType(Action.ActionType.CHOOSE_TARGET);
        action.setInputInt(0);
        
        str.beNotified(action, data);

        // O Ataque Leve tem dano base 3. Com +3 de Força, deve ir para 6.
        DamageCard attack = (DamageCard) data.getPossibleCards()[0];
        assertEquals(6, attack.getFinalDamage(), "O dano deve somar a força.");
        assertEquals(2, str.getAmount(), "A força deve gastar 1 acúmulo.");
    }
}