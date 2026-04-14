package Cards;

import Core.RendererConfig;
import Entities.Entity;
import Entities.Hero;

public class DamageCard extends Card {
    private int baseDamage;
    private int finalDamage;


    public DamageCard(String name, int cost, int baseDamage) {
        super(name, cost);
        this.baseDamage = baseDamage;
        this.finalDamage = this.baseDamage;
        this.description = this.name + " ".repeat(RendererConfig.VERTICAL_BAR_SIZE - 21 - this.name.length()) + "|" + " DMG:" + this.baseDamage + " CUSTO:" + this.cost;
    }


    @Override
    public boolean useCard(Hero user, Entity target) {
        // Uses Damage card and returns boolean based on success
        int user_energy = user.getEnergy();

        if (user_energy < this.cost) {
            return false;
        }

        user.setEnergy(user_energy - this.cost);
        target.receiveDamage(finalDamage);
        return true;
    }


    @Override
    public boolean requiresTarget() {
        return true;
    }


    public int getBaseDamage() {
        return baseDamage;
    }


    public void setBaseDamage(int damage) {
        this.baseDamage = damage;
    }


    public int getFinalDamage() {
        return finalDamage;
    }


    public void setFinalDamage(int finalDamage) {
        this.finalDamage = finalDamage;
    }
}
