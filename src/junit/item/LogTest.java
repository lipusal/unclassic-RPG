package junit.item;

import static org.junit.Assert.assertEquals;
import io.GameCreationMode;
import items.Item;
import items.consumable.ConsumableItemFactory;

import java.io.File;

import org.junit.Before;
import org.junit.Test;

import controller.Game;

public class LogTest {
	
	private static Game game;
	private static Item logToBeTested;
	

	@Before
	public void setUp() {
		game = Game.getInstance();
		String playerPath = "saves" + File.separator + "lipusal"  + ".player";
		game.init(playerPath, "world00", GameCreationMode.CREATE_NEW_GAME);
		logToBeTested = ConsumableItemFactory.makeItem("Common Log");
		//Adds a considerable amount of logs
		for (int i = 0 ; i < 10 ; i++) {
			game.putInPlayerInventory(logToBeTested, 1);
		}
	}

	@Test
	public void useTest() {
		assertEquals(0,ConsumableItemFactory.makeItem("Willow Log").use()); //Makes a test to the skill condition
		assertEquals(10,logToBeTested.use()); // 10 because that's the value of a private constant in the Log class
	}

	
	
	
	
	
}
