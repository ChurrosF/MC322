import java.util.Random;

public class Enemy extends Entity {
    private int[] damage_range;
    private final String enemy_sprite = """
        _
  ,-(_)-\"\"\"\"\"--,,
<  "             ";===""==,.
 `-../ )__... (  ,'        "==
   ==="    ,,==="
                """;


    public Enemy(String name, int life, int shield, int[] damage_range) {
        this.name = name;
        this.life = life;
        this.max_life = life;
        this.shield = shield;
        this.damage_range = damage_range;
    }


    public void attackHero(Hero hero) {
        int min_damage = damage_range[0];
        int max_damage = damage_range[1];
        int damage = new Random().nextInt(min_damage, max_damage);
        hero.receiveDamage(damage);
    }


    public String getEnemy_sprite() {
        return enemy_sprite;
    }


    public int[] getDamage_range() {
        return damage_range;
    }

    
    public void setDamage_range(int[] damage_range) {
        this.damage_range = damage_range;
    }
}

