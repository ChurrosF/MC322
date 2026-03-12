public class Hero {
    private String name;
    private int life;
    private int energy;
    private int shield;
    private final String hero_sprite = """
    |
    |
    + \\
    \\.G_.*=.
    `(H'/.|
    .>' (_--.
_=/d   ,^\\
~~ \\)-'   '
    / |
    '  '  
""";


    public Hero(String name) {
        this.name = name;
        this.life = 10;
        this.energy = 3;
        this.shield = 0;
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


    public int getShield() {
        return this.shield;
    }


    public void setShield(int shield) {
        this.shield = shield;
    }

    
    public void gainShield(int shield) {
        this.shield += shield;
    }


    public boolean isAlive() {
        return (this.life > 0);
    }


    public int getLife() {
        return this.life;
    }


    public String getName() {
        return this.name;
    }

    
    public void setName(String name) {
        this.name = name;
    }


    public int getEnergy() {
        return this.energy;
    }


    public void setEnergy(int energy) {
        this.energy = energy;
    }

    
    public String getHero_sprite() {
        return hero_sprite;
    }
}