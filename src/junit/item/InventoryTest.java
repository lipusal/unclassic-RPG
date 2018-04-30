package junit.item;

import static org.junit.Assert.*;
import io.GameCreationMode;
import items.Inventory;
import items.Item;
import items.equipment.EquipmentFactory;

import java.io.File;
import java.util.Vector;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import controller.Game;

public class InventoryTest {

	private static Game game;
	private static Inventory inventoryWithOneSlot;
	private static Inventory inventoryWith19Slots;
	
	@BeforeClass
	public static void setUp() {
		game = Game.getInstance();
		String playerPath = "saves" + File.separator + "lipusal"  + ".player";
		game.init(playerPath, "world00", GameCreationMode.CREATE_NEW_GAME);	
	}
	
	@Before
	public void initializeInventoryWithOneSlot() {
		inventoryWithOneSlot = new Inventory(1);
	}
	
	@Before
	public void initializeInventoryWith19Slots() {
		inventoryWith19Slots = new Inventory(19);
	}
	
	
	@Test
	public void canAddTest() {
		assertTrue(inventoryWithOneSlot.canAdd(EquipmentFactory.makeEquipment("Bronze Sword")));
		assertTrue(inventoryWithOneSlot.canAdd(null));
		inventoryWithOneSlot.put(EquipmentFactory.makeEquipment("Iron Sword"));
		assertFalse(inventoryWithOneSlot.canAdd(EquipmentFactory.makeEquipment("Bronze Sword")));
	}
	
	@Test
	public void containsTest() {
		assertFalse(inventoryWithOneSlot.contains("Bronze Sword"));
		assertFalse(inventoryWithOneSlot.contains(null));
		inventoryWithOneSlot.put(EquipmentFactory.makeEquipment("Bronze Sword"));
		inventoryWithOneSlot.put(null);
		assertTrue(inventoryWithOneSlot.contains("Bronze Sword"));
		assertFalse(inventoryWithOneSlot.contains(null));		
	}
	
	@Test
	public void countTest() {
		assertEquals(0,inventoryWithOneSlot.count("Bronze Sword"));
		inventoryWithOneSlot.put(EquipmentFactory.makeEquipment("Bronze Sword"));
		assertEquals(1,inventoryWithOneSlot.count("Bronze Sword"));
		inventoryWithOneSlot.put(EquipmentFactory.makeEquipment("Bronze Sword"));
		inventoryWithOneSlot.put(EquipmentFactory.makeEquipment("Bronze Sword"));
		assertEquals(3,inventoryWithOneSlot.count("Bronze Sword"));
		assertFalse(2 == inventoryWithOneSlot.count("Bronze Sword"));
	}
	
	@Test
	public void dropAllTest() {
		inventoryWith19Slots.put(EquipmentFactory.makeEquipment("Bronze Sword"));
		inventoryWith19Slots.put(EquipmentFactory.makeEquipment("Iron Sword"));
		inventoryWith19Slots.put(EquipmentFactory.makeEquipment("Iron Sword"));
		inventoryWith19Slots.dropAll("Iron Sword");
		assertFalse(inventoryWith19Slots.contains("Iron Sword"));
		assertTrue(inventoryWith19Slots.contains("Bronze Sword"));
	}
	
	@Test
	public void getTest() {
		assertTrue(inventoryWithOneSlot.get("Bronze Sword") == null);
		inventoryWithOneSlot.put(EquipmentFactory.makeEquipment("Bronze Sword"));
		assertFalse(inventoryWithOneSlot.get("Bronze Sword") == null);
		assertEquals(EquipmentFactory.makeEquipment("Bronze Sword"), inventoryWithOneSlot.get("Bronze Sword"));
		assertEquals(null, inventoryWithOneSlot.get(""));
		assertEquals(null, inventoryWithOneSlot.get(null));
	}
	
	@Test
	public void getDataVectorTest() {
		Item aux = EquipmentFactory.makeEquipment("Bronze Sword");
		inventoryWithOneSlot.put(aux);
		inventoryWithOneSlot.put(aux);
		inventoryWithOneSlot.put(aux);
		Vector<Vector<String>> vectorToTest = inventoryWithOneSlot.getDataVector();
		assertTrue(vectorToTest.get(0).get(0).equals("Bronze Sword"));
		assertTrue(vectorToTest.get(0).get(1).equals("3"));
	}
	
	@Test
	public void putTest() {		
		assertTrue(inventoryWithOneSlot.put(EquipmentFactory.makeEquipment("Bronze Sword")));
		assertTrue(inventoryWithOneSlot.put(EquipmentFactory.makeEquipment("Bronze Sword")));
		assertTrue(inventoryWithOneSlot.put(EquipmentFactory.makeEquipment("Bronze Sword")));
		assertTrue(inventoryWithOneSlot.put(null));
		assertFalse(inventoryWithOneSlot.put(EquipmentFactory.makeEquipment("Iron Sword")));
	}
	
	@Test
	public void swapTest() {
		Item aux = EquipmentFactory.makeEquipment("Bronze Sword");
		assertEquals(null, inventoryWithOneSlot.swap(aux, "Iron Sword"));
		inventoryWithOneSlot.put(aux);
		assertEquals("Bronze Sword", inventoryWithOneSlot.swap(EquipmentFactory.makeEquipment("Iron Sword"), "Bronze Sword").getName());
		assertEquals(null, inventoryWithOneSlot.swap(EquipmentFactory.makeEquipment("Iron Sword"), "Bronze Sword"));
		assertTrue(inventoryWithOneSlot.contains("Iron Sword"));
		assertEquals(1, inventoryWithOneSlot.count("Iron Sword"));
	}
	
	@Test
	public void takeTest() {
		
		Item aux = EquipmentFactory.makeEquipment("Bronze Sword");
		inventoryWithOneSlot.put(aux);
		inventoryWithOneSlot.put(aux);
		inventoryWithOneSlot.put(aux);
		assertEquals(aux,inventoryWithOneSlot.take("Bronze Sword"));
		assertEquals(null,inventoryWithOneSlot.take("bronze Sword"));
		assertEquals(2, inventoryWithOneSlot.count("Bronze Sword"));
		assertFalse(inventoryWithOneSlot.isEmpty());
		inventoryWithOneSlot.take("Bronze Sword");
		inventoryWithOneSlot.take("Bronze Sword");
		assertTrue(inventoryWithOneSlot.isEmpty());
	}	
	
}
