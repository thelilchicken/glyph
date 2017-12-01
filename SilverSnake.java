package main;

public class SilverSnake extends Animal {

	public SilverSnake() {
		super(10, 31, "Silver Snake", 45, 5, 7, new Bleeding(), 73);
		setDrop(new MetalRod());
	}

}
