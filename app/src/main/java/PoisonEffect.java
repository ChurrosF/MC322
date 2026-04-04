public class PoisonEffect extends StatusEffect {

    public PoisonEffect(String name, Entity owner, int amount) {
        this.name = name;
        this.owner = owner;
        this.amount = amount;
    }


    @Override
    public void beNotified(Action action, GameData data) {
    // Como o slay the spire, quando acaba o turno aplica o dano e depois diminui 1 stack (amount)
        if (action.getActionType() == Action.ActionType.SKIP) {
            if (this.amount > 0) {

                this.owner.receiveDamage(this.amount);
                this.amount--;
            }
        }
    }
}


