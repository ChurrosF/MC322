package Core;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

import Entities.Action;

class GameManagerTargetingTest {
    @Test
    void testCardAndTargetingFlow() {
        GameManager gm = new GameManager();
        GameData data = gm.getGameData();

        // Limpa a mão e força o índice 0 (Ataque Leve - requer alvo)
        data.getPlayerHand().clear();
        data.getPlayerHand().add(0);

        // 1. Jogador escolhe a carta de ataque
        Action chooseCard = new Action();
        chooseCard.setActionType(Action.ActionType.CHOOSE_CARD);
        chooseCard.setCardUsedIndex(0); // Usa a carta da posição 0 da mão
        gm.update(chooseCard);

        // O jogo deve entrar no estado de esperar o jogador escolher o inimigo
        assertEquals(GameState.TARGETING, gm.getState(), "O estado deve mudar para TARGETING.");

        // 2. Jogador escolhe o alvo (Inimigo 0)
        Action targetEnemy = new Action();
        targetEnemy.setActionType(Action.ActionType.CHOOSE_TARGET);
        targetEnemy.setTargetIndex(0);
        targetEnemy.setCardUsedIndex(0); // O input system repassa qual carta estava sendo segurada
        
        int initialEnemyLife = data.getEnemies().get(0).getLife();
        gm.update(targetEnemy);

        // O jogo deve voltar ao normal e o inimigo deve ter tomado dano
        assertEquals(GameState.CHOOSING_CARD, gm.getState(), "Deve voltar para a escolha de cartas.");
        assertTrue(data.getEnemies().get(0).getLife() < initialEnemyLife, "Inimigo deve tomar dano.");

        // 3. Testa carta que NÃO precisa de alvo (Escudo)
        data.getPlayerHand().clear();
        data.getPlayerHand().add(3); // Índice 3 é Defesa Parcial no catálogo
        
        Action chooseShield = new Action();
        chooseShield.setActionType(Action.ActionType.CHOOSE_CARD);
        chooseShield.setCardUsedIndex(0);
        
        int initialShield = data.getHero().getShield();
        gm.update(chooseShield);

        // Como não precisa de alvo, ganha escudo direto e não muda de estado
        assertTrue(data.getHero().getShield() > initialShield, "Deve ganhar escudo imediatamente.");
        assertEquals(GameState.CHOOSING_CARD, gm.getState());
    }
}