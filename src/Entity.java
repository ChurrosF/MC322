public abstract class Entity {
    protected String name;
    protected int life;
    protected int max_life;
    protected int shield;


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
}