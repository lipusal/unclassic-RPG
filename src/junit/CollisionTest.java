package junit;

import static org.junit.Assert.*;

import java.io.File;

import io.GameCreationMode;
import entities.creatures.player.Player;





import org.junit.BeforeClass;
import org.junit.Test;

import controller.Game;

public class CollisionTest {

	private static Game g;
	private static Player p;

	@BeforeClass
	public static void init() {
		g = Game.getInstance();
		g.init("saves" + File.separator + "testPlayer.player", "world00", GameCreationMode.CREATE_NEW_GAME);
		p = g.getPlayer();
	}

	@Test
	public void collisionShouldReturnFalse(){
		assertFalse(p.checkCollisionInTile(g.getCurrentWorld().getTileAt(8 , 10), -1.00f , 0 , 0));
		assertFalse(p.checkCollisionInTile(g.getCurrentWorld().getTileAt(12 , 10), 1.00f , 0 , 0));
		assertFalse(p.checkCollisionInTile(g.getCurrentWorld().getTileAt(10,12), 0 , 1.00f , 1));
		assertFalse(p.checkCollisionInTile(g.getCurrentWorld().getTileAt(10,89), 0 , -1.00f , 1));
		assertTrue(p.checkCollisionInTile(g.getCurrentWorld().getTileAt(8,10), -1.01f , 0 , 0));
		assertTrue(p.checkCollisionInTile(g.getCurrentWorld().getTileAt(12,10), 1.01f , 0 , 0));
		assertTrue(p.checkCollisionInTile(g.getCurrentWorld().getTileAt(10,12), 0 , 1.01f , 1));
		assertTrue(p.checkCollisionInTile(g.getCurrentWorld().getTileAt(10,8), 0 , -1.01f , 1));
		assertFalse(p.checkCollisionInTile(g.getCurrentWorld().getTileAt(8,10), -0.9f , 0 , 0));
		assertFalse(p.checkCollisionInTile(g.getCurrentWorld().getTileAt(12,10), 0.9f , 0 , 0));
		assertFalse(p.checkCollisionInTile(g.getCurrentWorld().getTileAt(10,12), 0 , 0.9f , 1));
		assertFalse(p.checkCollisionInTile(g.getCurrentWorld().getTileAt(10,8), 0 , -0.9f , 1));

	}
	@Test
	public void collisionShouldReturn(){
		assertFalse(p.checkCollisionWithTile(10f, 8f, 0f , -1f , 1 ));
		assertFalse(p.checkCollisionWithTile(10f, 12f, 0f , 1f , 1 ));
		assertFalse(p.checkCollisionWithTile(8f, 10f, -1f , 0f , 0 ));
		assertFalse(p.checkCollisionWithTile(12f, 10f, 1f , 0f  , 0 ));
		assertTrue(p.checkCollisionWithTile(10f, 8f, 0f , -1.1f , 1 ));
		assertTrue(p.checkCollisionWithTile(10f, 12f, 0f , 1.1f , 1 ));
		assertTrue(p.checkCollisionWithTile(8f, 10f, -1.1f , 0f , 0 ));
		assertTrue(p.checkCollisionWithTile(12f, 10f, 1.1f , 0f  , 0 ));
		assertFalse(p.checkCollisionWithTile(10f, 8f, 0f , -0.9f , 1 ));
		assertFalse(p.checkCollisionWithTile(10f, 12f, 0f , 0.9f , 1 ));
		assertFalse(p.checkCollisionWithTile(8f, 10f, -0.9f , 0f , 0 ));
		assertFalse(p.checkCollisionWithTile(12f, 10f, 0.9f , 0f  , 0 ));
	}

}
