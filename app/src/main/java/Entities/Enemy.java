package Entities;
import java.util.ArrayList;
import java.util.Random;

import Effects.PoisonEffect;
import Effects.StatusEffect;

/**
 * Representa um inimigo controlado pelo jogo (IA).
 * Possui um comportamento que é randomizado a cada turno, podendo atacar,
 * se defender ou envenenar o herói.
 */
public class Enemy extends Entity {
    
    /** Os valores mínimo e máximo de dano que este inimigo pode causar [min, max]. */
    private int[] damageRange;
    /** O dano precalculado que será causado se a ação deste turno for atacar. */
    private int roundDamage;
    /** A quantidade de acúmulos de veneno aplicados por este inimigo. */
    private final int poisonAmount = 2;
    /** A quantidade de escudo gerado caso a ação seja de defesa. */
    private final int shieldToAdd = 8;
    /** A intenção da IA para o turno atual. */
    private EnemyAction action;
    
    private final String enemySprite = """
 /\\ \\  / /\\
//\\\\ .. //\\\\
//\\((  ))/\\\\
/  < `' >  \\
                """;

    /**
     * Construtor do Inimigo.
     * @param name         O nome do inimigo.
     * @param life         Vida máxima e inicial.
     * @param shield       Escudo inicial (geralmente 0).
     * @param damage_range Array com os limites [min, max] de dano do ataque básico.
     */
    public Enemy(String name, int life, int shield, int[] damage_range) {
        this.name = name;
        this.life = life;
        this.maxLife = life;
        this.shield = shield;
        this.damageRange = damage_range;
        this.roundDamage = new Random().nextInt(damage_range[0], damage_range[1]);
        this.action = EnemyAction.ATTACK;
    }

    /**
     * Randomiza a intenção (ação) do inimigo para o próximo turno.
     */
    public void decideAction() {
        int actionInt = new Random().nextInt(0,  3);
        switch (actionInt) {
            case 0 -> this.action = EnemyAction.ATTACK;
            case 1 -> this.action = EnemyAction.POISON;
            case 2 -> this.action = EnemyAction.DEFEND;
        }
    }

    /**
     * Executa a ação predeterminada pela IA contra o herói e escolhe a próxima.
     * @param hero              O alvo das ações negativas.
     * @param effectSubscribers A lista global do GameManager para inscrever novos efeitos.
     */
    public void executeAction(Hero hero, ArrayList<StatusEffect> effectSubscribers) {
        switch (this.action) {
            case ATTACK -> attackHero(hero);
            case POISON -> poisonHero(hero, effectSubscribers);
            case DEFEND -> defend();
        }
        decideAction(); // Prepara a ação do turno seguinte (para a UI renderizar a intenção)
    }

    /** Causa dano ao herói e sorteia o dano do próximo turno. */
    public void attackHero(Hero hero) {
        hero.receiveDamage(roundDamage);
        this.roundDamage = new Random().nextInt(this.damageRange[0], this.damageRange[1]);
    }

    /** Cria um Efeito de Veneno, aplica ao herói e inscreve no sistema. */
    public void poisonHero(Hero hero, ArrayList<StatusEffect> effectSubscribers) {
        PoisonEffect poisonEffect = new PoisonEffect("Veneno", hero, this.poisonAmount);
        hero.applyEffect(hero, poisonEffect, this.poisonAmount);
        boolean alreadyInGlobalList = false;
        for (StatusEffect activeEffect : effectSubscribers) {
        if (activeEffect instanceof PoisonEffect && activeEffect.getOwner() == hero) {
            alreadyInGlobalList = true;
            break;
        }
    }

        if (!alreadyInGlobalList) {
            effectSubscribers.add(poisonEffect);
        }
    }

    /** Gera escudo para a própria entidade. */
    public void defend() { this.shield = this.shieldToAdd; }

    // ... Getters e Setters
    public String getEnemySprite() { return enemySprite; }
    public int[] getDamageRange() { return damageRange; }
    public void setDamageRange(int[] damage_range) { this.damageRange = damage_range; }
    public int getRoundDamage() { return roundDamage; }
    public EnemyAction getEnemyAction() { return this.action; }
    public int getShieldToAdd() { return this.shieldToAdd; }
    public int getPoisonAmount() { return this.poisonAmount; }
}