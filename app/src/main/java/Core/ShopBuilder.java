package Core;

import java.util.ArrayList;
import java.util.Random;

import Cards.Card;

public class ShopBuilder {
    public static Shop buildDefaultShop(GameData data, GameManager gm) {
        Shop shop = new Shop();
        shop.setData(data);
        shop.setHero(data.getHero());
        shop.setGameManager(gm);

        ArrayList<Card> obtainableCards = new ArrayList<>(data.getObtainableCards());
        ArrayList<Card> sellingCards = new ArrayList<>();

        for (int i = 0; i < 3; i++) {
            int randomIndex = new Random().nextInt(obtainableCards.size());
            sellingCards.add(obtainableCards.get(randomIndex));
            obtainableCards.remove(randomIndex);
        }

        shop.setSellingCards(sellingCards);
        data.setCurrentShop(shop);

        return shop;
    }
}
