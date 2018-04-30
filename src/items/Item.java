package items;

import java.io.IOException;
import java.io.Serializable;

/**
 * This class represents items that can be used by the player.
 * Items are objects that affect the player's "stats";
 * i.e. an Adamant Sword increases the "Attack" stat.
 */
public abstract class Item implements Serializable
{
	private static final long serialVersionUID = 611362519678113263L;
	private String examineText;
	private String name;
    
	/**
	 * Creates an item type object.
	 * Item are identified by their name.
	 * They also have an examine text with useful information about them,
	 * or a funny description.
	 * 
	 * @param name The item's name.
	 * @param examineText The item's examine text.
	 */
    public Item(String name, String examineText)
    {
    	this.name = name;
        this.examineText=examineText;
    }
    
    /**
     * Items are compared by their name
     */
    @Override
    public boolean equals(Object obj) {
    	if(obj == null) {
            return false;
        }
        if(obj == this) {
            return true;
        }
        if(getClass() != obj.getClass()) {
            return false;
        }
        Item other = (Item) obj;
        return this.toString().equals(other.toString());
    }

    public String examine() {
        return this.examineText;
    }
    
    public String getName() {
		return name;
	}
    
    @Override
    public int hashCode() {
        return this.toString().hashCode();
    }
    
    /**
     * Un-serializes this object following default behavior.
     * 
     * @param in The Stream to read this Entity from.
     * @throws IOException If an I/O error occurs while reading from the Stream.
     * @throws ClassNotFoundException when a wrong class is stored in the file.
     */
	private void readObject(java.io.ObjectInputStream in) throws IOException, ClassNotFoundException {
		in.defaultReadObject();
	}
    
    @Override
    public String toString() {
        return getName();
    }
    
    /**
     * Items can be used to have various effects on the player.
     * 
     * @return 0 If the item couldn't be used, or the number of items
     * of the same kind that should be taken from the user's inventory
     * after being used.
     */
    public abstract int use();
    
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
