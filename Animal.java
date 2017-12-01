package main;
public class Animal{
    
    private int damage;
    
    private int health;
    
    private int blockChance;
    
    private int level;
    
    private int statEffectChance;
    
    private StatusEffect statusGiven;
    
    private String name;
    
    private boolean dead;
    
    private int xpWorth;
    
    private String description;
    
    private Item drop;
    
    public Animal(int damage, int health, String name, int xpWorth, int blockChance, int level, StatusEffect statusGiven, int statEffectChance){
        super();
        this.damage = damage;
        this.health = health;
        this.description = name;
        this.dead = false;
        this.xpWorth = xpWorth;
        this.blockChance = blockChance;
        this.level = level;
        this.statusGiven = statusGiven;
        this.statEffectChance = statEffectChance;
        lowerHealth(0);
    }
    
    public int getStatChance() {
    	return statEffectChance;
    }
    
    public int getDamage() {
        return damage;   
    }
    
    public StatusEffect getEffect() {
    	return statusGiven;
    }
    
    public boolean tryEscape(int cl) {
    	if (cl < level) {
    		if (Math.random() * 100 < (70 + ((level - cl) * 5))) {
    			return true;
    		}
    		else {
    			return false;
    		}
    	}
    	else if (cl > level) {
    		if (Math.random() * 100 < (70 + ((cl - level) * 5))) {
    			return true;
    		}
    		else {
    			return false;
    		}
    	}
    	else {
    		if (Math.random() * 100 < 70) {
    			return true;
    		}
    		else {
    			return false;
    		}
    	}
    }
    
    public int getXP() {
    	return xpWorth;
    }
    
    public void setDamage(int damage) {
        this.damage = damage;
    }
    
    public void setDrop(Item dr) {
        drop = dr;
    }
    
    public Item getDrop() {
        return drop;
    }
    
    public int getHealth() {
        return health;
    }
    
    public String getTitle() {
        return name;
    }
    
    public void setHealth(int health) {
        this.health = health;
    }
    
    public void lowerHealth(int hurt) {
        health -= hurt;
        if (health > 0) {
            dead = false;
        }
        else {
            health = 0;
            dead = true;
        }
    }
    
    public boolean dodge() {
    	if (Math.random() * 100 < blockChance) {
    		return true;
    	}
    	else {
    		return false;
    	}
    	
    }
    
    public boolean isDead(){
        return dead;
        
    }
    
    public String getDes() {
        return description;
    }
    
}