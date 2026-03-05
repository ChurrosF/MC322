public class ShieldCard {
    String name;
    int shield;
    int cost;


    public ShieldCard(String name, int cost, int shield) {
        this.name = name;
        this.cost = cost;
        this.shield = shield;
    }


    public boolean useCard(Hero user) {
        /* Uses Shield card and returns boolean based on success */
        if (user.energy < this.cost) {
            return false;
        }

        user.energy = user.energy - this.cost;
        user.gainShield(shield);
        return true;
    }
}
