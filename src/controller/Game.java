package controller;

import io.GameCreationMode;
import io.Keyboard;
import io.PlayerSaverLoader;
import items.Item;

import java.awt.event.KeyEvent;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import tiles.World;
import utils.Randomizer;
import utils.Timer;
import view.Camera;
import view.DrawableWorld;
import view.TexturesLoader;
import view.View;
import combat.Combat;
import combat.CombatState;
import entities.Entity;
import entities.Interactable;
import entities.creatures.Direction;
import entities.creatures.MoveResult;
import entities.creatures.mobs.Mob;
import entities.creatures.player.Player;

public class Game implements Runnable {
	
	public static final int TILE_SIZE = 64;
	public static final String GAME_NAME = "UnClassic", DEFAULT_STARTING_WORLD = "world00";
	
	//Singleton instance
	private static Game instance;
	
	public static Game getInstance() {
		if(instance == null) {
			instance = new Game();		
		}
		return instance;
	}
	private Item currentlySelectedItem;
	//Running and timing
	@SuppressWarnings("unused")
	private int frames;
	private long oneSecondAgo;
	private boolean running, readyToRun;
	//Model
	private World currentWorld;
	private Player player;
	private Combat combat = null;
	private Set<Entity> entitiesToSpawn;
	private Set<Entity> entitiesToRemove;
	//View
	private View gui;
	private Camera camera;
	private DrawableWorld visibleWorld;
	
	//IO
	private PlayerSaverLoader playerSaverLoader;

	private Game() {
		oneSecondAgo = System.nanoTime();
		frames = 0;
		running = false;
		readyToRun = false;
	}
	
	/**
	 * Loads a new world file for the player to explore.
	 * 
	 * @param newWorldName The name of the new world.
	 * @param playerSpawnX The player's starting x coordinate.
	 * @param playerSpawnY The player's starting y coordinate.
	 */
	public void changeWorld(String newWorldName, float playerSpawnX, float playerSpawnY) {
		currentWorld = new World(newWorldName);
		player.moveTo(playerSpawnX, playerSpawnY);
		currentWorld.setPlayer(player);
		camera.centerOnEntity(player);
		visibleWorld = new DrawableWorld(currentWorld);
	}
	
	/**
	 * Passes on the given message to the view to be displayed and visible
	 * to the user.
	 * 
	 * @param message The message to show.
	 */
	public void display(String message) {
		gui.display(message);
	}
	
	/**
	 * "Drops" all items in the currently selected inventory slot
	 * (if any) by taking the entire stack without putting it
	 * anywhere. The user can't get these items back.
	 */
	public void dropCurrentlySelectedStack() {
		if(currentlySelectedItem != null) {
			player.getInventory().dropAll(currentlySelectedItem.toString());
		}
	}
	
	/**
	 * Initializes combat with the specified opponent, stopping the world's
	 * regular updates.
	 * 
	 * @param opponent The mob that the player will fight against.
	 */
	public void enterCombatMode(Mob opponent) {
		if(combat != null || opponent == null) {
			return;
		}
		combat = new Combat(opponent);
		gui.display("A wild " + opponent.toString().toUpperCase() + " has appeared!");
		gui.display((combat.isPlayersTurn() ? player : opponent) + " attacks first!");
	}
	
	/**
	 * Ends the combat for the world to continue updating.
	 */
	public void exitCombatMode() {
		combat = null;
	}
	
	/**
	 * Processes XP gain for the player. If the player levels up on a skill, the stats are
	 * updated if applicable, and the stats panel is updated. The skills table is always
	 * updated to reflect new level progress.
	 * 
	 * @param skillName The name of the skill to gain XP for.
	 * @param XP The amount of experience points gained.
	 */
	public void gainXP(String skillName, int XP) {
		if(player.getSkillSet().getSkill(skillName).accumulateXP(XP)) {
			display("Congrats! You leveled up on " + skillName);
			if(skillName.equals("Attack") || skillName.equals("Defense") || skillName.equals("Hitpoints")) {
				player.updateLevelStatBonus();
			}
			updateStatsPanel();
		}
		updateSkillsTable();
	}
	
