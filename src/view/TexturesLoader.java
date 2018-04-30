package view;

import io.ImageLoader;

import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;

/**
 * Utility class used to hold all the textures that can be drawn in the game. In general, every
 * entity's String representation (i.e. entity name or tile type) is the same used here to map
 * them with their texture.
 */
public class TexturesLoader {

	private static Map<String, BufferedImage> textures = new HashMap<String, BufferedImage>();
	static {
		/* ***********************************************
		 * Tiles
		 * **********************************************/
		textures.put("water", ImageLoader.loadImage("assets/textures/Tiles/Water2.png"));
		textures.put("grass", ImageLoader.loadImage("assets/textures/Tiles/Grass2.png"));
		textures.put("dirt", ImageLoader.loadImage("assets/textures/Tiles/Dirt2.png"));
		textures.put("dungeon", ImageLoader.loadImage("assets/textures/Tiles/DungeonFloor.png"));
		textures.put("mountain", ImageLoader.loadImage("assets/textures/Tiles/Mountain2.png"));
		textures.put("lobster", ImageLoader.loadImage("assets/textures/Tiles/LobsterWater.png"));
		textures.put("salmon", ImageLoader.loadImage("assets/textures/Tiles/SalmonWater.png"));
		textures.put("shark", ImageLoader.loadImage("assets/textures/Tiles/SharkWater.png"));
		textures.put("shrimp", ImageLoader.loadImage("assets/textures/Tiles/ShrimpWater.png"));
		textures.put("swordfish", ImageLoader.loadImage("assets/textures/Tiles/SwordFishWater.png"));
		textures.put("trout", ImageLoader.loadImage("assets/textures/Tiles/TroutWater.png"));
		textures.put("Fishing Spot", ImageLoader.loadImage("assets/textures/Tiles/SharkWater.png"));
		textures.put("World Changer", ImageLoader.loadImage("assets/textures/Tiles/WorldChanger.png"));
		
		/* ***********************************************
		 * Entities
		 * **********************************************/
		textures.put("Debug Player", ImageLoader.loadImage("assets/textures/Mobs/PlayerDebug.png"));
		textures.put("Goblin", ImageLoader.loadImage("assets/textures/Mobs/Goblin.png"));
		textures.put("Giant Rat", ImageLoader.loadImage("assets/textures/Mobs/GiantRat2.png"));
		textures.put("Giant Spider", ImageLoader.loadImage("assets/textures/Mobs/GiantSpider.png"));
		textures.put("Hell Assassin", ImageLoader.loadImage("assets/textures/Mobs/HellAssassin.png"));
		textures.put("Hell Knight", ImageLoader.loadImage("assets/textures/Mobs/HellKnight.png"));
		textures.put("Skeleton", ImageLoader.loadImage("assets/textures/Mobs/Skeleton.png"));
		textures.put("Molten Corki", ImageLoader.loadImage("assets/textures/Mobs/Molten Corki.png"));
		textures.put("FishingSpot", ImageLoader.loadImage("assets/textures/Mobs/LobsterSpot.png"));
		textures.put("Tree", ImageLoader.loadImage("assets/textures/Mobs/Tree.png"));
		textures.put("Fire", ImageLoader.loadImage("assets/textures/Mobs/Fire.png"));
		
		/* ***********************************************
		 * Equipment
		 * **********************************************/
		//Swords
		textures.put("adamant sword", ImageLoader.loadImage("assets/textures/Items/Armory/Weapons/Swords/AdamantSword.png"));
		textures.put("bronze sword", ImageLoader.loadImage("assets/textures/Items/Armory/Weapons/Swords/BronzeSword.png"));
		textures.put("dragon sword", ImageLoader.loadImage("assets/textures/Items/Armory/Weapons/Swords/DragonSword.png"));
		textures.put("iron sword", ImageLoader.loadImage("assets/textures/Items/Armory/Weapons/Swords/IronSword.png"));
		textures.put("mithril sword", ImageLoader.loadImage("assets/textures/Items/Armory/Weapons/Swords/MithrilSword.png"));
		textures.put("runite sword", ImageLoader.loadImage("assets/textures/Items/Armory/Weapons/Swords/RuniteSword.png"));
		textures.put("steel sword", ImageLoader.loadImage("assets/textures/Items/Armory/Weapons/Swords/SteelSword.png"));
		textures.put("white sword", ImageLoader.loadImage("assets/textures/Items/Armory/Weapons/Swords/WhiteSword.png"));
		//Helmets
		textures.put("adamant helmet", ImageLoader.loadImage("assets/textures/Items/Armory/Armor/Helmets/AdamantHelmet.png"));
		textures.put("bronze helmet", ImageLoader.loadImage("assets/textures/Items/Armory/Armor/Helmets/BronzeHelmet.png"));
		textures.put("iron helmet", ImageLoader.loadImage("assets/textures/Items/Armory/Armor/Helmets/IronHelmet.png"));
		textures.put("mithril helmet", ImageLoader.loadImage("assets/textures/Items/Armory/Armor/Helmets/MithrilHelmet.png"));
		textures.put("runite helmet", ImageLoader.loadImage("assets/textures/Items/Armory/Armor/Helmets/RuniteHelmet.png"));
		textures.put("steel helmet", ImageLoader.loadImage("assets/textures/Items/Armory/Armor/Helmets/SteelHelmet.png"));
		textures.put("white helmet", ImageLoader.loadImage("assets/textures/Items/Armory/Armor/Helmets/WhiteHelmet.png"));
	}
	
	/**
	 * Gets the texture corresponding to the given entity name, if it exists.
	 * @param entityName The name of the entity for which to get a texture.
	 * @return The Entity's texture as a {@link BufferedImage} which can be
	 * drawn onto the screen by a renderer, or <code>null</code> if there is
	 * no skin for the specified entity. 
	 */
	public static BufferedImage getTextureFor(String entityName) {
		return textures.get(entityName);
	}
	
	/**
	 * Since the textures map is created statically (and thus before the Game is running 
	 * and has a player name set), the Game needs to call this method in order to properly
	 * draw the player.
	 * @param playerName The name of the player.
	 */
	public static void setPlayerName(String playerName) {
		if(textures.containsKey(playerName)) {
			return;
		}
		textures.put(playerName, ImageLoader.loadImage("assets/textures/Mobs/PlayerUp.png"));
	}
}
