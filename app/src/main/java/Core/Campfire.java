package Core;

import Entities.Action;

public class Campfire {
    private final GameData data;
    private final GameManager gameManager;
    private final int HEAL_AMOUNT = 10;

    public Campfire(GameData data, GameManager gameManager) {
        this.data = data;
        this.gameManager = gameManager;
    }

    /**
     * Processa a escolha do jogador na fogueira.
     */
    public void update(Action action) {
        if (action.getActionType() == Action.ActionType.SKIP) {
            healHero();
            this.gameManager.setState(GameState.MAP);
        }
    }

    private void healHero() {
        data.getHero().heal(HEAL_AMOUNT);
    }
}