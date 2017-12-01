package main;

public class Human extends Race{
	public Human(Player player) {
		super("Human", "HMN", "Humans were the last race to settle in Nevrec. They quickly grew\nas one of the most dominant races. Their lands span\nfor miles on end.\n\n");
		player.clearRace();
		
		player.setCourage(2);
		player.setVitality(1);
		player.setknowledge(2);
		
	}

}
