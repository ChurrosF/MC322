public class ShieldCard extends Card {
    private int shield;


    public ShieldCard(String name, int cost, int shield) {
        super(name, cost);
        this.shield = shield;
        this.description = this.name + " ".repeat(RendererConfig.VERTICAL_BAR_SIZE - 21 - this.name.length()) + "|" + " SHD:" + this.shield + " CUSTO:" + this.cost;
    }


    @Override
    public boolean useCard(Hero user, Entity target) {
        /* Uses Shield card and returns boolean based on success */

        int user_energy = user.getEnergy(); 

        if (user_energy < this.cost) {
            return false;
        }

        user.setEnergy(user_energy - this.cost); 
        user.gainShield(shield);
        return true;
    }


    @Override
    public boolean requiresTarget() {
        return false;
    }


    public int getShield() {
        return shield;
    }
}
