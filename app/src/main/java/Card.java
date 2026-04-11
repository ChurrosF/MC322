/**
 * A classe base abstrata para todas as cartas jogáveis no jogo.
 * <p>
 * Esta classe estabelece a estrutura fundamental para qualquer carta no DeckBuilder.
 * Ela guarda os atributos básicos compartilhados por todas as cartas (nome, descrição e custo de energia)
 * e obriga todas as cartas específicas (subclasses) a implementarem sua própria lógica
 * através do método {@code useCard}.
 * </p>
 */
public abstract class Card {
    
    /** O nome de exibição da carta. */
    protected String name;
    
    /** O texto visual que explica o efeito da carta para o jogador na tela. */
    protected String description;
    
    /** A quantidade de energia exigida do herói para jogar esta carta. */
    protected int cost;

    /**
     * Construtor base para inicializar os atributos fundamentais da carta.
     * @param name O nome da carta.
     * @param cost O custo de energia.
     */
    public Card(String name, int cost) {
        this.name = name;
        this.cost = cost;
    }
    
    /**
     * Executa o efeito específico da carta quando jogada pelo herói.
     * <p>
     * Subclasses devem implementar este método para definir se a carta causa dano,
     * aplica um status, gera escudo, etc. O método também deve avaliar se o herói
     * cumpre as condições para jogá-la (como possuir energia suficiente).
     * </p>
     *
     * @param user   A entidade {@link Hero} que está tentando jogar a carta.
     * @param target O alvo da carta (pode ser null se a carta não exigir alvo).
     * @return {@code true} se a carta foi usada com sucesso, ou {@code false} se a ação falhou.
     */
    public abstract boolean useCard(Hero user, Entity target);

    /**
     * Define se a interface do jogo precisa pedir ao jogador para selecionar um alvo
     * inimigo antes de usar esta carta.
     * @return {@code true} se necessitar de alvo, {@code false} caso contrário.
     */
    public abstract boolean requiresTarget();

    public String getName() { return this.name; }
    public int getCost() { return this.cost; }
    public String getDescription() { return this.description; }
}