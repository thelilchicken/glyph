package main;

public class Funga extends Animal {

	public Funga() {
		super(5, 23, "Funga", 25, 35, 4, new Decay(), 50);
		setDrop(new Spore()); 
	}

}
