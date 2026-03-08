public class GameManager {
    private final GameData gameData = new GameData();


    public void update(Action action) {
        Hero hero = gameData.getHero();
        Enemy enemy = gameData.getEnemy();
        DamageCard strike = gameData.getDamageCard();
        ShieldCard defend = gameData.getShieldCard();
        boolean game_over = gameData.isGame_over();


        if (!game_over) {

            switch(action) {
                case ATTACK -> {
                    strike.useCard(hero, enemy);
                    if (!enemy.isAlive()) {
                        gameData.setGame_over(true);
                    }
                }
                case DEFEND -> {
                    defend.useCard(hero);
                }
                case SKIP -> {
                    enemy.attackHero(4, hero);
                    hero.setEnergy(3);
                    hero.setShield(0);
                }
            }
  
            if (!hero.isAlive()) {
                gameData.setGame_over(true);
            }
        }
    }


    public boolean gameEnded() {
        return gameData.isGame_over();
    }


    public GameData getGameData() {
        return this.gameData;
    }
}
