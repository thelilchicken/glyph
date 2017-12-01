package main;
public class Tree extends Terrain {
    
    public Tree() {
        setName("Tree");
        if ((int)(Math.random() * 2) == 0) {
            setDescription("You find a large tree!");
        }
        else {
            setDescription("You run into a tree.");
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