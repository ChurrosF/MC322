public class Enemy extends Entity {
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
        this.max_life = life;
        this.shield = shield;
    }


    public void attackHero(int damage, Hero hero) {
        hero.receiveDamage(damage);
    }


    public String getEnemy_sprite() {
        return enemy_sprite;
    }
}

