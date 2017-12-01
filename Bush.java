package main;
public class Bush extends Terrain {
    
    public Bush() {
        setName("Bush");
        if ((int)(Math.random() * 2) == 0) {
            setDescription("You brush past a large bush!");
        }
        else {
            setDescription("You walk into a thorny plant.");
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