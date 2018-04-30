package entities.creatures.player;

import items.equipment.Equipment;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class EquipmentSlots implements Serializable {

	private static final long serialVersionUID = 2290700828310815861L;
	Map<String, Equipment> slots;

	EquipmentSlots() {
		slots = new HashMap<String, Equipment>(7);
		slots.put("left hand", null);
		slots.put("right hand", null);
		slots.put("head", null);
		slots.put("torso", null);
		slots.put("legs", null);
		slots.put("arms", null);
		slots.put("feet", null);
	}
	
	public boolean isOccupied(String slotName) {
		if(!slots.containsKey(slotName)) {
			return true;	//To avoid attempting to put something in an invalid slot
		}
		return slots.get(slotName) != null;
	}
}