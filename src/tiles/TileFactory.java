package tiles;

import java.util.HashMap;
import java.util.Map;

/**
 * Factory-type class for creating different types of Tiles. Used when worlds are
 * being (re)built from their .structure file. 
 */
public class TileFactory {
	
	private static Map<Integer, String> tileTypes = new HashMap<Integer, String>();
	static {
		tileTypes.put(0, "water");
		tileTypes.put(1, "grass");
		tileTypes.put(2, "dirt");
		tileTypes.put(3, "rock");
		tileTypes.put(4, "dungeon");
		tileTypes.put(5, "mountain");
		tileTypes.put(6, "lobster");
		tileTypes.put(7, "salmon");
		tileTypes.put(8, "shark");
		tileTypes.put(9, "shrimp");
		tileTypes.put(10, "swordfish");
		tileTypes.put(11, "trout");
	}
	
	/**
	 * Creates a new Tile with the type corresponding to the specified ID.
	 * @param id The tile ID. These are read from the .structure files and
	 * with this method the corresponding tiles are created.
	 * @return A new Tile with type corresponding to the given id.
	 */
	public static Tile createTile(int id) {
		if(id > 0 && id <= 4) {
			return new Tile(tileTypes.get(id), false);	//Tiles with IDs 0 through 4 are non-solid
		}
		else {
			return new Tile(tileTypes.get(id), true);	//Other tile IDs are solid
		}
	}
}
