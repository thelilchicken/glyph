package main;

public class Dungeon extends Terrain {
	
	public Dungeon() {
		setName("Dungeon");
		if ((int)(Math.random() * 2) == 0) {
            setDescription("You find an ominous opening into blackness.");
        }
        else {
            setDescription("You stumble upon the cold opening of a dungeon.");
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
        for (int x = 0; x < 10; x++) {
        	for (int y = 0; y < 10; y++) {
        		setDPlot(x, y);
        	}
        }
	}
}
