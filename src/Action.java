public class Action {

    

    public enum ActionType {
        CARD,
        SKIP;
    }

    
    private int card_used_index;
    private ActionType action_type;


    public int getCard_used_index() {
        return card_used_index;
    }


    public void setCard_used_index(int card_used_index) {
        this.card_used_index = card_used_index;
    }


    public ActionType getAction_type() {
        return action_type;
    }


    public void setAction_type(ActionType action_type) {
        this.action_type = action_type;
    }
}
