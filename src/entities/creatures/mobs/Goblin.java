package entities.creatures.mobs;

import items.Item;
import items.equipment.EquipmentFactory;

import java.io.IOException;

import utils.Randomizer;

public class Goblin extends Mob {
	
	private static final long serialVersionUID = 3456681326587902849L;

	public Goblin(float x, float y) {
		super("Goblin", "Not as ugly as your mom! ZING!", x, y, false, 5, 8, 0.015625f, false);
	}

	@Override
	/**
	 * Has a 50% chance of dropping a bronze helmet.
	 */
	public Item dropItem() {
		double num = Randomizer.getRandomNumber();
		if(num <= 0.5) {
			return EquipmentFactory.makeEquipment("Bronze Helmet");
		}
		else {
			return null;
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
