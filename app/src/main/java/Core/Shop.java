package Core;

import java.util.ArrayList;

import Cards.Card;
import Entities.Action;
import Entities.Hero;

public class Shop extends Event {
    private GameData data;
    private GameManager gameManager;
    private ArrayList<Card> sellingCards;
    private Hero hero;


    public void update(Action action) {
        if (action.getActionType() == Action.ActionType.BUY_CARD && isCardValid(action)) {
            ArrayList<Card> currentCards = data.getCurrentCards();
            ArrayList<Card> obtainableCards = data.getObtainableCards();

            int selectedIndex = action.getInputInt();
            currentCards.add(sellingCards.get(selectedIndex));
            obtainableCards.remove(sellingCards.get(selectedIndex));
            sellingCards.remove(selectedIndex);
        }
        else if (action.getActionType() == Action.ActionType.BACK) {
            gameManager.setState(GameState.MAP);
        }
        else {
            data.setInvalidAction(true);
        }

    }


    public boolean isCardValid(Action action) {
        int selectedIndex = action.getInputInt() - 1;

        if (selectedIndex >= this.sellingCards.size()) {
            this.data.setInvalidAction(true);
            return false;
        }
        return true;
    }


    public void setData(GameData data) {
        this.data = data;
    }


    public Hero getHero() {
        return hero;
    }


    public void setHero(Hero hero) {
        this.hero = hero;
    }


    public ArrayList<Card> getSellingCards() {
        return sellingCards;
    }


    public void setSellingCards(ArrayList<Card> sellingCards) {
        this.sellingCards = sellingCards;
    }
}
