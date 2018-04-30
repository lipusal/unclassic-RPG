package entities.creatures.mobs;

import items.Item;

import java.io.IOException;
import java.io.Serializable;

import controller.Game;


import utils.Randomizer;
import utils.Timer;
import entities.Interactable;
import entities.creatures.Creature;
import entities.creatures.Direction;
import entities.creatures.player.Player;

/**
 * A Mob is a Creature that will pursue and attack the Player if "aggroed",
 * or will roam about if not.
 */
public abstract class Mob extends Creature implements Serializable, Interactable {

	private static final long serialVersionUID = 7419427808520012187L;
	private boolean aggroed = false;
	private final float aggroRadius = 4;
	private Direction roamingDirection;
	private long roamEndTime;
	
	/**
	 * Creates a new Mob with the specified information.
	 * 
	 * @param name The Mob's name.
	 * @param examineText This Mob's examination text.
	 * @param x The x component of this Mob's location.
	 * @param y The y component of this Mob's location.
	 * @param canShareTile Whether this Mob can share their tile with another Entity.
	 * @param startingHealth This Mob's maximum health.
	 * @param attackDamage The amount of damage this Mob deals when attacking.
	 * @param speed This Mob's movement speed.
	 * @param isAggroed Whether this Mob actively pursues and attacks the player on
	 * sight or not. 
	 */
	public Mob(String name, String examineText, float x, float y, boolean canShareTile,
			int startingHealth, int attackDamage, float speed, boolean isAggroed) {
		super(name, examineText, x, y, startingHealth, attackDamage, speed);
		setAggro(isAggroed);
		this.roamEndTime = -1;
	}

	private float distanceToPlayer() {
		Player p = Game.getInstance().getPlayer();
		return (float) Math.sqrt(Math.pow(p.getX() - this.getX(), 2)
						 + Math.pow(p.getY() - this.getY(), 2));
	}
	
	/**
	 * Mobs can drop Items when they are killed. Each Mob will specify its
	 * own behavior for dropping Items on death.
	 * 
	 * @return An Item for the player to keep or <code>null</code> if no
	 * item is dropped.
	 */
	public abstract Item dropItem();
	
	public boolean isAggroed() {
		return this.aggroed;
	}
	
	/**
	 * Makes this Mob move towards the Player. Called by aggroed
	 * Mobs. They will engage in combat with the Player if they
	 * make contact with them.
	 */
	private void moveToPlayer() {
		Player p = Game.getInstance().getPlayer();
		float xDiff = p.getX() - this.getX();
		float yDiff = p.getY() - this.getY();
		if(Math.abs(xDiff) > Math.abs(yDiff)) {
			move(xDiff < 0 ? Direction.LEFT : Direction.RIGHT);
		}
		else {
			move(yDiff < 0 ? Direction.UP : Direction.DOWN);
		}
	}
	
	/**
     * Un-serializes this object following default behavior.
     * @param in The Stream to read this Entity from.
     * @throws IOException If an I/O error occurs while reading from the Stream.
     * @throws ClassNotFoundException If an invalid class was read from the Stream.
     */
	private void readObject(java.io.ObjectInputStream in) throws IOException, ClassNotFoundException {
		in.defaultReadObject();
	}
	
	/**
	 * Enters combat with Player.
	 */
	public void respond() {
		Game.getInstance().enterCombatMode(this);
	}
	
	/**
	 * Makes this Mob move about the World in one direction
	 * for a specified amount of time. 
	 */
	private void roam() {
		if(roamEndTime != -1) {
			if(Timer.isItThatTimeYet(roamEndTime)) {
				roamEndTime = -1;
				return;
			}
			move(roamingDirection);
		}
		else
		{
			double num = Randomizer.newNumber();
			if(num <= 0.1) {
				roamingDirection = Direction.UP;
			}
			else if(num <= 0.2) {
				roamingDirection = Direction.RIGHT;
			}
			else if(num <= 0.3) {
				roamingDirection = Direction.LEFT;
			}
			else if(num <= 0.4) {
				roamingDirection = Direction.DOWN;
			}
			//Don't move
			else
			{
				return;
			}
			//Move in the chosen direction (if any) for 3 seconds
			roamEndTime = Timer.getFutureTime(3);
			move(roamingDirection);
		}
	}

	public void setAggro(boolean a) {
		this.aggroed = a;
	}
	
	@Override
	/**
	 * Makes this Mob roam about the world if not aggroed or if
	 * the Player is outside this Mob's aggro radius. Otherwise,
	 * makes this Mob move towards the player.
	 */
	public void update() {
		if(isAggroed()) {
			float dist = distanceToPlayer();
			if(dist <= aggroRadius) {
				moveToPlayer();
				if(dist <= 1.42) {
					Game.getInstance().enterCombatMode(this);
				}
			}
		}
		else
		{
			roam();
		}
	}
    
    /**
     * Serializes this object following default behavior.
     * @param out The Stream to write this Entity to.
     * @throws IOException If an I/O error occurs while writing to the Stream.
     */
    private void writeObject(java.io.ObjectOutputStream out) throws IOException {
		out.defaultWriteObject();
	}

}
