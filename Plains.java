package main;
public class Plains extends Terrain {
    
    public Plains() {
        setName("Plains");
        if ((int)(Math.random() * 2) == 0) {
            setDescription("You walk out onto expansive grasslands.");
        }
        else {
            setDescription("You walk into a large, grassy field.");
        }
        int en = (int)(Math.random() * 2);
        setEncounter(new GrassCrab(), new DefaultCharacter(), new Patoto());
        resetEncounter();
    }
    
}