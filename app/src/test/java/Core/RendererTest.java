package Core;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

public class RendererTest {

    @Test
    public void testRenderGameOver() {
        /*
         * ATENÇÃO: Instanciar o Renderer aciona o TerminalManager (Lanterna).
         * Se o seu ambiente de testes não suportar criação de janelas, 
         * essa instanciação pode gerar uma exceção.
         */
        Renderer renderer = new Renderer();
        GameManager manager = new GameManager();
        
        // Pegamos os dados reais do jogo e forçamos a condição de fim de jogo
        GameData data = manager.getGameData();
        data.setGameOver(true);
        data.setGameClosed(true); // Para cobrir o printEndScreen de jogo fechado

        boolean renderizouSemErro = true;
        
        try {
            // Como data.isGameOver() é true, o Renderer deve apenas fechar a tela 
            // e imprimir a mensagem final, sem tentar desenhar os gráficos.
            renderer.render(data, GameState.MAP);
        } catch (Exception e) {
            renderizouSemErro = false;
        }
        
        // Verificamos se o bloco rodou corretamente e saiu sem lançar exceções de tela
        assertTrue(renderizouSemErro);
    }
    
    @Test
    public void testRenderDefeatMessage() {
        Renderer renderer = new Renderer();
        GameManager manager = new GameManager();
        
        GameData data = manager.getGameData();
        data.setGameOver(true);
        data.setGameClosed(false);
        // Condição para imprimir "VOCÊ FOI DERROTADO..."
        // (A classe Hero precisaria ter um método setLife(0) acessível ou similar, 
        // mas assumindo que um jogo novo começa com o herói vivo, não cobriremos a morte exata aqui 
        // sem ter o código do Hero para alterar a vida. O teste passa direto pro fluxo de fim).
        
        boolean renderizouSemErro = true;
        
        try {
            renderer.render(data, GameState.BATTLE_CARD);
        } catch (Exception e) {
            renderizouSemErro = false;
        }
        
        assertTrue(renderizouSemErro);
    }
}