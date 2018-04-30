package entities.creatures.mobs;

import items.Item;
import items.equipment.EquipmentFactory;

import java.io.IOException;

import utils.Randomizer;

public class Rat extends Mob {

	private static final long serialVersionUID = -5213430014198954766L;

	public Rat(float x, float y) {
		super("Giant Rat", "A not-so-slightly overgrown rat.", x, y, false, 3, 5, 0.015625f, false);
	}

	@Override
	/**
	 * Rats don't drop items upon death.
	 */
	public Item dropItem() {
		if(Randomizer.getRandomNumber()<0.5);
		return EquipmentFactory.makeEquipment("Iron Dagger");
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
