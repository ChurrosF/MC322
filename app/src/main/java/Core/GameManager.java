package Core;

import Entities.Action;
import Map.Room;

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
    
    /** O estado inicial do jogo. Pode ser ajustado para MAP dependendo do seu fluxo de inicialização. */
    private GameState state = GameState.MAP;

    private final Battle battleManager;

    private boolean gameEnded;

    public GameManager() {
        this.battleManager = new Battle(this.data, this);
    }

    /**
     * Processa a ação atual e atualiza o estado geral do jogo.
     * Deve ser chamado a cada interação do usuário ou ciclo da interface.
     * @param action A ação solicitada para o turno atual (ex: Usar Carta, Pular Turno).
     */
    public void update(Action action) {
        switch(state) {
            case BATTLE_CARD, BATTLE_TARGETING -> {
                battleManager.update(action);
            }
            case MAP -> {
                updateMap(action);
            }
        }
    }


   public void updateMap(Action action) {
    if (data.getHeroCurrentFloor() == 6) {
                    data.setEnemies(data.getMap().getBossRoom().getEnemies());
                    data.setHeroCurrentFloor(7);
                    this.state = GameState.BATTLE_CARD;
                }

                
    if (action.getActionType() != Action.ActionType.CHOOSE_ROOM) {
        switch(action.getActionType()) {
            case QUIT -> {
                data.setGameOver(true);
                data.setGameClosed(true);
            }
            default -> {
            }
        }
        return;
    }

    int roomIndex = action.getInputInt();
    int currentFloor = data.getHeroCurrentFloor();

    if (roomIndex > 6) {
        data.setInvalidAction(true);
        return;
    }
    
    if (currentFloor == -1) {
        Room nextRoom = data.getMap().getStartRooms().get(roomIndex);
        
        data.setHeroCurrentFloor(nextRoom.getCurrentFloor());
        data.setHeroCurrentFloorPosition(nextRoom.getFloorPosition());
        data.setEnemies(nextRoom.getEnemies());
        this.state = GameState.BATTLE_CARD;
        nextRoom.setVisited(true);
        return;
    }

    
    int floorPosition = data.getHeroCurrentFloorPosition();
    Room currentRoom = data.getMap().getFloors()[currentFloor][floorPosition];

    int count = 0;
    Room nextRoom = null;


    for (int i = 0; i < 3; i++) {
        if (currentRoom.getNextRooms().get(i) != null) {
            if (count == roomIndex) {
                nextRoom = currentRoom.getNextRooms().get(i);
                break;        
            }
            count++;
        }
    }

    if (currentFloor + 1 == data.getMap().getHeight()) {
        data.setEnemies(data.getMap().getBossRoom().getEnemies());
        this.state = GameState.BATTLE_CARD;
    }
   
    if (nextRoom != null) {
        data.setHeroCurrentFloor(currentFloor + 1);
        data.setHeroCurrentFloorPosition(nextRoom.getFloorPosition());
        data.setEnemies(nextRoom.getEnemies());
        nextRoom.setVisited(true);
        this.state = GameState.BATTLE_CARD;
    } 
    else {
        data.setInvalidAction(true);
    }
}


    /**
     * Verifica se o jogo foi completamente encerrado.
     * @return {@code true} se o jogo acabou, {@code false} caso contrário.
     */
    public boolean isGameEnded() {
        return this.gameEnded;
    }


    public void setGameOver() {
        this.gameEnded = true;
    }


    /** * Retorna o repositório de dados do jogo.
     * @return A instância de {@link GameData}. 
     */
    public GameData getGameData() {
        return this.data;
    }


    public GameState getState() {
        return state;
    }


    public void setState(GameState state) {
        this.state = state;
    }
}