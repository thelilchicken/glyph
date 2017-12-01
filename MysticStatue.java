package main;

public class MysticStatue extends Terrain {
	public MysticStatue() {
        setName("Mystic Statue");
        if ((int)(Math.random() * 2) == 0) {
            setDescription("You glimpse a statue on a pedestal.");
        }
        else {
            setDescription("You find a statue that just doesn't feel right.");
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
