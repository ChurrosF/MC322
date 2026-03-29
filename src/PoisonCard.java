public class PoisonCard extends Card {
    private int poisonAmount;
    private Enemy target;

    public PoisonCard(String name, int cost, int poisonAmount, Enemy target) {
        this.name = name;
        this.cost = cost;
        this.poisonAmount = poisonAmount;
        this.target = target;
        this.description = "Carta " + this.name + "  |" + " PSN:" + this.poisonAmount + " CUSTO:" + this.cost;
    }

    @Override
    public boolean useCard(Hero user) {
        int user_energy = user.getEnergy();

        if (user_energy < this.cost) {
            return false;
        }

        user.setEnergy(user_energy - this.cost);
        
        // Creates the contition and call the apply
        PoisonEffect effect = new PoisonEffect("Veneno", target, poisonAmount);
        target.applyEffect(target, effect, poisonAmount);
        
        return true;
    }
}