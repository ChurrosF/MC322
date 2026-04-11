/**
 * Representa uma carta de ataque padrão que causa dano direto a uma entidade alvo.
 */
public class DamageCard extends Card {
    /** O dano base original da carta antes de quaisquer modificadores. */
    private int baseDamage;
    /** O dano final calculado (afetado por Força ou Fraqueza) a ser aplicado no alvo. */
    private int finalDamage;

    public DamageCard(String name, int cost, int baseDamage) {
        super(name, cost);
        this.baseDamage = baseDamage;
        this.finalDamage = this.baseDamage;
        this.description = this.name + " ".repeat(RendererConfig.VERTICAL_BAR_SIZE - 21 - this.name.length()) + "|" + " DMG:" + this.baseDamage + " CUSTO:" + this.cost;
    }

    @Override
    public boolean useCard(Hero user, Entity target) {
        int user_energy = user.getEnergy();

        if (user_energy < this.cost) {
            return false; // Falha por falta de energia
        }

        user.setEnergy(user_energy - this.cost);
        target.receiveDamage(finalDamage);
        return true;
    }

    @Override
    public boolean requiresTarget() { return true; }

    public int getBaseDamage() { return baseDamage; }
    public void setBaseDamage(int damage) { this.baseDamage = damage; }
    public int getFinalDamage() { return finalDamage; }
    public void setFinalDamage(int finalDamage) { this.finalDamage = finalDamage; }
}