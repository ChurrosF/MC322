public class App {
    public static void main(String[] args) throws Exception {
        Hero hero = new Hero("Felipe");
        Enemy enemy = new Enemy("Roga", 10, 2);
        ShieldCard ShieldCard = new ShieldCard("Ataque", 2, 5);

        ShieldCard.useCard(hero);
        System.out.println(hero.shield);
        System.out.println(enemy.life);
    }
}
