/**
 * Efeito de Status: Força (Buff).
 * <p>
 * Quando o jogador escolhe um alvo (ação indicativa de que vai usar uma carta),
 * o efeito intercepta a jogada. Se a carta for de ataque, a Força soma o seu valor
 * ao dano final do ataque, consumindo um de seus acúmulos no processo.
 * </p>
 */
public class StrengthEffect extends StatusEffect {

       public StrengthEffect(String name, Entity owner, int amount) {
        this.name = name;
        this.owner = owner;
        this.amount = amount;
    }

    @Override
    public void beNotified(Action action, GameData data) {
        // Escuta ativamente a intenção de mirar em um alvo
        if (action.getActionType() == Action.ActionType.CHOOSE_TARGET) {
            int card_index = action.getCardUsedIndex();
            int card_type = data.getPlayerHand().get(card_index);
            Card card = data.getPossibleCards()[card_type];

            // Injeta o bônus de dano na carta do tipo DamageCard ANTES dela calcular o golpe final
            if (card instanceof DamageCard damageCard) {
                if (this.amount > 0) {
                    damageCard.setFinalDamage(damageCard.getBaseDamage() + this.amount);
                    this.amount--;
                }
            }
        }
    }
}