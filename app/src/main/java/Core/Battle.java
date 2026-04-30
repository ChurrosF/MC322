package Core;

import java.util.ArrayList;

import Cards.Card;
import Cards.EffectCard;
import Effects.StatusEffect;
import Entities.Action;
import Entities.Enemy;
import Entities.Entity;
import Entities.Hero;

public class Battle extends Event {
    /** O repositório central de dados e estado da partida. */
    private final GameData data;

    /** Referência ao controlador principal para permitir alterações de GameState. */
    private final GameManager gameManager;
    
    /** Lista de efeitos ativos que escutam as ações do jogo (Observers). */
    private final ArrayList<StatusEffect> effectSubscribers = new ArrayList<>();
    
    /** Referência direta ao personagem do jogador. */
    private final Hero hero;
    
    /** Referência à mão atual de cartas do jogador (armazenada como índices numéricos). */
    private final ArrayList<Integer> playerHand;

    public Battle(GameData data, GameManager gameManager) {
        this.data = data;
        this.gameManager = gameManager;
        this.hero = this.data.getHero();
        this.playerHand = this.data.getPlayerHand();
    }

    /**
     * Processa a ação atual na batalha.
     * @param action A ação solicitada para o turno atual.
     */
    public void update(Action action) {
        Action.ActionType actionType = action.getActionType();

        if (actionType == Action.ActionType.SKIP) {
            this.hero.setEnergy(3);
        }

        updateEffects(action);

        this.data.setInvalidAction(false);

        switch(actionType) {
            case CHOOSE_CARD -> handleCardChoose(action);
            case CHOOSE_TARGET -> handleTargetChoose(action);
            case BACK -> this.gameManager.setState(GameState.BATTLE_CARD);
            case SKIP -> turnSkip();
            case QUIT -> {
                this.data.setGameOver(true);
                this.data.setGameClosed(true);
            } 
            case INVALID -> this.data.setInvalidAction(true);
        }
        
        this.data.getEnemies().removeIf(enemy -> !enemy.isAlive());
        checkBattleOver();
    }


    /** * Retorna o repositório de dados do jogo.
     */
    public GameData getGameData() {
        return this.data;
    }


    /**
     * Resolve a lógica de encerramento de turno do jogador.
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
     */
    private void handleCardChoose(Action action) {
        Integer cardIndex = action.getInputInt();

        if (!isCardChoosen(action)) {
            return;
        }

        if (isCardInvalid(cardIndex)) {
            this.data.setInvalidAction(true);
            return;
        }
        
        Card card = getCardFromIndex(cardIndex);
        
        
        if (card.requiresTarget()) {
            this.gameManager.setState(GameState.BATTLE_TARGETING);
            return;
        }

        handleCardUse(cardIndex, hero);
        this.data.addBattleRound();
    }


    /**
     * Resolve a ação de escolher um alvo para uma carta selecionada previamente.
     */
    private void handleTargetChoose(Action action) {
        int targetIndex = action.getTargetIndex();

        if (isTargetInvalid(targetIndex)) {
            this.data.setInvalidAction(true);
            return;
        }

        Enemy target = data.getEnemies().get(targetIndex);

        handleCardUse(action.getInputInt(), target);
        this.gameManager.setState(GameState.BATTLE_CARD);
        this.data.addBattleRound();
    }


    /**
     * Executa efetivamente os efeitos da carta.
     */
    private void handleCardUse(int cardIndex, Entity target) {
        Card card = getCardFromIndex(cardIndex);
        if (card.useCard(hero, target)) {
            this.data.discardCard(cardIndex);
            handleEffectCards(card);
        }
    }


    /**
     * Verifica se a carta recém-jogada aplica algum Efeito de Status.
     */
    private void handleEffectCards(Card card) {
        if (!(card instanceof EffectCard effectCard)) return;

        StatusEffect effect = effectCard.getEffect();

        if (notSubscribed(effect)) {
            subscribe(effect);
        }
    }


    /**
     * Traduz o índice numérico da mão do jogador para a instância real da Carta.
     */
    private Card getCardFromIndex(int cardIndex) {
        int card_type = this.playerHand.get(cardIndex);
        return this.data.getPossibleCards()[card_type];
    }
    

    /**
     * Valida se o índice de carta escolhido é possível.
     */
    private boolean isCardInvalid(int cardIndex) {
        return (playerHand.size() <= cardIndex || playerHand.isEmpty() || cardIndex < 0);
    }


    /**
     * Checa se a ação possui um índice de carta atrelado.
     */
    private boolean isCardChoosen(Action action) {
        return (action.getInputInt() != null);
    }


    /**
     * Valida se o índice do alvo (inimigo) selecionado é válido.
     */
    private boolean isTargetInvalid(int targetIndex) {
        return (targetIndex < 0 || targetIndex >= data.getEnemies().size());
    }


    /**
     * Avalia as condições de fim de jogo.
     */
    private void checkBattleOver() {
        if (!hero.isAlive() || data.getEnemies().isEmpty()) {
            this.data.setBattleOver(true);
            if (!hero.isAlive()) {
                this.data.setGameOver(true);
            }
            if (data.getEnemies().isEmpty() && data.getHeroCurrentFloor() == 7) {
                this.data.setGameOver(true);
            }
            gameManager.setState(GameState.MAP);
            hero.setEnergy(3);
        }
    }


    private void subscribe(StatusEffect effect) {
        this.effectSubscribers.add(effect);
    }


    public void unsubscribe(StatusEffect effect) {
        this.effectSubscribers.remove(effect);
    }


    private boolean notSubscribed(StatusEffect effect) {
        return !effectSubscribers.contains(effect);
    }


    private void notifyEffects(Action action) {
        for (StatusEffect effectSubscriber : effectSubscribers) {
            effectSubscriber.beNotified(action, this.data);
            if (effectSubscriber.getAmount() == 0) {
                effectSubscriber.getOwner().getEffects().remove(effectSubscriber);
            }
        }
    }
    

    private void updateEffects(Action action)  {
        notifyEffects(action);
        effectSubscribers.removeIf(effect -> effect.getAmount() == 0);
    }
}