package Effects;

import Core.GameData;
import Entities.Action;
import Entities.Entity;
import Entities.Hero;

/**
 * Efeito de status que fornece energia extra ao dono no início de um novo turno.
 * <p>
 * Este efeito ouve a ação de pular turno ({@code SKIP}) via padrão Observer. Quando
 * notificado, ele injeta pontos adicionais de energia no herói antes que as ações
 * do novo turno ocorram.
 * </p>
 */
public class ManaEffect extends StatusEffect {
    
    /**
     * Construtor do Efeito de Mana.
     * * @param name   O nome do efeito a ser exibido na interface.
     * @param owner  A entidade que possui o efeito ativo.
     * @param amount A duração inicial (em turnos) deste efeito.
     */
    public ManaEffect(String name, Entity owner, int amount) {
        this.name = name;
        this.owner = owner;
        this.amount = amount;
    }

    /**
     * Recebe a notificação de uma ação executada no jogo e aplica a lógica do buff.
     * <p>
     * Se a ação for um fim de turno ({@link Action.ActionType#SKIP}) e o efeito ainda 
     * possuir duração, ele adiciona +2 de energia ao herói e diminui sua própria duração.
     * </p>
     * * @param action A ação recém-executada pelo jogador ou sistema.
     * @param data   O estado atual e dados do jogo.
     */
    @Override
    public void beNotified(Action action, GameData data) {
        if (action.getActionType() == Action.ActionType.SKIP) {

            if (this.amount > 0) {    
                if (this.owner instanceof Hero hero) {
                    hero.setEnergy(hero.getEnergy() + 2);
                }
                
                this.amount--;
            }
        }
    }
}