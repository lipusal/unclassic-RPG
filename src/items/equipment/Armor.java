 package items.equipment;

 /**
  * This class represents any equipment that is an armor.
  * Each type of armor has a different equipment slot; i.e. helmets are equipped in the player's head.
  * Their needed skill is "Defense".
  * Armors are used to defend, but they may improve attack though.
  *
  */
public class Armor extends Equipment {

	private static final long serialVersionUID = -3265894796569073699L;

	public Armor(String name, String examineText, String slotName, int attackBonus, int defenseBonus, int criticalRating,
				 int minSkillLevel) {
		super(name, examineText, slotName, attackBonus, defenseBonus,
				criticalRating, "Defense" , minSkillLevel);
	}

}
