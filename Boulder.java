package main;
public class Boulder extends Terrain {
    
    public Boulder() {
        setName("Boulder");
        if ((int)(Math.random() * 2) == 0) {
            setDescription("You bump into a large boulder!");
        }
        else {
            setDescription("As you pass into the next plot, you see a large stone,\none that appears to be a boulder.");
        }
        int en = (int)(Math.random() * 1);
        switch (en) {
            case 0:
                setEncounter(new Jamos(), new DefaultCharacter(), new SilverSnake());
                break;
            default:
                setEncounter(new Jamos(), new DefaultCharacter(), new SilverSnake());
                break;
        }
        resetEncounter();
    }
    
}