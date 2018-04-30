package items.equipment;

import java.util.HashMap;
import java.util.Map;

/**
 * This class represents a list of equipment type items that can be used in the game.
 */
public class EquipmentFactory {

	private static Map<String, Equipment> factory = new HashMap<String, Equipment>();
	static {
		//Weapons		
		factory.put("Bronze Sword", new Weapon("Bronze Sword","A bronze sword. May cut more than my fists.", 1, 0, 1.10f, 1));
		factory.put("Iron Sword", new Weapon("Iron Sword","Meh, my salami is hardher than this. But not sharper...", 2, 0, 1.10f, 5));
		factory.put("Steel Sword", new Weapon("Steel Sword","Iron sword plus carbon. carbon is hard, right?", 2, 0, 1.10f, 10));
		factory.put("White Sword", new Weapon("White Sword","examine text", 2, 0, 1.10f, 15));
		factory.put("Mithril Sword", new Weapon("Mithril Sword","Just like Frodo's chainmail, but with an edge.", 4, 0, 1.10f, 20));
		factory.put("Adamant Sword", new Weapon("Adamant Sword","LOOK! A Wolverine claw.", 6, 0, 1.10f, 30));
		factory.put("Runite Sword", new Weapon("Runite Sword","This sword looks old, but its incredibly sharp!", 10, 1, 1.10f, 40));
		factory.put("Dragon Sword", new Weapon("Dragon Sword","Made out of extract of essence of dragon claw. It MUST rock.", 10, 2, 1.10f, 60));

		factory.put("Bronze Axe", new Weapon("Bronze Axe","It goes through the bark... a bit.", 1, 0, 1.25f, 1));
		factory.put("Iron Axe", new Weapon("Iron Axe","It's heavier, for a mightier blow!", 1, 0, 1.25f, 5));
		factory.put("Steel Axe", new Weapon("Steel Axe","You can feel the fiber of the wood spliting.", 2, 0, 1.25f, 10));
		factory.put("White Axe", new Weapon("White Axe","Is this better? Or just paint?", 1, 0, 1, 1));
		factory.put("Mithril Axe", new Weapon("Mithril Axe","Mithril beats rock, rock beats bark, so bark beats mithril?", 7, 0, 1.25f, 20));
		factory.put("Adamant Axe", new Weapon("Adamant Axe","Harder, Better, Stronger, Faster.", 12, 0, 1.25f, 30));
		factory.put("Runite Axe", new Weapon("Runite Axe","I found this in some old runes.",15, 1, 1.25f, 40));
		factory.put("Dragon Axe", new Weapon("Dragon Axe","I'll use it to cut trees. Isn't this a bit to much?", 20, 1, 1.25f, 60));

		factory.put("Bronze Dagger", new Weapon("Bronze Dagger","Maybe i'll look for a Tramontina knife. Or spoon...", 1, 0, 2f, 1));
		factory.put("Iron Dagger", new Weapon("Iron Dagger","Can't i get a bigger version of this?", 1, 0, 2f, 5));
		factory.put("Steel Dagger", new Weapon("Steel Dagger","If you think that size doesn't matter...", 2, 0, 3f, 10));
		factory.put("White Dagger", new Weapon("White Dagger","Tiny and white. Insert joke here.", 1, 0, 2f, 15));
		factory.put("Mithril Dagger", new Weapon("Mithril Dagger","examine text", 5, 0, 3f, 20));
		factory.put("Adamant Dagger", new Weapon("Adamant Dagger","Child Wolverine?", 7, 0, 1.25f, 30));
		factory.put("Runite Dagger", new Weapon("Runite Dagger","It's blade is rusty, but not mashed. Strange.", 7, 0, 4f, 40));
		factory.put("Dragon Dagger", new Weapon("Dragon Dagger","A short Dragon Shortsword.", 7, 0, 6f, 60));

		factory.put("Bronze Mace", new Weapon("Bronze Mace","Nice potato smasher", 2, 0, 1f, 1));
		factory.put("Iron Mace", new Weapon("Iron Mace","Harder than the goblin's skull.", 3, 0, 1f, 5));
		factory.put("Steel Mace", new Weapon("Steel Mace","Pure silver. Which is maleable...", 5, 0, 1f, 10));
		factory.put("White Mace", new Weapon("White Mace","Feel the calcium wrath!!", 3, 0, 1f, 15));
		factory.put("Mithril Mace", new Weapon("Mithril Mace","If a mithril mace hits a mithril armour...", 15, 0, 1f, 20));
		factory.put("Adamant Mace", new Weapon("Adamant Mace","That dragon shield broke my adamant sword. Oh well.", 20, 0, 1f, 30));
		factory.put("Runite Mace", new Weapon("Runite Mace","There are glyphs inscribed on its head, facinating.", 25, 0, 1f, 40));
		factory.put("Dragon Mace", new Weapon("Dragon Mace","It strikes whit the force of a dragon claw. But blunter.", 30, 0, 1f, 60));

		factory.put("Bronze Shield", new Weapon("Bronze Shield","I might use this as a door for my tent!", 0, 3, 1f, 1));
		factory.put("Iron Shield", new Weapon("Iron Shield","I wonder why the goblin wasn't usin this... Better for me!", 0, 5, 1f, 5));
		factory.put("Steel Shield", new Weapon("Steel Shield","I thought it was a mirror, but not. Still looking awesome tho.", 0, 3, 1f, 10));
		factory.put("White Shield", new Weapon("White Shield","The paint chipped, the inside looks like irom. Dat scammer.", 0, 0, 1f, 15));
		factory.put("Mithril Shield", new Weapon("Mithril Shield","This was crafted by dwarf masters, wow!", 0, 15, 1f, 20));
		factory.put("Adamant Shield", new Weapon("Adamant Shield","There are diamonds incrusted here! Must be worth a fortune!", 0, 20, 1f, 30));
		factory.put("Runite Shield", new Weapon("Runite Shield","This shild shines whenever i'm near runes.", 0, 25, 1f, 40));
		factory.put("Dragon Shield", new Weapon("Dragon Shield","Hard as the dragon skin itself!", 0, 30, 1f, 60));
		
		
		//Head Armor		
		factory.put("Bronze Helmet", new Armor("Bronze Helmet","A bronze helm. Not much protection, but...", "head", 0, 1, 1, 1));
		factory.put("Iron Helmet", new Armor("Iron Helmet","This will cover my eyes, at least!", "head", 0, 2, 1, 5));
		factory.put("Steel Helmet", new Armor("Steel Helmet","Head pice of my shiny armour.", "head", 0, 0, 1, 10));
		factory.put("White Helmet", new Armor("White Helmet","Head pice of the White Knights armour.", "head", 0, 2, 0, 15));
		factory.put("Mithril Helmet", new Armor("Mithril Helmet","It's more of a crown than a Helmet. But this aura...", "head", 0, 0, 0, 20));
		factory.put("Adamant Helmet", new Armor("Adamant Helmet","This thing could fit a barbarian!", "head", 0, 0, 0, 30));
		factory.put("Runite Helmet", new Armor("Runite Helmet","My God, This helmet is imponent!", "head", 0, 0, 0, 40));
		factory.put("Dragon Helmet", new Armor("Dragon Helmet","The dragon horns are still sharp.", "head", 0, 0, 0, 60));
		
		
		//Torso Armor
		factory.put("Bronze Breastplates", new Armor("Bronze Breastplates","Two bronze plates tied together.", "torso", 0, 0, 0, 1));
		factory.put("Iron Breastplates", new Armor("Iron Breastplates","I think the front is part of an oven...", "torso", 0, 0, 0, 5));
		factory.put("Steel Breastplates", new Armor("Steel Breastplates","This is some decent armour, me like.", "torso", 0, 0, 0, 10));
		factory.put("White Breastplates", new Armor("White Breastplates","I'm still angry about that shield...", "torso", 0, 0, 0, 15));
		factory.put("Mithril Breastplates", new Armor("Mithril Breastplates","Like Frodos chainmail. But better. Or at least bigger.", "torso", 0, 0, 0, 20));
		factory.put("Adamant Breastplates", new Armor("Adamant Breastplates","I don't like this green, I wanted a pink one.", "torso", 0, 0, 0, 30));
		factory.put("Runite Breastplates", new Armor("Runite Breastplates","This armour is lighter than my cloth, but so good.", "torso", 0, 0, 0, 40));
		factory.put("Dragon Breastplates", new Armor("Dragon Breastplates","There is a scratch where the dragon's heart was.", "torso", 0, 0, 0, 60));

		factory.put("Linen Robe", new Armor("Linen Robe","Apprentice clothing. Cheap.", "torso", 0, 0, 0, 0));
		factory.put("Wool Robe", new Armor("Wool Robe","Good for counting.", "torso", 0, 0, 0, 0));
		factory.put("Cotton Robe", new Armor("Cotton Robe","This robe was used by a powerful wizard on graduation day.", "torso", 0, 0, 0, 0));
		factory.put("Silk Robe", new Armor("Silk Robe","So soft, yet quite powerful.", "torso", 0, 0, 0, 0));
		factory.put("Spidersilk Robe", new Armor("Spidersilk Robe","I won't say where this came from...", "torso", 0, 0, 0, 0));
		factory.put("Mageweave Robe", new Armor("Mageweave Robe","I can feel the power from far away", "torso", 0, 0, 0, 0));
		factory.put("Embersilk Robe", new Armor("Embersilk Robe","This silk can't be burned. Also generates a nice light.", "torso", 0, 0, 0, 0));
		factory.put("Crystalweave Robe", new Armor("Crystalweave Robe","Transparent. Good for a surprise attack.", "torso", 0, 0, 0, 0));
		
		
		//Legs Armor		
		factory.put("Bronze Platelegs", new Armor("Bronze Platelegs","Haha they look like piled cans.", "legs", 0, 0, 0, 1));
		factory.put("Iron Platelegs", new Armor("Iron Platelegs","This will stop a blow from a sword.", "legs", 0, 0, 0, 5));
		factory.put("Steel Platelegs", new Armor("Steel Platelegs","So shiny and protective.", "legs", 0, 0, 0, 10));
		factory.put("White Platelegs", new Armor("White Platelegs","It looks a bit rusted on the knee.", "legs", 0, 0, 0, 15));
		factory.put("Mithril Platelegs", new Armor("Mithril Platelegs","The dwarves made a wonderful job here.", "legs", 0, 0, 0, 20));
		factory.put("Adamant Platelegs", new Armor("Adamant Platelegs","looks like green dragon, but feels heavier and softer.", "legs", 0, 0, 0, 30));
		factory.put("Runite Platelegs", new Armor("Runite Platelegs","The marks turn into fire when I equip the braceplate too.", "legs", 0, 0, 0, 40));
		factory.put("Dragon Platelegs", new Armor("Dragon Platelegs","The skales give me so much movility!", "legs", 0, 0, 0, 60));
		
		factory.put("Linen Robe Bottom", new Armor("Linen Robe Bottom","Apprentice clothing. Cheap.", "legs", 0, 0, 0, 0));
		factory.put("Wool Robe Bottom", new Armor("Wool Robe Bottom","Nice pijama.", "legs", 0, 0, 0, 0));
		factory.put("Cotton Robe Bottom", new Armor("Cotton Robe Bottom","SOMEONE WIPED WITH THESE?", "legs", 0, 0, 0, 0));
		factory.put("Silk Robe Bottom", new Armor("Silk Robe Bottom","Do not use without underwear. Power overwelming...", "legs", 0, 0, 0, 0));
		factory.put("Spidersilk Robe Bottom", new Armor("Spidersilk Robe Bottom","Allright, they came out of a spider but...", "legs", 0, 0, 0, 0));
		factory.put("Mageweave Robe Bottom", new Armor("Mageweave Robe Bottom","I can feel the power from far away", "legs", 0, 0, 0, 0));
		factory.put("Embersilk Robe Bottom", new Armor("Embersilk Robe Bottom","This silk can't be burned. Also generates a nice light.", "legs", 0, 0, 0, 0));
		factory.put("Crystalweave Robe Bottom", new Armor("Crystalweave Robe Bottom","This powers can corrup someone easily. Like a crystal...", "legs", 0, 0, 0, 0));
		
		
		//Arms Armor		
		factory.put("Bronze Gauntlets", new Armor("Bronze Gauntlets","I don't think these are safe.", "arms", 0, 0, 0, 1));
		factory.put("Iron Gauntlets", new Armor("Iron Gauntlets","My arms won't get cut this way.", "arms", 0, 0, 0, 5));
		factory.put("Steel Gauntlets", new Armor("Steel Gauntlets","These are quite heavy", "arms", 0, 0, 0, 10));
		factory.put("White Gauntlets", new Armor("White Gauntlets","My god, these things are weigths. Time to exercise.", "arms", 0, 0, 0, 15));
		factory.put("Mithril Gauntlets", new Armor("Mithril Gauntlets","This is better. Lighter.", "arms", 0, 0, 0, 20));
		factory.put("Adamant Gauntlets", new Armor("Adamant Gauntlets","This can deflect any arrow!", "arms", 0, 0, 0, 30));
		factory.put("Runite Gauntlets", new Armor("Runite Gauntlets","Two idols are printed in this.", "arms", 0, 0, 0, 40));
		factory.put("Dragon Gauntlets", new Armor("Dragon Gauntlets","I feel these boost my attack speed.", "arms", 0, 0, 0, 60));
		
		
		//Feet Armor		
		factory.put("Bronze Boots", new Armor("Bronze Boots","This seemed like a good idea for training.", "feet", 0, 0, 0, 1));
		factory.put("Iron Boots", new Armor("Iron Boots","Haha now i do really want to kick that chiken.", "feet", 0, 0, 0, 5));
		factory.put("Steel Boots", new Armor("Steel Boots","When you can lift them, they are quite effective.", "feet", 0, 0, 0, 10));
		factory.put("White Boots", new Armor("White Boots","The sole is made of iron!", "feet", 0, 0, 0, 15));
		factory.put("Mithril Boots", new Armor("Mithril Boots","It feels like a kiss on my feet.", "feet", 0, 0, 0, 20));
		factory.put("Adamant Boots", new Armor("Adamant Boots","Size 53.", "feet", 0, 0, 0, 30));
		factory.put("Runite Boots", new Armor("Runite Boots","The words written may be helpful: 'xolow'", "feet", 0, 0, 0, 40));
		factory.put("Dragon Boots", new Armor("Dragon Boots","I HAVE PAWS!!!!", "feet", 0, 0, 0, 60));
		
		//easter eggs
		factory.put("Corki Mask", new Armor("Corki Mask","Such corki much wow", "head", 20,20,20, 1));
		
		
	}
	
	/**
	 * This is the only method for accessing the contents of the factory.
	 * Each time an equipment is requested, the same instance of the
	 * item is returned.
	 * 
	 * @param name The name of the item you want to receive.
	 * @return The instance of the corresponding item.
	 * @throws IllegalArgumentException When you try to get an item that the factory doesn't contain.
	 */
	public static Equipment makeEquipment(String name) throws IllegalArgumentException {
		Equipment result = factory.get(name);
		if(result == null) {
			throw new IllegalArgumentException("Element \"" + name + "\" doesn't exist.");
		}
		return result;
	}

}
