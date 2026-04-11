import java.io.IOException;
import java.util.ArrayList;

import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.screen.Screen;

/**
 * O motor de renderização para a interface de utilizador do jogo.
 * <p>
 * Esta classe atua como o componente "Vista" (View) na arquitetura do jogo. É estritamente 
 * responsável por ler o estado a partir de {@link GameData} e desenhar os elementos visuais 
 * correspondentes (arte ASCII, bordas de interface, barras de vida e texto) no ecrã do terminal.
 * </p>
 * <p>
 * <b>Sistema de Layout:</b> As posições são geralmente armazenadas como arrays de inteiros no 
 * formato {@code [linha/y, coluna/x]} para mapeamento na grelha do terminal Lanterna.
 * </p>
 */
public class Renderer {
    private final TerminalManager terminalManager = TerminalManager.getInstance();

    // Getting Window constants from TerminalManager
    private final int HEIGHT = terminalManager.getHeight();
    private final int WIDTH = terminalManager.getWidth();

    private final int VERTICAL_BAR_SIZE = RendererConfig.VERTICAL_BAR_SIZE;
    private final int[] VERTICAL_BAR_POSITION = {1, VERTICAL_BAR_SIZE};

    private final int[] HP_BAR_POSITION = RendererConfig.HP_BAR_POSITION;
    private final int[] SHIELD_COUNTER_POSITION = RendererConfig.SHIELD_COUNTER_POSITION; 
    private final int[] ENERGY_BAR_POSITION = RendererConfig.ENERGY_BAR_POSITION;
    private final int[] NO_ENERGY_WARNING_POSITION = RendererConfig.NO_ENERGY_WARNING_POSITION;

    private final int DECK_TEXT_LINE = RendererConfig.DECK_TEXT_LINE;
    private final int[] BUY_PILE_POSITION = RendererConfig.BUY_PILE_POSITION;
    private final int[] DISCARD_PILE_POSITION = RendererConfig.DISCARD_PILE_POSITION;

    private final int[] HERO_POSITION = RendererConfig.HERO_POSITION;
    private final int[] ENEMIES_POSITION = RendererConfig.ENEMY_POSITION;
    
    // ==========================================
    // LANTERNA GRAPHICS CONTEXT
    // ==========================================
    private final Screen screen = terminalManager.getScreen();
    private final TextGraphics textGraphics = terminalManager.getTextGraphics();

    /**
     * Imprime uma string de texto numa coordenada específica no ecrã.
     * Suporta strings multilinhas dividindo o texto em {@code \n} e 
     * incrementando a linha (Eixo Y) para cada substring subsequente.
     *
     * @param position Um array contendo {@code [linha, coluna]}.
     * @param text     O texto a ser desenhado.
     */
    private void placeText(int[] position, String text) {
        int line = position[0];
        int column = position[1];

        String[] lines = text.split("\n");

        for (int i = 0; i < lines.length; i++) {
            textGraphics.putString(column, line + i, lines[i]);
        }
    }

    /**
     * Imprime uma string de texto numa coordenada específica utilizando uma cor personalizada.
     * Após desenhar, restaura a cor para o Branco padrão para evitar falhas visuais.
     *
     * @param position Um array contendo {@code [linha, coluna]}.
     * @param text     O texto a ser desenhado.
     * @param color    A cor {@link TextColor} do Lanterna a aplicar ao texto.
     */
    private void placeText(int[] position, String text, TextColor color) {
        textGraphics.setForegroundColor(color);
        placeText(position, text);
        textGraphics.setForegroundColor(TextColor.ANSI.WHITE);
    }

