public class StatusEffect {
    private String type; 
    private int amount;

    public StatusEffect(String type, int amount) {
        this.type = type;
        this.amount = amount;
    }

    public String getType() { 
        return type; 
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