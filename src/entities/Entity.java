package entities;

import java.io.IOException;
import java.io.Serializable;

/**
 * Entity is the superclass of all entities that make up the game. An
 * Entity is any object that is not a Tile that has coordinates to be
 * located within the world. Some Entities can share the tile they are
 * located in with another Entity. Entities also have an examination
 * text, for more information see {@link Entity#examine()}
 */
public abstract class Entity implements Serializable
{
	private static final long serialVersionUID = 8763184127863459261L;
	
	protected float x, y;
    private String name;
    private final String examineText;
    
    /**
     * Creates a new Entity.
     * @param name The Entity's name.
     * @param examineText The Entity's examination text. See @see {@link Entity#examine()}
     * @param x The Entity's (logical) x coordinate.
     * @param y The Entity's (logical) y coordinate.
     */
    public Entity(String name, String examineText, float x, float y) {
    	this.name = name;
        this.examineText = examineText;
    	this.x = x + 0.5f;
    	this.y = y + 0.5f;
    }
    public Entity(String name, float x, float y, boolean canShareTile) {
        this(name, "Yet another object of this realm", x, y);
    }
    public Entity(String name, float x, float y) {
        this(name, "Yet another object of this realm", x, y);
    }
     
    /**
     * An Entity can have behavior to update as the game progresses.
     * Each subclass should specify its own behavior, if any.
     */
    public abstract void update();
    
    public float getX() {
    	return x;
    }
    public float getY() {
    	return y;
    }
    public String getName()
    {
    	return this.name;
    }
    /**
     * Entities can be examined by the player to get useful information
     * about them, or in its defect a funny description of the Entity.
     * @return What the player would notice after examining this Entity.
     */
    public String examine()
    {
        return this.examineText;
    }
    /**
     * Gets a textual representation of this Entity's location.
     * @return A textual representation of this Entity's location.
     */
    public String getLocation() {
    	return "(" + x + ", " + y + ")";
    }
    
	
    @Override
	public String toString() {
		//return "A(n) " + name + " located at " + getLocation();
    	return name;
	}
	
    @Override
	public int hashCode() {
		return this.toString().hashCode();
	}
	
    @Override
    /**
     * Two Entities are considered equal if they have the same name and location.
     */
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Entity other = (Entity) obj;
		return (name.equals(other.getName()) && this.x == other.getX() && this.y == other.getY());
	}
    
    /**
     * Serializes this object following default behavior.
     * @param out The Stream to write this Entity to.
     * @throws IOException If an I/O error occurs while writing to the Stream.
     */
    private void writeObject(java.io.ObjectOutputStream out) throws IOException {
		out.defaultWriteObject();
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
}
