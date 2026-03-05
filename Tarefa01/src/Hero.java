public class Hero {
    String name;
    int life;
    int energy;
    int shield;


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


    public void gainShield(int shield) {
        this.shield += shield;
    }


    public boolean isAlive() {
        return (this.life > 0);
    }
}