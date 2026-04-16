package States;

import com.googlecode.lanterna.graphics.TextGraphics;

import Core.GameData;
import Core.TerminalManager;
import Entities.Action;

public abstract class GameState {
    protected StateType type;
    protected TextGraphics textGraphics = TerminalManager.getInstance().getTextGraphics();


    protected LogicHandler logicHandler;
    protected RenderHandler renderHandler;
    protected InputHandler inputHandler;


    public abstract void update(GameData data, Action action);
    
    public abstract void render(GameData data);

    public abstract void readInput();
}
