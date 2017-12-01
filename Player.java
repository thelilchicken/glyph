package main;
import java.util.ArrayList;

public class Player extends Animal {
    
    private int X, Y, DX, DY;
    
    private String name = "";
    
    private ArrayList<StatusEffect> effects = new ArrayList<StatusEffect>();
    
    private int courage, allure, knowledge, vitality, finesse, judgement;
    
    private int points;
    
    private int sm = 0;
    
    private String status = "";
    
    private int maxWeight = 0;
    
    private int weight = 0;
    
    private int mod = 0;
    
    private int xp = 0;
    
    private boolean inDungeon = false;
    
    private int level = 1;
    
    private Inventory inventory;
    
    private Race race;
    
    private boolean racePicked = false;
    private boolean attributesPicked = false;
    
    public Player(int d, int h, String n, int courage, int allure, int knowledge, int vitality, int finesse, int judgement) {
        super(d, h, n, 12345, 1, 12345, new DefaultStatus(), -1);
        X = 100;
        Y = 100;
        DX = 0;
        DY = 0;
        this.courage = courage;
        this.allure = allure;
        this.finesse = finesse;
        this.judgement = judgement;
        this.knowledge = knowledge;
        this.vitality = vitality;
        points = 0;
        inventory = new Inventory();
        //effects.add(new Bleeding());
        //effects.add(new BasicRegen());
        //effects.add(new CustomRegen(100));
    }
    
        public void setPicked(){
        if(race != null){
    	racePicked = true;
    	attributesPicked = true;
        }
    	
    }
        
    public void checkTime() {
    	for (int x = (effects.size() - 1); x >= 0; x--) {
    		if (effects.get(x).getTime() <= 0) {
    			effects.get(x).setTime();
    			effects.get(x).setDuration(effects.get(x).getDuration() + (int)(vitality * 0.5));
    			effects.remove(x);
    			mod = 0;
    		}
    	}
    }
    
    public void addStatus(StatusEffect stat) {
    	if (stat.getMorality()) {
    		stat.setDuration((stat.getDuration() - (int)(vitality * 0.5)));
    	}
    	else {
    		stat.setDuration((stat.getDuration() + (int)(vitality * 0.5)));
    	}
    	effects.add(stat);
    	
    }
    
    public int getMod() {
    	return mod;
    }
    
    public void removeStatus(int pos) {
    	effects.remove(pos);
    }
    
    public StatusEffect getPlayerStat(int pos) {
    	return effects.get(pos);
    }
        
    public int doStatus(int health) {
    	int[] finalHealth = {health, mod};
    	for(int x = 0; x < effects.size(); x++) {
			finalHealth = effects.get(x).runStatus(finalHealth[0], mod);
			
    	}
    	mod = finalHealth[1];
    	int currentHealth = finalHealth[0];
    	return currentHealth;
    }
    
    public void setSM(int val) {
    	sm = val;
    }
    
    public int getSM() {
    	return sm;
    }
    
    public String printStatus() {
    	status = "";
    	status += "Status Effects:\n";
    	for(int x = effects.size() - 1; x >= 0; x--) {
    		status += " - " + effects.get(x).getName() + " (" + effects.get(x).getTime() + " turns)\n";
    	}
    	return status;
    }
    
    public String printStatusName() {
    	String statName = "";
    	for(int x = 0; x < effects.size(); x++) {
    		statName += "You were affected by " + effects.get(x).getName() + "!\n";
    	}
    	statName += "\n";
    	return statName;
    }
        
    public void clearRace() {
    	courage = 0;
    	allure = 0;
    	finesse = 0;
    	judgement = 0;
    	knowledge = 0;
    	vitality = 0;
    }
        
    public Race getRace() {
    	return race;
    }
    
    public int getDungeonX() {
    	return DX;
    }
    
    public int getDungeonY() {
    	return DY;
    }
    
    public void dnorth(){
    	DY--;
    	
    }
    
    public void dsouth(){
    	DY++;
    	
    }
    public void dwest(){
    	DX--;
    	
    }
    
    public void deast(){
    	DX++;
    }

        
    public void setName(String name) {
    	this.name = name;
    }
    
