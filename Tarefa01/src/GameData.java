public class GameData {
    private Hero hero = new Hero("Hero");
    private Enemy enemy = new Enemy("Rat", 20, 0);
    private DamageCard strike = new DamageCard("Strike", 1, 4);
    private ShieldCard defend = new ShieldCard("Defend", 1, 2);
    private boolean game_over = false;


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

    
    public boolean isGame_over() {
        return game_over;
    }

    
    public void setGame_over(boolean game_over) {
        this.game_over = game_over;
    }
}
