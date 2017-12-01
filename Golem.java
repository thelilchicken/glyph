package main;

public class Golem extends Race {

	public Golem(Player player) {
		super("Golem", "GLM", "The Golems are different depending where they are found,\nbeing rock, grass, or water. They remain neutral,\nsimply because nobody could ever take them over,\nbut they have no desire to take over anything or anybody.\nNobody knows how long they have lived on their land,\nbut most say they have been here since the beginning of\ntime.\n\n");
		player.clearRace();
		
		player.setCourage(3);
		player.setVitality(2);
	}

}
