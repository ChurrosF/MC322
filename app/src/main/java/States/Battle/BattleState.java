package States.Battle;

import Core.GameData;
import Entities.Action;
import States.GameState;
import States.StateType;

public class BattleState extends GameState {
    public BattleState() {
        this.type = StateType.BATTLE_CARD;
        this.inputHandler = new BattleInputHandler();
        this.logicHandler = new BattleLogicHandler(data, action);
        this.renderHandler = new BattleRenderHandler(textGraphics, type);
    }


    @Override
    public void update(GameData data, Action action) {
        logicHandler.updateState(data, action);
    }


    @Override
    public void readInput() {
        inputHandler.readInputState();
    }

    
    @Override
    public void render(GameData data) {
        renderHandler.renderState(data);
    }
}
