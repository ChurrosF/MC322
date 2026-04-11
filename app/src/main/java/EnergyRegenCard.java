public class EnergyRegenCard extends Card {
    private final int regenAmount;

    public EnergyRegenCard(String name, int cost, int regenAmount) {
        super(name, cost);
        this.regenAmount = regenAmount;
        this.description = this.name + " ".repeat(RendererConfig.VERTICAL_BAR_SIZE - 21 - this.name.length()) + "|" + " REG:" + this.regenAmount + " CUSTO:" + this.cost;
    }

    @Override
    public boolean useCard(Hero user, Entity target) {
        int user_energy = user.getEnergy(); 

        if (user_energy < this.cost) {
            return false;
        }

        user.setEnergy(user_energy - this.cost + this.regenAmount);
        return true;
    }

    
    
    @Override
    public boolean requiresTarget() {
        return false;
    }
}
