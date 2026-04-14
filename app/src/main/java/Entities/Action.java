package Entities;
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
        CHOOSE_CARD,

        CHOOSE_TARGET,

        BACK,
        
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
    private Integer CardHandIndex;

    private Integer targetIndex;
    
    /** The specific category of action the player wants to execute. */
    private ActionType actionType = null;

    /**
     * Retrieves the index of the card intended to be played.
     *
     * @return The integer index representing the card's position in the hand.
     */
    public Integer getCardUsedIndex() {
        return CardHandIndex;
    }

    /**
     * Sets the index of the card the player wants to use.
     *
     * @param cardHandIndex The position of the card in the hand array (e.g., 0 for the first card).
     */
    public void setCardUsedIndex(Integer cardHandIndex) {
        this.CardHandIndex = cardHandIndex;
    }


    public Integer getTargetIndex() {
        return targetIndex;
    }


    public void setTargetIndex(Integer targetIndex) {
        this.targetIndex = targetIndex;
    }


    /**
     * Retrieves the category of the requested action.
     *
     * @return The current {@link ActionType} assigned to this action.
     */
    public ActionType getActionType() {
        return actionType;
    }

    /**
     * Defines the category of the action based on the player's input.
     *
     * @param actionType The specific {@link ActionType} to be assigned.
     */
    public void setActionType(ActionType actionType) {
        this.actionType = actionType;
    }
}