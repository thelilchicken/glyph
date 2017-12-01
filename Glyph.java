package main;
public class Glyph extends Terrain {
    
    public Glyph() {
        setName("Glyph");
        if ((int)(Math.random() * 2) == 0) {
            setDescription("You have come across a Glyph!");
        }
        else {
            setDescription("You enter a mysterious plot of land and notice a stone pillar in the center.");
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