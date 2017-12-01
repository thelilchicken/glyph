package main;

public class StoneFloor extends Terrain {
	public StoneFloor() {
        setName("Stone Floor");
        if ((int)(Math.random() * 2) == 0) {
            setDescription("Your feet click on the slick stone floor.");
        }
        else {
            setDescription("Torchlight flickers on the wet stone floor.");
        }
        int en = (int)(Math.random() * 1);
        switch (en) {
            case 0:
                setEncounter(new VieReaper(), new DefaultCharacter(), new VieReaper());
                break;
            default:
                setEncounter(new Jamos(), new DefaultCharacter(), new Patoto());
                break;
        }
        resetEncounter();
    }
}
