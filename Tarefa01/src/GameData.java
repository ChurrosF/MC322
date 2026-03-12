public class GameData {
    private Hero hero = new Hero("Hero");
    private Enemy enemy = new Enemy("Rat", 20, 0);
    private DamageCard strike = new DamageCard("Golpe", 1, 3);
    private ShieldCard defend = new ShieldCard("Escudo", 1, 2);

    private boolean battle_over = false;
    private int battle_rounds = 1;


    public Hero getHero() {
        return this.hero;
    }


    public Enemy getEnemy() {
        return this.enemy;
    }


    public DamageCard getDamageCard() {
        return this.strike;
    }


    public ShieldCard getShieldCard() {
        return this.defend;
    }

    
    public boolean isBattle_over() {
        return this.battle_over;
    }

    
    public void setBattle_over(boolean game_over) {
        this.battle_over = game_over;
    }


    public int getBattle_rounds() {
        return this.battle_rounds;
    }


    public void addBattle_round() {
        this.battle_rounds += 1;
    }
}
