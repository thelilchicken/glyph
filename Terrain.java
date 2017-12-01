package main;
abstract public class Terrain {
    
    private String name;
    
    private String description;
    
    private DPlot[][] dungeonMap = new DPlot[10][10];
    
    private Animal held;
    
    private Animal[] encounter = new Animal[3];
    
    private Item[] selling = new Item[3];
    
    public String getName() {
        return name;
    }
    
    public void setItem(Item i1, Item i2, Item i3) {
        selling[0] = i1;
        selling[1] = i2;
        selling[2] = i3;
    }
    
    public Item getItem(int spot) {
        return selling[spot];
    }
    
    public void setName(String n) {
        name = n;
    }
    
    public void setEncounter(Animal ch1, Animal ch2, Animal ch3) {
        encounter[0] = ch1;
        encounter[1] = ch2;
        encounter[2] = ch3;
    }
    
    public void resetEncounter() {
        held = getEncounter();
    }
    
    public Animal getHeld() {
        return held;
    }
    
    public Animal getEncounter() {
        int which = (int)(Math.random() * 3);
        return encounter[which];
    }
    
    public String getDes() {
        return description;
    }
    
    public void setDescription(String des) {
        description = des;
    }

    public void setShop(Item i1, Item i2, Item i3) {
        setItem(i1, i2, i3);
    }
    
    public void setDPlot(int x, int y) {
    	dungeonMap[x][y] = new DPlot(x, y, "dun");
    }
    
    public int dmrowlength() {
    	return dungeonMap.length;
    }
    
    public int dmcollength() {
    	return dungeonMap[0].length;
    }
    
    public DPlot getDPlot(int x, int y) {
    	return dungeonMap[x][y];
    }
    
}