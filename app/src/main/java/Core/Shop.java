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
            Card boughtCard = sellingCards.get(selectedIndex);

            if (boughtCard.getPrice() <= hero.getMoney()) {
                obtainableCards.remove(boughtCard);
                currentCards.add(boughtCard);
                sellingCards.remove(selectedIndex);
                hero.setMoney(hero.getMoney() - boughtCard.getPrice());
            }
        }
        else if (action.getActionType() == Action.ActionType.BACK) {
            gameManager.setState(GameState.MAP);
        }
        else if (action.getActionType() == Action.ActionType.QUIT) {
            gameManager.closeGame();
        }
        else {
            data.setInvalidAction(true);
        }
    }


    public boolean isCardValid(Action action) {
        int selectedIndex = action.getInputInt();

        if (selectedIndex >= this.sellingCards.size() || selectedIndex < 0 || this.sellingCards.isEmpty()) {
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


    public void setGameManager(GameManager gameManager) {
        this.gameManager = gameManager;
    }
}
