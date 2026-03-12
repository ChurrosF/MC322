public class GameManager {
    private final GameData gameData = new GameData();
    private boolean game_ended = false;


    public void update(Action action) {
        Hero hero = gameData.getHero();
        Enemy enemy = gameData.getEnemy();
        DamageCard strike = gameData.getDamageCard();
        ShieldCard defend = gameData.getShieldCard();
        boolean battle_over = gameData.isBattle_over();


        if (!battle_over) {
            switch(action) {
                case ATTACK -> {
                    strike.useCard(hero, enemy);
                    if (!enemy.isAlive()) {
                        gameData.setBattle_over(true);
                    }
                }
                case DEFEND -> {
                    defend.useCard(hero);
                }
                case SKIP -> {
                    enemy.attackHero(6, hero);
                    hero.setEnergy(3);
                    hero.setShield(0);
                    gameData.addBattle_round();
                }
            }
  
            if (!hero.isAlive()) {
                gameData.setBattle_over(true);
            }
        }
        else {
            this.game_ended = true;
        }
    }


    public boolean isGame_Ended() {
        return this.game_ended;
    }


    public GameData getGameData() {
        return this.gameData;
    }

}
