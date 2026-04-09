public abstract class EffectCard extends Card {
    protected StatusEffect effect;

    public EffectCard(String name, int cost) {
        super(name, cost);
    }

    

    public StatusEffect getEffect() {
        return effect;
    }
}
