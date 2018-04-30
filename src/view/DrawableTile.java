package view;

import java.awt.image.BufferedImage;

import tiles.Tile;

/**
 * Wrapper class used to graphically represent tiles within the visible world and to draw them.
 * This wrapper class exposes only as much information as necessary, and has behavior directed
 * towards the view rather than the model.
 */
public class DrawableTile {

	private int x, y;
	private BufferedImage texture;
	
	public DrawableTile(Tile t, int x, int y) {
		this.x = x * View.TILE_SIZE;
		this.y = y * View.TILE_SIZE;
		texture = TexturesLoader.getTextureFor(t.getType());
	}
	
	public BufferedImage getTexture() {
		return texture;
	}
	
	/**
	 * Gets the pixel coordinate of this tile.
	 * 
	 * @return The x coordinate of where this tile is to be drawn, in pixels.
	 */
	public int getX() {
		return x;
	}
	
	/**
	 * Gets the pixel coordinate of this tile.
	 * 
	 * @return The y coordinate of where this tile is to be drawn, in pixels.
	 */
	public int getY() {
		return y;
	}

}
