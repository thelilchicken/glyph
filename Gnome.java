package main;

public class Gnome extends Race {

	public Gnome(Player player) {
		super("Gnome", "GNM", "little peeps\n\n");
		player.clearRace();
		
		player.setAllure(2);
		player.setknowledge(2);
		player.setJudgement(1);
		
	}

}