	/**
	 * Ends the game, deleting the user's player file.
	 */
	public void gameOver() {
		display("Game over.");
		playerSaverLoader.deletePlayerFile();
		stopAndExit();
	}
	
	public Camera getCamera() {
		return camera;
	}

	public Item getCurrentlySelectedItem() {
		return currentlySelectedItem;
	}

	public World getCurrentWorld() {
		return currentWorld;
	}
	
	public Player getPlayer() {
		return player;
	}
	
	public DrawableWorld getState() {
		return visibleWorld;
	}
	
	/**
	 * Initializes all the resources required to run the game.
	 * By the time this method is run, the Game singleton has
	 * been properly instanced so other parts of the game can
	 * refer to it.
	 * 
	 * @param playerFilePath The path of the player file to load
	 * from the file system.
	 * @param worldName The name of the world to load from
	 * the file system.
	 * @param loadOrCreatePlayer Whether the game should create
	 * or load a player with the given name.
	 */
	public void init(String playerFilePath, String worldName, GameCreationMode loadOrCreatePlayer) {
		if(readyToRun) {
			return;
		}
		/* *****************************
		 * Initialize the model
		 * ****************************/
		try {
			playerSaverLoader = new PlayerSaverLoader(playerFilePath, loadOrCreatePlayer);	//Load or create the player
			player = playerSaverLoader.getPlayer();
		}
		catch (ClassNotFoundException | IOException e1) {
			System.err.println("Coulnd't load " + playerFilePath + ": " + e1.getMessage());
			e1.printStackTrace();
			System.exit(1);
		}
		currentWorld = new World(worldName);	//Load the world
		currentWorld.setPlayer(player);			//Add the newly created player to it
		entitiesToSpawn = new HashSet<Entity>();
		entitiesToRemove = new HashSet<Entity>();
		
		/* *****************************
		 * Initialize the view
		 * ****************************/
		TexturesLoader.setPlayerName(player.getName());
		camera = new Camera();													//Start the camera centered on the player to render properly
		visibleWorld = new DrawableWorld(currentWorld);							//Create the graphical representation of this world
		gui = new View(this.player.getSkillSet(), this.player.getInventory());	//Initialize the GUI: window, panels, canvas, etc.
		gui.getFrame().addKeyListener(Keyboard.getKeyboardController());		//Start listening for keyboard input
		updateAllViewElements();
		display("Welcome to the game, " + player.getName() + "!");
		readyToRun = true;
	}

	public boolean isInCombatMode() {
		return combat != null;
	}
	
	/**
	 * Marks an entity to be removed from the game after the next logic cycle.
	 * 
	 * @param e The entity to remove from the game.
	 */
	public void killEntity(Entity e) {
		if(!currentWorld.getEntities().contains(e)
			|| entitiesToRemove.contains(e)) {
			return;
		}
		entitiesToRemove.add(e);
	}
	
	/**
	 * Attempts to make the player move and moves the Camera as well
	 * if the player could move. Also updates the game state if the
	 * player changed tiles.
	 * 
	 * @param pressedKeyCode The code of the keyboard key the user
	 * pressed in an attempt to move.
	 */
	private void movePlayer(int pressedKeyCode) {
		MoveResult moveResult;
		Direction movingDirection;
		switch(pressedKeyCode) {
			case KeyEvent.VK_W:
			case KeyEvent.VK_UP:
				movingDirection = Direction.UP;
				break;
			case KeyEvent.VK_A:
			case KeyEvent.VK_LEFT:
				movingDirection = Direction.LEFT;
				break;
			case KeyEvent.VK_S:
			case KeyEvent.VK_DOWN:
				movingDirection = Direction.DOWN;
				break;
			case KeyEvent.VK_D:
			case KeyEvent.VK_RIGHT:
				movingDirection = Direction.RIGHT;
				break;
			default:
				return;
		}
		moveResult = player.move(movingDirection);
		if(moveResult != MoveResult.CANT_MOVE) {
			camera.move(player.getSpeed(), movingDirection);
		}
	}
	
