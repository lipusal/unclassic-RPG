package tiles;

import io.WorldLoader;

import java.io.IOException;
import java.util.Collections;
import java.util.Set;

import entities.Entity;
import entities.creatures.Creature;
import entities.creatures.player.Player;

/**
 * Represents any one region of the game's large world. A region is made up of a grid of
 * Tiles, which can be occupied by Entities. Entities always spawn from the location and
 * in the same initial state as specified in the world's .entities file. 
 */
public class World {
	
	private int width, height;
	private Tile[][] tiles;
	private Set<Entity> entities;
	private boolean playerAdded;

	/**
	 * Creates a new world by loading its corresponding <i>.structure</i> and <i>.entities</i>
	 * files. <b>Improper calls to this constructor will terminate the program.</b>
	 * 
	 * @param worldName The world name, in format "world00", "world01", etc.
	 */
	public World(String worldName) {
		playerAdded = false;
		try {
			tiles = WorldLoader.loadWorldStructure(worldName);
			width = tiles[0].length;
			height = tiles.length;
			entities = WorldLoader.loadWorldEntities(worldName, tiles);
		}
		catch(IOException|ClassNotFoundException|TileException e) {
			System.err.println("Error loading world: " + e.getMessage());
			e.printStackTrace();
			System.exit(1);
		}
	}
	
	/**
	 * Updates all the Entities contained in this world.
	 */
	public void update() {
		for(Entity e : this.entities) {
			e.update();
		}
	}
	
	/**
	 * Gets the Tile at the specified coordinates.
	 * 
	 * @param x The x coordinate, with a value within [0, world width)
	 * @param y The y coordinate, with a value within [0, world height)
	 * @return The corresponding tile or <code>null</code> if the specified coordinate
	 * is outside of the world's bounds.
	 */
	public Tile getTileAt(float x, float y) {
		if(x < 0 || x >= width || y < 0 || y >= height) {
			return null;
		}
		return tiles[(int) x][(int) y];
	}
	
	/**
	 * Gets the entity in front of the specified Creature, if any.
	 * 
	 * @param c The Creature from which to look. Note that the parameter is not of
	 * type Entity since Entities don't have a defined looking direction.
	 * @return The entity in front of e or <code>null</code> if there is no entity in
	 * front of e.
	 */
	public Entity getEntityInFrontOf(Creature c) {
		Tile inFront = null;
		switch(c.getLookingDirection()) {
			case UP:
				inFront = getTileAt(c.getX(), c.getY()-1.0f);
				break;
			case DOWN:
				inFront = getTileAt(c.getX(), c.getY()+1.0f);
				break;
			case LEFT:
				inFront = getTileAt(c.getX()-1.0f, c.getY());
				break;
			case RIGHT:
				inFront = getTileAt(c.getX()+1.0f, c.getY());
				break;
		}
		return inFront == null ? null : inFront.getEntityOnTop();
	}
	
	/**
	 * Gets the coordinates of the <b>unoccupied</b> tile in front of the given Creature. 
	 * If there is no such tile, or if it's not accessible, returns <code>null</code>.
	 * 
	 * @param c The Creature from which to look.
	 * @return A 2-element <code>float</code> array containing the x coordinate as first
	 * element, and the y coordinate as second, or <code>null</code> if the resulting
	 * location is not accessible or outside the world.
	 */
	public float[] getCoordsOfTileInFrontOf(Creature c) {
		float[] result = new float[2];
		switch(c.getLookingDirection()) {
		case UP:
			result[0] = c.getX();
			result[1] = c.getY() - 1;
			break;
		case DOWN:
			result[0] = c.getX();
			result[1] = c.getY() + 1;
			break;
		case LEFT:
			result[0] = c.getX() - 1;
			result[1] = c.getY();
			break;
		case RIGHT:
			result[0] = c.getX() + 1;
			result[1] = c.getY();
			break;
		}
		Tile resultingTile = getTileAt(result[0], result[1]); 
		if(resultingTile == null || !resultingTile.isAccessible()) {
			return null;
		}
		else {
			return result;
		}
	}
	
	/**
	 * Gets an unmodifiable version of this world's entity set.
	 * 
	 * @return An unmodifiable version of the entities contained in this world.
	 */
	public Set<Entity> getEntities() {
		return Collections.unmodifiableSet(entities);
	}
	
	public Tile[][] getStructure() {
		return tiles;
	}
	
	/**
	 * Adds the specified player to the World. Only one player can be present in
	 * the World at the time. Used when switching between worlds.
	 * 
	 * @param p The Player to add.
	 * @throws IllegalStateException If this world already has a player set.
	 */
	public void setPlayer(Player p) throws IllegalStateException {
		if(playerAdded) {
			throw new IllegalStateException("The current world already has a player set, only call this method when creating a new world.");
		}
		playerAdded = true;
		entities.add(p);
		getTileAt(p.getX(), p.getY()).setEntityOnTop(p);
	}
	
	/**
	 * "Spawns" an entity by putting it in the world and adding it to the world's entity set.
	 *  
	 * @param e The entity to spawn.
	 * @throws IllegalStateException If the new entity's location is already occupied. These
	 * checks should be made before spawning. 
	 */
	public void spawnEntity(Entity e) throws IllegalStateException {
		//Catch the possible exception and, if thrown, throw a new one of the same kind with a more specific message.
		try {
			getTileAt(e.getX(),e.getY()).setEntityOnTop(e);
		}
		catch (IllegalStateException ex) {
			throw new IllegalStateException("Can't spawn a(n) " + e + ", tile " + e.getLocation() + ", is already occupied.");
		}
		entities.add(e);
	}
	
	/**
	 * "Kills" an entity by removing it from its tile and from the world's entity set.
	 * 
	 * @param e The entity to kill.
	 */
	public void killEntity(Entity e) {
		//Do nothing if the specified entity is not in this world.
		if(entities.remove(e) == false) {
			return;
		}
		getTileAt(e.getX(),e.getY()).free();
	}
	
	@Override
	public String toString() {
		String result = "";
		for(int y = 0; y < this.height; y++) {
			for(int x = 0; x < this.width; x++) {
				if(tiles[x][y].isOccupied()) {
					result += (tiles[x][y].getEntityOnTop().toString() + "\t");
				}
				else
				{
					result += (tiles[x][y].toString() + "\t");
				}
			}
			result += "\n";
		}
		return result;
	}
	
	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}
}
