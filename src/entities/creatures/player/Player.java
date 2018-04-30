package entities.creatures.player;

import items.Inventory;
import items.Item;
import items.equipment.Equipment;

import java.io.IOException;
import java.io.Serializable;

import skills.SkillSet;
import utils.Randomizer;
import controller.Game;
import entities.Interactable;
import entities.creatures.Creature;

public class Player extends Creature implements Serializable {

	public static final int PLAYER_INVENTORY_SLOTS = 19,
							DEFAULT_PLAYER_STARTING_HEALTH = 10,
							DEFAULT_PLAYER_STARTING_X = 10,
							DEFAULT_PLAYER_STARTING_Y = 10;
	public static final float DEFAULT_PLAYER_SPEED = 0.125f;

	private static final long serialVersionUID = -1105151117566782638L;
	
	private SkillSet skills;
	private Inventory inventory;
	private EquipmentSlots equipmentSlots;
	private float criticalBonus;
	private int equipmentAttackBonus, levelAttackBonus, equipmentDefenseBonus,
					levelDefenseBonus, criticalChance;

	/**
	 * Creates a brand-new Player with default starting stats.
	 * 
	 * @param name The new Player's name.
	 */
	public Player(String name) {
		this(name, DEFAULT_PLAYER_STARTING_X, DEFAULT_PLAYER_STARTING_Y,
				DEFAULT_PLAYER_STARTING_HEALTH, DEFAULT_PLAYER_SPEED,
				new SkillSet(), new Inventory(PLAYER_INVENTORY_SLOTS));
	}

	/**
	 * Creates a new Player with the specified information.
	 * 
	 * @param name The Player's name.
	 * @param x The Player's starting horizontal position.
	 * @param y The Player's starting vertical position.
	 * @param startingHealth The Player's starting maximum health.
	 * @param speed The Player's speed.
	 * @param skills The Player's skill set.
	 * @param inventory The Player's inventory.
	 */
	public Player(String name, float x, float y, int startingHealth,
					float speed, SkillSet skills, Inventory inventory) {
		super(name, "Oh look! That's you, " + name + "!", x, y, startingHealth,
				0, speed);
		this.skills = skills;
		this.inventory = inventory;
		this.equipmentSlots = new EquipmentSlots();
		equipmentAttackBonus = 0;
		levelAttackBonus = 1;
		equipmentDefenseBonus = 0;
		levelDefenseBonus = 1;
		criticalChance = 10;
		criticalBonus = 0;
	}

	/**
	 * Player deals damage based on their combat skills. Calculations for taking
	 * them into consideration, as well as randomness, are made here.
	 * 
	 * @see entities.creatures.Creature#attack()
	 */
	@Override
	public int attack() {
		int hit = 1 + (int)(Randomizer.getRandomNumber()*(levelAttackBonus + equipmentAttackBonus));
		if(Randomizer.getRandomNumber() <= criticalChance/100){
			Game.getInstance().display("KKKKKKRITIKAL HIT");
			return (int) (hit * criticalBonus);
		}
		return hit;
	}
	
	/**
	 * Attempts to equip the given equipment on the Player, thus modifying their inventory
	 * as well as their attack, defense and critical bonuses. If the equipment belongs to
	 * a hand slot, it will default to the right hand, and will go to the left hand if the
	 * right is occupied.
	 * 
	 * @param e The equipment item to equip.
	 * @return 1 if the equipment could be equipped or 0 if not (i.e. no room in the
	 * inventory to store an already equipped item).
	 */
	public int equip(Equipment e) {
		if(e == null) {
			return 0;
		}
		if(e.getMinSkillLevel() > skills.getSkill(e.getNeededSkillName()).getLevel()) {
			Game.getInstance().display("You don't have enough skill to use that");
			return 0;
		}
		String slot = e.getSlotName();
		//Equip to right hand by default, or if not attempt to equip on the left hand.
		if(slot.equals("hand")) {
			if(equipmentSlots.isOccupied("right hand")) {
				slot = "left hand";
			}
			else {
				slot = "right hand";
			}
		}
		
		if(!equipmentSlots.slots.containsKey(slot)) {
			Game.getInstance().display("You're trying to equip something on an invalid slot.");
			return 0;
		}
		
		if(equipmentSlots.isOccupied(slot)) {
			/*
			 * Attempt to swap the currently equipped item with the item to equip.
			 * If successful, the item previously equipped is now in the inventory
			 * and swap returns the item to equip, but we already have it as a
			 * parameter, so we don't store it. If the swap was unsuccessful, there
			 * is no room in the inventory to unequip the already-equipped item.
			 */
			if(inventory.swap(equipmentSlots.slots.get(slot), e.toString()) == null) {
				Game.getInstance().display("There is no room in your inventory to store the equipment you have to take off, drop some items first.");
				return 0;
			}
			//Swap successful, now replace the equipment in the slot with the new one
			else {
				deleteBonus(equipmentSlots.slots.get(slot));	//Take away the bonuses from the equipment being taken off,
				equipmentSlots.slots.put(slot, e);				//Replace the old Equipment (which is now in the inventory) with the new one,
				addBonus(e);									//And add the bonuses of the new equipment.
				return 0;										//Finally, return 0 to avoid taking the item twice.
			}
		}
		else {
			equipmentSlots.slots.put(slot, e);
			addBonus(e);
			return 1;
		}
	}

	public int getAttack() {
		return equipmentAttackBonus + this.getSkillSet().getSkill("Attack").getLevel();
	}
	
	public int getCritical() {
		return criticalChance;
	}
	
	public int getDefense() {
		return equipmentDefenseBonus + levelDefenseBonus;
	}

