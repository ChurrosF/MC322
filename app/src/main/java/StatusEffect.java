/**
 * A classe base abstrata para todos os Efeitos de Status (Buffs e Debuffs).
 * <p>
 * Implementa o padrão de design Observer. Cada efeito instanciado se "inscreve" no
 * GameManager e fica escutando (listening) as ações que acontecem no jogo. Quando
 * algo relevante ocorre (como passar o turno ou jogar uma carta), o método {@code beNotified}
 * reage de acordo com a mecânica do efeito específico.
 * </p>
 */
public abstract class StatusEffect {
    /** Nome de exibição do efeito na interface. */
    protected String name;
    /** A entidade (Herói ou Inimigo) que está sob o efeito deste status. */
    protected Entity owner;
    /** A duração em turnos ou os "acúmulos/cargas" que este efeito possui antes de expirar. */
    protected int amount;
    
    /**
     * O método chamado pelo GameManager para notificar o efeito de que uma ação ocorreu.
     * @param action O objeto da ação que foi acabou de ser executada.
     * @param data   O repositório de dados com o estado atual da partida.
     */
    public abstract void beNotified(Action action, GameData data);

    public String getString() { return this.name + "Acúmulos: " + this.amount; }
    public String getName() { return name; }
    public Entity getOwner() { return this.owner; }
    public void setOwner(Entity owner) { this.owner = owner; }
    public int getAmount() { return amount; }
    public void setAmount(int amount) { this.amount = amount; }
    
    /**
     * Adiciona mais acúmulos/duração ao efeito já existente, em vez de criar um novo.
     * @param value O valor a ser somado.
     */
    public void addAmount(int value) { this.amount += value; }
}