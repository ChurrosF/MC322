package Cards;

import Entities.Entity;
import Entities.Hero;

/**
 * The abstract base class for all playable cards in the game.
 * <p>
 * This class establishes the foundational blueprint for any card in the DeckBuilder.
 * It holds the basic attributes shared by all cards (name, description, and energy cost)
 * and forces all specific card types (subclasses) to implement their unique logic 
 * through the <code>useCard</code> method.
 * </p>
 */
public abstract class Card {
    
    /** The display name of the card. */
    protected String name;
    
    /** The visual text explaining the card's effect to the player. */
    protected String description;
    
    /** The amount of energy required from the hero to play this card. */
    protected int cost;


    public Card(String name, int cost) {
        this.name = name;
        this.cost = cost;
    }

    
    /**
     * Executes the specific effect of the card when played by the hero.
     * <p>
     * Subclasses must implement this method to define whether the card deals damage,
     * applies a status effect, or provides a shield. The method should also evaluate
     * if the hero meets the conditions to play it (such as having enough energy).
     * </p>
     *
     * @param user The {@link Hero} entity that is attempting to play this card.
     * @return <code>true</code> if the card was successfully used, or <code>false</code> 
     * if the action failed (e.g., insufficient energy).
     */
    public abstract boolean useCard(Hero user, Entity target);

    public abstract boolean requiresTarget();
    /**
     * Retrieves the display name of the card.
     *
     * @return The card's name.
     */
    public String getName() {
        return this.name;
    }

    /**
     * Retrieves the energy cost required to play the card.
     *
     * @return The cost in energy points.
     */
    public int getCost() {
        return this.cost;
    }

    /**
     * Retrieves the descriptive text of the card's effect.
     *
     * @return The description string.
     */
    public String getDescription() {
        return this.description;
    }
}