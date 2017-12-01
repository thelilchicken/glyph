package main;

public class Hyeat extends Animal {
	public Hyeat() {
		super(15, 25, "Hyeat", 100, 30, 15, new Bleeding(), 70);
		setDrop(new HyeatFur());
	}

}
