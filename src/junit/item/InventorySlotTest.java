package junit.item;

import static org.junit.Assert.*;
import items.InventorySlot;
import items.Item;
import items.equipment.EquipmentFactory;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class InventorySlotTest {

	
	private static Item item;
	private static InventorySlot slotToBeTested;
	
	@BeforeClass
	public static void setUp() {
		item = EquipmentFactory.makeEquipment("Bronze Sword");
		
	}
	
	@Before
	public void initialize() {
		slotToBeTested = new InventorySlot(item);
	}

	@Test
	public void addTest() {
		
		slotToBeTested.add();
		assertEquals(2, slotToBeTested.getQuantity());
	}
	
	@Test
	public void takeTest() {
		
		assertEquals(item, slotToBeTested.take());
		assertEquals(null, slotToBeTested.take());
		assertEquals(null, slotToBeTested.take(2));
		slotToBeTested.add();
		slotToBeTested.add();
		slotToBeTested.add();
		assertEquals(item, slotToBeTested.take());
		assertEquals(2, slotToBeTested.getQuantity());
		slotToBeTested.take(2);
		assertEquals(0, slotToBeTested.getQuantity());
	}

}
