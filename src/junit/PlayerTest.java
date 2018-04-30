package junit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import io.GameCreationMode;
import items.Item;
import items.consumable.ConsumableItemFactory;
import items.consumable.Food;
import items.equipment.Equipment;
import items.equipment.EquipmentFactory;

import java.io.File;

import org.junit.BeforeClass;
import org.junit.Test;

import utils.Randomizer;
import controller.Game;
import entities.Fire;
import entities.creatures.player.Player;

public class PlayerTest {
	
	private static Game g;
	private static Player p;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		g = Game.getInstance();
		g.init("saves" + File.separator + "testPlayer.player", "world00", GameCreationMode.CREATE_NEW_GAME);
		p = g.getPlayer();
	}

	@Test
	public void testAttack() {
		for(int i = 0; i < 100; i++) {
			Randomizer.newNumber();
			assertTrue("Attack value outside of range", p.attack() >= 0);
		}
	}

	@Test
	public void testEquip() {
		Equipment itemToTest = EquipmentFactory.makeEquipment("Runite Boots");
		assertEquals("Player equipping items beyond their skill level", 0, p.equip(itemToTest));
		assertEquals("Player equipping null items", 0, p.equip(null));
	}

	@Test
	public void testHeal() {
		Food food = (Food) ConsumableItemFactory.makeItem("Lobster");
		int health = p.getHealth();
		int maxH = p.getMaxHealth();
		int heal = food.getHealValue();
		assertTrue("Food not healing", (health == maxH) || p.getHealth() == (health + heal) ||
				p.getHealth() == maxH);
	}

	@Test
	public void testInteractWith() {
		float[] t = g.getCurrentWorld().getCoordsOfTileInFrontOf(p);
		
		Fire fire = new Fire(100, t[0], t[1]);
		Food food = (Food) ConsumableItemFactory.makeItem("Raw Lobster");
		assertFalse("There is food in there", p.getInventory().contains(food.getName()));
		g.putInPlayerInventory(food, 1);
		g.setSelectedItem("Raw Lobster");
		p.interactWith(fire);
		assertFalse("Food NOT Cooked", p.getInventory().contains("Raw Lobster"));
		assertTrue("Error, not saving food", p.getInventory().contains("Lobster") || 
				p.getInventory().contains("Burnt Lobster"));
	}

	@Test
	public void testMoveTo() {
		float[] c = {p.getX(), p.getY()};
		p.moveTo(c[0]+5, c[1]+5);
		assertTrue("Error while movingTo", c[0]+5.5 == p.getX() && c[1]+5.5 == p.getY());
		/*
		 * This method does not validate that the new coordinates are valid
		 * so we do not care in this test.
		 */
	}

	@Test
	public void testRecieveDamage() {
		int health = p.getHealth();
		p.receiveDamage(2);
		assertTrue("Error in ReciveDamage", health - 2 == p.getHealth());
	}

	@Test
	public void testUnequip() {
		Equipment equipment = EquipmentFactory.makeEquipment(("Bronze Helmet"));
		assertFalse("Problem in unequipping an empty slot", p.unequip(equipment.getSlotName()));
		p.equip(equipment);
		assertNotEquals("Problem equipping item", "Unequipped", p.getEquipmentOn(equipment.getSlotName()));
		p.unequip(equipment.getSlotName());
		assertEquals("Problem with unequip", "Unequipped", p.getEquipmentOn(equipment.getSlotName()));
		}

	@Test
	public void testUseItem() {
		Item item = ConsumableItemFactory.makeItem("Lobster");
		assertEquals("Using item not in inventory", false, p.useItem(item));
		g.putInPlayerInventory(item, 1);
		assertTrue("Not using Item", p.useItem(item));
	}

}
