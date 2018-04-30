package junit;

import static org.junit.Assert.*;
import io.GameCreationMode;
import items.Inventory;
import items.Item;
import items.consumable.ConsumableItemFactory;
import items.equipment.EquipmentFactory;

import java.io.File;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import combat.Combat;
import combat.CombatState;
import controller.Game;
import entities.creatures.mobs.Skeleton;
import entities.creatures.player.Player;

/**
 * Tests for Combat class. Most methods are based on random numbers, so test should be
 * run several times.
 */
public class CombatTest {

	private static Game game;
	private static Player player;
	private static Inventory inventory;
	private static Item cookedFoodToBeTested;
	private static Item equipmentToBeTested;
	private static Combat combatToTest;
	private static int playerInitialHealth;
	private static int enemyInitialHealth;

	
	
	@BeforeClass
	public static void setUp() {
		game = Game.getInstance();
		String playerPath = "saves" + File.separator + "testPlayer"  + ".player";
		game.init(playerPath, "world00", GameCreationMode.CREATE_NEW_GAME);
		player = game.getPlayer();
	}

	@Before
	public void initialize()  {
		player = game.getPlayer();
		playerInitialHealth = player.getHealth();
		inventory = player.getInventory();
		cookedFoodToBeTested = ConsumableItemFactory.makeItem("Lobster");
		equipmentToBeTested = EquipmentFactory.makeEquipment("Adamant Sword");
		inventory.put(cookedFoodToBeTested);
		inventory.put(equipmentToBeTested);
		combatToTest = new Combat(new Skeleton(0, 0));
		enemyInitialHealth = combatToTest.getEnemy().getHealth();
		
	}

	@Test
	public void testMobAttack() {
		if (combatToTest.isPlayersTurn()) {
			combatToTest.mobAttack();
			assertEquals(playerInitialHealth, player.getHealth());
		}
		else {
			combatToTest.mobAttack();
			assertTrue(playerInitialHealth >= player.getHealth()); // May not cause damage because of random-based calculations
		}
		if (player.getHealth() == 0) {
			assertTrue(combatToTest.update() == CombatState.PLAYER_DIED);
		}
		else {
			assertTrue(combatToTest.update() == CombatState.NO_CHANGES);		
		}
	}

	@Test
	public void testPlayerAttack() {	
		if (combatToTest.isPlayersTurn()) {
			combatToTest.playerAttack();
			assertTrue(enemyInitialHealth > combatToTest.getEnemy().getHealth()); //Player always cause at least one point of damage
		}
		else {
			combatToTest.playerAttack();
			assertEquals(enemyInitialHealth,combatToTest.getEnemy().getHealth());
		}	
		if (player.getHealth() == 0) {
			assertTrue(combatToTest.update() == CombatState.MOB_DIED);
		}
		else {
			assertTrue(combatToTest.update() == CombatState.NO_CHANGES);		
		}
	}

}
