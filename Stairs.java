package main;

public class Stairs extends Terrain {
	public Stairs() {
        setName("Stairs");
        if ((int)(Math.random() * 2) == 0) {
            setDescription("You trip over the beginnings of some stairs.");
        }
        else {
            setDescription("You step upon stone stairs.");
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
