package main;

public class MetalPole extends Terrain {
	public MetalPole() {
        setName("Metal Pole");
        if ((int)(Math.random() * 2) == 0) {
            setDescription("You almost impale yourself upon a metal pole in the ground.");
        }
        else {
            setDescription("You see a rusty pole jutting from the floor.");
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
