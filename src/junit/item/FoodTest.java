package junit.item;

import static org.junit.Assert.assertEquals;
import io.GameCreationMode;
import items.Item;
import items.consumable.ConsumableItemFactory;

import java.io.File;

import org.junit.BeforeClass;
import org.junit.Test;

import controller.Game;

public class FoodTest {

	private static Game game;
	private static Item rawFoodToBeTested;
	private static Item cookedFoodToBeTested;
	private static Item burntFoodToBeTested;
	
	
	@BeforeClass
	public static void setUp() {
		game = Game.getInstance();
		String playerPath = "saves" + File.separator + "lipusal"  + ".player";
		game.init(playerPath, "world00", GameCreationMode.CREATE_NEW_GAME);
		rawFoodToBeTested = ConsumableItemFactory.makeItem("Raw Lobster");
		cookedFoodToBeTested = ConsumableItemFactory.makeItem("Lobster");
		burntFoodToBeTested = ConsumableItemFactory.makeItem("Burnt Lobster");

	}

	@Test
	public void useTest() {
		assertEquals(0,rawFoodToBeTested.use());
		assertEquals(0,burntFoodToBeTested.use());
		assertEquals(1,cookedFoodToBeTested.use());
	}

}
