package entities.creatures.mobs;

import items.Item;
import items.equipment.EquipmentFactory;

public class MoltenCorki extends Mob {

	private static final long serialVersionUID = 2538957507427789676L;

	public MoltenCorki(float x, float y) {
		super("Molten Corki", "Dafuq is this?!", x, y, false, 1000, 40, 0.25f, true);
	}

	@Override
	public Item dropItem() {
		return EquipmentFactory.makeEquipment("Corki Mask");
	}

}