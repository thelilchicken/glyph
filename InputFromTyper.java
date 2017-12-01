package main;
import java.awt.*;
import java.awt.event.*;

import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;

import java.util.*;
import java.util.List;

public class InputFromTyper implements ActionListener {
	
	/*
	 * World/Interface Related Variables
	 */
	
	private JTextField text;
	private JTextArea area;
	private JTextArea inventory;
	private WorldGen map;
	private Player player;
	private boolean turn = false;
	//private JLabel enemy, pl;
	private JTextArea inv;
	private JFrame win;
	private JPanel main;
	private JPanel start;
	private JScrollPane scrollPane;
	
	/*
	 * Statistics Variables
	 */
	
	private int tradesDone = 0;
	private int mobsKilled = 0;
	private int movesMade = 0;
	private int usedItems = 0;
	
	/*
	 * Command Specific Variables
	 */
	
	private String[] commandSplit;
	private List<Plot> savedCoord = new ArrayList<Plot>();
	private List<String> comm = new ArrayList<String>();
	
	private boolean inShop = false;
	private boolean inInventory = false;
	private boolean inInv = false;
	private boolean inHand = false;
	private int shopItemPos; 
	private int invItemPos;
	private Item tempItem;
	
	private int sm;
	
	private int effectSize;
	
	private int dmgItem1, dmgItem2;
	private double spellDmg;
	
	private boolean errorCatch = true;
	
	private int time = 1;
	private boolean dayNight = true;
	
	private boolean inGame;
	
	/*
	 * Character Creation Related Variables
	 */
	private boolean raceChosen = false;
	private boolean nameChosen = false;
	
	/*
	 * Inventory Screen Variables
	 */
	private String finalHealth;
	private int nHealth, dHealth;
	
	/*
	 * InputFromTyper Constructor (Edit at own risk!)
	 */
	
	public InputFromTyper(JTextField text, JTextArea area, JTextArea inventory, WorldGen map, Player player, JTextArea inv, List<String> comm, JFrame win, JPanel main, JPanel start, boolean inGame, JScrollPane scrollPane){
		super();
		this.text = text;
		this.area = area;
		this.inventory = inventory;
		this.map = map;
		this.player = player;
		this.main = main;
		this.start = start;
		this.inGame = inGame;
		this.scrollPane = scrollPane;
		//this.pl = pl;
		//this.enemy = enemy;
		this.inv = inv;
		this.comm = comm;
		this.win = win;
		
		sm = player.getSM();
		
		text.requestFocusInWindow();
		
		area.setText("");
		
		area.append("\nCharacter Creation\n\n");
		area.append("Your race determines how your starting 5 points are distributed among\nyour attributes, which gives you various buffs.\n");
		area.append("Your name is displayed at the top of the Inventory panel, or the right\n");
		area.append("panel. To select a race, type \"race\" into the input bar below, followed\n");
		area.append("by the race that you choose. That input bar is where you will be typing\n");
		area.append("all commands through the game. You can set your name by typing \"name\"\n");
		area.append("into the bar, followed by a name of your choice. It has to be between 2\n");
		area.append("and 15 characters, and can only contain letters. Any of the settings\n");
		area.append("you choose can be changed using the same commands (i.e. name Potato) until\n");
		area.append("you type confirm. After typing confirm, the game will start and your\n");
		area.append("character will be locked. The attributes recieved based on the race you\n");
		area.append("chose can be seen in the right panel.\n");
		
		area.append("\n");
		player.setMaxWeight(100);
		player.setDamage((int)(player.getHand(0).getItemDamage() + player.getHand(1).getItemDamage()) / 2);
		
		inv.setText("");
		//inv.append("Health: " + player.getHealth() + " | Enemy: \n\n");
		inv.append("\nChoose a race!\n - Use the command: Race *Human | Elf | Golem | Demon | Gnome*\n\n");
		inv.append("Set a name!\n - Use the command: Name *name*\n\n");
		inv.append(player.returnAttributes());
	}
	
	/*
	 * Commands : Follow this prompt:
	 * 
	 * Every command should contain:
	 * 
	 * - The "turn" boolean set to true at TOP of command, then "false" at end.
	 * - Use else if() with the commandSplit[0].toLowerCase() in the main else statement.
	 * - Check the length of the command with an if/else statement inside the else if() statement.
	 * - MAKE SURE TO RESET ALL NECESSARY VARIABLES (i.e inInv) AT END OF COMMAND!
	 * - Make sure to increment TIME when necessary.
	 * - Add the CORRECT command and explanation in the "else" statement that contains the "help" page.
	 */
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// Splits input into an array, to be grabbed later
		commandSplit = text.getText().split(" ");
		
		// Sets cursor to textbox
		
		text.requestFocusInWindow();
		
		// Adds command to Previous Commands ArrayList
		if (comm.size() > 0) {
			if (!(text.getText().trim().equals("")) && !(text.getText().toLowerCase().trim().equals(comm.get(0).toLowerCase().trim()))) {
				if (comm.size() < 25) {
					comm.add(0, text.getText().trim());
				}
				else {
					for (int x = comm.size() - 1; x > 0; x--) {
						comm.set(x, comm.get(x - 1));
					}
					comm.set(0, text.getText().trim());
				}
			}
		}
		else {
			if (!(text.getText().trim().equals(""))) {
				comm.add(0, text.getText().trim());
			}
		}
					
