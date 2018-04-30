package items;

import java.io.IOException;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Vector;

import controller.Game;

/**
 * This class represents the Player's inventory. It's made up of
 * inventory slots, which contain an unlimited amount of items.
 * When an item is added, it's put into a new slot if there is no one
 * holding an identical one, or it is added to the slot holding these kind of items.
 * For more info. about how items are identified, see {@link Item#equals(Object)}.
 * 
 */
public class Inventory implements Iterable<InventorySlot>, Serializable {

	private static final long serialVersionUID = -8883242190591155344L;
	
	private final int maxSize;
	private Map<String, InventorySlot> slots;
		
	/**
	 * Creates a new Inventory with the given size.
	 * 
	 * @param size The maximum number of slots to hold items
	 * the inventory can have
	 */
	public Inventory(int size) {
		this.maxSize = size;
		slots = new HashMap<String, InventorySlot>(size);
	}
	
	/**
	 * Checks whether the given Item can be added to the inventory.
	 * If the inventory isn't full, the item can be added. If not,
	 * the item can be added only if there is already a slot holding
	 * such items.
	 * 
	 * @param item The item to check for possible addition.
	 * @return <code>true</code> if the given item can be added,
	 * @throws NullPointerException If the given item is null.
	 *<code>false</code> if not.
	 */
	public boolean canAdd(Item item) {	
		if(!isFull()) {
			return true;
		}
		else {
			return contains(item.toString());
		}
	}
	
	/**
	 * Checks if the inventory contains an item.
	 * 
	 * @param itemName The item name to be checked.
	 * @return <code>true</code> if the inventory contains at least one
	 * unit of the item with the given name, or <code>false</code> if not.
	 */
	public boolean contains(String itemName) {
		return slots.containsKey(itemName);
	}
	
	/**
	 * Indicates how many items of the given name there are in the inventory.
	 * 
	 * @param itemName The item name to be checked
	 * @return The amount of items of the given name stored in the inventory
	 */
	public int count(String itemName) {
		if (contains(itemName)) {
			return slots.get(itemName).getQuantity();
		}
		else {
			return 0;
		}
	}

	/**
	 * Removes the slot containing the item(s) with
	 * the given name.
	 * 
	 * @param itemName The name of the items to be dropped.
	 */
	public void dropAll(String itemName) {
		//This covers the case in which the inventory is empty
		if(!contains(itemName))	{
			return;
		}
		slots.remove(itemName);
		Game.getInstance().updateInventoryTable();
	}
	
	/**
	 * Gets the item in this inventory WITHOUT taking it (the quantity
	 * isn't decremented).
	 * 
	 * @param itemName The name of the Item to get.
	 * @return The corresponding Item in this inventory, or <code>null</code>
	 * if there is no such item in this inventory.
	 */
	public Item get(String itemName) {
		if(!contains(itemName)) {
			return null;
		}
		else {			
			return slots.get(itemName).getItem();
		}
	}
	
	/**
	 * Creates a {@link Vector} with the inventory's data.
	 * This method is used to display this data, giving only
	 * relevant information (item's name and its amount).
	 * 
	 * @return The data vector
	 */
	public Vector<Vector<String>> getDataVector() {
		Vector<Vector<String>> outerVector = new Vector<Vector<String>>();
		for (Entry<String,InventorySlot> entry : slots.entrySet()) {
			Vector<String> innerVector = new Vector<String>();
			innerVector.add(new String(entry.getKey()));
			innerVector.add(Integer.toString(entry.getValue().getQuantity()));	
			outerVector.add(innerVector);
		}	
		return outerVector;
	}
	
	public int getFreeSlots() {
		return maxSize - slots.size();
	}
	
	public int getOccupiedSlots() {
		return slots.size();
	}
	
	public boolean isEmpty() {
		return slots.isEmpty();
	}
	
	public boolean isFull() {
		return slots.size() == maxSize;
	}
	
	@Override
	public Iterator<InventorySlot> iterator() {
		return slots.values().iterator();
	}
	
	/**
	 * Puts one of the given item in the inventory.
	 * 
	 * @param item The item to be stored.
	 * @return <code>true</code> if the item could be
	 * added, <code>false</code> if not (i.e. if the
	 * inventory is full)
	 */
	public boolean put(Item item) {
		return put(item, 1);
	}
	
	/**
	 * Attempts to put the given Item into the inventory,
	 * indicating the amount.
	 * 
	 * @param item The item to be stored.
	 * @param quantity Amount to be stored.
	 * @return <code>true</code> if the item could be
	 * added, <code>false</code> if not (i.e. if the
	 * inventory is full)
	 */
	public boolean put(Item item, int quantity) {
		if(item == null || quantity <= 0) {
			return true;
		}
		if(slots.containsKey(item.toString())) {
			InventorySlot aux = slots.get(item.toString());
			for (int i = 0 ; i < quantity ; i++) {
				aux.add();
			}
		}
		else if(isFull()) {
			return false;
		}
		else {
			slots.put(item.toString(), new InventorySlot(item, quantity));
		}
		Game.getInstance().updateInventoryTable();
		return true;
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
	 * @return The inventory's maximum amount of slots.
	 */
	public int size() {
		return this.maxSize;
	}
    
    /**
	 * Swaps the given Item with one of the item to take.
	 * 
	 * @param itemToPut The Item to put in exchange for
	 * the one being taken.
	 * @param itemToTake The name of the item to take
	 * in exchange for the one being put.
	 * @return the taken item,
	 * or <code>null</code> if the swap couldn't be made.
	 */
	public Item swap(Item itemToPut, String itemToTake) {
		if(!contains(itemToTake)) {
			return null;
		}
		Item result = take(itemToTake);
		if(isFull()) {
			put(result);	//The swap can't be completed, so the original item is put back.
			return null;
		}
		put(itemToPut);		//This takes care of updating the inventory table
		return result;
	}
	
	/**
	 * Takes one Item with the given name from the inventory.
	 * 
	 * @param itemName The name of the item to take.
	 * @return The requested item or <code>null</code> if
	 * there is no such item in the inventory.
	 */
	public Item take(String itemName) {
		return take(itemName, 1);
	}
	
	/**
	 * Attempts to take <code>quantity</code> many items from
	 * the inventory, returning one instance of the item.
	 * 
	 * @param itemName The name of the stack from which to take.
	 * @param quantity How many to take.
	 * @return An instance of the item that was taken, if
	 * successful, or <code>null</code> if the items couldn't be
	 * taken. 
	 */
	public Item take(String itemName, int quantity) {
		if(!contains(itemName) || slots.get(itemName).getQuantity() < quantity) {
			return null;
		}
		Item result = slots.get(itemName).take(quantity);
		if(slots.get(itemName).getQuantity() == 0) {
			slots.remove(itemName);
		}
		return result;
	}
	
	@Override
	public String toString() {
		String result = "This inventory contains:\n";
		if(isEmpty()) {
			result += "Nothing!\n";
		}
		else {
			for(InventorySlot i : slots.values()) {
				result += i.toString() + "\n";
			}
		}
		return result;
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
