public class Enemy {
    private String name;
    private int life;
    private int shield;


    public Enemy(String name, int life, int shield) {
        this.name = name;
        this.life = life;
        this.shield = shield;
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


    public void attackHero(int damage, Hero hero) {
        hero.receiveDamage(damage);
    }


    public boolean isAlive() {
        return (this.life > 0);
    }


    public String getName() {
        return this.name;
    }


    public int getLife() {
        return this.life;
    }


    public int getShield() {
        return this.shield;
    }
}

