package main;
public class Wet extends Terrain {
    
    public Wet() {
        setName("Wet");
        if ((int)(Math.random() * 2) == 0) {
            setDescription("You tread upon wet soil.");
        }
        else {
            setDescription("You step into the shallow puddles of what appears to be the \nbeginning of a marsh.");
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