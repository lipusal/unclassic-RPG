package entities;

import controller.Game;

public class WorldChanger extends Entity implements Interactable {

	private static final long serialVersionUID = -8939601786059625250L;

	private String newWorldName;
	private float newX, newY;
	
	public WorldChanger(float x, float y, String newWorldName,
			float newWorldX, float newWorldY) {
		super("World Changer", "Step here to go to another world!", x, y);
		this.newWorldName = newWorldName;
		this.newX = newWorldX;
		this.newY = newWorldY;
	}

	@Override
	/**
	 * The World Changers have no behavior.
	 */
	public void update() {
		return;
	}

	@Override
	/**
	 * Makes the game change worlds, specifying the
	 * new world to load and where to place the
	 * Player in it.
	 */
	public void respond() {
		Game.getInstance().changeWorld(newWorldName, newX, newY);
	}

}
