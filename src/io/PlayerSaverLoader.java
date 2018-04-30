package io;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import entities.creatures.player.Player;

/**
 * Class created for loading, saving and deleting players. Since worlds are always the same,
 * the only thing that needs to be saved is the player. This class can load, save and delete
 * <i>.player</i> files from the file system. Each <i>.player</i> file contains the save date.
 */
public class PlayerSaverLoader {

	private Player player;
	private String filePath, saveTime;
	
	/**
	 * Loads a saved Player with the given name from the file system.
	 * @param playerFilePath The name of the Player to load. Should match
	 * the save file name.
	 * @param loadOrCreate whether the game should be loaded from a file
	 * or a new player file should be created.
	 * @throws ClassNotFoundException If an invalid Object was serialized
	 * in the save file.
	 * @throws IOException If an I/O error occurs while reading from
	 * the Stream.
	 */
	public PlayerSaverLoader(String playerFilePath, GameCreationMode loadOrCreate) throws ClassNotFoundException, IOException {
		this.filePath = playerFilePath;
		if(loadOrCreate == GameCreationMode.CREATE_NEW_GAME) {
			System.out.println("Creating new game.");
			String playerName = filePath.substring(filePath.lastIndexOf(File.separator)+1);	//Get the "[name].player" part from the file path
			playerName = playerName.substring(0, playerName.lastIndexOf(".player"));		//Take out the ".player" part
			this.player = new Player(playerName);
			this.saveTime = now();
			savePlayer();
		}
		else {
			System.out.println("Loading game.");
			loadPlayer();
		}
	}
	
	public Player getPlayer() {
		return player;		
	}
	public String getSaveTime() {
		return saveTime;
	}
	
	public void deletePlayerFile() {
		File f = new File(filePath);
		f.delete();
	}
	
	/**
	 * Loads a Player with the given name from a file and save the information
	 * within the class, which can later be accessed via the public getters.
	 * @throws IOException If an I/O error occurs while reading from the Stream.
	 * @throws ClassNotFoundException If an invalid Object was saved in the file.
	 */
	private void loadPlayer() throws IOException, ClassNotFoundException {
		ObjectInputStream in = new ObjectInputStream(new BufferedInputStream(new FileInputStream(filePath)));
		this.player = (Player) in.readObject();
		this.saveTime = in.readUTF();
		in.close();
		player.moveTo(Player.DEFAULT_PLAYER_STARTING_X, Player.DEFAULT_PLAYER_STARTING_Y);
	}
	
	/**
	 * Saves the already created Player, along with the save date, to the path
	 * provided upon construction. If the player file already exists, it will
	 * be overwritten.
	 * @throws IOException If an I/O error occurs while writing to the Stream.
	 * 
	 */
	public void savePlayer() throws IOException {
		ObjectOutputStream out = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(filePath)));
		out.writeObject(player);
		out.writeUTF(saveTime);
		out.close();
	}
	
	/**
	 * Gets a String representation of the current time in MMM dd, YYYY HH:mm:ss
	 * format.
	 * @return A String representing the current time.
	 */
	private String now() {
		Date nowDate = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("MMM d, YYYY HH:mm:ss", new Locale("en"));
		//								English localization to capitalize months ^ ^ ^
		return formatter.format(nowDate);
	}
}
