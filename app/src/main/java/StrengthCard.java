public class StrengthCard extends EffectCard {
    private final int amountToAdd = 2;

    public StrengthCard(String name, int cost) {
        this.name = name;
        this.cost = cost;
        this.description = "Carta " + this.name + "  |" + " STR:" + amountToAdd + " CUSTO:" + this.cost;
    }

    @Override
    public boolean requiresTarget() {
        return false; 
    }

    @Override
    public boolean useCard(Hero user, Enemy target) {
        int user_energy = user.getEnergy();

        if (user_energy < this.cost) {
            return false;
        }

        user.setEnergy(user_energy - this.cost);

        StrengthEffect newEffect = new StrengthEffect("Força", user, amountToAdd);
        this.effect = user.applyEffect(user, newEffect, amountToAdd);
        
        return true;
    }
}