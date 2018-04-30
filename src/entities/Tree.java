package entities;

import items.consumable.ConsumableItemFactory;

import java.util.HashMap;
import java.util.Map;

import utils.Randomizer;
import controller.Game;
import entities.creatures.player.Player;

public class Tree extends Entity implements Interactable {

	private static final long serialVersionUID = 7823805053107175434L;
	String log;
	public static Map<String, Integer> lvlRequirement = new HashMap<String, Integer>();
	
	static{
		lvlRequirement.put("Common Log", 1);
		lvlRequirement.put("Oak Log", 5);
		lvlRequirement.put("Willow Log", 10);
	}
	
	public Tree(float x, float y, String log){
		super("Tree", "You can chop " + log.substring(1) + " here.", x, y);
		this.log = log;
	}
	
	public void respond() {
		chop();
	}
	
	/*
	 * As a response to a player's interaction, the player chops the tree if he has the requiered level and receives a log 
	 */
	private void chop() {
		Player player = Game.getInstance().getPlayer();
		if(lvlRequirement.get(log) > player.getSkillSet().getSkill("Woodcutting").getLevel()) {
			Game.getInstance().display("You don't have the post to chop this, practice more");
		}
		else {
			if(Randomizer.getRandomNumber() <= 0.5){
				Game.getInstance().putInPlayerInventory(ConsumableItemFactory.makeItem(log), 1);	
				Game.getInstance().gainXP("Woodcutting", lvlRequirement.get(log)*10);
				Game.getInstance().updateInventoryTable();
				Game.getInstance().display("You chopped a " + log);
			}
			else {
				Game.getInstance().display("No luck friend");
			}
		}
	}

	@Override
	public void update() {
		//Do nothing
	}

}