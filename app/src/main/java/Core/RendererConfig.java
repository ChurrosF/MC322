package Core;
public class RendererConfig {
    // ==========================================
    // UI LAYOUT COORDINATES [Line, Column]
    // ==========================================
    
    /** Height of the vertical separator line dividing the UI (Cards) from the Battle Screen. */
    public static final int VERTICAL_BAR_SIZE = 36;
    public static final int[] VERTICAL_BAR_POSITION = {1, VERTICAL_BAR_SIZE};

    public static final int[] HP_BAR_POSITION = {4, 38};
    public static final int[] SHIELD_COUNTER_POSITION = {4, 63}; 
    public static final int[] ENERGY_BAR_POSITION = {1, 2};
    public static final int[] NO_ENERGY_WARNING_POSITION = {1, 15};

    public static final int DECK_TEXT_LINE = 6;
    public static final int[] BUY_PILE_POSITION = {3, 2};
    public static final int[] DISCARD_PILE_POSITION = {4, 2};

    public static final int[] HERO_POSITION = {6, 38};
    public static final int[] ENEMY_POSITION = {9, 77};
}
