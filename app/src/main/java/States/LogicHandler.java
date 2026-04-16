package States;

import Core.GameData;
import Entities.Action;

public abstract class LogicHandler {
    public abstract void updateState(GameData data, Action action);
}
