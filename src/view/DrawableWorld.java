package view;

import java.util.HashSet;
import java.util.Set;

import tiles.Tile;
import tiles.World;
import entities.Entity;

/**
 * Class used to graphically represent the current world from the model. Just like a world contains
 * tiles and entities, the drawable world contains drawable tiles and drawable entities. When the
 * drawable world is created, it reflects the state of the world specified in its constructor at
 * that time. If the world changes later (i.e. entities spawn or die), then the drawable world must
 * be updated as well to reflect the changes.
 */
public class DrawableWorld {
	private DrawableTile[][] drawableTiles;
	private Set<DrawableEntity> drawableEntities;
	
	public DrawableWorld(World modelWorld) {
		drawableEntities = generateDrawableEntitiesFrom(modelWorld.getEntities()); 
		drawableTiles = generateDrawableTilesFrom(modelWorld.getStructure());
	}
	
	/**
	 * Creates a set of drawable tiles form a set of model tiles. 
	 * 
	 * @param structure The set from which to generate the new set of drawable tiles.
	 * @return The generated drawable tile set.
	 */
	private DrawableTile[][] generateDrawableTilesFrom(Tile[][] structure) {
		int width = structure[0].length, height = structure.length;
		DrawableTile[][]  result = new DrawableTile[width][height];
		for(int y = 0; y < height; y++) {
			for(int x = 0; x < width; x++) {
				result[x][y] = new DrawableTile(structure[x][y], x, y);
			}
		}
		return result;
	}

	/**
	 * Creates a set of drawable entities form a set of model entities. 
	 * 
	 * @param modelEntities The set from which to generate the new set of drawable entities.
	 * @return The generated drawable entity set.
	 */
	private Set<DrawableEntity> generateDrawableEntitiesFrom(Set<Entity> modelEntities) {
		Set<DrawableEntity> result = new HashSet<DrawableEntity>(modelEntities.size());
		for(Entity e : modelEntities) {
			result.add(new DrawableEntity(e));
		}
		return result;
	}
	
	public DrawableTile[][] getTiles() {
		return drawableTiles;
	}
	
	public Set<DrawableEntity> getEntities() {
		return drawableEntities;
	}
	
	public void addEntity(Entity e) {
		if(e == null) {
			return;
		}
		drawableEntities.add(new DrawableEntity(e));
	}
	
	public boolean removeEntity(Entity e) {
		if(e == null) {
			return false;
		}
		return drawableEntities.remove(new DrawableEntity(e));
	}
}
