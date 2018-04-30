package items.consumable;

import items.Item;
import controller.Game;

/**
 * This class represents food.
 * A food type item makes the player heal when used.
 * These type of items have a cook state. For more info.,
 * see {@link CookState}.
 */
public class Food extends Item {

	private static final long serialVersionUID = 4473431705791254478L;
	private final int healValue;
	private final int requiredCookingLevel;
	private CookState state;
	
	/**
	 * Creates a food type item.
	 * 
	 * @param name The item's name.
	 * @param examineText The item's examine text.
	 * @param cookState The item's cook state. For more info., see
	 * {@link CookState}.
	 * @param healValue The maximum amount of HP this item can heal.
	 * @param requiredCookingLevel The item's cooking skill minimum level.
	 */
	public Food(String name, String examineText, CookState cookState,
					int healValue, int requiredCookingLevel) {
		super(name, examineText);
		this.state = cookState;
		this.healValue = healValue;
		this.requiredCookingLevel = requiredCookingLevel;
	}
	
	/**
	 * Creates an uncooked food type item.
	 *
	 * @param name The item's name.
	 * @param examineText The item's examine text.
	 * @param healValue The maximum amount of HP this item can heal.
	 * @param requiredCookingLevel The item's cooking skill minimum level.
	 */
	public Food(String name, String examineText, int healValue, int requiredCookingLevel) {
		this(name, examineText, CookState.UNCOOKED, healValue, requiredCookingLevel);
	}
	
	public int getHealValue() {
		return healValue;
	}
	
	public int getRequiredCookingLevel() {
		return requiredCookingLevel;
	}


	public CookState getState() {
		return state;
	}
	
	public void setState(CookState newState) {
		this.state = newState;
	}
	
	/**
	 * This method makes use of a food type item.
	 * When used, the player heals the amount of HP specified in the food's
	 * heal value field.
	 * Food can only be used if their cook state is cooked. For more info.,
	 * see {@link CookState}. 
	 * 
	 * @return 1 if the food could be eaten, or 0 if not.
	 */
	@Override
	public int use() {		
		boolean canEat = (state == CookState.COOKED);
		if(canEat) {
			Game.getInstance().getPlayer().heal(healValue);
		}
		else {
			Game.getInstance().display("You can't eat " + (state == CookState.UNCOOKED ? "raw" : "burnt") + " food");
		}
		return canEat ? 1 : 0;
	}
}
