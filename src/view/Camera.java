package view;

import controller.Game;
import entities.Entity;
import entities.creatures.Direction;

/**
 * Since the worlds are much larger than the window the game
 * renders at any given time, a camera is needed to follow
 * the player around to display a limited section of the world.
 * This class fulfills such a task. The camera can also be used
 * to focus on other entities if attention requires it.
 */
public class Camera {

	private float xOffset, yOffset;
	
	/**
	 * Creates a new Camera with the given offsets.
	 * @param xOffset The horizontal offset of the camera.
	 * @param yOffset The vertical offset of the camera.
	 */
	public Camera(float xOffset, float yOffset) {
		this.xOffset = xOffset;
		this.yOffset = yOffset;
	}

	/**
	 * Creates a new Camera centered on the Player.
	 */
	public Camera() {
		centerOnEntity(Game.getInstance().getPlayer());
	}
	
	/**
	 * Centers the Camera on the given Entity.
	 * @param e The Entity to center the camera on.
	 */
	public void centerOnEntity(Entity e){
		xOffset = e.getX() * View.TILE_SIZE - View.WORLD_CANVAS_WIDTH / 2.0f + View.TILE_SIZE / 2.0f;
		yOffset = e.getY() * View.TILE_SIZE - View.WORLD_CANVAS_HEIGHT / 2.0f + View.TILE_SIZE / 2.0f;
	}
	
	/**
	 * "Moves" the camera by changing its offset to create the effect
	 * of following the Player around.
	 * @param delta The offset change.
	 * @param dir The Direction in which to apply the offset change.
	 */
	public void move(float delta, Direction dir) {
		switch(dir) {
			case UP:
				yOffset -= delta * View.TILE_SIZE;
				break;
			case DOWN:
				yOffset += delta * View.TILE_SIZE;
				break;
			case LEFT:
				xOffset -= delta * View.TILE_SIZE;
				break;
			case RIGHT:
				xOffset += delta * View.TILE_SIZE;
				break;
		}
	}
	
	public float getXOffset() {
		return xOffset;
	}
	public float getYOffset() {
		return yOffset;
	}
}
