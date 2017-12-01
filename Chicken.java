package main;

public class Chicken extends Animal {

	public Chicken() {
		super(1, 2, "Chicken", 1, 12, 1, new DefaultStatus(), -1);
		setDrop(new Drumstick());
	}
	
}
