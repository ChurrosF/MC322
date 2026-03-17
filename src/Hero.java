public class Hero extends Entity {
    private int energy;
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


    public Hero(String name, int life, int energy, int shield) {
        this.name = name;
        this.life = life;
        this.energy = energy;
        this.shield = shield;
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