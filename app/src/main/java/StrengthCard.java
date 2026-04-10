/**
 * Representa uma carta de buff que concede Força ao herói.
 * Aumenta temporariamente o dano da próxima carta de ataque jogada.
 */
public class StrengthCard extends EffectCard {
    private final int amountToAdd;

    public StrengthCard(String name, int cost, int amountToAdd) {
        super(name, cost);
        this.amountToAdd = amountToAdd;
        this.description = this.name + " ".repeat(RendererConfig.VERTICAL_BAR_SIZE - 21 - this.name.length())
        + "|" + " STR:" + amountToAdd + " CUSTO:" + this.cost;
    }

    @Override
    public boolean useCard(Hero user, Entity target) {
        StrengthEffect strengthEffect = new StrengthEffect("Força", target, amountToAdd);
        this.effect = strengthEffect;
        int user_energy = user.getEnergy();

        if (user_energy < this.cost) {
            return false;
        }

        user.setEnergy(user_energy - this.cost);

        if (strengthEffect.getAmount() == 0) {
            strengthEffect.setAmount(amountToAdd);
        }

        target.applyEffect(target, strengthEffect, amountToAdd);
        
        return true;
    }

    @Override
    public boolean requiresTarget() { return false; }
}