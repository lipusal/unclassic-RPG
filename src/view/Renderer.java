package view;

import java.awt.Graphics;
import java.awt.image.BufferStrategy;

import controller.Game;

/**
 * Class used to render the visible world onto the game canvas. Thie class has a drawable representation
 * of the world and knows which parts of it to draw at any given time based on the game camera. That way,
 * rather than inefficiently rendering the whole map, only a portion of it that is visible is actually
 * rendered at any given time.
 */
public class Renderer {
	
	private BufferStrategy bufferStrategy;
	private Graphics graphics;
	private float cameraXOffset, cameraYOffset;
	
	public Renderer(View view) {
		//Set up drawing methods
		view.getCanvas().createBufferStrategy(3);
		bufferStrategy = view.getCanvas().getBufferStrategy();
		if(bufferStrategy == null) {
			throw new IllegalStateException("Couldn't properly create Buffer Strategy for Renderer.");
		}
	}
	
	public void render(DrawableWorld world) {
		graphics = bufferStrategy.getDrawGraphics();
		//Clear previous frame
		graphics.clearRect(0, 0, View.WORLD_CANVAS_WIDTH, View.WORLD_CANVAS_HEIGHT);
		//Take offsets into consideration.
		cameraXOffset = Game.getInstance().getCamera().getXOffset();
		cameraYOffset = Game.getInstance().getCamera().getYOffset();
		
		//Draw tiles
		DrawableTile[][] tiles = world.getTiles();
		for(int y = 0; y < tiles.length; y++) {
			for(int x = 0; x < tiles[y].length; x++) {
				DrawableTile currentTile = tiles[x][y];
				if(isVisible(currentTile)) {
					graphics.drawImage(currentTile.getTexture(),
	        				(int) (currentTile.getX() - cameraXOffset),
	        				(int) (currentTile.getY() - cameraYOffset),
	        				null);
				}
			}
		}
		
		for(DrawableEntity e : world.getEntities()) {
			if(isVisible(e)) {
				graphics.drawImage(e.getTexture(),
        				(int) (e.getX() - cameraXOffset - View.TILE_SIZE/2),
        				(int) (e.getY() - cameraYOffset - View.TILE_SIZE/2),
        				null);
			}
		}
		
		bufferStrategy.show();
		graphics.dispose();
	}
	
	/**
	 * Checks whether a drawable entity is within view of the world.
	 * 
	 * @param e The entity to check.
	 * @return <code>true</code> If the specified entity can be seen within the canvas or is at most
	 * one tile-unit outside of the canvas.
	 */
	private boolean isVisible(DrawableEntity e) {
		return e.getX() - cameraXOffset >= -View.TILE_SIZE
				&& e.getX() - cameraXOffset <= View.WORLD_CANVAS_WIDTH + View.TILE_SIZE
				&& e.getY() - cameraYOffset >= -View.TILE_SIZE
				&& e.getY() - cameraYOffset <= View.WORLD_CANVAS_HEIGHT + View.TILE_SIZE;
	}
	
	/**
	 * Checks whether a drawable tile is within view of the world.
	 * 
	 * @param t The tile to check.
	 * @return <code>true</code> If the specified tile can be seen within the canvas or is at most
	 * one tile-unit outside of the canvas.
	 */
	private boolean isVisible(DrawableTile t) {
		return t.getX() - cameraXOffset >= -View.TILE_SIZE
				&& t.getX() - cameraXOffset <= View.WORLD_CANVAS_WIDTH + View.TILE_SIZE
				&& t.getY() - cameraYOffset >= -View.TILE_SIZE
				&& t.getY() - cameraYOffset <= View.WORLD_CANVAS_HEIGHT  + View.TILE_SIZE;
	}
}
