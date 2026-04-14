package Cards;
import Core.RendererConfig;
import Effects.ManaEffect;
import Entities.Entity;
import Entities.Hero;

/**
 * Representa uma carta que concede bônus de regeneração de mana (energia) ao jogador.
 * <p>
 * Esta carta herda de {@link EffectCard} e, ao ser jogada, aplica um {@link ManaEffect}
 * ao alvo (que, neste caso, é o próprio herói), prolongando a duração do ganho de mana
 * com base na quantidade de acúmulos (amount).
 * </p>
 */
public class ManaCard extends EffectCard {
    
    /** A quantidade de turnos (acúmulos) que o efeito de mana durará após a aplicação. */
    private final int amountToAdd;

    /**
     * Construtor da carta de Mana.
     * * @param name        O nome de exibição da carta.
     * @param cost        O custo em energia para jogar esta carta.
     * @param amountToAdd A quantidade de turnos que o bônus de mana ficará ativo.
     */
    public ManaCard(String name, int cost, int amountToAdd) {
        super(name, cost);
        this.amountToAdd = amountToAdd;
        this.description = this.name + " ".repeat(RendererConfig.VERTICAL_BAR_SIZE - 21 - this.name.length())
        + "|" + " MANA:" + amountToAdd + " CUSTO:" + this.cost;
    }

    /**
     * Define se a carta exige que o jogador escolha um alvo específico para ser usada.
     * * @return {@code false}, pois o efeito de mana é aplicado no próprio usuário da carta.
     */
    @Override
    public boolean requiresTarget() {
        return false; 
    }

    /**
     * Executa o efeito principal da carta: gasta a energia do usuário e aplica o efeito de mana.
     * * @param user   O herói que está utilizando a carta.
     * @param target A entidade alvo do efeito (neste caso, o próprio herói).
     * @return {@code true} se a carta foi usada com sucesso, ou {@code false} se o jogador não tiver energia suficiente.
     */
    @Override
    public boolean useCard(Hero user, Entity target) {
        ManaEffect manaEffect = new ManaEffect("Mana Overflow", target, amountToAdd);
        this.effect = manaEffect;

        int user_energy = user.getEnergy();

        if (user_energy < this.cost) {
            return false;
        }

        user.setEnergy(user_energy - this.cost);
        target.applyEffect(target, manaEffect, amountToAdd);

        return true;
    }
}