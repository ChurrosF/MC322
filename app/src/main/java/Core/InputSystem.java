package Core;
import java.io.IOException;

import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import com.googlecode.lanterna.screen.Screen;

import Entities.Action;

/**
 * Sistema responsável por capturar os comandos físicos do teclado e traduzi-los
 * em ações processáveis (intents) para o GameManager.
 */
public class InputSystem {
    private final Screen screen = TerminalManager.getInstance().getScreen();
    private final Action action = new Action();

    /**
     * Bloqueia o jogo e aguarda que o jogador pressione uma tecla. Avalia o input
     * com base no estado atual do jogo (se o utilizador está a selecionar uma carta ou um alvo).
     *
     * @param state O estado atual da interface (ex: {@link GameState#BATTLE_CARD}).
     * @return Um objeto {@link Action} encapsulando a intenção validada do jogador.
     */
    public Action readInput(GameState state) {
        try { 
            KeyStroke key = screen.readInput();
            switch (state) {
                case GameState.BATTLE_CARD -> CardChooseAction(key);
                case GameState.BATTLE_TARGETING -> TargetChooseAction(key);
                case GameState.MAP -> RoomChooseAction(key);
            }
            checkGameClose(key);
        }
        catch (IOException e){
            System.err.println("(InputSystem) Error when trying to read input from player:" + e);
        }

        
        return this.action;
    }

    /**
     * Lida com o processamento de teclas quando o jogador está na fase de escolher uma carta.
     * Separa a lógica entre teclas especiais (Enter, Esc), números (índice da carta) ou letras.
     *
     * @param key A tecla registada pelo Lanterna.
     */
    private void CardChooseAction(KeyStroke key) {
        if (isKeySpecial(key)) {
            handleSpecialInput(key);
        }
        else if (isKeyNumeric(key)) {
            CardChooseNumericalInput(key);
        }
        else {
            CardChooseAlphabeticInput(key);
        }
    }

    /**
     * Lida com o processamento de teclas quando o jogador está na fase de escolher um alvo (inimigo).
     *
     * @param key A tecla registada pelo Lanterna.
     */
    private void TargetChooseAction(KeyStroke key) {
        if (!isKeyNumeric(key)) {
            // Permite cancelar a seleção e voltar às cartas pressionando ESC
            if (key.getKeyType() == KeyType.Escape) {
                this.action.setInputInt(null);
                this.action.setActionType(Action.ActionType.BACK);
            }
            else {
                this.action.setActionType(Action.ActionType.INVALID);
            }
            return;
        }

        TargetingNumericalInput(key);
    }

    private void RoomChooseAction(KeyStroke key) {
        if (!isKeyNumeric(key)) {
            this.action.setActionType(Action.ActionType.INVALID);
        }
        else {
            RoomNumericalInput(key);
        }
    }


    private void RoomNumericalInput(KeyStroke key) {
        String inputStr = key.getCharacter().toString();
        int roomNumber = Integer.parseInt(inputStr);

        this.action.setActionType(Action.ActionType.CHOOSE_ROOM);
        this.action.setInputInt(roomNumber - 1);
    }

    /**
     * Processa comandos de teclas alfabéticas específicas do jogo.
     *
     * @param key A tecla alfabética pressionada.
     */
    private void CardChooseAlphabeticInput(KeyStroke key) {
        String inputStr = key.getCharacter().toString();

        switch (inputStr.toUpperCase()) {
                case "Q" -> this.action.setActionType(Action.ActionType.QUIT);
                case "P" -> this.action.setActionType(Action.ActionType.SKIP);
                default -> this.action.setActionType(Action.ActionType.INVALID);
        }
    }


    /**
     * Processa teclas numéricas durante a fase de escolha de cartas, mapeando o número
     * para o índice correspondente na mão do jogador.
     *
     * @param key A tecla numérica pressionada.
     */
    private void CardChooseNumericalInput(KeyStroke key) {
        String inputStr = key.getCharacter().toString();
        int cardInt = Integer.parseInt(inputStr);

        if (cardInt <= 5 || cardInt > 0) {
                this.action.setActionType(Action.ActionType.CHOOSE_CARD);
                this.action.setInputInt(cardInt - 1);
                return;
        }

        this.action.setActionType(Action.ActionType.SKIP);
    }

    /**
     * Processa teclas numéricas durante a fase de seleção de alvos, mapeando o número
     * para o índice do inimigo.
     *
     * @param key A tecla numérica pressionada.
     */
    private void TargetingNumericalInput(KeyStroke key) {
        String inputStr = key.getCharacter().toString();
        int targetInt = Integer.parseInt(inputStr);

        if (targetInt > 0 || targetInt <= 3) {
            this.action.setActionType(Action.ActionType.CHOOSE_TARGET);
            this.action.setTargetIndex(targetInt - 1);
            return;
        }

        this.action.setActionType(Action.ActionType.INVALID);
    }

    /**
     * Lida com teclas de controlo especiais do terminal.
     *
     * @param key A tecla especial pressionada (ex: Enter, EOF).
     */
    private void handleSpecialInput(KeyStroke key) {
        switch (key.getKeyType()) {
            case KeyType.Enter -> this.action.setActionType(Action.ActionType.SKIP);
            case KeyType.EOF -> this.action.setActionType(Action.ActionType.QUIT);
            default -> this.action.setActionType(Action.ActionType.INVALID);
        }
    }


    private void checkGameClose(KeyStroke key) {
        if (key.getKeyType() == KeyType.EOF) {
            this.action.setActionType(Action.ActionType.QUIT);
        }
    }


    /**
     * Verifica se a tecla pressionada é uma tecla especial (não-caractere).
     *
     * @param key A tecla a ser verificada.
     * @return {@code true} se for uma tecla especial.
     */
    private boolean isKeySpecial(KeyStroke key) {
        return !(key.getKeyType() == KeyType.Character);
    }

    /**
     * Verifica se a tecla pressionada representa um dígito numérico.
     *
     * @param key A tecla a ser verificada.
     * @return {@code true} se a tecla for um número.
     */
    private boolean isKeyNumeric(KeyStroke key) {
        if (key.getCharacter() != null) {
            return Character.isDigit(key.getCharacter());
        }
        return false;
    }
}