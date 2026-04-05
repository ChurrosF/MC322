/**
 * Represents a discrete action taken by the player during their turn.
 * <p>
 * This class acts as a Data Transfer Object (DTO). It encapsulates 
 * the player's intent (captured by the Input System) and safely transports it to 
 * the Game Manager to be processed and executed.
 * </p>
 */
public class Action {

    /**
     * Defines the possible types of actions a player can perform.
     */
    public enum ActionType {
        /** Represents the intent to play a specific card from the hand. */
        CARD,
        
        /** Represents the intent to end the turn early and recover energy. */
        SKIP,
        
        /** Represents a request to prematurely surrender or exit the battle. */
        QUIT,
        
        /** Represents an unrecognized input or an action blocked by game rules. */
        INVALID;
    }

    /** * The index position of the chosen card in the player's hand array. 
     * Only relevant if the action_type is {@link ActionType#CARD}.
     */
    private int card_used_index;
    
    // Guarda o índice do inimigo que vai receber o ataque (0 para o primeiro, 1 para o segundo, etc)
    private int target_index = -1; 
    
    /** The specific category of action the player wants to execute. */
    private ActionType action_type;

    /**
     * Retrieves the index of the card intended to be played.
     *
     * @return The integer index representing the card's position in the hand.
     */
    public int getCardUsedIndex() {
        return card_used_index;
    }

    /**
     * Sets the index of the card the player wants to use.
     *
     * @param card_used_index The position of the card in the hand array (e.g., 0 for the first card).
     */
    public void setCardUsedIndex(int card_used_index) {
        this.card_used_index = card_used_index;
    }

    // Método que o GameManager está procurando para saber quem é o alvo!
    public int getTargetIndex() {
        return target_index;
    }

    // Setter para o InputSystem definir o alvo digitado pelo jogador
    public void setTargetIndex(int target_index) {
        this.target_index = target_index;
    }

    /**
     * Retrieves the category of the requested action.
     *
     * @return The current {@link ActionType} assigned to this action.
     */
    public ActionType getActionType() {
        return action_type;
    }

    /**
     * Defines the category of the action based on the player's input.
     *
     * @param action_type The specific {@link ActionType} to be assigned.
     */
    public void setActionType(ActionType action_type) {
        this.action_type = action_type;
    }
}