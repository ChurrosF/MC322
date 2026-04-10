/**
 * Classe base abstrata para cartas que aplicam Efeitos de Status (Buffs/Debuffs).
 * Serve como um degrau intermediário na herança para garantir que toda carta de efeito
 * possua uma referência para o status que ela vai gerar.
 */
public abstract class EffectCard extends Card {
    
    /** A instância do Efeito de Status que esta carta aplica quando jogada. */
    protected StatusEffect effect;

    public EffectCard(String name, int cost) {
        super(name, cost);
    }

    /**
     * Recupera o efeito gerado por esta carta. Usado pelo GameManager para inscrever
     * o efeito no sistema de observadores da partida.
     * @return O objeto {@link StatusEffect}.
     */
    public StatusEffect getEffect() { return effect; }
}