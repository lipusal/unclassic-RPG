package view;

import java.awt.image.BufferedImage;

import entities.Entity;


/**
 * Wrapper class used to graphically represent entities within the game. This wrapper class only
 * exposes as little information as necessary for the view, and has behavior directed towards the
 * view rather than the model.
 */
public class DrawableEntity {

	private Entity e;
	private BufferedImage texture;
	
	public DrawableEntity(Entity e) {
		this.e = e;
		texture = TexturesLoader.getTextureFor(e.getName());
	}

	/**
	 * Gets the pixel coordinates of this entity.
	 * @return The x coordinate of this entity, in pixels.
	 */
	public float getX() {
		return e.getX() * View.TILE_SIZE;
	}

	/**
	 * Gets the pixel coordinates of this entity.
	 * @return The y coordinate of this entity, in pixels.
	 */
	public float getY() {
		return e.getY() * View.TILE_SIZE;
	}

	public BufferedImage getTexture() {
		return texture;
	}

	@Override
	public int hashCode() {
		return e.hashCode();
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof DrawableEntity)) {
			return false;
		}
		DrawableEntity other = (DrawableEntity) obj;
		return e.equals(other.e);
	}
	
	
}
