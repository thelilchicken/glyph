package main;
public class Shop extends Terrain {
    
    private char[] cons1 = {'B', 'C', 'D', 'F', 'G', 'H', 'J', 'K', 'L', 'M', 'N', 'P', 'Q', 'R', 'S', 'T', 'V', 'W', 'X', 'Z'};
    private char[] cons2 = {'b', 'c', 'd', 'f', 'g', 'h', 'j', 'k', 'l', 'm', 'n', 'p', 'q', 'r', 's', 't', 'v', 'w', 'x', 'z', ' ', '-'};
    private char[] vowel = {'a', 'e', 'i', 'o', 'u', 'y'};
    
    public Shop() {
        String res = "";
        setName("Shop");
        for (int x = 0; x < 6; x++) {
            if (x == 0) {
                res += cons1[(int)(Math.random() * cons1.length)];
            }
            else if (x == 1 || x == 4) {
                res += vowel[(int)(Math.random() * vowel.length)];
            }
            else if (x == 2 || x == 5) {
                res += cons2[(int)(Math.random() * (cons1.length))];
            }
            else if (x == 3) {
                res += cons2[(int)(Math.random() * cons2.length)];
            }
        }
        setDescription("You have entered the city of " + res + "!");
        
        int en = (int)(Math.random() * 1);
        switch (en) {
            case 0:
                setEncounter(new DefaultCharacter(), new DefaultCharacter(), new DefaultCharacter());
                break;
            default:
                setEncounter(new DefaultCharacter(), new DefaultCharacter(), new DefaultCharacter());
                break;
        }
        resetEncounter();
    }

}