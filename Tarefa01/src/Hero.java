public class Hero {
    private String name;
    private int life;
    private int energy;
    private int shield;


    public Hero(String name) {
        this.name = name;
        this.life = 10;
        this.energy = 3;
        this.shield = 0;
    }


    public void receiveDamage(int damage) {
        if (damage >= this.shield) {
            this.life -= (damage - this.shield);
            this.shield = 0;
        }
        else {
            this.shield -= damage;
        }
    }


    public int getShield() {
        return this.shield;
    }

    
    public void gainShield(int shield) {
        this.shield += shield;
    }


    public boolean isAlive() {
        return (this.life > 0);
    }


    public String getName() {
        return this.name;
    }


    public int getEnergy() {
        return this.energy;
    }


    public void setEnergy(int energy) {
        this.energy = energy;
    }
}