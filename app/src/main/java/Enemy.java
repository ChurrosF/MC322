import java.util.ArrayList;
import java.util.Random;

public class Enemy extends Entity {
    private int[] damageRange;
    private int roundDamage;
    private final int poisonAmount = 2;
    private final int shieldToAdd = 8;
    private EnemyAction action;
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
        this.action = EnemyAction.ATTACK;
    }


    public void decideAction() {
        int actionInt = new Random().nextInt(0,  3);
        switch (actionInt) {
            case 0 -> {
                this.action = EnemyAction.ATTACK;
            }
            case 1 -> {
                this.action = EnemyAction.POISON;
            }
            case 2 -> {
                this.action = EnemyAction.DEFEND;
            }
        }

    }


    public void executeAction(Hero hero, ArrayList<StatusEffect> effectSubscribers) {
        switch (this.action) {
            case ATTACK -> {
                attackHero(hero);
            }
            case POISON -> {
                poisonHero(hero, effectSubscribers);
            }
            case DEFEND -> {
                defend();
            }
        }
        decideAction();
    }


    public void attackHero(Hero hero) {
        /*Attacks hero with randomized round_damage, then randomizes damage for next round */
        hero.receiveDamage(roundDamage);
        this.roundDamage = new Random().nextInt(this.damageRange[0], this.damageRange[1]);
    }


    public void poisonHero(Hero hero, ArrayList<StatusEffect> effectSubscribers) {
        PoisonEffect poisonEffect = new PoisonEffect("Veneno", hero, this.poisonAmount);
        hero.applyEffect(hero, poisonEffect, this.poisonAmount);
        effectSubscribers.add(poisonEffect);
    }


    public void defend() {
        this.shield = this.shieldToAdd;
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


    public EnemyAction getEnemyAction() {
        return this.action;
    }


    public int getShieldToAdd() {
        return this.shieldToAdd;           
    }


    public int getPoisonAmount() {
        return this.poisonAmount;
    }
}

