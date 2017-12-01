package main;
public class DPlot {
	private final int XCOR, YCOR;
    
    private Terrain terrain;
    
    private String description;
    
    public DPlot(int xcor, int ycor, String use) {
        this.XCOR = xcor;
        this.YCOR = ycor;
        if (use.equals("dun")) {
        	setTerrain();
        }
    }
    
	public void setTerrain() {
        int terrainMod = (int)(Math.random() * 100);
        if (XCOR == 0 && YCOR == 0) {
        	terrain = new Stairs();
        	description = terrain.getDes();
        }
        else if (terrainMod < 30) {
        	terrain = new StoneFloor();
        	description = terrain.getDes();
        }
        else if (terrainMod < 40) {
        	terrain = new JustAHole();
        	description = terrain.getDes();
        }
        else if (terrainMod < 50) {
        	terrain = new Puddle();
        	description = terrain.getDes();
        }
        else if (terrainMod < 55) {
        	terrain = new MysticStatue();
        	description = terrain.getDes();
        }
        else if (terrainMod < 60) {
        	terrain = new UndergroundGrowth();
        	description = terrain.getDes();
        }
        else if (terrainMod < 65) {
        	terrain = new MetalPole();
        	description = terrain.getDes();
        }
        else {
        	terrain = new StoneFloor();
        	description = terrain.getDes();
        }
    }
    public String getTerrain() {
        return terrain.getName();
    }
    public int getXcor() {
        return XCOR;
    }
    public int getYcor() {
        return YCOR;
    }
    public String getText() {
        return description;
    }
    public Terrain getTerrainType() {
        return terrain;
    }
    public Animal getEnc() {
        return terrain.getHeld();
    }
    public void re() {
        terrain.resetEncounter();
    }
    public String getCharacterDes() {
        return getEnc().getDes();
    }
    public Item getShopItem(int pos) {
        return terrain.getItem(pos);
    }
    public String getItemDes(int pos) {
        return terrain.getItem(pos).getItemName();
    }
    public int getItemWorth(int pos) {
        return terrain.getItem(pos).getWorth();
    }
    public void setNewEncounter() {
        if (terrain instanceof StoneFloor) {
            terrain.setEncounter(new Jamos(), new DefaultCharacter(), new Patoto());
            terrain.resetEncounter();
        }
        
    }
}