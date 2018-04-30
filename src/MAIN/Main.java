package MAIN;

import io.GameCreationMode;
import view.MainMenu;
import controller.Game;

/**
 * Main class. Opens up the main menu and starts the game as appropriate.
 */
public class Main {

	public static void main(String[] args) {
		MainMenu menu = new MainMenu();
		while(menu.getUserChosenFilePath() == null || menu.loadOrCreatePlayer() == null) {
			System.out.println("Waiting.");
		}
		String playerFilePath = menu.getUserChosenFilePath();
		GameCreationMode loadOrCreate = menu.loadOrCreatePlayer();
		System.out.println(loadOrCreate + " with " + playerFilePath);
		menu.dispose();
		Game.getInstance().init(playerFilePath, Game.DEFAULT_STARTING_WORLD, loadOrCreate);
		Game.getInstance().run();
	}

}
