/**
 * Enumera as possíveis intenções/ações que um inimigo pode realizar em seu turno.
 */
public enum EnemyAction {
    /** O inimigo planeja causar dano direto ao herói. */
    ATTACK,
    /** O inimigo planeja aplicar acúmulos de veneno ao herói. */
    POISON,
    /** O inimigo planeja se proteger gerando escudo. */
    DEFEND
}