    /**
     * Desenha as bordas decorativas externas da janela do jogo.
     * Utiliza caracteres ASCII de linha dupla (═, ║, ╔, ╗, ╚, ╝).
     */
    private void placeBorders() {
        textGraphics.drawLine(1, 0, WIDTH  - 2, 0, '═');
        textGraphics.drawLine(1, HEIGHT - 1, WIDTH  - 2, HEIGHT - 1, '═');
        textGraphics.drawLine(1, HEIGHT - 3, WIDTH  - 2, HEIGHT - 3, '═');
        textGraphics.drawLine(0, 1, 0, HEIGHT - 2, '║');
        textGraphics.drawLine(WIDTH - 1, 1, WIDTH - 1, HEIGHT - 2, '║');

        textGraphics.putString(0, 0, "╔");
        textGraphics.putString(WIDTH - 1, 0, "╗");
        textGraphics.putString(0, HEIGHT - 1, "╚");
        textGraphics.putString(WIDTH - 1, HEIGHT - 1, "╝");
    }

    /**
     * Gera uma representação visual (String) em arte ASCII de uma barra de vida.
     * <p>
     * Exemplo de Output: {@code Vida: [█████░░░░░] 10/20}
     * </p>
     *
     * @param cur_hp Pontos de vida atuais.
     * @param max_hp Pontos de vida máximos.
     * @return Uma string formatada representando a barra de vida.
     */
    private String createHpBar(int cur_hp, int max_hp) {
        int white_health_bars = Math.round(((float) cur_hp / max_hp) * 10);
        String hp_bar = "Vida: [" + "█".repeat(white_health_bars) + "░".repeat(10-white_health_bars) + "]" + " " + cur_hp + "/" + max_hp;
        return hp_bar;
    }

    /**
     * Renderiza o sprite ASCII do Herói, o seu título e os efeitos de status atualmente ativos.
     *
     * @param gameData O repositório central contendo o estado do herói.
     * @param position As coordenadas base {@code [linha, coluna]} para ancorar o desenho.
     */
    private void placeHeroSprite(GameData gameData, int[] position) {
        int line = position[0];
        int column = position[1];

        String heroSprite = gameData.getHero().getHero_sprite();
        ArrayList<StatusEffect> effects = gameData.getHero().getEffects();

        placeText(new int[] {line - 4, column}, "Cavaleiro (Jogador):");
        placeText(new int[] {line, column + 3}, heroSprite);
        for (int i = 0; i < effects.size(); i++) {
            StatusEffect effect = effects.get(i);
            placeText(new int[] {line + 10 + i, column}, effect.getName() + ": " + effect.getAmount() + " Acúmulos");
        }
    }

    /**
     * Renderiza o sprite ASCII do Inimigo, a sua barra de vida, escudo, ação pretendida
     * para o próximo turno e os efeitos de status ativos.
     *
     * @param enemy    A entidade inimiga a ser desenhada.
     * @param position As coordenadas base {@code [linha, coluna]} para ancorar o desenho.
     * @param index    O índice numérico do inimigo na lista (usado para seleção visual).
     */
    private void placeEnemySprite(Enemy enemy, int[] position, int index) {
        int line = position[0];
        int column = position[1];

        // Getting Enemy Data
        String enemyName = enemy.getName();
        int enemyLife = enemy.getLife();
        int enemyShield = enemy.getShield();
        int enemyMaxLife = enemy.getMaxLife();
        int enemyRoundDamage = enemy.getRoundDamage();
        int enemyShieldtoAdd = enemy.getShieldToAdd();
        int enemyPoisonAmount = enemy.getPoisonAmount();

        EnemyAction enemyAction = enemy.getEnemyAction();
        String shieldCounter = "(+" + enemyShield + ")";
        ArrayList<StatusEffect> effects = enemy.getEffects();

        String ratSprite = enemy.getEnemySprite();

        String ratHpBarSprite = createHpBar(enemyLife, enemyMaxLife);

        placeText(new int[] {line - 7, column - 2}, enemyName + " (" + (index + 1) + ")");
        placeText(new int[] {line - 5, column - 2}, ratHpBarSprite, TextColor.ANSI.RED_BRIGHT);
        if (enemyShield > 0) {
            placeText(new int[] {line - 5, column + 23}, shieldCounter, TextColor.ANSI.BLUE_BRIGHT);
        }

        switch (enemyAction) {
            case ATTACK -> {
                placeText(new int[] {line - 4, column - 2}, String.format("Turno Seguinte: %d DMG", enemyRoundDamage));
            }
            case DEFEND -> {
                placeText(new int[] {line - 4, column - 2}, String.format("Turno Seguinte: %d SHD", enemyShieldtoAdd));
            }
            case POISON -> {
                placeText(new int[] {line - 4, column - 2}, String.format("Turno Seguinte: %d PSN", enemyPoisonAmount));
            }
        }
        for (int i = 0; i < effects.size(); i++) {
            StatusEffect effect = effects.get(i);
            placeText(new int[] {line + 7 + i, column - 2}, effect.getName() + ": " + effect.getAmount() + " Acúmulos");
        }
        placeText(new int[] {line + 2, column}, ratSprite);
    }

