import java.awt.Font;
import java.awt.FontFormatException;
import java.io.IOException;
import java.io.InputStream;

import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.swing.SwingTerminalFontConfiguration;

public class TerminalManager {
    private final int HEIGHT = 20;
    private final int WIDTH = 120;

    private static TerminalManager instance;
    private Screen screen;
    private TextGraphics textGraphics;

    public TerminalManager() {
        try {

            InputStream fontData = getClass().getResourceAsStream("DejaVuSansMono.ttf");
            Font font = Font.createFont(Font.TRUETYPE_FONT, fontData).deriveFont(Font.PLAIN, 18f);

            
            SwingTerminalFontConfiguration fontConfiguration = SwingTerminalFontConfiguration.newInstance(font);


            DefaultTerminalFactory terminalFactory = new DefaultTerminalFactory()
            .setInitialTerminalSize(new TerminalSize(WIDTH, HEIGHT))
            .setTerminalEmulatorFontConfiguration(fontConfiguration)
            .setTerminalEmulatorTitle("Deck Builder");

            this.screen = terminalFactory.createScreen();
            this.screen.startScreen();
            this.textGraphics = screen.newTextGraphics();
        }
        catch (FontFormatException | IOException e) {
            System.err.println("Terminal Manager: Erro ao criar Screen");
        }
    }


    public static TerminalManager getInstance() {
        if (instance == null) {
            instance = new TerminalManager();
        }
        return instance;
    }


    public Screen getScreen() {
        return this.screen;
    }


    public TextGraphics getTextGraphics() {
        return this.textGraphics;
    }


    public int getWidth() {
        return this.WIDTH;
    }


    public int getHeight(){
        return this.HEIGHT;
    }
}
