package main;

public class Puddle extends Terrain {
	public Puddle() {
        setName("Puddle");
        if ((int)(Math.random() * 2) == 0) {
            setDescription("You come across a dip in the floor filled with water.");
        }
        else {
            setDescription("You step in an area with more water than other places.");
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
