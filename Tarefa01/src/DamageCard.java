public class DamageCard {
    String name;
    int damage;
    int cost;


    public DamageCard(String name, int cost, int damage) {
        this.name = name;
        this.cost = cost;
        this.damage = damage;
    }


    public boolean useCard(Hero user, Enemy target) {
        /* Uses card and returns boolean based on success */
        if (user.energy < this.cost) {
            return false;
        }

        user.energy = user.energy - this.cost;
        target.receiveDamage(this.damage);
        return true;
    }
}
