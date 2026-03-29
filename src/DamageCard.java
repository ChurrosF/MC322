public class DamageCard extends Card {
    private int damage;
    private Enemy target;


    public DamageCard(String name, int cost, int damage, Enemy target) {
        this.name = name;
        this.cost = cost;
        this.damage = damage;
        this.target = target;
        this.description = "Carta " + this.name + "  |" + " DMG:" + this.damage + " CUSTO:" + this.cost;
    }


    @Override
    public boolean useCard(Hero user) {
        // Uses Damage card and returns boolean based on success
        int user_energy = user.getEnergy();

        if (user_energy < this.cost) {
            return false;
        }

        user.setEnergy(user_energy - this.cost);
        target.receiveDamage(damage);
        return true;
    }


    public int getDamage() {
        return damage;
    }


    public void setTarget(Enemy target) {
        this.target = target;
    }
}
