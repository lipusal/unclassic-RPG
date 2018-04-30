package junit.item;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import items.Item;
import items.consumable.ConsumableItemFactory;
import items.consumable.CookState;
import items.consumable.Food;

import org.junit.BeforeClass;
import org.junit.Test;

public class EquipmentFactoryTest {
	
	public static Item exampleToTest;
	
	@BeforeClass
	public static void setUp() {
		exampleToTest = new Food("Raw Lobster", "A wild Krabby appeared.", CookState.UNCOOKED, 3, 1);
	}
	
	@Test
	public void makeItemTest() {
		assertTrue(ConsumableItemFactory.makeItem("Raw Lobster").equals(exampleToTest));
		assertFalse(ConsumableItemFactory.makeItem("Willow Log").equals(exampleToTest));
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void makeItemExceptionTest() {
		ConsumableItemFactory.makeItem("asdasd");
		ConsumableItemFactory.makeItem("");
		ConsumableItemFactory.makeItem(null);
	}

}
