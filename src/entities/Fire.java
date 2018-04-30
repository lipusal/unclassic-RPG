package entities;

import items.Item;
import items.consumable.ConsumableItemFactory;
import items.consumable.CookState;
import items.consumable.Food;
import utils.Randomizer;
import utils.Timer;
import controller.Game;

public class Fire extends Entity implements Interactable {
	

	private static final long serialVersionUID = -2537120951787327955L;
	private long dieTime;
	
	public Fire (int duration, float x, float y) {
		super("Fire", "Perfect for cooking!", x, y);
		this.dieTime = Timer.getFutureTime(duration);
	}

	
	@Override
	public void update() {
		if(Timer.isItThatTimeYet(dieTime)) {
			Game.getInstance().killEntity(this);
		}
	}
	
	/** 
	 * Method used for cooking food.
	 * It takes off the inventory a unit of the given raw food, and adds a unit of the same food
	 * cooked, when success.
	 * If food couldn't be cooked, the method informs why success couldn't be achieved.
	 * 
	 * @param ingredient The given food to be cooked.
	 */
	private void cook(Food ingredient) {
		
		boolean canCook = Game.getInstance().getPlayer().getSkillSet().getSkill("Cooking").getLevel() >= ingredient.getRequiredCookingLevel();
		int cookingLvl = Game.getInstance().getPlayer().getSkillSet().getSkill("Cooking").getLevel();
		
		if(ingredient.getState() == CookState.UNCOOKED) {
			if(!canCook) {
				Game.getInstance().display("You don't know how to cook this. Yet...");
			}
			else {
				String subName = ingredient.getName().substring(4); //Name without "RAW"
				
				if(Randomizer.getRandomNumber() <= 0.5 + cookingLvl * 0.01) {
					if (!Game.getInstance().putInPlayerInventory(ConsumableItemFactory.makeItem(subName), 1)) {
						return; // This may happen if there was no space for the new cooked food in the inventory
					}	
					Game.getInstance().takeFromPlayerInventory(ingredient.getName(), 1);
					Game.getInstance().gainXP("Cooking", ingredient.getRequiredCookingLevel()*25);
					Game.getInstance().display("That smels GOOOOOOOOOD");
				}
				else {
					Game.getInstance().putInPlayerInventory(ConsumableItemFactory.makeItem("Burnt " + subName), 1);
					Game.getInstance().takeFromPlayerInventory(ingredient.getName(), 1);
					Game.getInstance().display("Owwww, you ruined it, you skilles bastard");
				}
			}
		}
	}
	
	public void respond() {
		Item itemToCook = Game.getInstance().getCurrentlySelectedItem(); 
		if(itemToCook != null) {			
			if(itemToCook instanceof Food) {
				cook((Food) itemToCook);
			}
			else {
				Game.getInstance().display("You can't cook a " + itemToCook.toString() + "!");
			}
		}
		else {
			Game.getInstance().display("You must select a raw food");
		}
	}

}