	/**
	 * Attempts to put the specified amount of the specified item into the
	 * player's inventory and updates the inventory table if needed.
	 * 
	 * @param item The item to attempt to add.
	 * @param quantity The amount of items of the same kind to add.
	 * @return <code>true</code> if the item was successfully added to the
	 * inventory, or <code>false</code> if not.
	 */
	public boolean putInPlayerInventory(Item item, int quantity) {
		boolean couldAdd = player.getInventory().put(item, quantity);
		if(couldAdd) {
			updateInventoryTable();
		}
		return couldAdd;
	}
	
	/**
	 * Takes the appropriate action based on user input and the
	 * game's state.
	 * 
	 * @param pressedKeyCode The code of the keyboard key the user pressed.
	 */
	public void respondToKeyboardInput(int pressedKeyCode) {
		//Keys always allowed in the game
		if(pressedKeyCode == Keyboard.SAVE_AND_QUIT) {
			stopAndExit();
		}
		else if(pressedKeyCode == Keyboard.USE) {
			if(currentlySelectedItem != null) {
				if(isInCombatMode()) {
					combat.useItemInCombat(currentlySelectedItem);
				}
				else {
					useSelectedItem();
				}
			}
			else {
				display("You must select an item!");
			}
		}
		else if(pressedKeyCode == Keyboard.UNEQUIP) {
			if(player.getEquipmentSlots().isOccupied("head")) {
				player.unequip("head");
			}
			if(player.getEquipmentSlots().isOccupied("torso")) {
				player.unequip("torso");
			}
			if(player.getEquipmentSlots().isOccupied("arms")) {
				player.unequip("arms");
			}
			if(player.getEquipmentSlots().isOccupied("left hand")) {
				player.unequip("left hand");
			}
			if(player.getEquipmentSlots().isOccupied("right hand")) {
				player.unequip("right hand");
			}
			if(player.getEquipmentSlots().isOccupied("legs")) {
				player.unequip("legs");
			}
			if(player.getEquipmentSlots().isOccupied("feet")) {
				player.unequip("feet");
			}
			updateEquipmentPanel();
		}
		else if(pressedKeyCode == Keyboard.EXAMINE) {
			Entity e = currentWorld.getEntityInFrontOf(player);
			if(e != null) {
				display(e.examine());
			}
		}
		else if(pressedKeyCode == Keyboard.DROP) {
			dropCurrentlySelectedStack();
		}
		else {
			//Keys only allowed in combat
			if(isInCombatMode()) {
				if(pressedKeyCode == Keyboard.ATTACK) {
					combat.playerAttack();
					//receiveCombatAction(CombatAction.ATTACK);
				}
				else if(pressedKeyCode == Keyboard.RUN_AWAY) {
					combat.runAway();
					//receiveCombatAction(CombatAction.RUN);
				}
			}
			//Keys only allowed outside of combat
			else {
				if(Keyboard.isDirectionalKey(pressedKeyCode)) {
					movePlayer(pressedKeyCode);
				}
				else if(pressedKeyCode == Keyboard.INTERACT) {
					Entity e = currentWorld.getEntityInFrontOf(player);
					if (e != null && e instanceof Interactable) {
						player.interactWith((Interactable) e);
					}
				}
			}
		}
	}
	
	/**
	 * Begins the busy update cycle of the game. Will continue until told to
	 * stop by an action within the game.
	 */
	public void run() {
		if(!readyToRun || running) {
			return;
		}
		running = true;
		while(running) {
			if(Timer.gameMustUpdate()){
				this.update();			//Do logic updates
				if(!this.isInCombatMode()) {
					gui.draw(visibleWorld);
				}
				frames++;
			}
			
			if(Timer.secondsSince(oneSecondAgo) >= 1){
				oneSecondAgo = System.nanoTime();
				
				//System.out.println("FPS: " + frames);
				frames = 0;
			}
		}
		stopAndExit();
	}

	/**
	 * Sets the currently selected item.
	 * 
	 * @param itemName The name of the item to set as currently selected.
	 */
	public void setSelectedItem(String itemName) {
		currentlySelectedItem = (itemName == null) ? null : player.getInventory().get(itemName);
	}
	
