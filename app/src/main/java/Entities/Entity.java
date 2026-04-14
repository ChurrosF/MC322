package Entities;
import java.util.ArrayList;

import Effects.StatusEffect;

/**
 * Classe base abstrata para todas as entidades vivas no jogo (Herói e Inimigos).
 * <p>
 * Fornece a estrutura comum de atributos (vida, escudo, nome) e lógicas de 
 * recebimento de dano e gerenciamento de Efeitos de Status (Buffs/Debuffs).
 * </p>
 */
public abstract class Entity {
    protected String name;
    protected int life;
    protected int maxLife;
    protected int shield;
    
    /** Lista de efeitos de status atualmente ativos nesta entidade. */
    private final ArrayList<StatusEffect> effects = new ArrayList<>();

    /**
     * Processa a lógica de recebimento de dano, priorizando o consumo do escudo.
     * Se o dano exceder o escudo, a diferença é subtraída da vida real da entidade.
     * @param damage A quantidade bruta de dano a ser recebida.
     */
    public void receiveDamage(int damage) {
        if (damage >= this.shield) {
            this.life = Math.max(this.life - (damage - this.shield), 0);
            this.shield = 0;
        }
        else {
            this.shield -= damage;
        }
    }

    public int getShield() { return this.shield; }
    public void setShield(int shield) { this.shield = shield; }
    
    /** Adiciona um valor ao escudo atual da entidade. */
    public void gainShield(int shield) { this.shield += shield; }

    /** @return {@code true} se a vida da entidade for maior que zero. */
    public boolean isAlive() { return (this.life > 0); }

    public int getLife() { return this.life; }
    public int getMaxLife() { return this.maxLife; }
    public String getName() { return this.name; }
    public void setName(String name) { this.name = name; }

    /**
     * Aplica um Efeito de Status a esta entidade.
     * Se um efeito do mesmo tipo já existir, apenas prolonga a duração (amount).
     * @param target        A entidade que está recebendo o efeito.
     * @param effectToApply A instância do efeito sendo aplicado.
     * @param amount        A duração (turnos) ou acúmulos a serem adicionados.
     */
    public void applyEffect(Entity target, StatusEffect effectToApply, int amount) {
        boolean found = false;
        for (StatusEffect effect: target.effects) {
            if (effect.getClass() == effectToApply.getClass()) {
                effect.addAmount(amount);
                found = true;
            }
        }
        if (!found) {
            target.effects.add(effectToApply);
            effectToApply.setOwner(target);
        }
    }

    /** @return A lista de efeitos ativos nesta entidade. */
    public ArrayList<StatusEffect> getEffects() { return effects; }
}