package main;
public class Jamos extends Animal{
    
    public Jamos(){
        super(2, 25, "a Jamos", 10, 15, 2, new Bleeding(), 10);
        setDrop(new JamosArm());
    }
    
    
}