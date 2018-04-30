package junit.item;

import static org.junit.Assert.assertEquals;
import io.GameCreationMode;
import items.equipment.Equipment;
import items.equipment.EquipmentFactory;

import java.io.File;

import org.junit.Before;
import org.junit.Test;

import controller.Game;
import entities.creatures.player.Player;

public class EquipmentTest {
	
	private static Game game;
	private static Player player;
	private static Equipment equipmentToBeTested;
	

	@Before
	public void setUp() {
		game = Game.getInstance();
		String playerPath = "saves" + File.separator + "testPlayer.player";
		game.init(playerPath, "world00", GameCreationMode.CREATE_NEW_GAME);
		player = game.getPlayer();
		equipmentToBeTested = EquipmentFactory.makeEquipment("Bronze Sword");
		player.equip(EquipmentFactory.makeEquipment("Iron Sword"));
		player.equip(EquipmentFactory.makeEquipment("Iron Shield"));
	}

	@Test
	public void test() {
		assertEquals(1, equipmentToBeTested.use()); // You can equip even though all slots are occupied. Items are swapped.
		player.unequip("right hand");
		assertEquals(1, equipmentToBeTested.use());
		player.unequip("right hand");
		player.unequip("left hand");
		assertEquals(1, equipmentToBeTested.use());
	}

}
