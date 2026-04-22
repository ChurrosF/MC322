package Effects;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.Test;

import Entities.Hero;

class EffectExtraTest {
    @Test
    void testEffectBaseMethods() {
        Hero hero1 = new Hero("Hero1", 20, 3, 0);
        PoisonEffect p = new PoisonEffect("Veneno", hero1, 2);
        
        assertEquals("Veneno", p.getName());
        assertEquals(hero1, p.getOwner());
        assertNotNull(p.getString());
        
        Hero hero2 = new Hero("Hero2", 20, 3, 0);
        p.setOwner(hero2); // Testa o setOwner
        assertEquals(hero2, p.getOwner());
    }
}