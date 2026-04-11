/**
 * Representa o personagem controlado pelo jogador.
 * Herda de {@link Entity} e adiciona a mecânica de pontos de energia para o uso de cartas,
 * além do seu próprio modelo visual (ASCII Sprite).
 */
public class Hero extends Entity {
    
    /** A quantidade de energia disponível para jogar cartas neste turno. */
    private int energy;
    
    /** A representação visual (ASCII Art) do herói para o renderizador. */
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

    /**
     * Construtor do Herói.
     * @param name   O nome de exibição do personagem.
     * @param life   A vida inicial e máxima.
     * @param energy A quantidade de energia máxima/inicial por turno.
     * @param shield O valor de escudo inicial (geralmente 0).
     */
    public Hero(String name, int life, int energy, int shield) {
        this.name = name;
        this.life = life;
        this.maxLife = life;
        this.energy = energy;
        this.shield = shield;
    }

    public int getEnergy() { return this.energy; }
    public void setEnergy(int energy) { this.energy = energy; }
    public String getHero_sprite() { return hero_sprite; }
}