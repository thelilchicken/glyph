package main;
public class Dense extends Terrain {
    
    public Dense() {
        setName("Dense");
        if ((int)(Math.random() * 2) == 0) {
            setDescription("You creep into dense foliage!");
        }
        else {
            setDescription("The land switches into a tree covered area. You push\n past the tree branches into a dense forest.");
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