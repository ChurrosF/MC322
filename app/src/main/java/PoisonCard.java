public class PoisonCard extends EffectCard {
    private final int amountToAdd = 3;

    public PoisonCard(String name, int cost) {
        this.name = name;
        this.cost = cost;
        this.description = "Carta " + this.name + " | PSN:" + amountToAdd + " CUSTO:" + this.cost;
    }

    @Override
    public boolean requiresTarget() {
        return true; 
    }

    @Override
    public boolean useCard(Hero user, Enemy target) {
        int user_energy = user.getEnergy();

        if (user_energy < this.cost || target == null) {
            return false;
        }

        user.setEnergy(user_energy - this.cost);

        PoisonEffect newEffect = new PoisonEffect("Veneno", target, amountToAdd);
        this.effect = target.applyEffect(target, newEffect, amountToAdd);
        
        return true;
    }
}