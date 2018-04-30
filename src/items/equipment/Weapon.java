package items.equipment;

/**
 * This class represents any equipment that is a weapon.
 * Weapons are equipped in the players hands, and their needed skill is "Attack".
 * Weapons are used to attack, but they may improve defense though.
 *
 */
public class Weapon extends Equipment {
	
	private static final long serialVersionUID = 2276989299500307542L;

	public Weapon(String name, String examineText,
			int attackBonus, int defenseBonus, float d, int minAttackLevel) {
		super(name, examineText, "hand", attackBonus, defenseBonus, d, "Attack", minAttackLevel);
		
	}

}
