public class App {
    public static void main(String[] args) throws Exception {
        Hero hero = new Hero("Felipe");
        Enemy enemy = new Enemy("Roga", 10, 2);
        DamageCard damageCard = new DamageCard("Ataque", 2, 5);

        damageCard.useCard(hero, enemy);
        System.out.println(hero.energy);
        System.out.println(enemy.life);
    }
}
