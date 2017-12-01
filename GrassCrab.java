package main;

public class GrassCrab extends Animal {

	public GrassCrab() {
		super(3, 75, "a Grass Crab", 30, 10, 5, new DefaultStatus(), -1);
		setDrop(new GrassCrabShellShard());
	}

}
