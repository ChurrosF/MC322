public class ShieldCard {
    private String name;
    private int shield;
    private int cost;


    public ShieldCard(String name, int cost, int shield) {
        this.name = name;
        this.cost = cost;
        this.shield = shield;
    }


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

    
    public String getName() {
        return this.name;
    }

    
    public int getCost() {
        return cost;
    }


    public int getShield() {
        return shield;
    }
}
