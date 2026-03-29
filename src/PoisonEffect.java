public class PoisonEffect extends StatusEffect {
    private int potency;

    public PoisonEffect(String name, Entity owner, int amount, int potency) {
        this.name = name;
        this.owner = owner;
        this.amount = amount;
        this.potency = potency;
    }

    @Override
    public void beNotified(Action action) {
    // Como o play the spire quando acaba o turno aplica o dano e depois diminui 1 stack
        if (action.getAction_type() == Action.ActionType.SKIP) {
            if (this.amount > 0) {

                this.owner.receiveDamage(this.amount);
                this.amount--;
            }
        }
    }
}


