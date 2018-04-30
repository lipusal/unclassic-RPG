package entities.creatures;

import java.io.IOException;

import tiles.Tile;
import utils.Randomizer;
import controller.Game;
import entities.Entity;

/**
 * A Creature is an Entity that can engage in combat, move, and drop items
 * upon death. As such they are the player's primary item resource.
 */
public abstract class Creature extends Entity
{

	private static final long serialVersionUID = -959036793620390852L;
	
	protected int maxHealth;
	private int health, attackDamage;
	protected float speed;
	protected transient Direction looking = Direction.DOWN;

	/**
	 * Creates a new Creature instance with the specified information.
	 * 
	 * @param name The creature's name.
	 * @param examineText The creature's examination text. 
	 * @param x The creature's starting x coordinate.
	 * @param y The creature's starting y coordinate.
	 * @param startingHealth The creature's starting health. This will be the creature's maximum health.
	 * @param attackDamage The creature's basic attack 
	 * @param speed The creature's movement speed.
	 */
	public Creature(String name, String examineText, float x, float y, int startingHealth,
			int attackDamage, float speed) {
		super(name, examineText, x, y);
		this.maxHealth = startingHealth;
		this.health = maxHealth;
		this.attackDamage = attackDamage;
		this.speed = speed;
	}
	public Creature(String name, String examineText, float x, float y, int startingHealth,
			int attackDamage) {
		this(name, examineText, x, y, startingHealth, attackDamage, 0.015625f);
	}

	/**
	 * Gets the damage that a creature deals. By default, creatures always
	 * deal the same damage amount. Other behavior should be specified by
	 * subclasses.
	 * 
	 * @return The damage this creature deals.
	 */
	public int attack() {
		return  Math.max(attackDamage/4, (int)(Randomizer.getRandomNumber()*attackDamage));
	}

	/**
	 * Checks in a specific direction if collisions are happening between
	 * the moving creature and other entities, or solid tiles, at the 3
	 * closest tiles in front of the creature.
	 * 
	 * @param deltaX The horizontal position change.
	 * @param deltaY The vertical position change.
	 * @return true if it collides and false if it doesn't.
	 */
	 
	public boolean checkCollision(float deltaX, float deltaY) {
		int x = (int) this.getX();
		int y = (int) this.getY();
		switch (looking){
			case UP:
				return	checkCollisionInTile(Game.getInstance().getCurrentWorld().getTileAt(x, y - 1), deltaX, deltaY, 1)|| 
						checkCollisionInTile(Game.getInstance().getCurrentWorld().getTileAt(x + 1, y - 1), deltaX, deltaY, 1)|| 
						checkCollisionInTile(Game.getInstance().getCurrentWorld().getTileAt(x - 1, y - 1), deltaX, deltaY, 1 )||
						checkCollisionWithTile(x, y - 1, deltaX, deltaY,1)||
						checkCollisionWithTile(x + 1, y - 1, deltaX, deltaY,1)||
						checkCollisionWithTile(x - 1, y - 1, deltaX, deltaY,1); 
						
			case DOWN:
				return 	checkCollisionInTile(Game.getInstance().getCurrentWorld().getTileAt(x, y + 1), deltaX, deltaY,1)|| 
						checkCollisionInTile(Game.getInstance().getCurrentWorld().getTileAt(x + 1, y + 1), deltaX, deltaY,1)|| 
						checkCollisionInTile(Game.getInstance().getCurrentWorld().getTileAt(x - 1, y + 1), deltaX, deltaY,1)||
						checkCollisionWithTile(x + 1, y + 1, deltaX, deltaY,1)||
						checkCollisionWithTile(x - 1, y + 1, deltaX, deltaY,1)||
						checkCollisionWithTile(x, y + 1, deltaX, deltaY,1);
			case LEFT:
				return	checkCollisionInTile(Game.getInstance().getCurrentWorld().getTileAt(x-1, y+1), deltaX, deltaY,0)|| 
						checkCollisionInTile(Game.getInstance().getCurrentWorld().getTileAt(x-1, y), deltaX, deltaY,0)|| 
						checkCollisionInTile(Game.getInstance().getCurrentWorld().getTileAt(x-1, y-1), deltaX, deltaY,0)||
						checkCollisionWithTile(x-1, y + 1, deltaX, deltaY,0)||
						checkCollisionWithTile(x-1, y - 1, deltaX, deltaY,0)||
						checkCollisionWithTile(x-1, y, deltaX, deltaY,0);   				
			case RIGHT:
				return	checkCollisionInTile(Game.getInstance().getCurrentWorld().getTileAt(x + 1 , y + 1), deltaX, deltaY,0)|| 
						checkCollisionInTile(Game.getInstance().getCurrentWorld().getTileAt(x + 1, y), deltaX, deltaY,0)|| 
						checkCollisionInTile(Game.getInstance().getCurrentWorld().getTileAt(x + 1, y - 1), deltaX, deltaY,0)||
						checkCollisionWithTile(x + 1, y + 1, deltaX, deltaY,0)||
						checkCollisionWithTile(x + 1, y, deltaX, deltaY,0)|| 
						checkCollisionWithTile(x + 1, y - 1, deltaX, deltaY,0);
		}
		return false;
	}
	
