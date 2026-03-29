import java.util.Random;

public class Enemy extends Entity {
    private int[] damageRange;
    private int roundDamage;
    private final String enemySprite = """
 /\\ \\  / /\\
//\\\\ .. //\\\\
//\\((  ))/\\\\
/  < `' >  \\
                """;


    public Enemy(String name, int life, int shield, int[] damage_range) {
        this.name = name;
        this.life = life;
        this.maxLife = life;
        this.shield = shield;
        this.damageRange = damage_range;
        this.roundDamage = new Random().nextInt(damage_range[0], damage_range[1]);
    }


    public void attackHero(Hero hero) {
        /*Attacks hero with randomized round_damage, then randomizes damage for next round */
        hero.receiveDamage(roundDamage);
        this.roundDamage = new Random().nextInt(this.damageRange[0], this.damageRange[1]);
    }


    public String getEnemySprite() {
        return enemySprite;
    }


    public int[] getDamageRange() {
        return damageRange;
    }

    
    public void setDamageRange(int[] damage_range) {
        this.damageRange = damage_range;
    }


    public int getRoundDamage() {
        return roundDamage;
    }
}

