package items.consumable;

/**
 * Enum created to apply game logic based on the food's state; 
 * i.e. food can only be eaten if it's cooked.
 */
public enum CookState { 
	
	/**
	 * Food initial's state.
	 */
	UNCOOKED, 
	
	/**
	 * Food that can be eaten.
	 */
	COOKED, 
	
	/**
	 * Food that was tried to be cooked,
	 * without success.
	 */
	BURNT
}