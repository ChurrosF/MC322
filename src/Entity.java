import java.util.ArrayList;

public abstract class Entity {
    protected String name;
    protected int life;
    protected int max_life;
    protected int shield;
    protected ArrayList<StatusEffect> effects = new ArrayList<>();


    public void receiveDamage(int damage) {
        if (damage >= this.shield) {
            this.life = Math.max(this.life - (damage - this.shield), 0);
            this.shield = 0;
        }
        else {
            this.shield -= damage;
        }
    }


    public int getShield() {
        return this.shield;
    }


    public void setShield(int shield) {
        this.shield = shield;
    }

    
    public void gainShield(int shield) {
        this.shield += shield;
    }


    public boolean isAlive() {
        return (this.life > 0);
    }


    public int getLife() {
        return this.life;
    }


    public int getMaxLife() {
        return this.max_life;
    }


    public String getName() {
        return this.name;
    }

    
    public void setName(String name) {
        this.name = name;
    }

    public int getStatusValue(String type) {
        for (StatusEffect effect : effects) {
            if (effect.getType().equals(type)) {
                return effect.getAmount();
            }
        }
        return 0;
    }

    public void applyStatus(String type, int amount) {
        for (StatusEffect effect : effects) {
            if (effect.getType().equals(type)) {
                effect.addAmount(amount);
                return;
            }
        }
        // If the aplly effect isn't in the array yet, creates it
        effects.add(new StatusEffect(type, amount));
    }
}