	/**
	 * checks collision with a creature in the given tile and and creature
	 * @param tile The tile to be checked.
	 * @param deltaX The horizontal position change.
	 * @param deltaY The vertical position change.
	 * @param dir 1 if its moving vertically or 0 if horizontally
 	 * @return true if it collides and false if it doesn't
	 */
	public boolean checkCollisionInTile(Tile tile, float deltaX, float deltaY, int dir) {
		if(tile == null){
			return false;
		}
		Entity entity = tile.getEntityOnTop();
		if(entity == null){
			return false;
		}
		//We add half a Tile's width to get to the Entity's geometric center.
		if(dir==0){
			if((Math.abs(entity.getY() - this.getY()) - Math.abs(deltaY)) < 1){
				if((Math.abs(entity.getX() - this.getX()) -  Math.abs(deltaX)) < 1){
					return true;
				}
			}
			return false;
		}
		if((Math.abs(entity.getX() - this.getX()) -  Math.abs(deltaX)) < 1){
			if((Math.abs(entity.getY() - this.getY()) -  Math.abs(deltaY)) < 1){
				return true;
			}
		}
		return false;
	}

	/**
	 * Increases the Creature's life to continue fighting.
	 * @param healValue How many lifepoints this Creature
	 * recovers.
	 * @return The Creature's health after healing.
	 */

	/**
	 * checks collision with the given tile and and creature
	 * @param x The tile's horizontal position.
	 * @param y The tile's vertical position.
	 * @param deltaX The horizontal position change.
	 * @param deltaY The vertical position change.
	 * @param dir 1 if its moving vertically or 0 if horizontally
	 * @return <code>true</code> if the creature would collide in the specified circumstances.
	 */
	public boolean checkCollisionWithTile(float x, float y, float deltaX, float deltaY, int dir){
		Tile tile=Game.getInstance().getCurrentWorld().getTileAt(x,y);
		x+= 0.5f;
		y+= 0.5f;
		if(tile==null) {
			return false;
		}
		if(!(tile.isAccessible())) {
			if(dir==0){
				if((Math.abs(y - this.getY()) - Math.abs(deltaY)) < 1) {
					if((Math.abs(x - this.getX()) - Math.abs(deltaX)) < 1) {
						return true;
					}
				return false;
				}
			}
			if((Math.abs(x - this.getX()) - Math.abs(deltaX)) < 1) {
				if((Math.abs(y - this.getY()) - Math.abs(deltaY)) < 1) {
					return true;
				}
			}
		}
		return false;
	}

