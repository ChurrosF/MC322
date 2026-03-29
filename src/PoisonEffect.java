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

    }
}

