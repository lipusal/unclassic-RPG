package junit;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import io.GameCreationMode;
import items.Inventory;
import items.Item;
import items.consumable.ConsumableItemFactory;
import items.equipment.EquipmentFactory;

import java.io.File;

import org.junit.BeforeClass;
import org.junit.Test;

import controller.Game;
import entities.Fire;
import entities.creatures.player.Player;

public class FireTest {

	private static Game game;
	private static Player player;
	private static Inventory inventory;
	private static Item rawFoodToBeTested;
	private static Item cookedFoodToBeTested;
	private static Item burntFoodToBeTested;
	private static Item logToBeTested;
	private static Item EquipmentToBeTested;
	private static Fire fireToTest;
	
	@BeforeClass
	public static void setUp() {
		game = Game.getInstance();
		String playerPath = "saves" + File.separator + "testPlayer.player";
		game.init(playerPath, "world00", GameCreationMode.CREATE_NEW_GAME);
		player = game.getPlayer();
		inventory = player.getInventory();
		rawFoodToBeTested = ConsumableItemFactory.makeItem("Raw Lobster");
		cookedFoodToBeTested = ConsumableItemFactory.makeItem("Lobster");
		burntFoodToBeTested = ConsumableItemFactory.makeItem("Burnt Lobster");
		logToBeTested = ConsumableItemFactory.makeItem("Common Log");
		EquipmentToBeTested = EquipmentFactory.makeEquipment("Adamant Sword");
		fireToTest = new Fire(60, 0, 0);
		inventory.put(rawFoodToBeTested);
		inventory.put(cookedFoodToBeTested);
		inventory.put(burntFoodToBeTested);
		inventory.put(logToBeTested);
		inventory.put(EquipmentToBeTested);
		
	}

	/**
	 * The respond method of fire calls a private method that depends on random numbers,
	 * so the test may fail.
	 */
	@Test
	public void testRespond() {
		//Testing raw food
		game.setSelectedItem(rawFoodToBeTested.getName());
		fireToTest.respond();
		assertFalse(inventory.contains(rawFoodToBeTested.getName())); //Tests whether the raw food is still in the inventory
		assertTrue(inventory.contains(cookedFoodToBeTested.getName())); //Tests whether the cooked food was added to the inventory
		//Testing cooked food
		game.setSelectedItem(cookedFoodToBeTested.getName());
		fireToTest.respond();
		assertTrue(inventory.contains(cookedFoodToBeTested.getName())); //Tests whether the cooked food is still in the inventory
		//Testing cooked food
		game.setSelectedItem(burntFoodToBeTested.getName());
		fireToTest.respond();
		assertTrue(inventory.contains(burntFoodToBeTested.getName())); //Tests whether the burnt food is still in the inventory
		//Testing log
		game.setSelectedItem(logToBeTested.getName());
		fireToTest.respond();
		assertTrue(inventory.contains(logToBeTested.getName())); //Tests whether the burnt food is still in the inventory
		//Testing equipment
		game.setSelectedItem(EquipmentToBeTested.getName());
		fireToTest.respond();
		assertTrue(inventory.contains(EquipmentToBeTested.getName())); //Tests whether the burnt food is still in the inventory
		
	}

}
