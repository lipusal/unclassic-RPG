package utils;

import io.WorldLoader;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.HashSet;
import java.util.Set;

import tiles.Tile;
import tiles.World;
import entities.Entity;
import entities.FishingSpot;
import entities.Tree;
import entities.WorldChanger;
import entities.creatures.mobs.Goblin;
import entities.creatures.mobs.Rat;
import entities.creatures.mobs.Skeleton;

/**
 * Utility class made for creating <i>.entities</i> files for worlds. The desired
 * entities for the world are initialized here in a Set and they are serialized.
 * When the world is accessed in the future, the entities will always spawn in the
 * sane place in the same initial state. Correct placement of entities is validated
 * here with exceptions, since this is not part of the actual game.
 */
public class WorldGenerator {
	
	public static void main(String[] args) {
		String WORLD_NAME;
		
		Set<Entity> entities = new HashSet<Entity>();
		/*
		//World 00
		WORLD_NAME = "world00";
		entities.add(new Tree(12,6,"Common Log"));
		entities.add(new Tree(17, 2, "Oak Log"));
		entities.add(new Skeleton(15, 10));
		entities.add(new Goblin(3, 3));
		entities.add(new Goblin(4, 5));
		entities.add(new Goblin(6, 8));
		entities.add(new Rat(8, 14));
		entities.add(new FishingSpot(1, 8, "Raw Lobster"));
		entities.add(new WorldChanger(19, 0, "world01", 6, 6));
		entities.add(new FishingSpot(4, 14, "Raw Salmon"));
		entities.add(new FishingSpot(4, 13, "Raw Salmon"));
		*/
		
		//World 01
		WORLD_NAME = "world01";
		entities.add(new Skeleton(1, 3));
		entities.add(new Goblin(15, 15));
		entities.add(new Rat(18, 19));
		entities.add(new WorldChanger(6, 5, "world00", 19, 1));
		entities.add(new WorldChanger(7, 5, "world00", 19, 1));
		entities.add(new WorldChanger(8, 5, "world00", 19, 1));
		entities.add(new WorldChanger(19, 9, "world02", 1, 9));
		entities.add(new WorldChanger(19, 10, "world02", 1, 10));
		entities.add(new Tree(12, 17, "Willow Log"));
		entities.add(new Tree(8, 14, "Willow Log"));
		entities.add(new FishingSpot(0,12, "Raw Shark"));
		
		/*
		//World 02
		WORLD_NAME = "world02";
		entities.add(new WorldChanger(0, 9, "world01", 18, 9));
		entities.add(new WorldChanger(0, 10, "world01", 18, 10));
		entities.add(new MoltenCorki(10,12));
		*/
		
		Tile[][] tiles = null;
		try {
			tiles = WorldLoader.loadWorldStructure(WORLD_NAME);
		}
		catch (IOException e) {
			System.err.println("Couldn't load world structure for " + WORLD_NAME + ": " + e.getMessage());
			e.printStackTrace();
		}
		
		System.out.println("Validating Entity placement...");
		for(Entity e : entities) {
			try {
				if(e.getX() < 0 || e.getX() >= tiles[0].length || e.getY() < 0 || e.getY() >= tiles.length) {
					throw new IndexOutOfBoundsException("Coordinates outside of map range for " + e.toString() + ": " + e.toString());
				}
				try {
					tiles[(int) e.getX()][(int) e.getY()].setEntityOnTop(e);
				}
				catch(IllegalStateException e1) {
					throw new IllegalStateException("Can't place " + e + " on an occupied tile (" + e.getX() + ", " + e.getY() + ")");
				}
			}
			catch (IllegalStateException|IndexOutOfBoundsException e2) {
				System.err.println("Entity placement validation failed: " + e2.getMessage());
				e2.printStackTrace();
				System.exit(1);
			}
		}
		System.out.println("Validation complete.");
		
		System.out.println("Generating .entities file...");
		try {
			saveEntitiesToFile(WORLD_NAME, entities);
		}
		catch (IOException e) {
			System.err.println("Couldn't generate .entities file: " + e.getMessage());
			e.printStackTrace();
			System.exit(1);
		}
		System.out.println("Successfully generated " + WORLD_NAME + "!");
		
		System.out.println("Attempting to reconstruct World now...");
		World reconstructedWorld = new World(WORLD_NAME);
		System.out.println("Reconstructed world:");
		System.out.println(reconstructedWorld.toString());
		
		
		System.out.println("\nExamining this World's Entities:");
		for(Entity e : entities) {
			System.out.println(e.examine());
		}
	}
	
	/**
	 * Writes all the Entities as they are to normally spawn in the world to a file.
	 * @param worldName The name of the world to which the Entities belong.
	 * @param entities The Entities set to save.
	 * @throws IOException if an I/O error occurs while attempting to read the file
	 */
	private static void saveEntitiesToFile(String worldName, Set<Entity> entities) throws IOException {
		
		/*if(entities.isEmpty()) {
			throw new IllegalArgumentException("No entities to save to file!");
		}*/
		if(worldName.length() == 0) {
			throw new IllegalArgumentException("Empty world name provided.");
		}
		ObjectOutputStream out = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream("assets/worlds/" + worldName + ".entities")));
		out.writeInt(entities.size());
		for(Entity e : entities)
		{
			out.writeObject(e);
		}
		out.close();
	}
}
