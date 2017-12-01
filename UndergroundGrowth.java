package main;

public class UndergroundGrowth extends Terrain {
	public UndergroundGrowth() {
        setName("Underground Growth");
        if ((int)(Math.random() * 2) == 0) {
            setDescription("You brush by a deep green, extremely thick mass of foliage.");
        }
        else {
            setDescription("You find a think growth of moss and various other plants.");
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
