package entities.creatures.mobs;

import items.Item;
import items.equipment.EquipmentFactory;
import utils.Randomizer;

/**
 * The skeleton is an aggressive mob that follows the player around. It's
 * a relatively slow mob that can be outrun by the player.
 */
public class Skeleton extends Mob {

	private static final long serialVersionUID = 2538957507427789676L;

	public Skeleton(float x, float y) {
		super("Skeleton", "A chiropractor's dream! No? Ok.", x, y, false, 5, 25, 0.015f, true);
	}

	@Override
	public Item dropItem() {
		switch((int) Math.floor(Randomizer.getRandomNumber()*10)+1) {
		case 5:
		case 6:
		case 7:
			return EquipmentFactory.makeEquipment("Bronze Shield");
		case 8:
		case 9:
			return EquipmentFactory.makeEquipment("Iron Axe");
		case 10:
			return EquipmentFactory.makeEquipment("Steel Gauntlets");
		default: 
			return null;
		}
		
		
	}

}