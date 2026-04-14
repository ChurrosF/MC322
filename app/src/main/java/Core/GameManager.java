package Core;

import java.util.ArrayList;

import Cards.Card;
import Cards.EffectCard;
import Effects.StatusEffect;
import Entities.Action;
import Entities.Enemy;
import Entities.Entity;
import Entities.Hero;

/**
 * Controlador principal da lógica do jogo (Game Loop e Regras de Batalha).
 * <p>
 * O {@code GameManager} é responsável por processar as ações do jogador, atualizar 
 * o estado do jogo em {@link GameData}, coordenar os turnos e gerenciar o ciclo de vida 
 * dos efeitos de status (Buffs/Debuffs) utilizando o padrão Observer.
 * </p>
 */
public class GameManager {
    
    /** O repositório central de dados e estado da partida. */
    private final GameData data = new GameData();
    
    /** Lista de efeitos ativos que escutam as ações do jogo (Observers). */
    private final ArrayList<StatusEffect> effectSubscribers = new ArrayList<>();
    
    /** Referência direta ao personagem do jogador. */
    private final Hero hero = data.getHero();
    
    /** Referência à mão atual de cartas do jogador (armazenada como índices numéricos). */
    private final ArrayList<Integer> player_hand = data.getPlayerHand();
    
    /** O estado atual da interface/máquina de estados do jogo. */
    private GameState state = GameState.CHOOSING_CARD;
    
    /** Flag que indica se a batalha atual chegou ao fim. */
    private boolean gameEnded = false;


    /**
     * Processa a ação atual e atualiza o estado geral do jogo.
     * Deve ser chamado a cada interação do usuário ou ciclo da interface.
     * * @param action A ação solicitada para o turno atual (ex: Usar Carta, Pular Turno).
     */
    public void update(Action action) {
        Action.ActionType actionType = action.getActionType();

    
        if (actionType == Action.ActionType.SKIP) {
            this.hero.setEnergy(3);
        }
        updateEffects(action);

        this.data.setInvalidAction(false);
        
        if (data.isBattleOver()) {
            this.gameEnded = true;
            return;
        }

        switch(actionType) {
            case CHOOSE_CARD -> handleCardChoose(action);
            case CHOOSE_TARGET -> handleTargetChoose(action);
            case BACK -> this.state = GameState.CHOOSING_CARD;
            case SKIP -> turnSkip();
            case QUIT -> this.data.setBattleOver(true);
            case INVALID -> this.data.setInvalidAction(true);
        }
        
        // Remove inimigos derrotados
        this.data.getEnemies().removeIf(enemy -> !enemy.isAlive());
        checkBattleOver();
    }


    /**
     * Verifica se o jogo foi completamente encerrado (batalha finalizada).
     * * @return {@code true} se o jogo acabou, {@code false} caso contrário.
     */
    public boolean isGameEnded() {
        return this.gameEnded;
    }


    /** * Retorna o repositório de dados do jogo.
     * * @return A instância de {@link GameData} gerenciada por este controlador. 
     */
    public GameData getGameData() {
        return this.data;
    }


    /**
     * Resolve a lógica de encerramento de turno do jogador.
     * <p>
     * A seguinte sequência de eventos ocorre:
     * <ol>
     * <li>Descarta a mão restante do jogador.</li>
     * <li>Se a pilha de compras estiver vazia, embaralha o descarte de volta.</li>
     * <li>Compra uma nova mão para o próximo turno.</li>
     * <li>Os inimigos executam suas ações programadas.</li>
     * <li>Os escudos do herói são resetados (não acumulam para o próximo turno).</li>
     * </ol>
     * </p>
     */
    private void turnSkip() {
        this.data.discardHand();
        if (this.data.getBuyPile().isEmpty()) {
            this.data.resetBuyPile();
        }
        this.data.setInvalidAction(false);
        this.data.buyRoundCards();
        
        for (Enemy enemy : data.getEnemies()) {
            enemy.setShield(0);
            enemy.executeAction(hero, effectSubscribers);
        }
        
        this.hero.setShield(0);
        this.data.addBattleRound();
    }


    /**
     * Tenta processar o uso de uma carta a partir da ação escolhida pelo jogador.
     * Verifica se a carta é válida e se necessita de um alvo antes de ser jogada.
     * * @param action O objeto de ação contendo o índice da carta na mão.
     */
    private void handleCardChoose(Action action) {
        Integer cardIndex = action.getCardUsedIndex();

        if (!isCardChoosen(action)) {
            return;
        }

        if (isCardInvalid(cardIndex)) {
            this.data.setInvalidAction(true);
            return;
        }
        
        Card card = getCardFromIndex(cardIndex);
        
        // Se a carta precisar de um alvo, muda o estado do jogo para esperar a escolha
        if (card.requiresTarget()) {
            this.state = GameState.TARGETING;
            return;
        }

        handleCardUse(cardIndex, hero);
        this.data.addBattleRound();
    }


    /**
     * Resolve a ação de escolher um alvo para uma carta selecionada previamente.
     * * @param action O objeto de ação contendo o índice do alvo selecionado.
     */
    private void handleTargetChoose(Action action) {
        int targetIndex = action.getTargetIndex();

        if (isTargetInvalid(targetIndex)) {
            this.data.setInvalidAction(true);
            return;
        }

        Enemy target = data.getEnemies().get(targetIndex);

        handleCardUse(action.getCardUsedIndex(), target);
        this.state = GameState.CHOOSING_CARD; // Retorna ao estado normal
        this.data.addBattleRound();
    }


