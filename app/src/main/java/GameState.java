/**
 * Enumera os possíveis estados de interface durante o turno do jogador.
 */
public enum GameState {
    /** O jogador está na fase de visualização da mão e escolha de uma carta para jogar. */
    CHOOSING_CARD,
    /** O jogador selecionou uma carta que exige alvo e o sistema aguarda a seleção do inimigo. */
    TARGETING
}