package items.consumable;

import items.Item;

import java.util.HashMap;
import java.util.Map;

public class ConsumableItemFactory {
	
	public static Map<String, Item> factory = new HashMap<String, Item>();
	
	static {
		//Logs
		factory.put("Common Log", new Log("Common Log", 10, 1));
		factory.put("Oak Log", new Log("Oak Log", 20, 10));
		factory.put("Willow Log", new Log("Willow Log", 30, 20));	
		
		//Raw food
		factory.put("Raw Lobster", new Food("Raw Lobster", "A wild Krabby appeared.", CookState.UNCOOKED, 3, 1));
		factory.put("Raw Salmon", new Food("Raw Salmon", "Against the current.", CookState.UNCOOKED, 5, 5));
		factory.put("Raw Shark", new Food("Raw Shark", "It's a dangerous animal.", CookState.UNCOOKED, 7, 10));
		factory.put("Raw Shrimp", new Food("Raw Shrimp", "Tiny bites, loads of protein!", CookState.UNCOOKED, 10,20));
		factory.put("Raw Swordfish", new Food("Raw Swordfish", "It's not a weapon, unless it's Burnt?", CookState.UNCOOKED, 15, 30));
		factory.put("Raw Trout", new Food("Raw Trout", "Looks funny, heals a lot." ,CookState.UNCOOKED, 30, 45));
		
		//Cooked, edible food
		factory.put("Lobster", new Food("Lobster", "Taste like chicken. Heals 3 HP", CookState.COOKED, 3, 1));
		factory.put("Salmon", new Food("Salmon", "Quite expensive. 5 HP in a fish.", CookState.COOKED, 5, 5));
		factory.put("Shark", new Food("Shark", "It was a dangerous animal. Now it's 7 HP.", CookState.COOKED, 7, 10));
		factory.put("Shrimp", new Food("Shrimp", "Tiny bites, loads of protein! 10 HP.", CookState.COOKED, 10,20));
		factory.put("Swordfish", new Food("Swordfish", "It's not a weapon. But recovers 15 HP", CookState.COOKED, 15, 30));
		factory.put("Trout", new Food("Trout", "Recovers 30 HP." ,CookState.COOKED, 30, 45));
		
		//Burnt food
		factory.put("Burnt Lobster", new Food("Burnt Lobster", "A black Krabby appeared.", CookState.BURNT, 3, 1));
		factory.put("Burnt Salmon", new Food("Burnt Salmon", "Current... Too... Strong...", CookState.BURNT, 5, 5));
		factory.put("Burnt Shark", new Food("Burnt Shark", "It's a dangerous animal.", CookState.BURNT, 7, 10));
		factory.put("Burnt Shrimp", new Food("Burnt Shrimp", "Burnt protein...", CookState.BURNT, 10,20));
		factory.put("Burnt Swordfish", new Food("Burnt Swordfish", "Still not a weapon.", CookState.BURNT, 15, 30));
		factory.put("Burnt Trout", new Food("Burnt Trout", "It's not funny anymore." ,CookState.BURNT, 30, 45));	
	}
	
	
	public static Item makeItem(String name) {
		Item result = factory.get(name);
		if(result == null) {
			throw new IllegalArgumentException("Item \"" + name + "\" doesn't exixt");
		}
		return result;
	}
}
