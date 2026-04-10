/**
 * Classe de configuração contendo constantes estáticas para o layout visual do jogo.
 * <p>
 * Centraliza as posições (em coordenadas [linha, coluna]) de todos os elementos 
 * da interface no terminal. Isso facilita os ajustes no design gráfico e alinhamento 
 * sem ter que alterar a lógica interna de renderização na classe {@link Renderer}.
 * </p>
 */
public class RendererConfig {
    // ==========================================
    // COORDENADAS DE LAYOUT DA UI [Linha, Coluna]
    // ==========================================
    
    /** Altura (tamanho) da linha separadora vertical que divide a UI (Cartas) da Tela de Batalha. */
    public static final int VERTICAL_BAR_SIZE = 36;
    
    /** Posição [linha, coluna] onde a barra vertical começa a ser desenhada. */
    public static final int[] VERTICAL_BAR_POSITION = {1, VERTICAL_BAR_SIZE};

    /** Posição [linha, coluna] da barra de vida (HP) do herói. */
    public static final int[] HP_BAR_POSITION = {4, 38};
    
    /** Posição [linha, coluna] do contador numérico de escudo do herói. */
    public static final int[] SHIELD_COUNTER_POSITION = {4, 63}; 
    
    /** Posição [linha, coluna] do texto e contador de energia atual do herói. */
    public static final int[] ENERGY_BAR_POSITION = {1, 2};
    
    /** Posição [linha, coluna] da mensagem de aviso exibida quando o jogador tenta agir sem energia. */
    public static final int[] NO_ENERGY_WARNING_POSITION = {1, 15};

    /** Linha onde o cabeçalho "Mão:" e a lista de cartas começam a ser renderizados. */
    public static final int DECK_TEXT_LINE = 6;
    
    /** Posição [linha, coluna] do indicador de quantidade de cartas na Pilha de Compra. */
    public static final int[] BUY_PILE_POSITION = {3, 2};
    
    /** Posição [linha, coluna] do indicador de quantidade de cartas na Pilha de Descarte. */
    public static final int[] DISCARD_PILE_POSITION = {4, 2};

    /** Posição base [linha, coluna] (âncora) para desenhar o sprite e os status do Herói. */
    public static final int[] HERO_POSITION = {6, 38};
    
    /** Posição base [linha, coluna] (âncora) para desenhar o primeiro Inimigo da lista. */
    public static final int[] ENEMY_POSITION = {9, 77};
}