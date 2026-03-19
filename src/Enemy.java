import java.util.Random;

public class Enemy extends Entity {
    private int[] damage_range;
    private int round_damage;
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
        this.round_damage = new Random().nextInt(damage_range[0], damage_range[1]);
    }


    public void attackHero(Hero hero) {
        hero.receiveDamage(round_damage);
        this.round_damage = new Random().nextInt(this.damage_range[0], this.damage_range[1]);
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


    public int getRound_damage() {
        return round_damage;
    }
}