	public int getHealth() {
		return health;
	}
	
	public Direction getLookingDirection() {
		return looking;
	}

	public int getMaxHealth() {
		return maxHealth;
	}
	public float getSpeed() {
		return speed;
	}
	public boolean isAlive() {
		return !isDead();
	}
	
	public boolean isDead() {
		return health <= 0;
	}
	
	
	/**
	 * Moves this Creature in the specified Direction, if
	 * the World allows it.
	 * @param d A Direction: UP, DOWN, LEFT or RIGHT.
	 * @return A member of {@link MoveResult} to inform the
	 * result of the movement attempt.
	 */
	public MoveResult move(Direction d)
	{
		this.looking = d;
		MoveResult result;
		switch(d)
		{
			case UP:
				//Y axis is inverted
				result = processMovement(0, -speed);
				break;
			case DOWN:
				//Y axis is inverted
				result = processMovement(0, speed);
				break;
			case LEFT:
				result = processMovement(-speed, 0);
				break;
			case RIGHT:
				result = processMovement(speed, 0);
				break;
			default:
				result = MoveResult.CANT_MOVE;
				break;
		}
		return result;
	}
	
	/**
	 * Damages this Creature the specified damage amount
	 * and returns the Creature's life after being hit.
	 * @param damage The amount of damage taken.
	 * @return The Creature's health after receiving damage.
	 */
	public int receiveDamage(int damage) {
		int newHealth = getHealth() - damage;
		setHealth(getHealth() - damage);
		return newHealth;
	}
	
	public void setSpeed(float newSpeed) {
		speed = newSpeed;
	}
	/**
	 * @see Entity#update()
	 */
	public abstract void update();
	
	/**
	 * Validates the Creature can move in the direction specified and actually moves it
	 * if it is allowed to.
	 * 
	 * @param deltaX The horizontal position change.
	 * @param deltaY The vertical position change.
	 * @return {@link MoveResult#CHANGED_TILE} if this Creature moved and changed Tile,
	 * {@link MoveResult#MOVED_WITHIN_TILE} if this Creature moved but didn't change tile,
	 * or {@link MoveResult#CANT_MOVE} if the Creature couldn't move (either due to a
	 * collision with another Entity or with a solid Tile).
	 */
	private MoveResult processMovement(float deltaX, float deltaY) {
		//If we'd be moving outside the world
		if(x + deltaX - 0.5f < 0 || x + deltaX + 0.5f  >= Game.getInstance().getCurrentWorld().getWidth()
		|| y + deltaY - 0.5f < 0 || y + deltaY + 0.5f >= Game.getInstance().getCurrentWorld().getHeight()) {
			return MoveResult.CANT_MOVE;
		}
		//If no collisions are possible the creature moves
		if(!checkCollision(deltaX, deltaY)){
			//if the creature is moving from one tile to another
			if((int)(x+deltaX) != (int) x || (int)(y+deltaY) != (int) y){
				Game.getInstance().getCurrentWorld().getTileAt(x, y).free();
				Game.getInstance().getCurrentWorld().getTileAt(x + deltaX, y + deltaY).setEntityOnTop(this);
				x+=deltaX;
				y+=deltaY;
				return MoveResult.CHANGED_TILE;
			}
			x+=deltaX;
			y+=deltaY;
			return MoveResult.MOVED_WITHIN_TILE;
		}
		return MoveResult.CANT_MOVE;
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
     * Serializes this object following default behavior.
     * @param out The Stream to write this Entity to.
     * @throws IOException If an I/O error occurs while writing to the Stream.
     */
    private void writeObject(java.io.ObjectOutputStream out) throws IOException {
		out.defaultWriteObject();
	}
    
    protected void setHealth(int newHealth) {
		if(newHealth < 0) {
			health = 0;
		}
		else if(newHealth > maxHealth) {
			health = maxHealth;
		}
		else {
			health = newHealth;
		}
	}
}