public abstract class Card {
    protected String name;
    protected String description;
    protected int cost;
    
    public abstract boolean useCard(Hero user);


    public String getName() {
        return this.name;
    }


    public int getCost() {
        return this.cost;
    }


    public String getDescription() {
        return this.description;
    }
}
