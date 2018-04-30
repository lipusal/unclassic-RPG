package junit;

import static org.junit.Assert.assertEquals;
import io.GameCreationMode;

import java.io.File;

import org.junit.BeforeClass;
import org.junit.Test;

import view.DrawableWorld;
import controller.Game;

public class DrawableWorldTest {

	private static Game g;
	private static DrawableWorld w;
	
	@BeforeClass
	public static void setUp() {
		g = Game.getInstance();
		String playerPath = "saves" + File.separator + "testPlayer.player";
		g.init(playerPath, "world00", GameCreationMode.CREATE_NEW_GAME);
		//g.run();
		w = new DrawableWorld(g.getCurrentWorld());
	}
	
	@Test
	public void drawableWorldShouldBeTheSameAsTheModelWorld() {
		assertEquals("Different world widths", g.getCurrentWorld().getWidth(),  w.getTiles()[0].length);
		assertEquals("Different world heights", g.getCurrentWorld().getHeight(),  w.getTiles().length);
		assertEquals("Different set of entities", g.getCurrentWorld().getEntities().size(),  w.getEntities().size());
	}
}
