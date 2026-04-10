/**
 * O ponto de entrada principal para a aplicação "Slay the Tuff Rat".
 * <p>
 * Esta classe atua como a orquestradora de todo o jogo. Ela inicializa os 
 * subsistemas centrais (Modelo, Vista, Controlador) e executa o <b>Game Loop</b> principal. 
 * Como este é um RPG baseado em turnos, o loop do jogo é síncrono — ele pausa a execução 
 * para aguardar o input do utilizador antes de processar a próxima frame (quadro).
 * </p>
 */
public class App {
    /**
     * O método de execução principal que inicia o jogo.
     * <p>
     * A sequência de execução segue o padrão clássico de Game Loop por Turnos:
     * <ol>
     * <li><b>Renderizar:</b> Desenha o estado atual do jogo no ecrã do terminal.</li>
     * <li><b>Verificar Estado:</b> Avalia se a batalha foi concluída (Vitória/Derrota). Se sim, quebra o loop.</li>
     * <li><b>Input:</b> Bloqueia e aguarda que o jogador pressione uma tecla válida.</li>
     * <li><b>Atualizar:</b> Passa a ação do jogador para o GameManager calcular danos, efeitos e regras.</li>
     * </ol>
     *
     * @param args Argumentos de linha de comandos (não utilizados nesta aplicação).
     * @throws Exception Caso ocorra uma interrupção não tratada durante a inicialização do terminal ou leitura de input.
     */
    public static void main(String[] args) throws Exception {
        // Inicialização dos Subsistemas Centrais
        GameManager gameManager = new GameManager();
        Renderer renderer = new Renderer();
        InputSystem inputSystem = new InputSystem();

        while (true) {
            GameData data = gameManager.getGameData();
            GameState state = gameManager.getState();
            renderer.render(data, state);
            if (gameManager.getGameData().isBattleOver()) {
                break;
            }
            Action action = inputSystem.readInput(state);
            gameManager.update(action);
        }
    }
}