public class DamageCard extends Card {
    private int baseDamage;
    private int finalDamage;
    private Enemy target;


    public DamageCard(String name, int cost, int baseDamage, Enemy target) {
        this.name = name;
        this.cost = cost;
        this.baseDamage = baseDamage;
        this.finalDamage = this.baseDamage;
        this.target = target;
        this.description = this.name + " ".repeat(RendererConfig.VERTICAL_BAR_SIZE - 21 - this.name.length()) + "|" + " DMG:" + this.baseDamage + " CUSTO:" + this.cost;
    }


    @Override
    public boolean useCard(Hero user) {
        // Uses Damage card and returns boolean based on success
        int user_energy = user.getEnergy();

        if (user_energy < this.cost) {
            return false;
        }

        user.setEnergy(user_energy - this.cost);
        target.receiveDamage(finalDamage);
        return true;
    }


    public int getBaseDamage() {
        return baseDamage;
    }


    public void setBaseDamage(int damage) {
        this.baseDamage = damage;
    }


    public void setTarget(Enemy target) {
        this.target = target;
    }


    public int getFinalDamage() {
        return finalDamage;
    }


    public void setFinalDamage(int finalDamage) {
        this.finalDamage = finalDamage;
    }
}
