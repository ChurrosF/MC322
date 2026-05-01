package Cards;

import Effects.StatusEffect;

public abstract class EffectCard extends Card {
    protected StatusEffect effect;

    public EffectCard(String name, int cost, int price) {
        super(name, cost, price);
    }

    

    public StatusEffect getEffect() {
        return effect;
    }
}
