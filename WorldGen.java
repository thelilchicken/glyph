package main;
public class WorldGen {
    
    private Plot[][] map;
    
    private int height, width;
    
    public WorldGen(int height, int width) {
        this.height = height;
        this.width = width;
        
        map = new Plot[height][width];
        for(int x = 0; x < height; x++) {
            for(int y = 0; y < width; y++) {
                map[x][y] = new Plot(x, y, "map");
            }
        }
    }
    /*
     * Tester
     */
    public String toString() {
        
        String res = "";
        
        for(int x = 0; x < map.length; x++) {
            for(int y = 0; y < map[x].length; y++) {
                res += "" + (map[x][y].getTerrain()) + "(" + map[x][y].getXcor() + ", " + map[x][y].getYcor() + ") " + map[x][y].getText() + " | ";
            }
            res += "\n";
        }
        res += "__________";
        
        return res;
    }
    
    public static void main(String[] args) {
        WorldGen world = new WorldGen(200, 200);
        System.out.println(world.toString());
    }
    
    public Plot getMap(int x, int y) {
        return map[x][y];
    }
}