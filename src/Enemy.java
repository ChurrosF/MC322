public class Enemy {
    private String name;
    private int life;
    private int shield;
    private final String enemy_sprite = """
        _
  ,-(_)-\"\"\"\"\"--,,
<  "             ";===""==,.
 `-../ )__... (  ,'        "==
   ==="    ,,==="
                """;


    public Enemy(String name, int life, int shield) {
        this.name = name;
        this.life = life;
        this.shield = shield;
    }


    public void receiveDamage(int damage) {
        if (damage >= this.shield) {
            this.life = Math.max(this.life - (damage - this.shield), 0);
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

    public String getEnemy_sprite() {
        return enemy_sprite;
    }
}

