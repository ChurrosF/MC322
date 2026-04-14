package Effects;
import Core.GameData;
import Entities.Action;
import Entities.Entity;

/**
 * Efeito de Status: Veneno (Debuff).
 * <p>
 * Danifica a entidade que o possui ao final de cada turno e, em seguida, perde
 * uma carga (acúmulo). Inspirado na mecânica de Poison do jogo Slay the Spire.
 * </p>
 */
public class PoisonEffect extends StatusEffect {

    public PoisonEffect(String name, Entity owner, int amount) {
        this.name = name;
        this.owner = owner;
        this.amount = amount;
    }

    @Override
    public void beNotified(Action action, GameData data) {
        // Causa dano contínuo apenas no momento em que o turno é encerrado
        if (action.getActionType() == Action.ActionType.SKIP) {
            if (this.amount > 0) {
                this.owner.receiveDamage(this.amount);
                this.amount--;
            }
        }
    }
}