import java.util.Scanner;

public class GameManager {
    private Hero hero;
    private Enemy enemy;
    private Scanner scanner = new Scanner(System.in);

    

    public void start() {
        // Initializing characters
        hero = new Hero("Hero"); 
        enemy = new Enemy("Rat", 20, 1); 
        
        // Initializing cards
        DamageCard strike = new DamageCard("Strike", 1, 6); 
        ShieldCard defend = new ShieldCard("Defend", 1, 5); 

        System.out.println("A wild " + enemy.getName() + " appeared!");

        // Main Game Loop
        while (hero.isAlive() && enemy.isAlive()) {
            playerTurn(strike, defend);
            
            if (enemy.isAlive()) {
                enemyTurn();
            }
        }
    }

    private void playerTurn(DamageCard strike, ShieldCard defend) {
        // Reset energy at turn start
        hero.setEnergy(3); 
        boolean endTurn = false;

    

    private void enemyTurn() {
        int damage = 6;
        enemy.attackHero(damage, hero);
    }
}