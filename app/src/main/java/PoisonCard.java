/**
 * Representa uma carta ofensiva que aplica acúmulos de veneno ao alvo selecionado.
 */
public class PoisonCard extends EffectCard {
    private final int amountToAdd;

    public PoisonCard(String name, int cost, int amountToAdd) {
        super(name, cost);
        this.amountToAdd = amountToAdd;
        this.description = this.name + " ".repeat(RendererConfig.VERTICAL_BAR_SIZE - 21 - this.name.length())
        + "|" + " PSN:" + amountToAdd + " CUSTO:" + this.cost;
    }

    @Override
    public boolean useCard(Hero user, Entity target) {
        PoisonEffect poisonEffect = new PoisonEffect("Veneno", target, amountToAdd);
        this.effect = poisonEffect;

        int user_energy = user.getEnergy();

        if (user_energy < this.cost) {
            return false;
        }

        user.setEnergy(user_energy - this.cost);

        if (poisonEffect.getAmount() == 0) {
            poisonEffect.setAmount(amountToAdd);
        }
        
        target.applyEffect(target, poisonEffect, amountToAdd);
        
        return true;
    }

    @Override
    public boolean requiresTarget() { return true; }
}