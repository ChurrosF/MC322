import java.util.ArrayList;

public abstract class Entity {
    protected String name;
    protected int life;
    protected int maxLife;
    protected int shield;
    private final ArrayList<StatusEffect> effects = new ArrayList<>();


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
        return this.maxLife;
    }


    public String getName() {
        return this.name;
    }

    
    public void setName(String name) {
        this.name = name;
    }

    public StatusEffect applyEffect(Entity target, StatusEffect effectToApply, int amount) {
        for (StatusEffect effect: target.effects) {
            // Se já tem esse efeito (ex: já tá envenenado)
            if (effect.getClass() == effectToApply.getClass()) {
                effect.addAmount(amount);
                return effect; // Retorna o efeito que já existia com o acúmulo novo
            }
        }
        // Se é um efeito novo
        target.effects.add(effectToApply);
        effectToApply.setOwner(target);
        return effectToApply; // Retorna o efeito recém-criado
    }
    
    public ArrayList<StatusEffect> getEffects() {
        return effects;
    }

}