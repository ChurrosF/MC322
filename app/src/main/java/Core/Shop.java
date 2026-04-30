package Core;

import Cards.Card;
import Entities.Action;
import Entities.Hero;

public class Shop extends Event {
    private GameData data;
    private Card[] sellingCards;
    private Hero hero;


    public void update(Action action) {
        if (action.getActionType() == Action.ActionType.BUY_CARD && isCardValid(action)) {
        
        }
    }


    public boolean isCardValid(Action action) {
        int selectedIndex = action.getInputInt() - 1;

        if (selectedIndex > this.sellingCards.length) {
            data.setInvalidAction(true);
            return false;
        }
        return true;
    }


    public Hero getHero() {
        return hero;
    }


    public void setHero(Hero hero) {
        this.hero = hero;
    }


    public Card[] getSellingCards() {
        return sellingCards;
    }


    public void setSellingCards(Card[] sellingCards) {
        this.sellingCards = sellingCards;
    }
}
