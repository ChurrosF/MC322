public class StrengthEffect extends StatusEffect {

       public StrengthEffect(String name, Entity owner, int amount) {
        this.name = name;
        this.owner = owner;
        this.amount = amount;
    }


    @Override
    public void beNotified(Action action, GameData data) {
        if (action.getAction_type() == Action.ActionType.CARD) {
            int card_index = action.getCard_used_index();
            int card_type = data.getPlayerHand().get(card_index);
            Card card = data.getPossible_cards()[card_type];

            if (card instanceof DamageCard damageCard) {
                if (this.amount > 0) {
                    damageCard.setFinalDamage(damageCard.getBaseDamage() + this.amount);
                    this.amount--;
                }
            }
        }
    }
}
