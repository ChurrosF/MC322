package Core;

import Entities.Action;

public abstract class Event {
    public abstract void update(Action action);
}
