package junit.item;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import items.equipment.Equipment;
import items.equipment.EquipmentFactory;
import items.equipment.Weapon;

import org.junit.BeforeClass;
import org.junit.Test;

public class ConsumableItemFactoryTest {
	
	public static Equipment exampleToTest;
	
	@BeforeClass
	public static void setUp() {
		exampleToTest = new Weapon("Bronze Sword","A bronze sword. May cut more than my fists.", 1, 0, 1.10f, 1);
	}
	
	@Test
	public void makeItemTest() {
		assertTrue(EquipmentFactory.makeEquipment("Bronze Sword").equals(exampleToTest));
		assertFalse(EquipmentFactory.makeEquipment("Iron Sword").equals(exampleToTest));
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void makeItemExceptionTest() {
		EquipmentFactory.makeEquipment("asdasd");
		EquipmentFactory.makeEquipment("");
		EquipmentFactory.makeEquipment(null);
	}

}
