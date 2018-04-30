package items.equipment;

import controller.Game;
import items.Item;

/** 
 * This class represents any item that can be equipped.
 * Equipment type items can modify the player's battle states:
 * Attack, Defense, Critical Rating and Hit Rating.
 * Also, each equipment has a minimum needed skill level to be used,
 * and a slot name where it can be equipped; i.e. weapons are equipped
 * in the player's hands.
 */
public abstract class Equipment extends Item {
	
	private static final long serialVersionUID = 7415948548672702023L;
	private final int attackBonus;
	private final float criticalBonus;
	private final int defenseBonus;
	private final int minSkillLevel;
	private final String neededSkillName;
	private final String slotName;
	
	
	/**
	 * Creates an equipment type item.
	 *
	 * @param name The item's name.
	 * @param examineText The item's examine text.
	 * @param slotName The item's slot name
	 * @param attackBonus The item's attack bonus.
	 * @param defenseBonus The item's defense bonus.
	 * @param criticalBonus The item's critical bonus.
	 * @param neededSkillName The item's needed skill
	 * @param minSkillLevel The item's minimun needed skill level.
	 */
	public Equipment(String name, String examineText, String slotName,
					 int attackBonus, int defenseBonus, float criticalBonus, String neededSkillName, int minSkillLevel) {
		
		super(name, examineText);
		this.attackBonus = attackBonus;
		this.criticalBonus = criticalBonus;
		this.defenseBonus = defenseBonus;
		this.minSkillLevel = minSkillLevel;
		this.neededSkillName = neededSkillName;
		this.slotName = slotName;
		
	}

	public int getAttackBonus() {
		return attackBonus;
	}
	
	public float getCriticalRating() {
		return criticalBonus;
	}

	public int getDefenseBonus() {
		return defenseBonus;
	}

	public int getMinSkillLevel() {
		return minSkillLevel;
	}
	
	public String getNeededSkillName() {
		return neededSkillName;
	}

	public String getSlotName() {
		return slotName;
	}

	/**
	 * This method makes use of an equipment type item.
	 * When these kind of items are used, they are equipped.
	 * 
     * @return 1 if the item could be equipped, or 0 if not.
	 */
	@Override
	public int use() {
		return Game.getInstance().getPlayer().equip(this);
	}
	
}