    /**
     * Itera sobre a lista de inimigos vivos e desenha cada um deles no ecrã com o devido espaçamento.
     *
     * @param gameData O repositório contendo a lista de inimigos.
     * @param position A posição inicial ancorada para o primeiro inimigo.
     */
    private void placeEnemies(GameData gameData, int position[]) {
        ArrayList<Enemy> enemies = gameData.getEnemies();
        for (int i = 0; i < enemies.size(); i++) {
            int[] cur_position = {position[0], position[1] + i * 29};
            placeEnemySprite(enemies.get(i), cur_position, i);
        }
    }

    /**
     * Renderiza os elementos do HUD superior, incluindo vida, escudo, energia, 
     * e o tamanho das pilhas de compra e descarte. Desenha também o separador vertical da UI.
     *
     * @param gameData O modelo de dados para extrair as estatísticas do herói e baralho.
     */
    private void placeHeroInfo(GameData gameData) {
        // Getting Hero Data
        int hero_life = gameData.getHero().getLife();
        int hero_max_life = gameData.getHero().getMaxLife();
        int hero_shield = gameData.getHero().getShield();
        int hero_energy = gameData.getHero().getEnergy();
        int buy_pile_size = gameData.getBuyPile().size();
        int discard_pile_size = gameData.getDiscardPile().size();
        
        String hero_hp_bar_sprite = createHpBar(hero_life, hero_max_life);
        String shield_counter = "(+" + hero_shield + ")";
        String energy_bar_sprite = "Energia: " + "■ ".repeat(hero_energy) + hero_energy + "/3";
        String vertical_bar = "║\n".repeat(HEIGHT - 4);

        placeText(HP_BAR_POSITION, hero_hp_bar_sprite, TextColor.ANSI.RED_BRIGHT);

        if (hero_shield > 0) {
            placeText(SHIELD_COUNTER_POSITION, shield_counter, TextColor.ANSI.BLUE_BRIGHT);
        }

        placeText(ENERGY_BAR_POSITION, energy_bar_sprite, TextColor.ANSI.YELLOW_BRIGHT);

        if (hero_energy == 0) { 
            placeText(NO_ENERGY_WARNING_POSITION, "| Sem energia!");
        }

        placeText(BUY_PILE_POSITION, "Pilha de Compra: x" + buy_pile_size);
        placeText(DISCARD_PILE_POSITION, "Pilha de Descarte: x" + discard_pile_size);
        placeText(VERTICAL_BAR_POSITION, vertical_bar);
    }

    /**
     * Renderiza uma entrada individual de carta dentro da lista da mão do jogador.
     *
     * @param position As coordenadas para posicionar o texto da carta.
     * @param card     A instância da carta para extrair a descrição.
     * @param index    O índice numérico utilizado para seleção (ex: pressionar "1").
     */
    private void placeCard(int[] position, Card card, int index) {
        placeText(new int[] {position[0], position[1]}, "(" + (index + 1) + ") " + card.getDescription());
        if (index < 4) {
            placeText(new int[] {position[0] + 1, position[1]}, "-".repeat(VERTICAL_BAR_SIZE - 1));
        }
    }
    
