package combat;

/**
 * Enum created to apply game logic based on updates in combat.
 */
public enum CombatState {
	/**
	 * Combat state when there is no change
	 */
	NO_CHANGES,
	
	/**
	 *  Combat state when the player dies
	 */
	PLAYER_DIED,
	
	/**
	 * Combat state when the enemy dies
	 */
	MOB_DIED,
	
	/**
	 * Combat state when the player successfully run away from battle
	 */
	PLAYER_ESCAPED
}