    public String getName() {
    	return name;
    }
    
    public void enterDungeon() {
    	inDungeon = true;
    }
    
    public void exitDungeon() {
    	inDungeon = false;
    }
    
    public boolean inDungeon() {
    	return inDungeon;
    }
    
    public String doXP() {
    	if (xp >= ((int)(0.2 * (Math.pow(level - 1, 2)) * 100) + (int)(0.2 * (Math.pow(level, 2)) * 100))) {
    		level++;
    		points++;
    		return "You leveled to level " + level + "! You have gained one point.\n\n";
    	}
    	else {
    		return "";
    	}
    	
    	
    }
        
//    public String descRace(String desc){
//    	switch(desc.toLowerCase()){
//    	case "human":
//    		return Human.getDescription();
//    	case "elf":
//    		return Elf.getDescription();
//    	default:
//    		return "This race does not exist!\n\n";
//    	}
//    }
    
    public String setRace(String pick){
    	if(!racePicked){
    	switch(pick.toLowerCase()){
    	case "human":
    		race = new Human(this);
    		return "You are now a Human!\n\n" + race.getDescription() + "\n\n";
    	case "elf":
    		race = new Elf(this);
    		return "You are now an Elf!\n\n" + race.getDescription() + "\n\n";
    	case "golem":
    		race = new Golem(this);
    		return "You are now a Golem!\n\n" + race.getDescription() + "\n\n";
    	case "demon":
    		race = new Demon(this);
    		return "You have taken the form of a Demon.\n\n" + race.getDescription() + "\n\n";
    	case "gnome":
    		race = new Gnome(this);
    		return "You have become a gnome!\n\n" + race.getDescription() + "\n\n";
    	default:
    		return "That race does not exist!\n";
    	}
    	
    	}else{
    		return "You cannot choose another race!\n\n";
    		
    	}
    	
    	
    }
    
    public int getEffectSize() {
    	return effects.size();
    }
    
    public String returnAttributes(){
    	return "\nAttributes:\nCourage- " + courage +"\nFinesse-" + finesse +"\nVitality- " + vitality+"\nKnowledge- "+knowledge+"\nAllure- "+allure+"\nJudgement- "+judgement+"\n\nPoints-"+points+"\n\n";
    	
    }
    
    public int getCurrentXP() {
    	return xp;
    }
    
    public boolean characterCreation() {
    	return racePicked;
    }
    
    public void incXP(int add) {
    	xp += add;
    }
    