    /**
     * Renderiza a barra lateral esquerda contendo a mão atual de cartas do jogador,
     * as opções disponíveis (Passar Turno) e possíveis avisos de erro (Ação Inválida).
     *
     * @param gameData O modelo de dados contendo o array da mão do jogador.
     */
    private void placeCardUI(GameData gameData) {
        int hand_size = gameData.getPlayerHand().size();
        
        placeText(new int[] {DECK_TEXT_LINE - 1, 1}, "=".repeat(VERTICAL_BAR_SIZE - 1));
        placeText(new int[] {DECK_TEXT_LINE, 1}, "               Mão:");
        placeText(new int[] {DECK_TEXT_LINE + 1, 1}, "=".repeat(VERTICAL_BAR_SIZE - 1));
        
        int start_line = 8;
        int line = 0;
        for (int i = 0; i < hand_size; i++) {
            int card_index = gameData.getPlayerHand().get(i);
            Card card = gameData.getPossibleCards()[card_index];
            placeCard(new int[] {line + start_line, 1}, card, i);
            line += 2;
        }

        placeText(new int[] {HEIGHT - 5, 1}, "=".repeat(VERTICAL_BAR_SIZE - 1));
        placeText(new int[] {HEIGHT - 4, 1}, "(P) Passar Turno (+3 energia)");
        if (gameData.isActionInvalid()) {
            placeText(new int[] {HEIGHT - 4, 38}, "AVISO: AÇÃO INVÁLIDA");
        }
    }

    /**
     * Orquestra a renderização de todos os componentes do ecrã principal de batalha.
     * Chama métodos individuais para compor a frame final.
     *
     * @param gameData O estado do jogo atual a ser renderizado.
     */
    private void placeBattleScreen(GameData gameData) {
        placeHeroSprite(gameData, HERO_POSITION);
        placeEnemies(gameData, ENEMIES_POSITION);
        placeHeroInfo(gameData);
        placeCardUI(gameData);
    }

    /**
     * Desenha a barra de contexto na parte inferior do ecrã, guiando o jogador
     * sobre o que o jogo espera que ele faça (Escolher carta vs Escolher alvo).
     *
     * @param state O estado atual da máquina de estados do jogo.
     */
    private void placeContextBar(GameState state) {
        if (state == GameState.CHOOSING_CARD) {
            placeText(new int [] {HEIGHT - 2, WIDTH / 2 - 24}, "------------ ESPERANDO AÇÃO DO JOGADOR ------------");
        }
        else {
            placeText(new int [] {HEIGHT - 2, WIDTH / 2 - 24}, "----------------- ESCOLHA O ALVO ------------------");
        }
    }

    /**
     * Resolve o estado de fim de jogo e imprime a mensagem de vitória ou derrota
     * correspondente na consola padrão (System.out) após o ecrã do Lanterna ser fechado.
     *
     * @param gameData O estado do jogo contendo as métricas finais de vida.
     */
    private void drawEndScreen(GameData gameData) {
        // Directly prints end screen
        if (!gameData.getHero().isAlive()) {
            System.out.println("\n--- VOCÊ FOI DERROTADO... ---\n");
        }
        else if (!gameData.getEnemies().isEmpty()) {
            System.out.println("\n--- VOCÊ GANHOU! ---\n");
        }
        else {
            System.out.println("\n--- JOGO ENCERRADO ---\n");
        }
    }

    /**
     * A chamada principal do loop de renderização. Limpa a frame anterior, constrói a nova 
     * interface baseada no estado mais recente, empurra-a para o buffer do ecrã, e verifica
     * o fim do jogo.
     *
     * @param gameData O estado atualizado do jogo a ser desenhado no ecrã.
     * @param state    O estado de interação atual (escolhendo carta ou alvo).
     */
    public void render(GameData gameData, GameState state) {
        try {
            screen.clear();
            screen.setCursorPosition(null);
            placeBorders();
            placeBattleScreen(gameData);
            placeContextBar(state);
            screen.refresh();
            if (gameData.isBattleOver()) {
                drawEndScreen(gameData);
                screen.close();
            }  
        }
        catch (IOException e) {
        }
    }
}