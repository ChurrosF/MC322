package Core;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class SaveManager {
    private static final String SAVE_PATH = "STTF_save.json";
    private final Gson gson;

    public SaveManager() {
        this.gson = new GsonBuilder().setPrettyPrinting().create();
    }

    public void save(GameData data) {
        SaveData saveData = new SaveData(data);
        
        try (FileWriter writer = new FileWriter(SAVE_PATH)) {
            gson.toJson(saveData, writer);
        } catch (IOException e) {
        }
    }

    public SaveData load() {
        try (FileReader reader = new FileReader(SAVE_PATH)) {
            return gson.fromJson(reader, SaveData.class);
        } catch (IOException e) {
            return null;
        }
    }
    
    public void applySaveToGame(GameData data, SaveData save) {
        if (save == null) return;

        data.getHero().setEnergy(save.heroEnergy);
        
        // Zera as cartas atuais e joga o baralho salvo inteiro na pilha de compra
        // Acho que esse seria o melhor jeito por agora visto que ainda não fizemos mais de 1 luta
        data.getBuyPile().clear();
        data.getDiscardPile().clear();
        data.getPlayerHand().clear();
        
        data.getBuyPile().addAll(save.playerDeck);
        data.buyRoundCards(); 
    }
}