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
 * Main manager for the terminal-based user interface (ASCII).
 * <p>
 * Uses the Singleton design pattern to ensure that only a single terminal window 
 * is open during the entire execution of the "Slay the Tuff Rat" game.
 * This class abstracts the Lanterna library configuration, managing the screen size
 * and loading monospaced fonts (essential for keeping the ASCII art of cards 
 * and enemies perfectly aligned).
 * </p>
 * @see com.googlecode.lanterna.screen.Screen
 * @see com.googlecode.lanterna.graphics.TextGraphics
 */
public class TerminalManager {
    
    /** Default height of the terminal screen in character lines. */
    private final int HEIGHT = 22;
    
    /** Default width of the terminal screen in character columns. */
    private final int WIDTH = 140;

    /** The single, global instance of this class (Singleton). */
    private static TerminalManager instance;
    
    /** The Lanterna screen, used to refresh and manage the visual buffer. */
    private Screen screen;
    
    /** The drawing engine, used to print text, lines, and ASCII arts on the screen. */
    private TextGraphics textGraphics;

    /**
     * Constructs the Terminal Manager.
     * <p>
     * Initializes the Lanterna graphical screen, loads the custom font 
     * {@code DejaVuSansMono.ttf} (to prevent art distortion), and sets 
     * the title and initial size of the window.
     * </p>
     * <p><b>Implementation Note:</b> If the font is not found or an I/O error occurs, 
     * the game does not crash, but issues a warning to the standard error stream.</p>
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
     * Retrieves the single instance of the Terminal Manager.
     * Implements Lazy Initialization, creating the terminal only when 
     * this method is called for the first time.
     *
     * @return The active and configured instance of {@link TerminalManager}.
     */
    public static TerminalManager getInstance() {
        if (instance == null) {
            instance = new TerminalManager();
        }
        return instance;
    }

    /**
     * Gets the current Screen object.
     * <p>
     * Generally used within the main Game Loop to call the 
     * {@code screen.refresh()} method after drawing a frame, 
     * or {@code screen.clear()} to clear the screen for the next turn.
     * </p>
     * @return The {@link Screen} object managed by this class.
     */
    public Screen getScreen() {
        return this.screen;
    }

    /**
     * Gets the graphical tool for drawing on the terminal.
     * <p>
     * A fundamental object for rendering the interface. Example usage:
     * <pre>
     * TextGraphics tg = terminalManager.getTextGraphics();
     * tg.putString(10, 5, "HP: 50/50");
     * </pre>
     * @return The {@link TextGraphics} object ready for character printing.
     */
    public TextGraphics getTextGraphics() {
        return this.textGraphics;
    }

    /**
     * @return The maximum width of the terminal (in number of columns/characters).
     */
    public int getWidth() {
        return this.WIDTH;
    }

    /**
     * @return The maximum height of the terminal (in number of lines).
     */
    public int getHeight(){
        return this.HEIGHT;
    }
}