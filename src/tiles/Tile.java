package tiles;

import java.io.Serializable;

import entities.Entity;
import entities.FishingSpot;
import entities.creatures.Creature;

/**
 * Every world is made up of a grid of tiles, which are the logical
 * placeholders for the world's entities. Each tile has a type in
 * order to be differentiated from other types and to be rendered
 * properly. Tiles can be solid, meaning that entities can't walk
 * into them. Both solid and non-solid tiles can be occupied by
 * up to one entity; solid tiles are occupied by non-moving entities
 * upon world creation, for example water tiles are occupied by
 * a {@link FishingSpot} for the player to interact with.
 */
public class Tile implements Serializable {

	private static final long serialVersionUID = -5378960426047686444L;

	private boolean isSolid;
	
	/*
	 * The following variables are transient because they are given
	 * values upon world (re)construction.
	 */
	private transient String type;
	private transient Entity occupiedBy;
	
	/**
	 * Creates a new unoccupied Tile.
	 * @param type This Tile's type: dirt, grass, water, etc.
	 * @param isSolid Whether this tile is solid or not.
	 */
	public Tile(String type, boolean isSolid) {
		this.type = type;
		this.isSolid = isSolid;
		this.occupiedBy = null;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (isSolid ? 1231 : 1237);
		result = prime * result
				+ ((occupiedBy == null) ? 0 : occupiedBy.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof Tile)) {
			return false;
		}
		Tile other = (Tile) obj;
		if (isSolid != other.isSolid) {
			return false;
		}
		if (occupiedBy == null) {
			if (other.occupiedBy != null) {
				return false;
			}
		} else if (!occupiedBy.equals(other.occupiedBy)) {
			return false;
		}
		if (type == null) {
			if (other.type != null) {
				return false;
			}
		} else if (!type.equals(other.type)) {
			return false;
		}
		return true;
	}



	/**
	 * Marks this Tile as free so it can be occupied by
	 * another Entity.
	 * 
	 * @return <code>true</code> if the tile could be
	 * successfully freed.
	 */
	public boolean free() {
		if(isSolid) {
			return false;
		}
		occupiedBy = null;
		return true;
	}
	
	/**
	 * Gets the entity currently occupying this tile.
	 * @return The {@link Entity} currently occupying this tile, or
	 * <code>null</code> if not occupied.
	 */
	public Entity getEntityOnTop() {
		return occupiedBy;
	}
	
	/**
	 * Gets this tile's type.
	 * @return This tile's type.
	 */
	public String getType() {
		return type;
	}
	
	/**
	 * Checks whether an Entity can walk into this Tile.
	 * @return <code>true</code> if this tile is not solid and
	 * not occupied.
	 */
	public boolean isAccessible() {
		return !isSolid && !this.isOccupied();
	}

	/**
	 * Checks whether there is currently an Entity on this Tile or not.
	 * @return <code>true</code> if this tile is not occupied by any
	 * entity.
	 */
	public boolean isOccupied() {
		return occupiedBy != null;
	}
	
	/**
	 * Marks this tile as occupied by the specified entity.
	 * 
	 * @param e The entity now occupying this tile.
	 * @throws IllegalStateException If this tile is already occupied. These checks
	 * should be performed elsewhere, for instance in {@link Creature#move(entities.creatures.Direction)}.
	 */
	public void setEntityOnTop(Entity e) throws IllegalStateException {
		if(isOccupied()) {
			throw new IllegalStateException("Attempted to occupy an already occupied tile. Free the tile first.");
		}
		occupiedBy = e;
	}
	
	@Override
	public String toString() {
		return type;
	}
}
