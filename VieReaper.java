package main;

public class VieReaper extends Animal {

	public VieReaper() {
		super(27, 313, "Vie Reaper", 450, 30, 7, new Reaped(), 100);
		setDrop(new ScorchedScythe());
	}

}
