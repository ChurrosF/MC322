public class DamageCard {
    private String name;
    private int damage;
    private int cost;


    public DamageCard(String name, int cost, int damage) {
        this.name = name;
        this.cost = cost;
        this.damage = damage;
    }


    public boolean useCard(Hero user, Enemy target) {
        /* Uses Damage card and returns boolean based on success */

        int user_energy = user.getEnergy(); 

        if (user_energy < this.cost) {
            return false;
        }

        user.setEnergy(user_energy - this.cost);
        target.receiveDamage(this.damage);
        return true;
    }

    
    public String getName() {
        return this.name;
    }


    public int getCost() {
        return cost;
    }

    
    public int getDamage() {
        return damage;
    }
}
