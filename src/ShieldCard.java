public class ShieldCard extends Card {
    private int shield;


    public ShieldCard(String name, int cost, int shield) {
        this.name = name;
        this.cost = cost;
        this.shield = shield;
    }


    @Override
    public boolean useCard(Hero user) {
        /* Uses Shield card and returns boolean based on success */

        int user_energy = user.getEnergy(); 

        if (user_energy < this.cost) {
            return false;
        }

        user.setEnergy(user_energy - this.cost); 
        user.gainShield(shield);
        return true;
    }


    public int getShield() {
        return shield;
    }
}
