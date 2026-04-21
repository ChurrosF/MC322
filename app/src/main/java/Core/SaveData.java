package Core;

import java.util.ArrayList;

public class SaveData {
    public int heroLife;
    public int heroMaxLife;
    public int heroEnergy;
    
    public int battleRounds;
    public ArrayList<Integer> playerDeck;

    // Construtor vazio é obrigatório para o Gson conseguir recriar o objeto
    public SaveData() {}

    public SaveData(GameData data) {
        this.heroLife = data.getHero().getLife();
        this.heroMaxLife = data.getHero().getMaxLife();
        this.heroEnergy = data.getHero().getEnergy();
        this.battleRounds = data.getBattleRounds();
        
        // Junta mão, descarte e pilha de compra para salvar o baralho completo da run
        this.playerDeck = new ArrayList<>();
        this.playerDeck.addAll(data.getBuyPile());
        this.playerDeck.addAll(data.getPlayerHand());
        this.playerDeck.addAll(data.getDiscardPile());
    }
}