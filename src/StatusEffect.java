public abstract class StatusEffect {
    protected String name;
    protected Entity owner;
    protected int amount;
    

    public abstract void beNotified(Action action);


    public String getString() {
        return this.name + "Acúmulos: " + this.amount;
    }


    public String getName() { 
        return name; 
    }


    public Entity getOwner() {
        return this.owner;
    }


    public void setOwner(Entity owner) {
        this.owner = owner;
    }


    public int getAmount() { 
        return amount; 
    }


    public void setAmount(int amount) {
        this.amount = amount; 
    }
    

    public void addAmount(int value) { 
        this.amount += value;
    }
}