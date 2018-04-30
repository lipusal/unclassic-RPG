package entities;

import items.consumable.ConsumableItemFactory;

import java.util.HashMap;
import java.util.Map;

import utils.Randomizer;
import controller.Game;
import entities.creatures.player.Player;

public class FishingSpot extends Entity implements Interactable {
	
	private static final long serialVersionUID = 2488244145475778958L;
	String fish;
	public static Map<String, Integer> lvlRequirement = new HashMap<String, Integer>();
	
	static{
		lvlRequirement.put("Raw Lobster", 1);
		lvlRequirement.put("Raw Salmon", 5);
		lvlRequirement.put("Raw Shark", 10);
		lvlRequirement.put("Raw Shrimp", 20);
		lvlRequirement.put("Raw Swordfish", 30);
		lvlRequirement.put("Raw Trout", 45);
	}
	
	public FishingSpot(float x, float y, String fish){
		super("Fishing Spot", "You can find " + fish.substring(4) + " here.", x, y);
		this.fish = fish;
	}
	
	
	
	
	private void fish() {
		Game g = Game.getInstance();
		Player p = g.getPlayer();
		if(lvlRequirement.get(fish) > p.getSkillSet().getSkill("Fishing").getLevel()) {
			g.display("You don't have the post to fish here, practice more");
		}
		else {
			if(Randomizer.getRandomNumber() <= 0.5){
				g.putInPlayerInventory(ConsumableItemFactory.makeItem(fish), 1);
				g.gainXP("Fishing", lvlRequirement.get(fish)*10);
				g.updateInventoryTable();
				g.display("You catch a " + fish);
			}
			else {
				g.display("No luck friend");
			}
		}
	}

	@Override
	public void update() {
		//Do nothing
	}
	
	public void respond(){
		fish();
	}

}
