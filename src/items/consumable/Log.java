package items.consumable;

import items.Item;
import controller.Game;
import entities.Fire;
import entities.creatures.player.Player;

/**
 * This class represents logs.
 * Log type items are used to light a fire.
 * They have a duration, which indicates how much
 * time will a fire made with them persist.
 */
public class Log extends Item {
	
	/**
	 * Indicates how many logs the player needs to light a fire.
	 */
	private static final int LOGS_REQUIRED_FOR_A_FIRE = 10;
	private static final long serialVersionUID = 938694319562037615L;
	private final int duration;
	private final int lvlReq;
	
	/**
	 * Creates a log type item.
	 *
	 * @param name The item's name.
	 * @param duration How much time will a fire lighted 
	 * with this type of wood persist
	 * @param lvlReq The item's "fire making" skill minimum level.
	 * 
	 */
	public Log(String name, int duration, int lvlReq) {
		super(name, "A nice "+ name);
		this.duration = duration;
		this.lvlReq = lvlReq;
	}
	
	private boolean light() {
		Game game = Game.getInstance();
		Player player = game.getPlayer();
		
		if(player.getInventory().count(getName()) < LOGS_REQUIRED_FOR_A_FIRE) {
			game.display("You need " + LOGS_REQUIRED_FOR_A_FIRE + " logs of this kind to light a fire.");
			return false;
		}
		else if(player.getSkillSet().getSkill("Firemaking").getLevel() < lvlReq) {
			game.display(getName() + " requires firemaking level " + lvlReq + " to be lit.");
			return false;
		}
		else {
			float[] positionInFront = game.getCurrentWorld().getCoordsOfTileInFrontOf(player);
			if(positionInFront == null) {
				game.display("You can't light a fire there.");
				return false;
			}
			game.spawn(new Fire(duration, (int) positionInFront[0], (int) positionInFront[1]));
			game.display("You successfully light a fire.");
			game.gainXP("Firemaking", lvlReq*50);
			return true;
		}
	}
	
	/**
	 * This method makes use of a number of logs specified by the
	 * constant {@link #LOGS_REQUIRED_FOR_A_FIRE}.
	 * When used, logs start a fire.
	 * 
	 * @return the number of logs used to light the fire, specified in
	 * the constant {@link #LOGS_REQUIRED_FOR_A_FIRE}, or 0 if the fire
	 * couldn't be started.
	 */
	@Override
	public int use() {
		return light() ? LOGS_REQUIRED_FOR_A_FIRE : 0;
	}	
}
