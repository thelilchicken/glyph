package main;
public class Hot extends Terrain {
    
    public Hot() {
        setName("Hot");
        if ((int)(Math.random() * 2) == 0) {
            setDescription("You have entered a hot area!");
        }
        else {
            setDescription("You have entered the sandy wasteland of the desert.");
        }
        int en = (int)(Math.random() * 1);
        switch (en) {
            case 0:
                setEncounter(new Jamos(), new DefaultCharacter(), new Patoto());
                break;
            default:
                setEncounter(new Jamos(), new DefaultCharacter(), new Patoto());
                break;
        }
        resetEncounter();
    }
    
}