	/**
	 * Marks a new entity to be spawned between update cycles of the world.
	 * 
	 * @param newEntity The entity to add to the world.
	 */
	public void spawn(Entity newEntity) {
		if(newEntity == null
				|| currentWorld.getEntities().contains(newEntity)
				|| entitiesToSpawn.contains(newEntity)
				|| currentWorld.getTileAt(newEntity.getX(), newEntity.getY()).isOccupied()) {
			return;
		}
		entitiesToSpawn.add(newEntity);
	}
	
	public void stopAndExit() {
		if(!running) {
			return;
		}
		running = false;
		if(player.isAlive()) {
			try {
				playerSaverLoader.savePlayer();
			} catch (IOException e) {
				e.printStackTrace();
			}
			System.out.println("Game saved. Stopping!");
		}
		else {
			System.out.println("Dead player, not saving game. Stopping!");
		}
		System.exit(0);
	}
	
	/**
	 * Attempts to take the specified amount of the specified item from the
	 * player's inventory and updated the inventory table if needed.
	 * 
	 * @param itemName The name of the item to take.
	 * @param quantity The amount of items of the same kind to take.
	 * @return An instance of the item(s) taken, if successfully taken, or
	 * <code>null</code> if they couldn't be taken.
	 */
	public Item takeFromPlayerInventory(String itemName, int quantity) {
		if(itemName == null || itemName.isEmpty() || quantity <=0 ) {
			return null;
		}
		Item takenItem = player.getInventory().take(itemName, quantity);
		if(takenItem != null) {
			updateInventoryTable();
		}
		return takenItem;
	}
	
	/**
	 * Updates the world if the game is not in combat mode, or updates the
	 * combat if in combat mode.
	 */
	@SuppressWarnings("incomplete-switch")
	private void update() {
		Randomizer.newNumber();
		if(this.isInCombatMode()) {
			CombatState turnResult = combat.update(); 
			if(turnResult != CombatState.NO_CHANGES) {
				switch(turnResult) {
					case MOB_DIED:
						//Check the PUT result to see if Player could take item
						Item droppedItem = combat.getEnemy().dropItem();
						if(droppedItem != null) {
							putInPlayerInventory(droppedItem, 1);
							display(combat.getEnemy() + " dropped " + droppedItem + "!");
							updateInventoryTable();
						}
						/*
						 * Rather than killing the entity normally, delete
						 * the enemy directly since the world is not updating,
						 * so there is no danger of a concurrent modification
						 * exception.  
						 */
						currentWorld.killEntity(combat.getEnemy());
						visibleWorld.removeEntity(combat.getEnemy());
						exitCombatMode();
						display("Congratulations, you won!");
						break;
					case PLAYER_DIED:
						exitCombatMode();
						display("You died! Game over.");
						long wait = Timer.getFutureTime(1);
						while(!Timer.isItThatTimeYet(wait)) {}
						gameOver();
						break;
					case PLAYER_ESCAPED:
						exitCombatMode();
						display("You got away! Lucky bastard...");
						break;
				}
			}
		}
		else
		{
			currentWorld.update();
			if(!entitiesToSpawn.isEmpty()) {
				for(Entity e : entitiesToSpawn) {
					currentWorld.spawnEntity(e);
					visibleWorld.addEntity(e);					
				}
				entitiesToSpawn.clear();
			}
			if(!entitiesToRemove.isEmpty()) {
				for(Entity e : entitiesToRemove) {
					currentWorld.killEntity(e);
					visibleWorld.removeEntity(e);
				}
				entitiesToRemove.clear();
			}
			//this.updateState();
		}
	}
	
	public void updateAllViewElements() {
		updateEquipmentPanel();
		updateInventoryTable();
		updateSkillsTable();
		updateStatsPanel();
	}

	public void updateEquipmentPanel() {
		gui.updateEquipmentPanel();
	}
	
	public void updateInventoryTable() {
		gui.updateInventoryTable();
	}
	
	public void updateSkillsTable() {
		gui.updateSkillsTable();
	}
	
	public void updateStatsPanel() {
		gui.updateStatsPanel();
	}
	
	
	/**
	 * Makes the currently selected Item be used by the player.
	 * 
	 * @return <code>true</code> if the item could be used, or <code>false</code> if not
	 */
	private boolean useSelectedItem() {
		if (currentlySelectedItem == null) {
			return false;
		}
		return player.useItem(currentlySelectedItem);
		
	}
}
