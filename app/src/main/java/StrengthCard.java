public class StrengthCard extends EffectCard {
    private Entity target;
    private final int amountToAdd = 2;
    private final StrengthEffect strengthEffect = new StrengthEffect("Força", target, amountToAdd);

    public StrengthCard(String name, int cost, Entity target) {
        this.name = name;
        this.cost = cost;
        this.target = target;
        this.effect = strengthEffect;
        this.description = this.name + " ".repeat(RendererConfig.VERTICAL_BAR_SIZE - 21 - this.name.length()) + "|" + " STR:" + strengthEffect.getAmount() + " CUSTO:" + this.cost;
    }

    @Override
    public boolean useCard(Hero user) {
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
    
}
