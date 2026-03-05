public class Enemy {
    String name;
    int life;
    int shield;

    
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
}

