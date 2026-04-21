package Core;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import org.junit.jupiter.api.Test;

class RendererTest {
    @Test
    void testFullRenderCycle() {
        GameData data = new GameData();
        Renderer renderer = new Renderer();

        // Mandamos o renderizador desenhar todas as telas possíveis.
        // O assertDoesNotThrow garante que a interface gráfica não quebre com null pointers.
        assertDoesNotThrow(() -> {
            // Desenha tela normal
            renderer.render(data, GameState.CHOOSING_CARD);
            
            // Desenha tela de mirar (muda o texto lá embaixo)
            renderer.render(data, GameState.TARGETING);
            
            // Força a tela de "Game Over" (Você ganhou/perdeu)
            data.setBattleOver(true);
            renderer.render(data, GameState.CHOOSING_CARD);
        }, "O Renderer não deve lançar exceções ao desenhar os estados do jogo.");
    }
}