package main;

public class Plot {
    
    private final int XCOR, YCOR;
    
    private Terrain terrain;
    
    private Item[] shopItems = {new Scythe(), new HeartInABottle(), new Book1(), new Essence(), new IronSword(), new Log(), new Potato(), new Stone(), new ZapBook(), new GlassBottle(), new MetalRod(), new GreaterCrafting(), new Drumstick()};
    
    // Used to keep track of number of Shop possibilities, DO NOT CHANGE!
    private int numInShop = shopItems.length;
    
    private boolean playerHere = false;
    
    private String description;
    
    public Plot(int xcor, int ycor, String use) {
        this.XCOR = xcor;
        this.YCOR = ycor;
        if (use.equals("map")) {
        	setTerrain();
        }
        else if (use.equals("dun")) {
        	setTerrain();
        }
    }
    
	public void setTerrain() {
        int terrainMod = (int)(Math.random() * 100);
        if (XCOR == 100 && YCOR == 100) {
            terrain = new Shop();
            /*
             * Change depending on the length of setShop.
             * 
             *  case [num]:
             *      shop[num] = [item];
             *      break;
             */
            int choose1 = (int)(Math.random() * numInShop);
            int choose2 = (int)(Math.random() * numInShop);
            int choose3 = (int)(Math.random() * numInShop);
            
            terrain.setShop(shopItems[choose1], shopItems[choose2], shopItems[choose3]);
            description = terrain.getDes();
        }
        else if (terrainMod < 20) {
            terrain = new Tree();
            description = terrain.getDes();
        }
        else if (terrainMod < 30) {
            terrain = new Bush();
            description = terrain.getDes();
        }
        else if (terrainMod < 35) {
            terrain = new Boulder();
            description = terrain.getDes();
        }
        else if (terrainMod < 40) {
        	terrain = new Dungeon();
        	description = terrain.getDes();
        }
        else if (terrainMod < 45) {
            terrain = new Wet();
            description = terrain.getDes();
        }
        else if (terrainMod < 50) {
            terrain = new Hot();
            description = terrain.getDes();
        }
        else if (terrainMod < 55) {
            terrain = new Dense();
            description = terrain.getDes();
        }
        else if (terrainMod < 59) {
            terrain = new Shop();
            /*
             * Change depending on the number of items that can possibly spawn.
             */
            int choose1 = (int)(Math.random() * numInShop);
            int choose2 = (int)(Math.random() * numInShop);
            int choose3 = (int)(Math.random() * numInShop);
            
            terrain.setShop(shopItems[choose1], shopItems[choose2], shopItems[choose3]);
            description = terrain.getDes();
        }
        else if (terrainMod < 60) {
            terrain = new Glyph();
            description = terrain.getDes();
        }
        else {
            terrain = new Plains();
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
        if (terrain instanceof Glyph) {
            terrain.setEncounter(new Jamos(), new DefaultCharacter(), new Patoto());
            terrain.resetEncounter();
        }
        else if (terrain instanceof Hot) {
            terrain.setEncounter(new Jamos(), new DefaultCharacter(), new Hyeat());
            terrain.resetEncounter();
        }
        else if (terrain instanceof Wet) {
            terrain.setEncounter(new Funga(), new DefaultCharacter(), new Patoto());
            terrain.resetEncounter();
        }
        else if (terrain instanceof Tree) {
            terrain.setEncounter(new Jamos(), new DefaultCharacter(), new Patoto());
            terrain.resetEncounter();
        }
        else if (terrain instanceof Boulder) {
            terrain.setEncounter(new Jamos(), new DefaultCharacter(), new SilverSnake());
            terrain.resetEncounter();
        }
        else if (terrain instanceof Plains) {
            terrain.setEncounter(new GrassCrab(), new DefaultCharacter(), new Chicken());
            terrain.resetEncounter();
        }
        else if (terrain instanceof Bush) {
            terrain.setEncounter(new Jamos(), new DefaultCharacter(), new Patoto());
            terrain.resetEncounter();
        }
        else if (terrain instanceof Shop) {
            terrain.setEncounter(new DefaultCharacter(), new DefaultCharacter(), new DefaultCharacter());
            terrain.resetEncounter();
        }
        else {
            terrain.setEncounter(new Jamos(), new DefaultCharacter(), new Patoto());
            terrain.resetEncounter();
        }
    }
}