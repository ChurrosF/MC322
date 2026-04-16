package States;

import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;

import Core.GameData;

public abstract class RenderHandler {
    protected TextGraphics textGraphics;


    public RenderHandler(TextGraphics textGraphics) {
        this.textGraphics = textGraphics;
    }


    public abstract void renderState(GameData gameData);


    protected void placeText(int[] position, String text) {
        int line = position[0];
        int column = position[1];

        String[] lines = text.split("\n");

        for (int i = 0; i < lines.length; i++) {
            textGraphics.putString(column, line + i, lines[i]);
        }
    }


    /**
     * Prints a text string at a specific coordinate using a custom color.
     * After drawing, resets the foreground color back to standard White to prevent color bleeding.
     *
     * @param position An array containing {@code [line, column]}.
     * @param text     The text to be drawn.
     * @param color    The Lanterna {@link TextColor} to apply to the text.
     */
    protected void placeText(int[] position, String text, TextColor color) {
        textGraphics.setForegroundColor(color);
        placeText(position, text);
        textGraphics.setForegroundColor(TextColor.ANSI.WHITE);
    }
}