    public String incrementPoint(String attribute){
    	//if(!attributesPicked){
    	if(points > 0){
    	switch(attribute.toLowerCase()){
    	case "courage":
    		if(courage < 16){
    			courage++;
    			points--;
    		}else{
    			return "You cannot put anymore points into this attribute!\n\n";
    			
    		}
    	break;
    	case "allure":
    		if(allure < 16){
    			allure++;
    			points--;
    		}else{
    			return "You cannot put anymore points into this attribute!\n\n";
    			
    		}
    	break;
    	case "knowledge":
    		if(knowledge < 16){
    			knowledge++;
    			points--;
    		}else{
    			return "You cannot put anymore points into this attribute!\n\n";
    			
    		}
    	break;
    	case "vitality":
    		if(vitality < 16){
    			vitality++;
    			lowerHealth(-10);
    			points--;
    		}else{
    			return "You cannot put anymore points into this attribute!\n\n";
    			
    		}
    	break;
    	case "finesse":
    		if(finesse < 16){
    			finesse++;
    			points--;
    		}else{
    			return "You cannot put anymore points into this attribute!\n\n";
    			
    		}
    	break;
    	case "judgement":
    		if(judgement < 16){
    			judgement++;
    			points--;
    		}else{
    			return "You cannot put anymore points into this attribute!\n\n";
    			
    		}
    	break;
    	default:
    		return "That attribute does not exist!\n\n";
    	}
    	return "You add a point to "+ attribute.toLowerCase() + ".\n\n";
    }else{
    	return "You do not have enough points!\n\n";
    	
    }
    	//}else{
    	//	
    	//	return "You have already selected your attributes!";
    	//}
    	
    }
    public String decrementPoint(String attribute){
    	if(!attributesPicked){
    	if(points < 10){
    	switch(attribute.toLowerCase()){
    	case "courage":
    		if(courage > 0){
    			courage--;
    			points++;
    		}else{
    			return "You cannot remove anymore points from this attribute!\n\n";
    			
    		}
    	break;
    	case "allure":
    		if(allure > 0){
    			allure--;
    			points++;
    		}else{
    			return "You cannot remove anymore points from this attribute!\n\n";
    			
    		}
    	break;
    	case "knowledge":
    		if(knowledge > 0){
    			knowledge--;
    			points++;
    		}else{
    			return "You cannot remove anymore points from this attribute!\n\n";
    			
    		}
    	break;
    	case "vitality":
    		if(vitality > 0){
    			vitality--;
    			lowerHealth(10);
    			points++;
    		}else{
    			return "You cannot remove anymore points from this attribute!\n\n";
    			
    		}
    	break;
    	case "finesse":
    		if(finesse > 0){
    			finesse--;
    			points++;
    		}else{
    			return "You cannot remove anymore points from this attribute!\n\n";
    			
    		}
    	break;
    	case "judgement":
    		if(judgement > 0){
    			judgement--;
    			points++;
    		}else{
    			return "You cannot remove anymore points from this attribute!\n\n";
    			
    		}
    	break;
    	default:
    		return "That attribute does not exist!\n\n";
    	}
    	return "You remove a point from "+ attribute.toLowerCase() + ".\n\n";
    }else{
    	return "You cannot remove anymore points!\n\n";
    	
    }
    	}else{
    		return "You have already selected your attributes!\n\n";
    		
    	}
    }
    
    
    public int getPoints(){
    	return points;
    	
    	
    }
    public int getCourage(){
    	return courage;
    	
    }
    public int getAllure(){
    	return allure;
    	
    }
    public int getknowledge(){
    	return knowledge;
    	
    }
    public int getVitality(){
    	return vitality;
    	
    }
    public int getFinesse(){
    	return finesse;
    	
    }
    public int getJudgement(){
    	return judgement;
    	
    }
    public void setCourage(int courage){
    	this.courage = courage;
    	
    }
    public void setAllure(int allure){
    	this.allure = allure;
    	
    }
    public void setknowledge(int knowledge){
    	this.knowledge = knowledge;
    	
    }
    public void setVitality(int vitality){
    	this.vitality = vitality;
    	setHealth(100);
    	lowerHealth(vitality * -10);
    	
    }
    public void setFinesse(int finesse){
    	this.finesse = finesse;
    	
    }
    public void setJudgement(int judgement){
    	this.judgement = judgement;
    	
    }
    public void setInv(int pos, Item item) {
        inventory.setInv(pos, item);
    }
    
    public void setMaxWeight(int w) {
    	maxWeight = w;
    }
    
    public int getMaxWeight() {
    	return maxWeight;
    }
    
    public int getWeight() {
    	return weight;
    }
    
    public void setWeight(int w) {
    	weight = w;
    }
    
    public void setHand(int pos, Item item) {
        inventory.setEquip(pos, item);
    }
    
    public Item getHand(int pos) {
        return inventory.getEquipItem(pos);
    }
    
    public int getInvWorth(int pos) {
        return getInv(pos).getWorth();
    }
    
    public String getInvList() {
        return inventory.printInv();
    }
    
    public Item getInv(int pos) {
        return inventory.getInvItem(pos);
    }
    
    public void setX(int move) {
        X += move;
    }
    
    public int setX(){
    	X += 1;
    	return X;
    }
    
    public void setY(int move) {
        Y += move;
    }
    
    public int getX() {
        return X;
    }
    
    public int getY() {
        return Y;
    }
    
    
    public void north(){
    	Y--;
    	
    }
    
    public void south(){
    	Y++;
    	
    }
    public void west(){
    	X--;
    	
    }
    
    public void east(){
    	X++;
    }

	public int getLevel() {
		return level;
	}
}