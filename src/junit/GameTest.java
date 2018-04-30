package junit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import io.GameCreationMode;
import items.Item;
import items.consumable.ConsumableItemFactory;

import java.awt.event.KeyEvent;
import java.io.File;

import org.junit.BeforeClass;
import org.junit.Test;

import controller.Game;
import entities.creatures.mobs.Skeleton;

public class GameTest {

	private static Game g;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		g = Game.getInstance();
		g.init("saves" + File.separator + "testPlayer.player", "world00", GameCreationMode.CREATE_NEW_GAME);
	}

	@Test
	public void testGetInstance() {
		assertTrue("Game instance not created propely", g != null);
	}

	@Test
	public void testChangeWorld() {
		int newX = 10, newY = 10;
		g.changeWorld("world01", newX, newY);
		assertTrue("Player not relocated properly in new world", g.getPlayer().getX() == newX+0.5f && g.getPlayer().getY() == newY+0.5f);
	}

	@Test
	public void testDropCurrentlySelectedStack() {
		int slots = g.getPlayer().getInventory().getFreeSlots();
		g.dropCurrentlySelectedStack();
		assertEquals("Dropped an item when there was no selected stack.", slots, g.getPlayer().getInventory().getFreeSlots());
		g.putInPlayerInventory(ConsumableItemFactory.makeItem("Common Log"), 1);
		g.setSelectedItem("Common Log");
		g.dropCurrentlySelectedStack();
		assertNotEquals("Added an item and selected it but it wasn't properly dropped", slots, g.getPlayer().getInventory().getFreeSlots());
	}

	@Test
	public void testEnterCombatMode() {
		g.enterCombatMode(null);
		assertEquals("Game entered combat mode with a null enemy", false, g.isInCombatMode());
		g.enterCombatMode(new Skeleton(5, 5));
		assertEquals("Game didn't properly enter combat mode with a valid enemy", true, g.isInCombatMode());
	}

	@Test
	public void testExitCombatMode() {
		g.exitCombatMode();
		assertEquals("Game didn't exit combat mode properly", false, g.isInCombatMode());
	}

	@Test
	public void testGainXP() {
		int originalLevel = g.getPlayer().getSkillSet().getSkill("Cooking").getLevel();
		g.gainXP("Cooking", -42);
		assertEquals("Negative XP gain caused a change in level", originalLevel, g.getPlayer().getSkillSet().getSkill("Cooking").getLevel());
		g.gainXP("Cooking", 468949841);
		assertNotEquals("Gained XP didn't cause a change in level", originalLevel, g.getPlayer().getSkillSet().getSkill("Cooking").getLevel());
	}

	@Test
	public void testInit() {
		assertNotNull("Player not initialized", g.getPlayer());
		assertNotNull("World not initialized", g.getCurrentWorld());
		assertNotNull("Camera not initialized", g.getCamera());
	}

	@Test
	public void testPutInPlayerInventory() {
		Item i = ConsumableItemFactory.makeItem("Common Log");
		g.putInPlayerInventory(i, 1);
		assertEquals("Couldn't put item in inventory", 1, g.getPlayer().getInventory().getOccupiedSlots());
		i = ConsumableItemFactory.makeItem("Oak Log");
		g.putInPlayerInventory(i, 0);
		assertEquals("Put 0 items in inventory", 1, g.getPlayer().getInventory().getOccupiedSlots());
		g.putInPlayerInventory(null, -1);
		assertEquals("Put null item in inventory", 1, g.getPlayer().getInventory().getOccupiedSlots());
	}

	@Test
	public void testRespondToKeyboardInput() {
		String originalLocation = g.getPlayer().getLocation();
		g.respondToKeyboardInput(KeyEvent.VK_UP);
		assertNotEquals("Player not moving from keyboard input", originalLocation, g.getPlayer().getLocation());
	}

	@Test
	public void testTakeFromPlayerInventory() {
		assertNull("Taking non-existant item from inventory", g.takeFromPlayerInventory("Adamant Sword", 1));
		g.putInPlayerInventory(ConsumableItemFactory.makeItem("Common Log"), 50);
		assertNull("Taking negative items from inventory", g.takeFromPlayerInventory("Common Log", -2));
		assertNull("Taking null items from inventory", g.takeFromPlayerInventory(null, -2));
		assertNull("Taking invalid items from inventory", g.takeFromPlayerInventory("ask.dhsad", 2));
		assertNull("Taking too many items from inventory", g.takeFromPlayerInventory("Common Log", 3000));
		assertNotNull("Not taking valid items from inventory", g.takeFromPlayerInventory("Common Log", 3));
	}

}
