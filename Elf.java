package main;

public class Elf extends Race{

	public Elf(Player player) {
		super("Elf", "ELF", "The Elven race settled in Nevrec long before the humans.\nThey are one of the three dominant powers of the land.\n\n");
		player.clearRace();
		player.setFinesse(3);
		player.setJudgement(1);
		player.setVitality(1);
	}

}
