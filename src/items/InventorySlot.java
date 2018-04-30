package items;

import java.io.IOException;
import java.io.Serializable;

/**
 * This class represents a stack of items of the same kind
 * in the inventory.
 */
public class InventorySlot implements Serializable {

	private static final long serialVersionUID = -6701256520754200517L;
	private final Item item;
	private int quantity;
	
	/**
	 * Creates a new inventory slot holding just one unit
	 * of the given item.
	 * 
	 * @param item The item to be stored in this slot.
	 */
	public InventorySlot(Item item) {
		this(item, 1);
	}
	
	/**
	 * Creates a new InventorySlot holding a given quantity
	 * of the given item.
	 * 
	 * @param item The Item to be stored in this slot.
	 * @param quantity The initial amount of units of
	 * the given item to be stored.
	 */
	public InventorySlot(Item item, int quantity)
	{
		this.item = item;
		this.quantity = quantity;
	}
	
	/**
	 * Adds one unit of the item stored in this slot.
	 */
	public void add() {
		this.quantity++;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		InventorySlot other = (InventorySlot) obj;
		return this.getItem().equals(other.getItem()); 
	}

	public Item getItem() {
		return this.item;
	}
	
	public int getQuantity() {
		return this.quantity;
	}
	
	@Override
	public int hashCode() {
		return item.hashCode();
	} 
	
	/**
     * Un-serializes this object following default behavior.
     * @param in The Stream to read this Entity from.
     * @throws IOException If an I/O error occurs while reading from the Stream.
     * @throws ClassNotFoundException when a wrong class is stored in the file.
     */
	private void readObject(java.io.ObjectInputStream in) throws IOException, ClassNotFoundException {
		in.defaultReadObject();
	}
	
	/**
	 * Takes one unit of the given Item from this slot.
	 *  
	 * @return The Item stored in this slot or
	 * <code>null</code> if this slot is empty.
	 */
	public Item take() {
		return take(1);
	}

	/**
	 * Takes a given amount of units of the given Item from this slot.
	 *  @param quantity How many items must be present/used.
	 * @return The Item stored in this slot or
	 * <code>null</code> if this slot is empty.
	 */
	public Item take(int quantity) {
		if(this.quantity == 0) {
			return null;
		}
		this.quantity -= quantity;
		return item;
	}
	
	@Override
	public String toString() {
		return item.toString() + " x " + quantity;
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
