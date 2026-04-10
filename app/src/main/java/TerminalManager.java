import java.awt.Font;
import java.awt.FontFormatException;
import java.io.IOException;
import java.io.InputStream;

import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.swing.SwingTerminalFontConfiguration;

/**
 * Gestor principal para a interface de utilizador baseada em terminal (ASCII).
 * <p>
 * Utiliza o padrão de desenho Singleton para garantir que apenas uma única janela 
 * de terminal esteja aberta durante toda a execução do jogo.
 * Esta classe abstrai a configuração da biblioteca Lanterna, gerindo o tamanho do ecrã
 * e carregando fontes monoespaçadas (essencial para manter a ASCII art perfeitamente alinhada).
 * </p>
 */
public class TerminalManager {
    
    /** Altura padrão do ecrã do terminal em linhas de caracteres. */
    private final int HEIGHT = 22;
    
    /** Largura padrão do ecrã do terminal em colunas de caracteres. */
    private final int WIDTH = 140;

    /** A única instância global desta classe (Singleton). */
    private static TerminalManager instance;
    
    /** O ecrã (Screen) do Lanterna, utilizado para atualizar e gerir o buffer visual. */
    private Screen screen;
    
    /** O motor de desenho, utilizado para imprimir texto, linhas e artes ASCII no ecrã. */
    private TextGraphics textGraphics;

    /**
     * Construtor do Gestor de Terminal.
     * <p>
     * Inicializa o ecrã gráfico do Lanterna, carrega a fonte personalizada 
     * {@code DejaVuSansMono.ttf} (para evitar distorção da arte), e define 
     * o título e tamanho inicial da janela. Caso a fonte não seja encontrada,
     * o erro é capturado e uma mensagem é enviada para a saída de erro (stderr).
     * </p>
     */
    public TerminalManager() {
        try {
            InputStream fontData = getClass().getResourceAsStream("DejaVuSansMono.ttf");
            Font font = Font.createFont(Font.TRUETYPE_FONT, fontData).deriveFont(Font.PLAIN, 18f);

            SwingTerminalFontConfiguration fontConfiguration = SwingTerminalFontConfiguration.newInstance(font);

            DefaultTerminalFactory terminalFactory = new DefaultTerminalFactory()
            .setInitialTerminalSize(new TerminalSize(WIDTH, HEIGHT))
            .setTerminalEmulatorFontConfiguration(fontConfiguration)
            .setTerminalEmulatorTitle("Slay the Tuff Rat");

            this.screen = terminalFactory.createScreen();
            this.screen.startScreen();
            this.textGraphics = screen.newTextGraphics();
        }
        catch (FontFormatException | IOException e) {
            System.err.println("(TerminalManager) Error when creating screen: " + e);
        }
    }

    /**
     * Recupera a única instância do Gestor de Terminal.
     * Implementa Inicialização Tardia (Lazy Initialization), criando o terminal apenas 
     * quando este método for chamado pela primeira vez.
     *
     * @return A instância ativa e configurada de {@link TerminalManager}.
     */
    public static TerminalManager getInstance() {
        if (instance == null) {
            instance = new TerminalManager();
        }
        return instance;
    }

    /**
     * Recupera o objeto Screen atual.
     * <p>
     * Geralmente utilizado dentro do loop principal do jogo para atualizar
     * ou limpar o ecrã a cada novo turno.
     * </p>
     * @return O objeto {@link Screen} gerido por esta classe.
     */
    public Screen getScreen() {
        return this.screen;
    }

    /**
     * Recupera a ferramenta gráfica para desenhar no terminal.
     * <p>
     * Objeto fundamental para a renderização de strings, formas e cores na interface.
     * </p>
     * @return O objeto {@link TextGraphics} pronto para impressão de caracteres.
     */
    public TextGraphics getTextGraphics() {
        return this.textGraphics;
    }

    /**
     * @return A largura máxima do terminal (em número de colunas/caracteres).
     */
    public int getWidth() {
        return this.WIDTH;
    }

    /**
     * @return A altura máxima do terminal (em número de linhas).
     */
    public int getHeight(){
        return this.HEIGHT;
    }
}