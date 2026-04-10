/**
 * Representa uma ação discreta tomada pelo jogador durante o seu turno.
 * <p>
 * Esta classe atua como um Objeto de Transferência de Dados (DTO). Ela encapsula 
 * a intenção do jogador (capturada pelo InputSystem) e a transporta de forma segura 
 * para o GameManager processar e executar.
 * </p>
 */
public class Action {

    /**
     * Define os tipos possíveis de ações que um jogador pode realizar.
     */
    public enum ActionType {
        /** Representa a intenção de jogar uma carta específica da mão. */
        CHOOSE_CARD,
        /** Representa a intenção de escolher um alvo para a carta jogada. */
        CHOOSE_TARGET,
        /** Representa o cancelamento de uma seleção (voltar à escolha de cartas). */
        BACK,
        /** Representa a intenção de encerrar o turno e recuperar energia. */
        SKIP,
        /** Representa um pedido para se render prematuramente ou sair da batalha. */
        QUIT,
        /** Representa um input não reconhecido ou uma ação bloqueada pelas regras do jogo. */
        INVALID;
    }

    /** * A posição (índice) da carta escolhida no array da mão do jogador. 
     * Só é relevante se o actionType for {@link ActionType#CHOOSE_CARD}.
     */
    private Integer CardHandIndex;

    /** * O índice do alvo escolhido (posição do inimigo no array de inimigos). 
     * Só é relevante se o actionType for {@link ActionType#CHOOSE_TARGET}.
     */
    private Integer targetIndex;
    
    /** A categoria específica de ação que o jogador deseja executar. */
    private ActionType actionType = null;

    /**
     * Recupera o índice da carta que se pretende jogar.
     * @return O índice inteiro representando a posição da carta na mão.
     */
    public Integer getCardUsedIndex() {
        return CardHandIndex;
    }

    /**
     * Define o índice da carta que o jogador quer usar.
     * @param cardHandIndex A posição da carta (ex: 0 para a primeira carta).
     */
    public void setCardUsedIndex(Integer cardHandIndex) {
        this.CardHandIndex = cardHandIndex;
    }

    /**
     * Recupera o índice do alvo selecionado.
     * @return O índice inteiro representando a posição do inimigo.
     */
    public Integer getTargetIndex() {
        return targetIndex;
    }

    /**
     * Define o índice do alvo que o jogador quer atingir.
     * @param targetIndex A posição do inimigo alvo.
     */
    public void setTargetIndex(Integer targetIndex) {
        this.targetIndex = targetIndex;
    }

    /**
     * Recupera a categoria da ação solicitada.
     * @return O {@link ActionType} atual atribuído a esta ação.
     */
    public ActionType getActionType() {
        return actionType;
    }

    /**
     * Define a categoria da ação com base no input do jogador.
     * @param actionType O {@link ActionType} específico a ser atribuído.
     */
    public void setActionType(ActionType actionType) {
        this.actionType = actionType;
    }
}