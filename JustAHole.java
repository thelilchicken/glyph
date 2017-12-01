package main;

public class JustAHole extends Terrain {
	public JustAHole() {
        setName("Hole");
        if ((int)(Math.random() * 2) == 0) {
            setDescription("You look over the edge of a seemingly bottomless hole.");
        }
        else {
            setDescription("You quickly stop youself as the floor ends to a deep hole.");
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