    /**
     * Executa efetivamente os efeitos da carta sobre o alvo, descarta a carta e 
     * processa potenciais efeitos de status associados a ela.
     * * @param cardIndex O índice da carta na mão do jogador.
     * @param target    A entidade alvo da carta (pode ser inimigo ou o próprio herói).
     */
    private void handleCardUse(int cardIndex, Entity target) {
        Card card = getCardFromIndex(cardIndex);
        if (card.useCard(hero, target)) {
            this.data.discardCard(cardIndex);
            handleEffectCards(card);
        }
    }


    /**
     * Verifica se a carta recém-jogada aplica algum Efeito de Status (Buff/Debuff).
     * Se aplicar e o efeito ainda não estiver ativo no sistema global, o inscreve.
     * * @param card A carta que acabou de ser jogada.
     */
    private void handleEffectCards(Card card) {
        if (!(card instanceof EffectCard effectCard)) return;
        
        StatusEffect effect = effectCard.getEffect();
        Entity target = effect.getOwner();
        
        boolean alreadySubscribed = false;
        for (StatusEffect activeEffect : effectSubscribers) {
            if (activeEffect.getClass() == effect.getClass() && activeEffect.getOwner() == target) {
                alreadySubscribed = true;
                break;
            }
        }

        if (!alreadySubscribed) {
            subscribe(effect); //
        }

    }


    /**
     * Traduz o índice numérico da mão do jogador para a instância real da Carta.
     * * @param cardIndex A posição da carta na interface/mão.
     * @return O objeto {@link Card} correspondente.
     */
    private Card getCardFromIndex(int cardIndex) {
        int card_type = this.player_hand.get(cardIndex);
        return this.data.getPossibleCards()[card_type];
    }

    
    /**
     * Valida se o índice de carta escolhido é possível (evita OutOfBounds ou tentar
     * jogar com a mão vazia).
     * * @param cardIndex O índice escolhido.
     * @return {@code true} se a escolha for inválida, {@code false} caso contrário.
     */
    private boolean isCardInvalid(int cardIndex) {
        return (player_hand.size() <= cardIndex || player_hand.isEmpty() || cardIndex < 0);
    }


    /**
     * Checa se a ação possui um índice de carta atrelado.
     * * @param action A ação gerada pelo sistema de input.
     * @return {@code true} se uma carta foi escolhida na ação.
     */
    private boolean isCardChoosen(Action action) {
        return (action.getCardUsedIndex() != null);
    }


    /**
     * Valida se o índice do alvo (inimigo) selecionado é válido dentro da lista atual.
     * * @param targetIndex O índice do alvo.
     * @return {@code true} se a seleção for inválida, {@code false} caso contrário.
     */
    private boolean isTargetInvalid(int targetIndex) {
        return (targetIndex < 0 || targetIndex >= data.getEnemies().size());
    }


    /**
     * Avalia as condições de fim de jogo: morte do herói ou derrota de todos os inimigos.
     * Caso alguma condição seja atingida, altera a flag na classe GameData.
     */
    private void checkBattleOver() {
        if (!hero.isAlive() || data.getEnemies().isEmpty()) {
            this.data.setBattleOver(true);
        }
    }


    /**
     * Inscreve um novo efeito de status para observar as ações do jogo.
     * * @param effect O efeito a ser adicionado à lista de observadores.
     */
    private void subscribe(StatusEffect effect) {
        this.effectSubscribers.add(effect);
    }


    /**
     * Remove um efeito de status da lista de observadores do jogo.
     * * @param effect O efeito a ser removido.
     */
    public void unsubscribe(StatusEffect effect) {
        this.effectSubscribers.remove(effect);
    }


    /**
     * Verifica se um efeito de status ainda não está na lista de observadores.
     * * @param effect O efeito a ser checado.
     * @return {@code true} se o efeito não estiver inscrito, {@code false} caso contrário.
     */
    private boolean notSubscribed(StatusEffect effect) {
        return !effectSubscribers.contains(effect);
    }


    /**
     * Dispara as notificações de que uma ação ocorreu para todos os efeitos ativos.
     * Em seguida, remove da entidade proprietária os efeitos cuja duração (amount) zerou.
     * * @param action A ação recente que pode engatilhar lógicas dentro dos efeitos.
     */
    private void notifyEffects(Action action) {
        for (StatusEffect effectSubscriber : effectSubscribers) {
            effectSubscriber.beNotified(action, this.data);
            if (effectSubscriber.getAmount() == 0) {
                effectSubscriber.getOwner().getEffects().remove(effectSubscriber);
            }
        }
    }

    
    /**
     * Chama a notificação para os efeitos ativos e realiza a limpeza interna da lista
     * do GameManager, removendo efeitos que expiraram.
     * * @param action A ação realizada no turno atual.
     */
    private void updateEffects(Action action)  {
        notifyEffects(action);
        effectSubscribers.removeIf(effect -> effect.getAmount() == 0);
    }

    /**
     * Recupera o estado atual do sistema de inputs/ações (ex: selecionando carta vs. alvo).
     * * @return O {@link GameState} atual.
     */
    public GameState getState() {
        return state;
    }
}