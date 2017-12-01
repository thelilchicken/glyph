package main;
public class Patoto extends Animal {
    
    public Patoto() {
        super(1, 5, "a Patoto", 2, 65, 1, new DefaultStatus(), -1);
        setDrop(new Potato());
    }
    
}