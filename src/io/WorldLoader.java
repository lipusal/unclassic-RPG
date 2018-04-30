package io;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.HashSet;
import java.util.Set;

import tiles.Tile;
import tiles.TileFactory;
import entities.Entity;

/**
 * Class used to load worlds from files. Each world is constructed by loading a <i>.structure</i>
 * plain-text file, containing tile type id's from which a tile grid is generated, and a <i>.entities</i>
 * file containing the world's serialized entities. This class is responsible for reading and interpreting
 * each file and returning the parsed data for the game to use.  
 */
public class WorldLoader {

	/**
	 * Loads the specified world's Tile structure from a plain-text <i>.structure</i> file.
	 * 
	 * @param worldName The name of the world to load, WITHOUT the ".structure" extension.
	 * @return An array of arrays of Tiles that represent the world as described by the .structure file.
	 * @throws IOException If an I/O error occurs while reading the appropriate file.
	 */
	public static Tile[][] loadWorldStructure(String worldName) throws IOException {
		int[] fileContents = formatStructureFile(worldName);
		int width = fileContents[0], height = fileContents[1];
		if(width <= 0 || height <= 0) {
			throw new InvalidWorldFormatException("Invalid width and/or height: " + width + "x" + height);
		}
		Tile[][] result = new Tile[width][height];
		for(int y = 0; y < height; y++) {
			for(int x = 0; x < width; x++) {
				int tileType = fileContents[2 + (width*y + x)];
				result[x][y] = TileFactory.createTile(tileType);
			}
		}
		return result;
	}
	
	/**
	 * Loads the entities for the specified world from the world's <i>.entities</i> file and places
	 * them in their corresponding tile. 
	 * 
	 * @param worldName The name of the world whose entities to load.
	 * @param worldTiles The tile structure of this world to place Entities into.
	 * @return A Set with the loaded entities.
	 * @throws IOException If an I/O error occurs while reading the appropriate file.
	 * @throws ClassNotFoundException If an invalid Object was saved in the file.
	 */
	public static Set<Entity> loadWorldEntities(String worldName, Tile[][] worldTiles)
			throws IOException, ClassNotFoundException {
		Set<Entity> result;
		ObjectInputStream objIn = new ObjectInputStream(new BufferedInputStream(new FileInputStream("assets/worlds/" + worldName + ".entities")));
		int size = objIn.readInt();
		result = new HashSet<Entity>(size);
		while(size-- > 0) {
			Entity e = (Entity) objIn.readObject();
			result.add(e);
			//Next line should never return false since entity placement is validated on world generation.
			worldTiles[(int) e.getX()][(int) e.getY()].setEntityOnTop(e);
		}
		objIn.close();
		return result;
	}

	/**
	 * Reads the <i>.structure</i> file of the specified world and splits the integers stored
	 * in it by space.
	 * 
	 * @param worldName The name of the world whose file to load.
	 * @return An array of integers containing all the plain-text integers saved in this file.
	 * @throws IOException If an I/O error occurs while reading from the appropriate file.
	 * @throws InvalidWorldFormatException If invalid data is read from the file.
	 */
	private static int[] formatStructureFile(String worldName) throws IOException, InvalidWorldFormatException {
		StringBuilder fileContents = new StringBuilder();
		BufferedReader reader = new BufferedReader(new FileReader("assets/worlds/" + worldName + ".structure"));
		String line = "";
		while((line = reader.readLine()) != null) {
			fileContents.append(line + "\n");
		}
		reader.close();
		//Split world data by spaces
		String[] data = fileContents.toString().split("\\s+");
		//Parse data to ints
		int[] result = new int[data.length];
		for(int i = 0; i < result.length; i++) {
			try {
				result[i] = Integer.parseInt(data[i]);
			}
			catch(NumberFormatException e) {
				throw new InvalidWorldFormatException("Invalid data read from structure file: " + data[i]);
			}
		}
		return result;
	}

}