	/**
	 * Returns the String representation of the equipment on the given slot.
	 * 
	 * @param slot The slot from which to get the equipment.
	 * @return The String representation of the equipment on the provided slot,
	 * or <i>"Unequipped"</i> if there is no such slot or if it's unoccupied.
	 */
	public String getEquipmentOn(String slot) {
		if(!equipmentSlots.slots.containsKey(slot) || !equipmentSlots.isOccupied(slot)) {
			return "Unequipped";
		}
		else {			
			return equipmentSlots.slots.get(slot).toString();
		}
	}
	
	public EquipmentSlots getEquipmentSlots() {
		return equipmentSlots;
	}
	
	public Inventory getInventory() {
		return inventory;
	}
	
	public SkillSet getSkillSet() {
		return this.skills;
	}
	
	public void heal(int HPToRecover) {
		setHealth(getHealth() + HPToRecover);
	}
	
	/**
	 * Makes the interactable entity respond to the player's interaction.
	 *  
	 * @param e The interactable entity the player is interacting with. 
	 */
	public void interactWith(Interactable e) {
		e.respond();
	}
	
	/**
	 * Relocates the Player. Used when changing worlds.
	 * 
	 * @param newX The new X coordinate.
	 * @param newY The new Y coordinate.
	 */
	public void moveTo(float newX, float newY) {
		this.x = newX + 0.5f;
		this.y = newY + 0.5f;
	}
	
	@Override
	public int receiveDamage(int damage) {
		int damageApplied = damage;
		return super.receiveDamage(Math.max(0, damageApplied));
	}
	
	
	/**
	 * Unequips the item currently loaded onto the given slot.
	 * 
	 * @param slotName The name of the slot to unequip.
	 * @return <code>true</code> if the slot could be unequipped or <code>false</code> if
	 * not (i.e. no such slot, slot is not occupied or there's no room to store equipment
	 * in inventory).
	 */
	public boolean unequip(String slotName) {
		if(!equipmentSlots.slots.containsKey(slotName)) {
			Game.getInstance().display(slotName + " is not a valid equipment slot.");
			return false;
		}
		if(!equipmentSlots.isOccupied(slotName)) {
			Game.getInstance().display("There is nothing currently equipped on your " + slotName);
			return false;
		}
		else {
			if(!inventory.canAdd(equipmentSlots.slots.get(slotName))) {
				Game.getInstance().display("There is no room in your inventory to unequip your " + slotName);
				return false;
			}
			else {
				deleteBonus(equipmentSlots.slots.get(slotName));
				inventory.put(equipmentSlots.slots.get(slotName));
				equipmentSlots.slots.put(slotName, null);
				Game.getInstance().updateEquipmentPanel();
				Game.getInstance().updateInventoryTable();
				Game.getInstance().updateStatsPanel();
				return true;
			}
		}
	}


	@Override
	public void update() {
		//Do nothing, the player does not get updated by the back end.
	}

	public void updateLevelStatBonus() {
		this.levelAttackBonus = (int) Math.pow(this.skills.getSkill("Attack").getLevel(),1.25);
		this.levelDefenseBonus = (int) Math.pow(this.skills.getSkill("Defense").getLevel(),1.25);
		if((this.maxHealth-10) != this.skills.getSkill("Hitpoints").getLevel()){
			this.maxHealth = this.skills.getSkill("Hitpoints").getLevel()+10;
			//Heal back to max health on level up
			setHealth(maxHealth);
		}
	}

	/**
	 * Makes the Item be used by the player.
	 * If the item could be used, it is taken from the inventory
	 * 
	 * @param e The item to be used by the player
	 * @return <code>true</code> if the item could be used, or <code>false</code> if not.
	 */
	public boolean useItem(Item e) {
		
		if (e == null || !inventory.contains(e.toString())) {
			return false;
		}
		
		int itemsToTake = e.use();
		if (itemsToTake > 0) {
			inventory.take(e.toString(), itemsToTake);
		}
		Game.getInstance().updateAllViewElements();
		return true;
	}
	
	private void addBonus(Equipment e) {
		this.equipmentAttackBonus += e.getAttackBonus();
		this.equipmentDefenseBonus += e.getDefenseBonus();
		this.criticalBonus += e.getCriticalRating();
		if(e.getCriticalRating() != 0) {
			this.criticalBonus *= e.getCriticalRating();
		}
		/*
		 * De esta manera dos armas con critico x1 no hacen un critico x2
		 * Ningun arma deberia tener critico 0, si tiene critico 0 se lo considera 1
		 */
	}
	

	private void deleteBonus(Equipment e) throws ArithmeticException {
		this.equipmentAttackBonus -= e.getAttackBonus();
		this.equipmentDefenseBonus -= e.getDefenseBonus();
		this.criticalChance -= e.getCriticalRating();
		if(e.getCriticalRating() != 0) {
			this.criticalBonus /= e.getCriticalRating();
		}
	}
	
	/**
	 * Un-serializes this object following default behavior.
	 * 
	 * @param in The Stream to read this Entity from.
	 * @throws IOException If an I/O error occurs while reading from the Stream.
	 * @throws ClassNotFoundException when an invalid class is stored in the file.
	 */
	private void readObject(java.io.ObjectInputStream in) throws IOException,
			ClassNotFoundException {
		in.defaultReadObject();
	}
	
	/**
	 * Serializes this object following default behavior.
	 * 
	 * @param out The Stream to write this Entity to.
	 * @throws IOException If an I/O error occurs while writing to the Stream.
	 */
	private void writeObject(java.io.ObjectOutputStream out) throws IOException {
		out.defaultWriteObject();
	}
}
