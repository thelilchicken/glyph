package main;

public class Demon extends Race {

	public Demon(Player player) {
		super("Demon", "DMN", "A Demon is feared by many. While not one of the three\ndominant powers, they remain one of the most feared beings\nin all of Nevrec.\n\n");
		
		player.clearRace();
		
		player.setAllure(1);
		player.setCourage(2);
		player.setVitality(1);
		player.setJudgement(1);
		
	}

}
