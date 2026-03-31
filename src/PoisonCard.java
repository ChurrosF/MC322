public class PoisonCard extends EffectCard {
    private Enemy target;
    private final int amountToAdd = 3;
    private final PoisonEffect poisonEffect = new PoisonEffect("Veneno", target, amountToAdd);

    public PoisonCard(String name, int cost, Enemy target) {
        this.name = name;
        this.cost = cost;
        this.target = target;
        this.effect = poisonEffect;
        this.description = "Carta " + this.name + " |" + " PSN:" + poisonEffect.getAmount() + " CUSTO:" + this.cost;
    }

    @Override
    public boolean useCard(Hero user) {
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
}