package main;
public class Item {
    
    private String name;
    
    private int successChance;
    
    private int worth;
    
    private int damage;
    
    private String description;
    
    private int numInInv;
    
    private int weight;
    
    private String content;
    
    public Item(String name, int sc, int worth, int damage, int weight) {
        this.worth = worth;
        this.name = name;
        this.damage = damage;
        this.weight = weight;
        successChance = sc;
        numInInv = 1;
    }
    
    public void setBookContent(String con) {
        content = con;
    }
    
    public int getWeight() {
    	return weight;
    	
    }
    
    public String getBookContent() {
        return content;
    }
    
    public String getItemName() {
        return name;
    }
    
    public int getItemDamage() {
        return damage;
    }
    
    public void incNumInInv(int inc) {
        numInInv += inc;
    }
    
    public void setNumInInv(int set) {
        numInInv = set;
    }
    
    public int getNumInInv() {
        return numInInv;
    }
    
    public int getSuccessChance() {
        return successChance;
    }
    
    public int getWorth() {
        return worth;
    }
    
    public String getDes() {
        return description;
    }
}