		/*
		 * THIS STATEMENT IS USED TO DETERMINE THE DIFFERENCE BETWEEN CHARACTER
		 * CREATION AND ACTUAL GAMEPLAY. CHANGING WITH NO KNOWLEDGE OF WHAT IT
		 * DOES CAN RENDER THE GAME UNPLAYABLE.
		 */
		if (main.isVisible() && !(commandSplit[0].equals(""))) {
			if (player.characterCreation()) {
			
				/*
				 * These variables are set when any command is run, and before any command is run.
				 * They determine the modifications made to various things based on the player's attributes.
				 * Use these variables when you want to get player effects on enemies (i.e. modifications,
				 * damage, etc.) These contain the equations for all the point modifications, and modifying
				 * these variables/equations will drastically effect game play.
				 */
				
				if (player.getCourage() > 0) {
					dmgItem1 = (player.getHand(0).getItemDamage() + (int)(player.getHand(0).getItemDamage() * ((0.5) * Math.log(player.getCourage()))));
					// area.append("" + dmgItem1 + "\n\n");
					dmgItem2 = (player.getHand(1).getItemDamage() + (int)(player.getHand(1).getItemDamage() * ((0.5) * Math.log(player.getCourage()))));
					// area.append("" + dmgItem2 + "\n\n");
				}
				else {
					dmgItem1 = (player.getHand(0).getItemDamage());
					// area.append("" + dmgItem1 + "\n\n");
					dmgItem2 = (player.getHand(1).getItemDamage());
					// area.append("" + dmgItem2 + "\n\n");
				}
				
				effectSize = player.getEffectSize();
				
				//Runs Status Effects
			    if (!(text.getText().equals(""))) {
			    	if (effectSize > 0) {
			    		player.setHealth(player.doStatus(player.getHealth()));
			    		area.append(player.printStatusName());
			    		player.checkTime();
			    	}
			    }
			    
			    sm = ((-1 * player.getFinesse()) + player.getMod());
				
				// Needs to be fixed
				spellDmg = (((int)(Math.pow(0.3, player.getknowledge()))));
			    
				/*
			    * Time Cycle (Not a command, runs the time cycle)
			    */
			    //if (time % 100 == 0) {
			    //    if (dayNight == true) {
			    //        dayNight = false;
			    //        area.append("The sun has set.\n\n");
			    //    }
			    //    else {
			    //        dayNight = true;
			    //        area.append("The sun shines over the horizon.\n\n");
			    //    }
			    //}
			    
			    //Prevents 2 commands from running at the same time, or before another has finished.
			    
			    if (turn == false) {
			    	
			    	/*
			    	 * Input = "hi" (Exception to certain rules because it is not an element of gameplay)
			    	 */
			    	if(commandSplit[0].toLowerCase().equals("hi")){
			    		turn = true;
			            area.append("Why hello there!\n");
			            area.append("\n");
			            
			            turn = false;
			    	}
			    	
			    	/*
			    	 * Input = "quit"
			    	 */
			    	if (commandSplit[0].toLowerCase().equals("quit")) {
			    		turn = true;
			    		area.append("Closing Game");
			    		win.dispose();
			    		turn = false;
			    	}
			    	
			    	/*
			         * Input = "clear" (Doesn't need all the stuff specified above)
			         */
			        else if(commandSplit[0].trim().toLowerCase().equals("clear")) {
			        	turn = true;
			          	area.setText("");
			          	 
			          	turn = false;
			        }
			    	
			    	/*
			    	 * Input = "terrain"
			    	 */
			        else if (commandSplit[0].trim().toLowerCase().equals("terrain")) {
			            turn = true;
			            if (player.inDungeon()) {
			            	area.append("Terrain: " + map.getMap(player.getX(), player.getY()).getTerrainType().getDPlot(player.getDungeonX(), player.getDungeonY()).getText() + "\n");
			            	area.append("\n");
			            }
			            else {
			            	area.append("Terrain: " + map.getMap(player.getX(), player.getY()).getText() + "\n");
			            	area.append("\n");
			            }
			            
			             
			            turn = false;
			        }
			    	
			    	/*
			    	 * Input = "up" OR Input = "north"
			    	 */
			        else if(commandSplit[0].trim().toLowerCase().equals("up") || commandSplit[0].trim().toLowerCase().equals("north")) {
			        	if (commandSplit.length == 1) {
			        		turn = true;
			        		if ((Math.random() * 100) + 1 < player.getMaxWeight() - (player.getWeight() - player.getMaxWeight())) {
			        			if (!player.inDungeon()) {
			        				if (map.getMap(player.getX(), player.getY()).getEnc().getHealth() <= 0 || map.getMap(player.getX(), player.getY()).getEnc().tryEscape(player.getLevel())) {
			        					if (player.getY() > 0) { // For if edge of map is not reached
			        						player.north();
			        						area.append("You move north!\n");
			        						area.append("Terrain: " + map.getMap(player.getX(), player.getY()).getText() + "\n");
			        						area.append("Encounters: You encounter " + map.getMap(player.getX(), player.getY()).getCharacterDes() + "!\n");
			        						
			        						area.append("\n");	        			
			        						map.getMap(player.getX(), player.getY() + 1).setNewEncounter(); // Resets previous tile encounter (random encounter)
			        						
			        					}
			        					else { // If edge of map is reached
			        						area.append("You cannot move any further north!\n\n");
			        						 
			        					}
			        				}
			        				else {
			        					area.append("Your attempt to escape failed, and " + map.getMap(player.getX(), player.getY()).getCharacterDes() + " dealt " + map.getMap(player.getX(), player.getY()).getEnc().getDamage() + " damage!\n\n");
			        					player.lowerHealth(map.getMap(player.getX(), player.getY()).getEnc().getDamage());
			        					
			        				}
			        			}
			        			else {
			        				if (map.getMap(player.getX(), player.getY()).getTerrainType().getDPlot(player.getDungeonX(), player.getDungeonY()).getEnc().getHealth() <= 0 || map.getMap(player.getX(), player.getY()).getTerrainType().getDPlot(player.getDungeonX(), player.getDungeonY()).getEnc().tryEscape(player.getLevel())) {
			        					if (player.getDungeonY() > 0) { // For if edge of map is not reached
			        						player.dnorth();
			        						area.append("You walk north!\n");
			        						area.append("Terrain: " + map.getMap(player.getX(), player.getY()).getTerrainType().getDPlot(player.getDungeonX(), player.getDungeonY()).getText() + "\n");
			        						area.append("Encounters: You encounter " + map.getMap(player.getX(), player.getY()).getTerrainType().getDPlot(player.getDungeonX(), player.getDungeonY()).getCharacterDes() + "!\n");
			        						
			        						area.append("\n");	        			
			        						map.getMap(player.getX(), player.getY()).getTerrainType().getDPlot(player.getDungeonX(), player.getDungeonY() + 1).setNewEncounter(); // Resets previous tile encounter (random encounter)
			        						
			        					}
			        					else { // If edge of map is reached
			        						area.append("You ran into the wall!\n\n");
			        						 
			        					}
			        				}
			        				else {
			        					area.append("Your attempt to escape failed, and " + map.getMap(player.getX(), player.getY()).getTerrainType().getDPlot(player.getDungeonX(), player.getDungeonY()).getCharacterDes() + " dealt " + map.getMap(player.getX(), player.getY()).getTerrainType().getDPlot(player.getDungeonX(), player.getDungeonY()).getEnc().getDamage() + " damage!\n\n");
			        					player.lowerHealth(map.getMap(player.getX(), player.getY()).getTerrainType().getDPlot(player.getDungeonX(), player.getDungeonY()).getEnc().getDamage());
			        					
			        				}
			        			}
			        		}
			        		else {
			        			area.append("You are carrying more than you can handle, and were unable to move!\n\n");
			        		}
			        		
			        		 
			        		movesMade++; // Statistics
			        		time++; // Time	        		
			        	}
			        	else {
			        		area.append("" + commandSplit[0].toLowerCase() + " does not take any arguements!\n\n");
			        	}
			        	turn = false;
			        	 
			        }
			    	
			    	/*
			    	 * Input = "down" OR Input = "south"
			    	 */
			        else if(commandSplit[0].trim().toLowerCase().equals("down") || commandSplit[0].trim().toLowerCase().equals("south")) {
			        	if (commandSplit.length == 1) {
			        		turn = true;
			        		if ((Math.random() * 100) + 1 < player.getMaxWeight() - (player.getWeight() - player.getMaxWeight())) {
			        			if (!player.inDungeon()) {
			        				if (map.getMap(player.getX(), player.getY()).getEnc().getHealth() <= 0 || map.getMap(player.getX(), player.getY()).getEnc().tryEscape(player.getLevel())) {
			        					if (player.getY() < 199) { // For if edge of map is not reached
			        						player.south();
			        						area.append("You move south!\n");
			        						area.append("Terrain: " + map.getMap(player.getX(), player.getY()).getText() + "\n");
			        						area.append("Encounters: You encounter " + map.getMap(player.getX(), player.getY()).getCharacterDes() + "!\n");
			        						
			        						area.append("\n");	        			
			        						map.getMap(player.getX(), player.getY() - 1).setNewEncounter(); // Resets previous tile encounter (random encounter)
			        						
			        					}
			        					else { // If edge of map is reached
			        						area.append("You cannot move any further south!\n\n");
			        						 
			        					}
			        				}
			        				else {
			        					area.append("Your attempt to escape failed, and " + map.getMap(player.getX(), player.getY()).getCharacterDes() + " dealt " + map.getMap(player.getX(), player.getY()).getEnc().getDamage() + " damage!\n\n");
			        					player.lowerHealth(map.getMap(player.getX(), player.getY()).getEnc().getDamage());
			        					
			        				}
			        			}
			        			else {
			        				if (map.getMap(player.getX(), player.getY()).getTerrainType().getDPlot(player.getDungeonX(), player.getDungeonY()).getEnc().getHealth() <= 0 || map.getMap(player.getX(), player.getY()).getTerrainType().getDPlot(player.getDungeonX(), player.getDungeonY()).getEnc().tryEscape(player.getLevel())) {
			        					if (player.getDungeonY() < 9) { // For if edge of map is not reached
			        						player.dsouth();
			        						area.append("You walk south!\n");
			        						area.append("Terrain: " + map.getMap(player.getX(), player.getY()).getTerrainType().getDPlot(player.getDungeonX(), player.getDungeonY()).getText() + "\n");
			        						area.append("Encounters: You encounter " + map.getMap(player.getX(), player.getY()).getTerrainType().getDPlot(player.getDungeonX(), player.getDungeonY()).getCharacterDes() + "!\n");
			        						
			        						area.append("\n");	        			
			        						map.getMap(player.getX(), player.getY()).getTerrainType().getDPlot(player.getDungeonX(), player.getDungeonY() - 1).setNewEncounter(); // Resets previous tile encounter (random encounter)
			        						
			        					}
			        					else { // If edge of map is reached
			        						area.append("You ran into the wall!\n\n");
			        						 
			        					}
			        				}
			        				else {
			        					area.append("Your attempt to escape failed, and " + map.getMap(player.getX(), player.getY()).getTerrainType().getDPlot(player.getDungeonX(), player.getDungeonY()).getCharacterDes() + " dealt " + map.getMap(player.getX(), player.getY()).getTerrainType().getDPlot(player.getDungeonX(), player.getDungeonY()).getEnc().getDamage() + " damage!\n\n");
			        					player.lowerHealth(map.getMap(player.getX(), player.getY()).getTerrainType().getDPlot(player.getDungeonX(), player.getDungeonY()).getEnc().getDamage());
			        					
			        				}
			        			}
			        		}
			        		else {
			        			area.append("You are carrying more than you can handle, and were unable to move!\n\n");
			        		}
			        		movesMade++; // Statistics
			        		time++; // Time	 
			        		
			        		turn = false;
			        	}
			        	else {
			        		area.append("" + commandSplit[0].toLowerCase() + " does not take any arguements!\n\n");
			        	}
			        	 
			        }
			    	
			    	/*
			    	 * Input = "left" OR Input = "west"
			    	 */
			        else if(commandSplit[0].trim().toLowerCase().equals("left") || commandSplit[0].trim().toLowerCase().equals("west")) {
			        	if (commandSplit.length == 1) {
			        		turn = true;
			        		if ((Math.random() * 100) + 1 < player.getMaxWeight() - (player.getWeight() - player.getMaxWeight())) {
			        			if (!player.inDungeon()) {
			        				if (map.getMap(player.getX(), player.getY()).getEnc().getHealth() <= 0 || map.getMap(player.getX(), player.getY()).getEnc().tryEscape(player.getLevel())) {
			        					if (player.getX() > 0) {
			        						player.west();
			        						area.append("You move west!\n");
			        						area.append("Terrain: " + map.getMap(player.getX(), player.getY()).getText() + "\n");
			        						area.append("Encounters: You encounter " + map.getMap(player.getX(), player.getY()).getCharacterDes() + "!\n");
			        						
			        						area.append("\n");
			        						 
			        						map.getMap(player.getX() + 1, player.getY()).setNewEncounter(); // Reset encounters
			        					}
			        					else {
			        						area.append("You cannot move any further west!\n\n");
			        						 
			        					}
			        				}
			        				else {
			        					area.append("Your attempt to escape failed, and " + map.getMap(player.getX(), player.getY()).getCharacterDes() + " dealt " + map.getMap(player.getX(), player.getY()).getEnc().getDamage() + " damage!\n\n");
			        					player.lowerHealth(map.getMap(player.getX(), player.getY()).getEnc().getDamage());
			        					
			        				}
			        			}
			        			else {
			        				if (map.getMap(player.getX(), player.getY()).getTerrainType().getDPlot(player.getDungeonX(), player.getDungeonY()).getEnc().getHealth() <= 0 || map.getMap(player.getX(), player.getY()).getTerrainType().getDPlot(player.getDungeonX(), player.getDungeonY()).getEnc().tryEscape(player.getLevel())) {
			        					if (player.getDungeonX() > 0) { // For if edge of map is not reached
			        						player.dwest();
			        						area.append("You walk west!\n");
			        						area.append("Terrain: " + map.getMap(player.getX(), player.getY()).getTerrainType().getDPlot(player.getDungeonX(), player.getDungeonY()).getText() + "\n");
			        						area.append("Encounters: You encounter " + map.getMap(player.getX(), player.getY()).getTerrainType().getDPlot(player.getDungeonX(), player.getDungeonY()).getCharacterDes() + "!\n");
			        						
			        						area.append("\n");	        			
			        						map.getMap(player.getX(), player.getY()).getTerrainType().getDPlot(player.getDungeonX() + 1, player.getDungeonY()).setNewEncounter(); // Resets previous tile encounter (random encounter)
			        						
			        					}
			        					else { // If edge of map is reached
			        						area.append("You ran into the wall!\n\n");
			        						 
			        					}
			        				}
			        				else {
			        					area.append("Your attempt to escape failed, and " + map.getMap(player.getX(), player.getY()).getTerrainType().getDPlot(player.getDungeonX(), player.getDungeonY()).getCharacterDes() + " dealt " + map.getMap(player.getX(), player.getY()).getTerrainType().getDPlot(player.getDungeonX(), player.getDungeonY()).getEnc().getDamage() + " damage!\n\n");
			        					player.lowerHealth(map.getMap(player.getX(), player.getY()).getTerrainType().getDPlot(player.getDungeonX(), player.getDungeonY()).getEnc().getDamage());
			        					
			        				}
			        			}
			        		}
			        		else {
			        			area.append("You are carrying more than you can handle, and were unable to move!\n\n");
			        		}
			        		movesMade++; // Statistics
			        		time++; // Time
			        		turn = false;
			        	}
			        	else {
			        		area.append("" + commandSplit[0].toLowerCase() + " does not take any arguements!\n\n");
			        	}
			        	 
			        }
			    	
			    	/*
			    	 * Input = "right" OR Input = "east"
			    	 */
			        else if(commandSplit[0].trim().toLowerCase().equals("right") || commandSplit[0].trim().toLowerCase().equals("east")) {
			        	if (commandSplit.length == 1) {
			        		turn = true;
			        		if ((Math.random() * 100) + 1 < player.getMaxWeight() - (player.getWeight() - player.getMaxWeight())) {
			        			if (!player.inDungeon()) {
			        				if (map.getMap(player.getX(), player.getY()).getEnc().getHealth() <= 0 || map.getMap(player.getX(), player.getY()).getEnc().tryEscape(player.getLevel())) {
			        					if (player.getX() < 199) {
			        						player.east();
			        						area.append("You move east!\n");
			        						area.append("Terrain: " + map.getMap(player.getX(), player.getY()).getText() + "\n");
			        						area.append("Encounters: You encounter " + map.getMap(player.getX(), player.getY()).getCharacterDes() + "!\n");
			        						if (!(map.getMap(player.getX(), player.getY()).getEnc() instanceof DefaultCharacter)) {
			        							
			        						}
			        						else {
			        							
			        						}
			        						area.append("\n");
			        						 
			        						map.getMap(player.getX() - 1, player.getY()).setNewEncounter();
			        					}
			        					else {
			        						area.append("You cannot move any further east!\n\n");
			        						 
			        					}
			        				}
			        				else {
			        					area.append("Your attempt to escape failed, and " + map.getMap(player.getX(), player.getY()).getCharacterDes() + " dealt " + map.getMap(player.getX(), player.getY()).getEnc().getDamage() + " damage!\n\n");
			        					player.lowerHealth(map.getMap(player.getX(), player.getY()).getEnc().getDamage());
			        				
			        				}
			        			}
			        			else {
			        				if (map.getMap(player.getX(), player.getY()).getTerrainType().getDPlot(player.getDungeonX(), player.getDungeonY()).getEnc().getHealth() <= 0 || map.getMap(player.getX(), player.getY()).getTerrainType().getDPlot(player.getDungeonX(), player.getDungeonY()).getEnc().tryEscape(player.getLevel())) {
			        					if (player.getDungeonX() < 9) { // For if edge of map is not reached
			        						player.deast();
			        						area.append("You walk east!\n");
			        						area.append("Terrain: " + map.getMap(player.getX(), player.getY()).getTerrainType().getDPlot(player.getDungeonX(), player.getDungeonY()).getText() + "\n");
			        						area.append("Encounters: You encounter " + map.getMap(player.getX(), player.getY()).getTerrainType().getDPlot(player.getDungeonX(), player.getDungeonY()).getCharacterDes() + "!\n");
			        						if (!(map.getMap(player.getX(), player.getY()).getTerrainType().getDPlot(player.getDungeonX(), player.getDungeonY()).getEnc() instanceof DefaultCharacter)) {
			        							
			        						}
			        						else {
			        							
			        						}
			        						area.append("\n");	        			
			        						map.getMap(player.getX(), player.getY()).getTerrainType().getDPlot(player.getDungeonX() - 1, player.getDungeonY()).setNewEncounter(); // Resets previous tile encounter (random encounter)
			        						
			        					}
			        					else { // If edge of map is reached
			        						area.append("You ran into the wall!\n\n");
			        						 
			        					}
			        				}
			        				else {
			        					area.append("Your attempt to escape failed, and " + map.getMap(player.getX(), player.getY()).getTerrainType().getDPlot(player.getDungeonX(), player.getDungeonY()).getCharacterDes() + " dealt " + map.getMap(player.getX(), player.getY()).getTerrainType().getDPlot(player.getDungeonX(), player.getDungeonY()).getEnc().getDamage() + " damage!\n\n");
			        					player.lowerHealth(map.getMap(player.getX(), player.getY()).getTerrainType().getDPlot(player.getDungeonX(), player.getDungeonY()).getEnc().getDamage());
			        					
			        				}
			        			}
			        		}
			        		else {
			        			area.append("You are carrying more than you can handle, and were unable to move!\n\n");
			        		}
			        		movesMade++; // Statistics
			        		time++; // Time	  
			        		turn = false;
			        	}
			        	else {
			        		area.append("" + commandSplit[0].toLowerCase() + " does not take any arguements!\n\n");
			        	}
			        	 
			        }
			    	
			    	/*
			    	 * Input = "xy"
			    	 */
			        else if (commandSplit[0].toLowerCase().trim().equals("xy")) {
			        	turn = true;
			        	if (commandSplit.length == 1) {
			        		if (player.inDungeon()) {
			        			turn = true;
			        			area.append("You are located at X = " + player.getDungeonX() + ", Y = " + (10 - (player.getDungeonY())) + ".\n\n");
			        			turn = false;
			        		}
			        		else {
			        			turn = true;
			        			area.append("You are located at X = " + player.getX() + ", Y = " + (200 - (player.getY())) + ".\n");
			        			area.append("\n");
			        			turn = false;
			        		}
			        	}
			        	else {
			        		area.append("" + commandSplit[0].toLowerCase() + " does not take any arguements!\n\n");
			        	}
			        	
			        	turn = false;
			        	 
			        }
			    	
			    	/*
			    	 * Input = "Enter"
			    	 */
			        else if (commandSplit[0].trim().toLowerCase().equals("enter")) {
			        	turn = true;
			        	if (commandSplit.length == 1) {
			        		if (map.getMap(player.getX(), player.getY()).getTerrainType() instanceof Dungeon && !player.inDungeon()) {
			        			if (map.getMap(player.getX(), player.getY()).getEnc().getHealth() < 0 || map.getMap(player.getX(), player.getY()).getEnc().tryEscape(player.getLevel())) {
			        				player.enterDungeon();
			        				area.append("You walk through the entrance of a dungeon.\n\n");
			        			}
			        			else {
		        					area.append("Your attempt to escape failed, and " + map.getMap(player.getX(), player.getY()).getCharacterDes() + " dealt " + map.getMap(player.getX(), player.getY()).getEnc().getDamage() + " damage!\n\n");
		        					player.lowerHealth(map.getMap(player.getX(), player.getY()).getEnc().getDamage());
		        					
		        				}
			        		}
			        		else {
			        			area.append("You must be at a dungeon entrance!\n\n");
			        		}
			        	}
			        	else {
			        		area.append("Enter does not take any arguments!\n\n");
			        	}
			        	turn = false;
			        }
			    	
			    	/*
			    	 * Input = "Exit"
			    	 */
			        else if (commandSplit[0].trim().toLowerCase().equals("exit")) {
			        	turn = true;
			        	if (commandSplit.length == 1) {
			        		if (player.inDungeon()) {
			        			if (map.getMap(player.getX(), player.getY()).getTerrainType().getDPlot(player.getDungeonX(), player.getDungeonY()).getTerrainType() instanceof Stairs) {
			        				if (map.getMap(player.getX(), player.getY()).getTerrainType().getDPlot(player.getDungeonX(), player.getDungeonY()).getEnc().getHealth() < 0 || map.getMap(player.getX(), player.getY()).getTerrainType().getDPlot(player.getDungeonX(), player.getDungeonY()).getEnc().tryEscape(player.getLevel())) {
			        					player.exitDungeon();
			        					area.append("You exit the dungeon!\n\n");
			        				}
			        				else {
			        					area.append("Your attempt to escape failed, and " + map.getMap(player.getX(), player.getY()).getTerrainType().getDPlot(player.getDungeonX(), player.getDungeonY()).getCharacterDes() + " dealt " + map.getMap(player.getX(), player.getY()).getTerrainType().getDPlot(player.getDungeonX(), player.getDungeonY()).getEnc().getDamage() + " damage!\n\n");
			        					player.lowerHealth(map.getMap(player.getX(), player.getY()).getTerrainType().getDPlot(player.getDungeonX(), player.getDungeonY()).getEnc().getDamage());
			        					
			        				}
			        			}
			        			else {
			        				area.append("This is not an exit to the dungeon!\n\n");
			        			}
			        		}
			        		else {
			        			area.append("You must be inside a dungeon!\n\n");
			        		}
			        	}
			        	else {
			        		area.append("Exit does not take any arguments!\n\n");
			        	}
			        	turn = false;
			        }
			    	
			    	/*
			    	 * Input = "Attack"
			    	 */
			    	
			        else if(commandSplit[0].toLowerCase().trim().equals("attack")){
			        	turn = true;
			        	if (commandSplit.length == 1) {
			        		if (player.inDungeon()) {
			        			if(!(map.getMap(player.getX(), player.getY()).getTerrainType().getDPlot(player.getDungeonX(), player.getDungeonY()).getEnc() instanceof DefaultCharacter) && map.getMap(player.getX(), player.getY()).getTerrainType().getDPlot(player.getDungeonX(), player.getDungeonY()).getEnc().getHealth() != 0){
			        				int dmgDealt = 0; // Total damage dealt to enemy
			        				// Checks if first hand hits
			        				if ((Math.random() * 100) < (player.getHand(0).getSuccessChance() - (sm))) {
			        					if (map.getMap(player.getX(), player.getY()).getTerrainType().getDPlot(player.getDungeonX(), player.getDungeonY()).getEnc().dodge()) {
			        						if (player.getHand(0) instanceof DefaultItem) {
			        							area.append("" + map.getMap(player.getX(), player.getY()).getTerrainType().getDPlot(player.getDungeonX(), player.getDungeonY()).getEnc().getDes() + " dodged your fist.\n");
			        						}
			        						else {
			        							area.append("" + map.getMap(player.getX(), player.getY()).getTerrainType().getDPlot(player.getDungeonX(), player.getDungeonY()).getEnc().getDes() + " dodged your " + player.getHand(0).getItemName() + ".\n");
			        						}
			        					}
			        					else {
			        						map.getMap(player.getX(), player.getY()).getTerrainType().getDPlot(player.getDungeonX(), player.getDungeonY()).getEnc().lowerHealth(dmgItem1);
			        						dmgDealt += dmgItem1;
			        					}
			        				}
			        				else {
			        					area.append("You miss your " + player.getHand(0).getItemName() + ".\n");
			        				}
			        				// Checks is second hand hits
			        				if ((Math.random() * 100) < (player.getHand(1).getSuccessChance() - (sm))) {
			        					if (map.getMap(player.getX(), player.getY()).getTerrainType().getDPlot(player.getDungeonX(), player.getDungeonY()).getEnc().dodge()) {
			        						if (player.getHand(1) instanceof DefaultItem) {
			        							area.append("" + map.getMap(player.getX(), player.getY()).getTerrainType().getDPlot(player.getDungeonX(), player.getDungeonY()).getEnc().getDes() + " dodged your fist.\n");
			        						}
			        						else {
			        							area.append("" + map.getMap(player.getX(), player.getY()).getTerrainType().getDPlot(player.getDungeonX(), player.getDungeonY()).getEnc().getDes() + " dodged your " + player.getHand(1).getItemName() + ".\n");
			        						}
			        					}
			        					else {
			        						map.getMap(player.getX(), player.getY()).getTerrainType().getDPlot(player.getDungeonX(), player.getDungeonY()).getEnc().lowerHealth(dmgItem2);
			        						dmgDealt += dmgItem2;
			        					}
			        				}
			        				else {
			        					area.append("You miss your " + player.getHand(1).getItemName() + ".\n");
			        				}
			        				// Prints total damage dealt to enemy
			        				area.append("You dealt " + dmgDealt + " damage to " + map.getMap(player.getX(), player.getY()).getTerrainType().getDPlot(player.getDungeonX(), player.getDungeonY()).getCharacterDes() + "!\n");
			        				//area.append("D1: " + dmgItem1 + " D2: " + dmgItem2 + "\n");
			        				dmgDealt = 0;
			        				// Sets health labels of both at the top if enemy still has health
			        				if (map.getMap(player.getX(), player.getY()).getTerrainType().getDPlot(player.getDungeonX(), player.getDungeonY()).getEnc().getHealth() > 0) {
			        					
			        					player.lowerHealth(map.getMap(player.getX(), player.getY()).getTerrainType().getDPlot(player.getDungeonX(), player.getDungeonY()).getEnc().getDamage());
			        					// Give Status Effect to Player
			        					boolean effected = false;
			        					for (int x = 0; x < player.getEffectSize(); x++) {
			        						if (player.getPlayerStat(x).getVal() == map.getMap(player.getX(), player.getY()).getTerrainType().getDPlot(player.getDungeonX(), player.getDungeonY()).getEnc().getEffect().getVal()) {
			        							effected = true;
			        						}
			        					}
			        					if (map.getMap(player.getX(), player.getY()).getTerrainType().getDPlot(player.getDungeonX(), player.getDungeonY()).getEnc().getStatChance() >= (int)(Math.random() * 101) && !effected) {
			        						player.addStatus(map.getMap(player.getX(), player.getY()).getTerrainType().getDPlot(player.getDungeonX(), player.getDungeonY()).getEnc().getEffect());
			        						area.append("You have been given " + map.getMap(player.getX(), player.getY()).getTerrainType().getDPlot(player.getDungeonX(), player.getDungeonY()).getEnc().getEffect().getName() + " for " + map.getMap(player.getX(), player.getY()).getTerrainType().getDPlot(player.getDungeonX(), player.getDungeonY()).getEnc().getEffect().getDuration() + " turns!\n");
			        					}
			        					
			        					
			        				}
			        				// If enemy loses all health
			        				else {
			        					finalHealth = "Health: " + player.getHealth() + " | Enemy: " + map.getMap(player.getX(), player.getY()).getTerrainType().getDPlot(player.getDungeonX(), player.getDungeonY()).getEnc().getHealth() + "\n\n";
			        					area.append("You have killed " + map.getMap(player.getX(), player.getY()).getTerrainType().getDPlot(player.getDungeonX(), player.getDungeonY()).getCharacterDes() + "!\n\n");
			        					player.incXP(map.getMap(player.getX(), player.getY()).getTerrainType().getDPlot(player.getDungeonX(), player.getDungeonY()).getEnc().getXP());
			        					mobsKilled++;
			        					
			        					/*
			        					 * Give Items to Player
			        					 */
			        					for (int x = 0; x < 9; x++) {
			        						if (player.getInv(x).getItemName().toLowerCase().equals(map.getMap(player.getX(), player.getY()).getTerrainType().getDPlot(player.getDungeonX(), player.getDungeonY()).getEnc().getDrop().getItemName().toLowerCase())) {
			        							player.getInv(x).incNumInInv(1);
			        							inInv = true;
			        							break;
			        						}
			        					}
			        					
			        					if (inInv == false) {
			        						AddToInv:
			        							for (int x = 0; x < 9; x++) {
			        								if (player.getInv(x) instanceof DefaultItem) {
			        									player.setInv(x, map.getMap(player.getX(), player.getY()).getTerrainType().getDPlot(player.getDungeonX(), player.getDungeonY()).getEnc().getDrop());
			        									player.getInv(x).setNumInInv(1);
			        									break AddToInv;
			        								}
			        								
			        								else {
			        									if (x == 8) {
			        										area.append("Your inventory is full!\n\n");
			        										break AddToInv;
			        									}
			        								}
			        								//area.append("Outside\n");
			        							}
			        					}
			        				}
			        			}else{
			        				area.append("There is nothing to attack!\n\n");
			        			}
			        		}
			        		else {
			        			if(!(map.getMap(player.getX(), player.getY()).getEnc() instanceof DefaultCharacter) && map.getMap(player.getX(), player.getY()).getEnc().getHealth() != 0){
			        				int dmgDealt = 0; // Total damage dealt to enemy
			        				// Checks if first hand hits
			        				if ((Math.random() * 100) < (player.getHand(0).getSuccessChance() - (sm))) {
			        					if (map.getMap(player.getX(), player.getY()).getEnc().dodge()) {
			        						if (player.getHand(0) instanceof DefaultItem) {
			        							area.append("" + map.getMap(player.getX(), player.getY()).getEnc().getDes() + " dodged your fist.\n");
			        						}
			        						else {
			        							area.append("" + map.getMap(player.getX(), player.getY()).getEnc().getDes() + " dodged your " + player.getHand(0).getItemName() + ".\n");
			        						}
			        					}
			        					else {
			        						map.getMap(player.getX(), player.getY()).getEnc().lowerHealth(dmgItem1);
			        						dmgDealt += dmgItem1;
			        					}
			        				}
			        				else {
			        					area.append("You miss your " + player.getHand(0).getItemName() + ".\n");
			        				}
			        				// Checks is second hand hits
			        				if ((Math.random() * 100) < (player.getHand(1).getSuccessChance() - (sm))) {
			        					if (map.getMap(player.getX(), player.getY()).getEnc().dodge()) {
			        						if (player.getHand(1) instanceof DefaultItem) {
			        							area.append("" + map.getMap(player.getX(), player.getY()).getEnc().getDes() + " dodged your fist.\n");
			        						}
			        						else {
			        							area.append("" + map.getMap(player.getX(), player.getY()).getEnc().getDes() + " dodged your " + player.getHand(1).getItemName() + ".\n");
			        						}
			        					}
			        					else {
			        						map.getMap(player.getX(), player.getY()).getEnc().lowerHealth(dmgItem2);
			        						dmgDealt += dmgItem2;
			        					}
			        				}
			        				else {
			        					area.append("You miss your " + player.getHand(1).getItemName() + ".\n");
			        				}
			        				// Prints total damage dealt to enemy
			        				area.append("You dealt " + dmgDealt + " damage to " + map.getMap(player.getX(), player.getY()).getCharacterDes() + "!\n");
			        				//area.append("D1: " + dmgItem1 + " D2: " + dmgItem2 + "\n");
			        				dmgDealt = 0;
			        				// Sets health labels of both at the top if enemy still has health
			        				if (map.getMap(player.getX(), player.getY()).getEnc().getHealth() > 0) {
			        					
			        					player.lowerHealth(map.getMap(player.getX(), player.getY()).getEnc().getDamage());
			        					boolean effected = false;
			        					for (int x = 0; x < player.getEffectSize(); x++) {
			        						if (player.getPlayerStat(x).getVal() == map.getMap(player.getX(), player.getY()).getEnc().getEffect().getVal()) {
			        							effected = true;
			        						}
			        					}
			        					if (map.getMap(player.getX(), player.getY()).getEnc().getStatChance() >= (int)(Math.random() * 101) && !effected) {
			        						player.addStatus(map.getMap(player.getX(), player.getY()).getEnc().getEffect());
			        						area.append("You have been given " + map.getMap(player.getX(), player.getY()).getEnc().getEffect().getName() + " for " + map.getMap(player.getX(), player.getY()).getEnc().getEffect().getDuration() + " turns!\n");
			        					}
			        					finalHealth = "Health: " + player.getHealth() + " | Enemy: " + map.getMap(player.getX(), player.getY()).getEnc().getHealth() + "\n\n";
			        				}
			        				// If enemy loses all health
			        				else {
			        					finalHealth = "Health: " + player.getHealth() + " | Enemy: " + map.getMap(player.getX(), player.getY()).getEnc().getHealth() + "\n\n";
			        					area.append("You have killed " + map.getMap(player.getX(), player.getY()).getCharacterDes() + "!\n\n");
			        					player.incXP(map.getMap(player.getX(), player.getY()).getEnc().getXP());
			        					mobsKilled++;
			        					
			        					/*
			        					 * Give Items to Player
			        					 */
			        					for (int x = 0; x < 9; x++) {
			        						if (player.getInv(x).getItemName().toLowerCase().equals(map.getMap(player.getX(), player.getY()).getEnc().getDrop().getItemName().toLowerCase())) {
			        							player.getInv(x).incNumInInv(1);
			        							inInv = true;
			        							break;
			        						}
			        					}
			        					
			        					if (inInv == false) {
			        					AddToInv:
			        						for (int x = 0; x < 9; x++) {
			        							if (player.getInv(x) instanceof DefaultItem) {
			        								player.setInv(x, map.getMap(player.getX(), player.getY()).getEnc().getDrop());
			        								player.getInv(x).setNumInInv(1);
			        								break AddToInv;
			        							}
			        							
			        							else {
			        								if (x == 8) {
			        									area.append("Your inventory is full!\n\n");
			        									break AddToInv;
			        								}
			        							}
			        							//area.append("Outside\n");
			        						}
			        					}
			        				}
			        			}else{
			        				area.append("There is nothing to attack!\n\n");
			        			}
			        		}
			        	}
			        	else {
			        		area.append("" + commandSplit[0].toLowerCase() + " does not take any arguements!\n\n");
			        	}
			            inv.setText("");
			            
			            dmgItem1 = 0;
			            dmgItem2 = 0;
			            turn = false;
			            time++;
			            inInv = false;
			             
			        }
			    	
			    	/*
			    	 * Input = "equip"
			    	 */
			        else if (commandSplit[0].toLowerCase().trim().equals("equip")) {
			        	
			        	turn = true;
			            
			            if (commandSplit.length == 3) {
			            	// Checks to make sure that the second 2 numbers inputed are Integers
			            	try {	            		
			            		Integer.parseInt(commandSplit[1]);
			            		errorCatch = false;
			            	}
			            	catch (NumberFormatException ex) {
			            		area.append("" + commandSplit[1] + " is not a number!\n\n");
			            	}
			            	
			            	if (!errorCatch) {
			            		try {
			            			// Checks to make sure inventory number is valid
			            			if (Integer.parseInt(commandSplit[1]) <= 9 && Integer.parseInt(commandSplit[1]) > 0) {
			            				// Checks to make sure hand number is valid
			            				if (Integer.parseInt(commandSplit[2]) == 1 || Integer.parseInt(commandSplit[2]) == 2) {
			            					// Makes sure you don't equip nothing
			            					if (!(player.getInv(Integer.parseInt(commandSplit[1]) - 1) instanceof DefaultItem)) {
			            						// Checks if item is already in that hand slot
				            					if (player.getHand((Integer.parseInt(commandSplit[2]) - 1)).getItemName().toLowerCase().equals(player.getInv(Integer.parseInt(commandSplit[1]) - 1).getItemName().toLowerCase())) {
				                                    inHand = true;
				                                }
				            					// If item is already in hand
				            					if (inHand == true) {
				            						area.append("You already have that item equiped!\n\n");
				            					}
				            					// If item is not in hand already
				            					else {
				            						// If hand contains another item
				            						if (!(player.getHand(Integer.parseInt(commandSplit[2]) - 1) instanceof DefaultItem)) {
				            							Item temp = player.getHand(Integer.parseInt(commandSplit[2]) - 1);
				            							player.setHand(Integer.parseInt(commandSplit[2]) - 1, player.getInv(Integer.parseInt(commandSplit[1]) - 1));
				            							
				            							for (int x = 0; x < 9; x++) { // Increment number of items in the inventory
				                                            if (player.getInv(x).getItemName().toLowerCase().equals(temp.getItemName().toLowerCase())) {
				                                                player.getInv(x).incNumInInv(1);
				                                                inInv = true;
				                                                break;
				                                            }
				                                        }
				                                        
				                                        if (!inInv) {
				                                            AddToInv: // find first instance of DefaultItem() and replace it with TEMP (hand) item
				                                            for (int x = 0; x < 9; x++) {
				                                                if (player.getInv(x) instanceof DefaultItem) {
				                                                    player.setInv(x, temp);
				                                                    player.getInv(x).setNumInInv(1);
				                                                    break AddToInv;
				                                                }
				                                                else {
				                                                    if (x == 8) {
				                                                        area.append("Your inventory is full!\n\n");
				                                                        break AddToInv;
				                                                    }
				                                                }
				                                                //area.append("Outside\n");
				                                            }
				                                        }
				            						}
				            						// If hand item is nothing
				            						else {
				            							player.setHand(Integer.parseInt(commandSplit[2]) - 1, player.getInv(Integer.parseInt(commandSplit[1]) - 1));
				            						}
				            						
				            						// Prints out item equipped, uses 'a' or 'an' depending on the first letter of the equipped item
			                                        if ("aeiou".indexOf(player.getHand((Integer.parseInt(commandSplit[2]) - 1)).getItemName().toLowerCase().charAt(0)) < 0) {
			                                            area.append("You equip a " + player.getHand((Integer.parseInt(commandSplit[2]) - 1)).getItemName() + ".\n\n");
			                                        }
			                                        else {
			                                            area.append("You equip an " + player.getHand((Integer.parseInt(commandSplit[2]) - 1)).getItemName() + ".\n\n");
			                                        }
				            						
				            						// Get rid of item if numInInv is less than or equal to 0
				            						player.getInv(Integer.parseInt(commandSplit[1]) - 1).incNumInInv(-1);
				            						if (player.getInv(Integer.parseInt(commandSplit[1]) - 1).getNumInInv() == 0) {
				            							player.setInv((Integer.parseInt(commandSplit[1]) - 1), new DefaultItem());
				            						}
				            					}
			            					}
			            					else {
			            						area.append("You cannot equip nothing!\n\n");
			            					}
				            			}
				            			else {
				            				area.append("You don't have that number of hands!\n\n");
				            			}
			            			}
			            			else {
			            				area.append("You don't have that inventory slot!\n\n");
			            			}
			            		}
			            		catch (NumberFormatException ex) {
			            			area.append("" + commandSplit[2] + " is not a number!\n\n");
			            		}
			            	}
			            }
			            else {
			            	area.append("" + commandSplit[0].toLowerCase() + " requires 2 arguments, *inventory slot* *hand slot*\n\n");
			            }
			            
			            player.setDamage((player.getHand(0).getItemDamage()) + (player.getHand(1).getItemDamage()));
			            
			            inv.setText("");
			            
			            errorCatch = true;
			            inHand = false;
			            inInv = false;
			            turn = false;
			             
			        }
			    	
			    	/*
			    	 * Input = "stats"
			    	 */
			        else if (commandSplit[0].trim().toLowerCase().equals("stats")) {
			        	turn = true;
			        	if (commandSplit.length == 1) {
			        		area.append("Current Stats:\n Mobs Killed: " + mobsKilled + "\n Times Moved: " + movesMade + "\n Trades Made: " + tradesDone + "\n Items Used: " + usedItems + "\n\n");
			        	}
			        	else {
			        		area.append("" + commandSplit[0].toLowerCase() + " does not take any arguments!\n\n");
			        	}
			        	
			        	turn = false;
			        	 
			        }
			    	
			    	/*
			    	 * Input = "shop"
			    	 */
			        else if (commandSplit[0].toLowerCase().trim().equals("shop")) {
			        	turn = true;
			        	if (commandSplit.length == 1) {
			        		if (map.getMap(player.getX(), player.getY()).getTerrainType() instanceof Shop) {
			        			// Prints out contents of shop, as well as the worth of each
			        			area.append("This shop is trading:\n");
			        			area.append(" 1. " + map.getMap(player.getX(), player.getY()).getItemDes(0) + " (" + map.getMap(player.getX(), player.getY()).getItemWorth(0) + ")\n");
			        			area.append(" 2. " + map.getMap(player.getX(), player.getY()).getItemDes(1) + " (" + map.getMap(player.getX(), player.getY()).getItemWorth(1) + ")\n");
			        			area.append(" 3. " + map.getMap(player.getX(), player.getY()).getItemDes(2) + " (" + map.getMap(player.getX(), player.getY()).getItemWorth(2) + ")\n\n");
			        		}
			        		else {
			        			area.append("There is no shop here!\n\n");
			                
			        		}
			        	}
			        	else {
			        		area.append("" + commandSplit[0].toLowerCase() + " does not take any arguments!\n\n");
			        		
			        	}
			        	
			        	turn = false;
			             
			        }
			    	
			    	/*
			    	 * Input = "use"
			    	 */
			        else if (commandSplit[0].trim().toLowerCase().equals("use")) {
			    		turn = true;
			    		// Checks length
			    		if (commandSplit.length == 2) {
			    			// Checks that inventory slot input is a number
			    			try {
			    				// Inventory slot (shorter than typing Integer.blah every time)
			    				int useInvPos = Integer.parseInt(commandSplit[1]) - 1;
			    				// Checks to make sure it is within inventory boundaries
			    				if (useInvPos >= 0 && useInvPos <= 8) {
			    					// Checks if it is nothing
			    					if (!(player.getInv(useInvPos) instanceof DefaultItem)) {
			    						// Potato
			    						if (player.getInv(useInvPos) instanceof Potato) {
			    							area.append("You use a potato.\n\n");
			    						}
			    						
			    						else if (player.getInv(useInvPos) instanceof Book) {
			    							// Zap Spell Book
			    							if (player.getInv(useInvPos) instanceof ZapBook) {
			    								if (!(map.getMap(player.getX(), player.getY()).getEnc() instanceof DefaultCharacter) && map.getMap(player.getX(), player.getY()).getEnc().getHealth() != 0) {
			    									area.append(player.getInv(useInvPos).getBookContent());
			    									area.append(" The ball hits " + map.getMap(player.getX(), player.getY()).getCharacterDes() + ", dealing\n20 damage!\n\n");
			    									// Lower Enemy Health
			    									map.getMap(player.getX(), player.getY()).getEnc().lowerHealth(20);
			    									//
			    									if (map.getMap(player.getX(), player.getY()).getEnc().getHealth() > 0) {
			    				        				
			    				        				player.lowerHealth(map.getMap(player.getX(), player.getY()).getEnc().getDamage());
			    				        				finalHealth = "Health: " + player.getHealth() + " | Enemy: " + map.getMap(player.getX(), player.getY()).getEnc().getHealth() + "\n\n";
			    				        			}
			    				        			// If enemy loses all health
			    				        			else {
			    				        				finalHealth = "Health: " + player.getHealth() + " | Enemy: " + map.getMap(player.getX(), player.getY()).getEnc().getHealth() + "\n\n";
			    				        				area.append("You have killed " + map.getMap(player.getX(), player.getY()).getCharacterDes() + "!\n\n");
			    				        				player.incXP(map.getMap(player.getX(), player.getY()).getEnc().getXP());
			    				        				mobsKilled++;
			    				                
			    				        				/*
			    				        				 * Give Items to Player
			    				        				 */
			    				        				for (int x = 0; x < 9; x++) {
			    				        					if (player.getInv(x).getItemName().toLowerCase().equals(map.getMap(player.getX(), player.getY()).getEnc().getDrop().getItemName().toLowerCase())) {
			    				        						player.getInv(x).incNumInInv(1);
			    				        						inInv = true;
			    				        						break;
			    				        					}
			    				        				}
			    				        				
			    				        				if (inInv == false) {
			    				        					AddToInv:
			    				        						for (int x = 0; x < 9; x++) {
			    				        							if (player.getInv(x) instanceof DefaultItem) {
			    				        								player.setInv(x, map.getMap(player.getX(), player.getY()).getEnc().getDrop());
			    				        								player.getInv(x).setNumInInv(1);
			    				        								break AddToInv;
			    				        							}
			    				                        
			    				        							else {
			    				        								if (x == 8) {
			    				        									area.append("Your inventory is full!\n\n");
			    				        									break AddToInv;
			    				        								}
			    				        							}
			    				        							//area.append("Outside\n");
			    				        						}
			    				        				}
			    				        			}
			    									player.getInv(Integer.parseInt(commandSplit[1]) - 1).incNumInInv(-1);
				            						if (player.getInv(Integer.parseInt(commandSplit[1]) - 1).getNumInInv() == 0) {
				            							player.setInv((Integer.parseInt(commandSplit[1]) - 1), new DefaultItem());
				            						}
			    								}
			    								else {
			    									area.append(player.getInv(useInvPos).getBookContent());
			    									area.append(" The ball then flies into the distance,\nhitting nothing.\n\n");
			    									player.getInv(Integer.parseInt(commandSplit[1]) - 1).incNumInInv(-1);
				            						if (player.getInv(Integer.parseInt(commandSplit[1]) - 1).getNumInInv() == 0) {
				            							player.setInv((Integer.parseInt(commandSplit[1]) - 1), new DefaultItem());
				            						}
			    								}
			    							}
			    							else {
			    								area.append("The book reads:\n\n" + player.getInv(useInvPos).getBookContent() + "\n\n");
			    							}
			    						}
			    						
			    						// Fire Vial
			    						else if (player.getInv(useInvPos) instanceof FireVial) {
		    								if (!(map.getMap(player.getX(), player.getY()).getEnc() instanceof DefaultCharacter) && map.getMap(player.getX(), player.getY()).getEnc().getHealth() != 0) {
		    									area.append("The bottle hits " + map.getMap(player.getX(), player.getY()).getCharacterDes() + " in a splash of\nfire, dealing 30 damage!\n\n");
		    									// Lower Enemy Health
		    									map.getMap(player.getX(), player.getY()).getEnc().lowerHealth(30);
		    									//
		    									if (map.getMap(player.getX(), player.getY()).getEnc().getHealth() > 0) {
		    				        				
		    				        				player.lowerHealth(map.getMap(player.getX(), player.getY()).getEnc().getDamage());
		    				        				finalHealth = "Health: " + player.getHealth() + " | Enemy: " + map.getMap(player.getX(), player.getY()).getEnc().getHealth() + "\n\n";
		    				        				
		    				        			}
		    				        			// If enemy loses all health
		    				        			else {
		    				        				
		    				        				area.append("You have killed " + map.getMap(player.getX(), player.getY()).getCharacterDes() + "!\n\n");
		    				        				player.incXP(map.getMap(player.getX(), player.getY()).getEnc().getXP());
		    				        				mobsKilled++;
		    				                
		    				        				/*
		    				        				 * Give Items to Player
		    				        				 */
		    				        				for (int x = 0; x < 9; x++) {
		    				        					if (player.getInv(x).getItemName().toLowerCase().equals(map.getMap(player.getX(), player.getY()).getEnc().getDrop().getItemName().toLowerCase())) {
		    				        						player.getInv(x).incNumInInv(1);
		    				        						inInv = true;
		    				        						break;
		    				        					}
		    				        				}
		    				        				
		    				        				if (inInv == false) {
		    				        					AddToInv:
		    				        						for (int x = 0; x < 9; x++) {
		    				        							if (player.getInv(x) instanceof DefaultItem) {
		    				        								player.setInv(x, map.getMap(player.getX(), player.getY()).getEnc().getDrop());
		    				        								player.getInv(x).setNumInInv(1);
		    				        								break AddToInv;
		    				        							}
		    				                        
		    				        							else {
		    				        								if (x == 8) {
		    				        									area.append("Your inventory is full!\n\n");
		    				        									break AddToInv;
		    				        								}
		    				        							}
		    				        							//area.append("Outside\n");
		    				        						}
		    				        				}
		    				        			}
		    									player.getInv(Integer.parseInt(commandSplit[1]) - 1).incNumInInv(-1);
			            						if (player.getInv(Integer.parseInt(commandSplit[1]) - 1).getNumInInv() == 0) {
			            							player.setInv((Integer.parseInt(commandSplit[1]) - 1), new DefaultItem());
			            						}
		    								}
		    								else {
		    									area.append("The glass of liquid fire hits the ground, igniting the terrain around it.\nIt does not hit any enemies.\n\n");
		    									player.getInv(Integer.parseInt(commandSplit[1]) - 1).incNumInInv(-1);
			            						if (player.getInv(Integer.parseInt(commandSplit[1]) - 1).getNumInInv() == 0) {
			            							player.setInv((Integer.parseInt(commandSplit[1]) - 1), new DefaultItem());
			            						}
		    								}
		    							}
			    						
			    						//Heart-In-A-Bottle
			    						else if (player.getInv(useInvPos) instanceof HeartInABottle) {
			    							area.append("You drink the bottle of murky pink\nliquid, and feel a warm feeling spread\nacross your body.\n\n");
			    							player.addStatus(new CustomRegen(8));
			    							player.getInv(Integer.parseInt(commandSplit[1]) - 1).incNumInInv(-1);
		            						if (player.getInv(Integer.parseInt(commandSplit[1]) - 1).getNumInInv() == 0) {
		            							player.setInv((Integer.parseInt(commandSplit[1]) - 1), new DefaultItem());
		            						}
			    						}
			    						
			    						// All other cases
			    						else {
			    							area.append("You see that this item is indeed ");
			    							if ("aeiou".indexOf(player.getInv((Integer.parseInt(commandSplit[1]) - 1)).getItemName().toLowerCase().charAt(0)) < 0) {
			    								area.append("a " + player.getInv((Integer.parseInt(commandSplit[1]) - 1)).getItemName() + ".\n\n");
			    							}
			    							else {
			    								area.append("an " + player.getInv((Integer.parseInt(commandSplit[1]) - 1)).getItemName() + ".\n\n");
			    							}
			    						}
			    						
			    						usedItems++;
			    					}
			    					else {
				    					area.append("You shake your hands vigorously.\n\n");
				    				}
			    				}
			    				else {
			    					area.append("You do not have that inventory slot!\n\n");
			    				}
			    			}
			    			catch (NumberFormatException ex) {
			    				area.append("" + commandSplit[1] + " is not a number!\n\n");
			    			}
			    		}
			    		else {
			    			area.append("" + commandSplit[0].toLowerCase() + " requires 1 arguement, *inventory slot*\n\n");
			    		}
			    		
			    		turn = false;
			    		 
			    	}
			    	
			    	/*
			    	 * Trade
			    	 */
			        else if (commandSplit[0].trim().toLowerCase().equals("trade")) {
			        	turn = true;
			        	if (commandSplit.length == 3) {
			        		try {	            		
			            		Integer.parseInt(commandSplit[1]);
			            		errorCatch = false;
			            	}
			            	catch (NumberFormatException ex) {
			            		area.append("" + commandSplit[1] + " is not a number!\n\n");
			            	}
			            	
			            	if (!errorCatch) {
			            		try {
			            			// Checks to make sure inventory number is valid
			            			if (Integer.parseInt(commandSplit[1]) <= 9 && Integer.parseInt(commandSplit[1]) > 0) {
			            				// Checks to make sure shop number is valid
			            				if (Integer.parseInt(commandSplit[2]) <= 3 && Integer.parseInt(commandSplit[2]) >= 1) {
			            					// Makes sure you don't trade nothing
			            					if (!(player.getInv(Integer.parseInt(commandSplit[1]) - 1) instanceof DefaultItem)) {
			            						// Checks to make sure you are in a shop
			            						if (map.getMap(player.getX(), player.getY()).getTerrainType() instanceof Shop) {
			            							// Shop item worth more than player item
			            							if (map.getMap(player.getX(), player.getY()).getTerrainType().getItem(Integer.parseInt(commandSplit[2]) - 1).getWorth() > player.getInv(Integer.parseInt(commandSplit[1]) - 1).getWorth()) {
			            								int multiplier = 0;
			                                            while ((player.getInv(Integer.parseInt(commandSplit[1]) - 1).getWorth() * multiplier) < map.getMap(player.getX(), player.getY()).getShopItem(Integer.parseInt(commandSplit[2]) - 1).getWorth()) {
			                                                multiplier++;
			                                            }
			                                            
			                                            if (multiplier <= player.getInv(Integer.parseInt(commandSplit[1]) - 1).getNumInInv()) {
			                                            	
			                                            	area.append("You traded " + multiplier + " " + player.getInv(Integer.parseInt(commandSplit[1]) - 1).getItemName() + " and got 1 " + map.getMap(player.getX(), player.getY()).getShopItem(Integer.parseInt(commandSplit[2]) - 1).getItemName() + "!\n\n");
			                                                
			                                                for (int x = 0; x < 9; x++) {
			                                                    if (player.getInv(x).getItemName().toLowerCase().equals(map.getMap(player.getX(), player.getY()).getItemDes(Integer.parseInt(commandSplit[2]) - 1).toLowerCase())) {
			                                                        player.getInv(x).incNumInInv(1);
			                                                        inInv = true;                                                                                                                                  
			                                                        break;
			                                                    }
			                                                }
			                                                
			                                                if (!inInv) {
			                                                    AddToInv:
			                                                    for (int x = 0; x < 9; x++) {
			                                                        if (player.getInv(x) instanceof DefaultItem) {
			                                                            player.setInv(x, map.getMap(player.getX(), player.getY()).getShopItem(Integer.parseInt(commandSplit[2]) - 1));
			                                                            //player.getInv(x).incNumInInv(1);
			                                                            player.getInv(x).setNumInInv(1);
			                                                            break AddToInv;
			                                                        }
			                                                        else {
			                                                            if (x == 8) {
			                                                                area.append("Your inventory is full!\n\n");
			                                                                break AddToInv;
			                                                            }
			                                                        }
			                                                        //area.append("Outside\n");
			                                                    }
			                                                }
			                                            
			                                                player.getInv(Integer.parseInt(commandSplit[1]) - 1).incNumInInv(-multiplier);
						            						if (player.getInv(Integer.parseInt(commandSplit[1]) - 1).getNumInInv() == 0) {
						            							player.setInv((Integer.parseInt(commandSplit[1]) - 1), new DefaultItem());
						            						}	                                                
			                                            }
			                                            else {
			                                                area.append("You don't have enough to trade for that!\n\n");
			                                            }
			            							}
			            							// Shop item worth less than player item
			            							else if ((map.getMap(player.getX(), player.getY()).getTerrainType().getItem(Integer.parseInt(commandSplit[2]) - 1).getWorth() < player.getInv(Integer.parseInt(commandSplit[1]) - 1).getWorth())) {
			            								int multiplier = 0;
			                                            while (((map.getMap(player.getX(), player.getY()).getShopItem(Integer.parseInt(commandSplit[2]) - 1).getWorth()) * multiplier) < player.getInv(Integer.parseInt(commandSplit[1]) - 1).getWorth()) {
			                                                multiplier++;
			                                            }
			                                            
			                                            area.append("You traded 1 " + player.getInv(Integer.parseInt(commandSplit[1]) - 1).getItemName() + " for " + multiplier + " " + map.getMap(player.getX(), player.getY()).getShopItem(Integer.parseInt(commandSplit[2]) - 1).getItemName() + "!\n\n");
		
			                                            for (int x = 0; x < 9; x++) {
			                                                if (player.getInv(x).getItemName().toLowerCase().equals(map.getMap(player.getX(), player.getY()).getItemDes(Integer.parseInt(commandSplit[2]) - 1).toLowerCase())) {
			                                                    player.getInv(x).incNumInInv(multiplier);
			                                                    inInv = true;
			                                                    break;
			                                                }
			                                            }
			                                            
			                                            if (inInv == false) {
			                                                AddToInv:
			                                                for (int x = 0; x < 9; x++) {
			                                                    if (player.getInv(x) instanceof DefaultItem) {
			                                                        player.setInv(x, map.getMap(player.getX(), player.getY()).getShopItem(Integer.parseInt(commandSplit[2]) - 1));
			                                                        player.getInv(x).incNumInInv(multiplier - 1);
			                                                        break AddToInv;
			                                                    }
			                                                    
			                                                    else {
			                                                        if (x == 8) {
			                                                            area.append("Your inventory is full!\n\n");
			                                                            break AddToInv;
			                                                        }
			                                                    }
			                                                    //area.append("Outside\n");
			                                                }   
			                                            }
			                                            
			                                            player.getInv(Integer.parseInt(commandSplit[1]) - 1).incNumInInv(-1);
					            						if (player.getInv(Integer.parseInt(commandSplit[1]) - 1).getNumInInv() == 0) {
					            							player.setInv((Integer.parseInt(commandSplit[1]) - 1), new DefaultItem());
					            						}
			            							}
			            							// Shop and inventory items equal
			            							else {
			            								area.append("You traded 1 " + player.getInv(Integer.parseInt(commandSplit[1]) - 1).getItemName() + " for 1 " + map.getMap(player.getX(), player.getY()).getShopItem(Integer.parseInt(commandSplit[2]) - 1).getItemName() + "!\n\n");
			            								for (int x = 0; x < 9; x++) {
			                                                if (player.getInv(x).getItemName().toLowerCase().equals(map.getMap(player.getX(), player.getY()).getItemDes(Integer.parseInt(commandSplit[2]) - 1).toLowerCase())) {
			                                                    player.getInv(x).incNumInInv(1);
			                                                    inInv = true;
			                                                    break;
			                                                }
			                                            }
			                                            
			                                            if (!inInv) {
			                                                AddToInv:
			                                                for (int x = 0; x < 9; x++) {
			                                                    if (player.getInv(x) instanceof DefaultItem) {
			                                                        player.setInv(x, map.getMap(player.getX(), player.getY()).getShopItem(Integer.parseInt(commandSplit[2]) - 1));
			                                                        player.getInv(x).incNumInInv(1);
			                                                        break AddToInv;
			                                                    }
			                                                    
			                                                    else {
			                                                        if (x == 8) {
			                                                            area.append("Your inventory is full!\n\n");
			                                                            break AddToInv;
			                                                        }
			                                                    }
			                                                    //area.append("Outside\n");
			                                                }   
			                                            }
			                                            
					            						player.getInv(Integer.parseInt(commandSplit[1]) - 1).incNumInInv(-1);
					            						if (player.getInv(Integer.parseInt(commandSplit[1]) - 1).getNumInInv() == 0) {
					            							player.setInv((Integer.parseInt(commandSplit[1]) - 1), new DefaultItem());
					            						}
			            							}
			            							tradesDone++;
			            							
			            						}
			            						else {
			            							area.append("You are not in a city!\n\n");
			            						}
			            					}
			            						
			            					else {
			            						area.append("You do not have anything in slot " + commandSplit[1].toLowerCase() + "!\n\n");
			            					}
				            			}
				            			else {
				            				area.append("The shop is not selling that number of items!\n\n");
				            			}
			            			}
			            			else {
			            				area.append("You don't have that inventory slot!\n\n");
			            			}
			            		}
			            		catch (NumberFormatException ex) {
			            			area.append("" + commandSplit[2] + " is not a number!\n\n");
			            		}
			            	}
			        		
			        	}
			        	else {
			        		area.append("" + commandSplit[0].toLowerCase() + " takes 2 arguments, *inventory slot* *shop slot*\n\n");
			        	}
			        	
			        	
			        	inInv = false;
			        	errorCatch = true;
			        	turn = false;
			        	 
			        }
			    	
			    	
			    	/*
			    	 * add command - add + "attribute"
			    	 * adds 1 onto the desired attribute
			    	 * 
			    	 */
			    	
			        else if(commandSplit[0].toLowerCase().equals("add")){
			        	turn = true;
			        	if(commandSplit.length == 2){
			        		area.append(player.incrementPoint(commandSplit[1]));
			        		 
			        	}else{
			        		area.append("" + commandSplit[0].toLowerCase() + " takes 1 argument, *attribute*\n\n");
			        		 	
			        		
			        	}
			        	turn = false;
			        /*
			         * point command - points
			         * returns the current number of points
			         * 	
			         */
			        }
			        
			    	///*
			    	// * Input = "remove"
			    	// */
			        //else if(commandSplit[0].toLowerCase().equals("remove")){
			        //	turn = true;
			        //	if(commandSplit.length == 2){
			        //		area.append(""+player.decrementPoint(commandSplit[1]));
			        //		 
			        //	}else{
			        //		area.append("" + commandSplit[0].toLowerCase() + " takes 1 argument, *attribute*\n\n");
			        //		 	
			        //	}
			        //	turn = false;
			        //}
			        
			        /*
			    	 * Input = "save"
			    	 */
			        else if (commandSplit[0].trim().toLowerCase().equals("save")) {
			        	turn = true;
			        	if (commandSplit.length == 1) {
			        		boolean saved = false;
			        		for (int x = 0; x < savedCoord.size(); x++) {
		        				if (savedCoord.get(x).getXcor() == map.getMap(player.getX(), player.getY()).getXcor() && savedCoord.get(x).getYcor() == map.getMap(player.getX(), player.getY()).getYcor()) {
		        					area.append("You already have this plot saved!\n\n");
		        					saved = true;
		        					break;
		        				}
		        			}
			        		if (!saved) {
			        			savedCoord.add(map.getMap(player.getX(), player.getY()));
			        			area.append("Plot saved! Use print to view all saved plots.\n\n");
			        		}
		
			        	}
			        	else {
			        		area.append("" + commandSplit[0].toLowerCase() + " does not take any arguments\n\n");
			        	}
			        	
			        	turn = false;
			        	 
			        }
			    	
			    	/*
			    	 * Input = "print"
			    	 */
			        else if (commandSplit[0].trim().toLowerCase().equals("print")) {
			        	turn = true;
			        	if (commandSplit.length == 1) {
			        		if (savedCoord.size() > 0) {
			        			area.append("These are your saved plots:\n\n");
			        			for (int x = 0; x < savedCoord.size(); x++) {
			        				if (savedCoord.get(x).getTerrainType() instanceof Shop) {
			    	        			area.append(" " + (x + 1) + ". X: " + savedCoord.get(x).getXcor() + ", Y: " + (200 - savedCoord.get(x).getYcor()) + ", Terrain: " + savedCoord.get(x).getText().substring(29, 35) + "\n");
			    	        		}
			    	        		else {
			    	        			area.append(" " + (x + 1) + ". X: " + savedCoord.get(x).getXcor() + ", Y: " + (200 - savedCoord.get(x).getYcor()) + ", Terrain: " + savedCoord.get(x).getTerrain() + "\n");
			    	        		}
			        			}
			        			area.append("\n");
			        		}
			        		else {
			        			area.append("You have no saved plots!\n\n");
			        		}
			        	}
			        	else {
			        		area.append("" + commandSplit[0].toLowerCase() + " does not take any arguments\n\n");
			        	}
			        	
			        	turn = false;
			        	 
			        }
			    	
			    	/*
			    	 * Input = "store"
			    	 */
			        else if (commandSplit[0].trim().toLowerCase().equals("store")) {
			        	turn = true;
			        	
			        	if (commandSplit.length == 2) {
			        		try {
			        			if (Integer.parseInt(commandSplit[1]) - 1 == 0 || Integer.parseInt(commandSplit[1]) - 1 == 1) {
			        				if (!(player.getHand(Integer.parseInt(commandSplit[1]) - 1) instanceof DefaultItem)) {
			        					area.append("You put a " + player.getHand(Integer.parseInt(commandSplit[1]) - 1).getItemName() + " back into your inventory!\n\n");
										for (int x = 0; x < 9; x++) {
		                                    if (player.getInv(x).getItemName().toLowerCase().equals(player.getHand(Integer.parseInt(commandSplit[1]) - 1).getItemName().toLowerCase())) {
		                                        player.getInv(x).incNumInInv(1);
		                                        player.setHand(Integer.parseInt(commandSplit[1]) - 1, new DefaultItem());
		                                        inInv = true;
		                                        break;
		                                    }
		                                }
		                                
		                                if (!inInv) {
		                                    AddToInv:
		                                    for (int x = 0; x < 9; x++) {
		                                        if (player.getInv(x) instanceof DefaultItem) {
		                                            player.setInv(x, player.getHand(Integer.parseInt(commandSplit[1]) - 1));
		                                            player.getInv(x).setNumInInv(1);
		                                            player.setHand(Integer.parseInt(commandSplit[1]) - 1, new DefaultItem());
		                                            break AddToInv;
		                                        }
		                                        
		                                        else {
		                                            if (x == 8) {
		                                                area.append("Your inventory is full!\n\n");
		                                                break AddToInv;
		                                            }
		                                        }
		                                        //area.append("Outside\n");
		                                    }   
		                                }
		                                
		        						//player.getInv(Integer.parseInt(commandSplit[1]) - 1).incNumInInv(-1);
		        						//if (player.getInv(Integer.parseInt(commandSplit[1]) - 1).getNumInInv() == 0) {
		        						//	player.setInv((Integer.parseInt(commandSplit[1]) - 1), new DefaultItem());
		        						//}
			        				}
			        				else {
			        					area.append("You cannot remove your hand!\n\n");
			        				}
			        			}
			        			else {
			        				area.append("You do not have that hand!\n\n");
			        			}
			        		}
			        		catch (NumberFormatException ex) {
			        			area.append("" + commandSplit[1] + " is not a number!\n\n");
			        		}
			        	}
			        	else {
			        		area.append("" + commandSplit[0].toLowerCase() + " takes 1 argument, *hand slot*\n\n");
			        	}
			        	
			        	inInv = false;
			        	turn = false;
			        	 
			        }
			    	
			    	/*
			    	 * Input = "build"
			    	 */
			        else if (commandSplit[0].trim().toLowerCase().equals("build")) {
			        	turn = true;
			        	
			        	if (commandSplit.length == 4) {
			        		// Checks to make sure you input numbers
			        		try {
			        			int s1 = Integer.parseInt(commandSplit[1]) - 1;
			        			int s2 = Integer.parseInt(commandSplit[2]) - 1;
			        			int s3 = Integer.parseInt(commandSplit[3]) - 1;
			        			
			        			// In Shop
			        			if (map.getMap(player.getX(), player.getY()).getTerrainType() instanceof Shop) {
			        				// First number inside inventory
			        				if (s1 >= 0 && s1 <= 8) {
			        					// Second number inside inventory
			        					if (s2 >= 0 && s2 <= 8) {
			        						// Third number inside inventory
			        						if (s3 >= 0 && s3 <= 8) {
			        							// Checks first position for nothing
			    	        					if (!(player.getInv(s1) instanceof DefaultItem)) {
			    	        						// Checks second position for nothing
			    	        						if (!(player.getInv(s2) instanceof DefaultItem)) {
			    	        							// Checks third position for nothing
			    	        							if (!(player.getInv(s3) instanceof DefaultItem)) {
			    	        								// Fire Vial Crafting
			    	    	        						if (player.getInv(s1) instanceof Stone && player.getInv(s2) instanceof GlassBottle && player.getInv(s3) instanceof Essence) {
			    	    	        							area.append("You created a Fire Vial!\n\n");
			    			            						
			    			            						for (int x = 0; x < 9; x++) {
			    			                                        if (player.getInv(x) instanceof FireVial) {
			    			                                            player.getInv(x).incNumInInv(1);
			    			                                            inInv = true;
			    			                                            
			    			                                            player.getInv(s1).incNumInInv(-1);
			    	    			            						if (player.getInv(s1).getNumInInv() == 0) {
			    	    			            							player.setInv(s1, new DefaultItem());
			    	    			            						}
			    	    			            						
			    	    			            						player.getInv(s2).incNumInInv(-1);
			    	    			            						if (player.getInv(s2).getNumInInv() == 0) {
			    	    			            							player.setInv(s2, new DefaultItem());
			    	    			            						}
			    	    			            						
			    	    			            						player.getInv(s3).incNumInInv(-1);
			    	    			            						if (player.getInv(s3).getNumInInv() == 0) {
			    	    			            							player.setInv(s3, new DefaultItem());
			    	    			            						}
			    			                                            break;
			    			                                        }
			    			                                    }
			    			                                    
			    			                                    if (!inInv) {
			    			                                        AddToInv:
			    			                                        for (int x = 0; x < 9; x++) {
			    			                                            if (player.getInv(x) instanceof DefaultItem) {
			    			                                                player.setInv(x, new FireVial());
			    			                                                player.getInv(x).setNumInInv(1);
			    			                                                
			    			                                                player.getInv(s1).incNumInInv(-1);
			    		    			            						if (player.getInv(s1).getNumInInv() == 0) {
			    		    			            							player.setInv(s1, new DefaultItem());
			    		    			            						}
			    		    			            						
			    		    			            						player.getInv(s2).incNumInInv(-1);
			    		    			            						if (player.getInv(s2).getNumInInv() == 0) {
			    		    			            							player.setInv(s2, new DefaultItem());
			    		    			            						}
			    		    			            						
			    		    			            						player.getInv(s3).incNumInInv(-1);
			    		    			            						if (player.getInv(s3).getNumInInv() == 0) {
			    		    			            							player.setInv(s3, new DefaultItem());
			    		    			            						}
			    			                                                break AddToInv;
			    			                                            }
			    			                                            
			    			                                            else {
			    			                                                if (x == 8) {
			    			                                                    area.append("Your inventory is full!\n\n");
			    			                                                    break AddToInv;
			    			                                                }
			    			                                            }
			    			                                            //area.append("Outside\n");
			    			                                        }   
			    			                                    }
			    	    	        						}
			    	    	        						
			    	    	        						else {
			    	    	        							area.append("These items in this order do not create anything!\n\n");
			    	    	        						}
			    	    	        					}
			    	    	        					else {
			    	    	        						area.append("There is nothing in slot " + (s3 + 1) + "!\n\n");
			    	    	        					}
				    	        					}
				    	        					else {
				    	        						area.append("There is nothing in slot " + (s2 + 1) + "!\n\n");
				    	        					}
			    	        					}
			    	        					else {
			    	        						area.append("There is nothing in slot " + (s1 + 1) + "!\n\n");
			    	        					}
			    	        				}
			    	        				else {
			    	        					area.append("You do not have slot " + (s3 + 1) + "!\n\n");
			    	        				}
				        				}
				        				else {
				        					area.append("You do not have slot " + (s2 + 1) + "!\n\n");
				        				}
			        				}
			        				else {
			        					area.append("You do not have slot " + (s1 + 1) + "!\n\n");
			        				}
			        			}
			        			else {
			        				area.append("You can only construct in a city!\n\n");
			        			}
			        		}
			        		catch (NumberFormatException ex) {
			        			area.append("Build requires 3 numbers, for the inventory slot position of each item to be combined.\n\n");
			        		}
			        	}
			        	else {
			        		area.append("" + commandSplit[0].toLowerCase() + " takes 3 arguments, *inventory slot 1* *inventory slot 2* *inventory slot 3*\n\n");
			        	}
			        	
			        	inInv = false;
			        	turn = false;
			        	 
			        }
			    	
			        //else if (commandSplit[0].trim().toLowerCase().equals("give")) {
			        //	player.setInv(0, new Stone());
			        //	player.setInv(1, new GlassBottle());
			        //	player.setInv(2, new Essence());
			        //	
			        //	 
			        //}
			    	
			    	/*
			    	 * Else statement:
			    	 * 
			    	 * - Used for when an unrecognizable command is typed
			    	 * - Prints EVERY command available (may be edited in the future to account for too many commands).
			    	 * 
			    	 * Doesn't follow normal rules because it is the exception case.
			    	 * 
			    	 * Format is: area.append("- (command) *argument 1* *argument 2* = (what it does)\n");
			    	 */
			        else {
			        	turn = true;
			            if (!(text.getText().toLowerCase().trim().equals(""))) {
			              area.append("Unknown Command! These are the available commands:\n");
			              area.append("- Quit = Exit the game\n");
			              area.append("- North/Up = Move Up\n");
			              area.append("- East/Right = Move Right\n");
			              area.append("- South/Down = Move Down\n");
			              area.append("- West/Left = Move Left\n");
			              area.append("- Clear = Clear Text Area\n");
			              area.append("- Terrain = Display current terrain\n");
			              area.append("- XY = Display Coordinates\n");
			              area.append("- Attack = Attack on this plot\n");
			              area.append("- Shop = View shop on this plot\n");
			              area.append("- Trade *inventory slot* *shop slot* = Trade these items at a shop\n");
			              area.append("- Use *inventory slot* = Use inventory item\n");
			              area.append("- Stats = Current player stats\n");
			              area.append("- Equip *inventory position* *slot position* = Equip this item\n");
			              area.append("- Save = Save current plot X coordinate, Y coordinate, and terrain\n");
			              area.append("- Print = Print all saved plots\n");
			              area.append("- Store *hand slot* = store item in that hand\n");
			              area.append("- Add *attribute* = add a point to that attribute\n");
			              //area.append("- Remove *attribute* = remove a point from that attribute\n");
			              area.append("- Build *inventory slot 1* *inventory slot 2* *inventory slot 3* = Combine\n specified items in a city to craft an item\n");
			              area.append("- Enter = Enter a dungeon\n");
			              area.append("- Exit = Exit a dungeon\n");
			              area.append("\n");
			               
			            }
			            turn = false;
			        }
		    	}
			    
			    effectSize = player.getEffectSize();
			    
			    //Health stuff
			    
			    if (map.getMap(player.getX(), player.getY()).getTerrainType() instanceof Dungeon && player.inDungeon()) {
			    	if (!(map.getMap(player.getX(), player.getY()).getTerrainType().getDPlot(player.getDungeonX(), player.getDungeonY()).getEnc() instanceof DefaultCharacter)) {
			    		dHealth = map.getMap(player.getX(), player.getY()).getTerrainType().getDPlot(player.getDungeonX(), player.getDungeonY()).getEnc().getHealth();
			    		finalHealth = "Health: " + player.getHealth() + " | Enemy: " + dHealth + "\n";
			    	}
			    	else {
			    		finalHealth = "Health: " + player.getHealth() + " | Enemy: \n";
			    	}
			    
			    }
			    else {
			    	if (!(map.getMap(player.getX(), player.getY()).getEnc() instanceof DefaultCharacter)) {
			    		nHealth = map.getMap(player.getX(), player.getY()).getEnc().getHealth();
			    		finalHealth = "Health: " + player.getHealth() + " | Enemy: " + nHealth + "\n";
			    	}
			    	else {
			    		finalHealth = "Health: " + player.getHealth() + " | Enemy: \n";
			    	}
			    			
			    }
			    
			    //Sets inventory
			    
			    area.append("" + player.doXP() + "");
			    
			    inv.setText("");
				if (!player.getName().equals("")) {
					inv.append("\n" + player.getName() + "\n");
					inv.append("\n-----------------\n\n");
					inv.append(finalHealth);
				}
				
				inv.append("\nExp: " + player.getCurrentXP() + ":" + ((int)(0.2 * (Math.pow(player.getLevel() - 1, 2)) * 100) + (int)(0.2 * (Math.pow(player.getLevel(), 2)) * 100)) + "\n");
				if (player.getRace() != null) {
					inv.append("\nLevel " + player.getLevel() + " " + player.getRace().getName() + "\n");
				}
				else {
					inv.append("\nLevel " + player.getLevel() + "\n");
				}
				inv.append(player.getInvList());
				inv.append("\nCurrent weight: " + player.getWeight() + "/" + player.getMaxWeight() + "\n\n");
				inv.append(player.printStatus());
				inv.append(player.returnAttributes());
				
			}
			/*
			 * Character Creation Section - In set order of things to create
			 */
			else {
				
				if (commandSplit.length == 2) {
					// Choose Race
					if (commandSplit[0].trim().toLowerCase().equals("race")) {
						if(player.setRace(commandSplit[1]).equals("That race does not exist!\n")) //This .EQUALS command MUST be the same as the returned statement in Player!
							area.append(player.setRace(commandSplit[1]));
						else {
							area.append(player.setRace(commandSplit[1]));
							raceChosen = true;
						}
					}
					// Choose Name
					else if (commandSplit[0].trim().toLowerCase().equals("name")) {
						if (commandSplit[1].length() <= 15 && commandSplit[1].length() >= 2) {
							boolean containsOnlyLetter = true;
							if (true) {
								
								for (int x = 0; x < commandSplit[1].length(); x++) {
									if (!Character.isLetter((commandSplit[1].charAt(x)))) {
										containsOnlyLetter = false;
										break;
									}
								}
								if (containsOnlyLetter) {
									area.append("Your name has been set to " + (commandSplit[1].substring(0, 1).toUpperCase() + commandSplit[1].substring(1).toLowerCase()) + "\n\n");
									player.setName(commandSplit[1].substring(0, 1).toUpperCase() + commandSplit[1].substring(1).toLowerCase());
									nameChosen = true;
								}
								else {
									area.append("Name can only contain letters!\n");
								}
							}
						}
						else {
							area.append("Name must be less than 15 characters, and longer than 2!\n");
						}
					}
					//If it is not recognized as a command
					else {
						area.append("" + commandSplit[0] + " is not a command! See right panel for available commands.\n\n");
					}
				}
				// Confirm everything after setting the stuff
				else if (commandSplit.length == 1) {
					if (commandSplit[0].toLowerCase().equals("quit")) {
			    		turn = true;
			    		area.append("Closing Game");
			    		win.dispose();
			    		turn = false;
			    	}
					if (commandSplit[0].trim().toLowerCase().equals("confirm")) {
						if (nameChosen) {
							if (raceChosen) {
								area.setText("");
								area.append("\nCharacter has been created.\n\n");
								area.append("" + map.getMap(100, 100).getText() + "\n\n");
								//print inventory
								inv.setText("");
								if (!player.getName().equals("")) {
									inv.append("\n" + player.getName() + "\n");
									inv.append("\n-----------------\n\n");
								}
								if (map.getMap(player.getX(), player.getY()).getTerrainType() instanceof Dungeon && player.inDungeon()) {
							    	if (!(map.getMap(player.getX(), player.getY()).getTerrainType().getDPlot(player.getDungeonX(), player.getDungeonY()).getEnc() instanceof DefaultCharacter)) {
							    		dHealth = map.getMap(player.getX(), player.getY()).getTerrainType().getDPlot(player.getDungeonX(), player.getDungeonY()).getEnc().getHealth();
							    		finalHealth = "Health: " + player.getHealth() + " | Enemy: " + dHealth + "\n";
							    	}
							    	else {
							    		finalHealth = "Health: " + player.getHealth() + " | Enemy: \n";
							    	}
							    
							    }
							    else {
							    	if (!(map.getMap(player.getX(), player.getY()).getEnc() instanceof DefaultCharacter)) {
							    		nHealth = map.getMap(player.getX(), player.getY()).getEnc().getHealth();
							    		finalHealth = "Health: " + player.getHealth() + " | Enemy: " + nHealth + "\n";
							    	}
							    	else {
							    		finalHealth = "Health: " + player.getHealth() + " | Enemy: \n";
							    	}
							    			
							    }
								inv.append(finalHealth);
								
								inv.append("\nExp: " + player.getCurrentXP() + ":" + ((int)(0.2 * (Math.pow(player.getLevel() - 1, 2)) * 100) + (int)(0.2 * (Math.pow(player.getLevel(), 2)) * 100)) + "\n");
								if (player.getRace() != null) {
									inv.append("\nLevel " + player.getLevel() + " " + player.getRace().getName() + "\n");
								}
								else {
									inv.append("\nLevel " + player.getLevel() + "\n");
								}
								inv.append(player.getInvList());
								inv.append("\nCurrent weight: " + player.getWeight() + "/" + player.getMaxWeight() + "\n\n");
								inv.append(player.printStatus());
								inv.append(player.returnAttributes());
	
								raceChosen = false;
								nameChosen = false;
								player.setPicked();
							}
							else {
								area.append("You have not set your race!\n\n");
							}
						}
						else {
							area.append("You have not set your name!\n\n");
						}
					}
					else {
						area.append("" + commandSplit[0] + " is not a command! See right panel for available commands.\n\n");
					}
				}
				else {
					area.append("" + commandSplit[0] + " is not a command! See right panel for available commands.\n\n");
				}
				
				if (nameChosen && raceChosen) {
					area.append("You have filled in all information for your character!\nType \"Confirm\" to complete your character and begin the game.\n\n");
				}
				
				//inventory side for character creation
				if (!player.characterCreation()) {
					inv.setText("");
					if (player.getRace() != null) {
						inv.append("\nRace: " + player.getRace().getName() + "\n\n");
					}		
					else {
						inv.append("\nChoose a race!\n - Use the command: Race *Human | Elf | Golem*\n\n");
					}		
					if (!player.getName().equals("")) {
						inv.append("Name: " + player.getName() + "\n\n");
					}
					else {
						inv.append("Set a name!\n - Use the command: Name *name*\n\n");
					}	
					inv.append(player.returnAttributes());
				}
			}
			
			// Glyph heals you to full, BUT there cannot be any enemies on the tile
			if (map.getMap(player.getX(), player.getY()).getTerrainType() instanceof Glyph && player.getHealth() != 100 + (10 * player.getVitality()) && ((map.getMap(player.getX(), player.getY()).getEnc().getHealth() <= 0) || (map.getMap(player.getX(), player.getY()).getEnc() instanceof DefaultCharacter))) {
				area.append("Touching the glyph restored " + ((100 + (10 * player.getVitality())) - player.getHealth()) + " health, returning you to full!\n\n");
				player.setHealth(100 + (10 * player.getVitality()));
				for (int x = player.getEffectSize() - 1; x >= 0; x--) {
					if (player.getPlayerStat(x).getMorality()) {
						area.append("You were cleared of " + player.getPlayerStat(x).getName() + "!\n\n");
						player.removeStatus(x);
					}
				}
				inv.setText("");
				if (map.getMap(player.getX(), player.getY()).getTerrainType() instanceof Dungeon && player.inDungeon()) {
			    	if (!(map.getMap(player.getX(), player.getY()).getTerrainType().getDPlot(player.getDungeonX(), player.getDungeonY()).getEnc() instanceof DefaultCharacter)) {
			    		dHealth = map.getMap(player.getX(), player.getY()).getTerrainType().getDPlot(player.getDungeonX(), player.getDungeonY()).getEnc().getHealth();
			    		finalHealth = "Health: " + player.getHealth() + " | Enemy: " + dHealth + "\n";
			    	}
			    	else {
			    		finalHealth = "Health: " + player.getHealth() + " | Enemy: \n";
			    	}
			    
			    }
			    else {
			    	if (!(map.getMap(player.getX(), player.getY()).getEnc() instanceof DefaultCharacter)) {
			    		nHealth = map.getMap(player.getX(), player.getY()).getEnc().getHealth();
			    		finalHealth = "Health: " + player.getHealth() + " | Enemy: " + nHealth + "\n";
			    	}
			    	else {
			    		finalHealth = "Health: " + player.getHealth() + " | Enemy: \n";
			    	}
			    			
			    }
				if (!player.getName().equals("")) {
					inv.append("\n" + player.getName() + "\n");
					inv.append("\n-----------------\n\n");
					inv.append(finalHealth);
				}
				
				inv.append("\nExp: " + player.getCurrentXP() + ":" + ((int)(0.2 * (Math.pow(player.getLevel() - 1, 2)) * 100) + (int)(0.2 * (Math.pow(player.getLevel(), 2)) * 100)) + "\n");
				if (player.getRace() != null) {
					inv.append("\nLevel " + player.getLevel() + " " + player.getRace().getName() + "\n");
				}
				else {
					inv.append("\nLevel " + player.getLevel() + "\n");
				}
				inv.append(player.getInvList());
				inv.append("\nCurrent weight: " + player.getWeight() + "/" + player.getMaxWeight() + "\n\n");
				inv.append(player.printStatus());
				inv.append(player.returnAttributes());
			}
			
			//Setting player weight			
			player.setWeight(0);
			for (int x = 0; x < 9; x++) {
				player.setWeight(player.getWeight() + (player.getInv(x).getWeight() * player.getInv(x).getNumInInv()));
			}
			player.setWeight(player.getWeight() + (player.getHand(0).getWeight() + player.getHand(1).getWeight()));
			
			area.append("" + player.doXP() + "");
			text.setText("");
			
			inInv = false;
			
		    if (player.getHealth() <= 0) {
		    	area.setText("");
		    	text.setText("");
		    	inv.setText("");
		    	area.append("You died!");
		    	text.setEditable(false);
		    }
		}
		
		//Set scrollbar to bottom of textArea
		
		scrollPane.validate();
		
		JScrollBar vert = scrollPane.getVerticalScrollBar();
		vert.setValue(vert.getMaximum());
		
		//Update Frame to Help Rendering issues
		
		SwingUtilities.updateComponentTreeUI(win);
		
		/*
		 * Start/Quit Game
		 */
		
		if ("start".equals(e.getActionCommand())) {
			win.dispose();
		}
		else if ("quit".equals(e.getActionCommand())) {
			start.setVisible(false);
			main.setVisible(true);
		}
		else if ("main".equals(e.getActionCommand())) {
			main.setVisible(false);
			start.setVisible(true);
		}
	}
	
	
}
