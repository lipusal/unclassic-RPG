package combat;

import items.Item;
import utils.Randomizer;
import utils.Timer;
import controller.Game;
import entities.creatures.mobs.Mob;
import entities.creatures.player.Player;

/**
 * This class is used when the player enters in combat. It's responsible for allowing
 * only the current turn owner to make a move, for delaying the enemy's attacks (merely
 * to give a realistic reaction effect, creatures don't normally react in microseconds)
 * and for updating its current state.
 */
public class Combat {
	
	private static final double PLAYER_START_CHANCE = 0.5, RUN_AWAY_CHANCE = 0.2;
	
	private Game game;
	private Player player;
	private Mob enemy;
	private boolean playerTurn;
	private boolean couldRunAway;
	public long nextMobAttackTime;
	
	/**
	 * Creates a new Combat instance to control the combat between the player and the
	 * specified enemy.
	 * 
	 * @param enemy The {@link Mob} the player will fight against.
	 */
	public Combat(Mob enemy) {
		this.enemy = enemy;
		game = Game.getInstance();
		player = game.getPlayer();
		playerTurn = Randomizer.getRandomNumber() < PLAYER_START_CHANCE;
		couldRunAway = false;
		nextMobAttackTime = Timer.getFutureTime(1);
	}

	public Mob getEnemy() {
		return enemy;
	}
	
	public boolean isPlayersTurn() {
		return playerTurn;
	}
	
	/**
	 * Carries out the mob's attack, damaging the player based on their defense
	 * skill and giving them defense experience for being hit.
	 */
	public void mobAttack() {
		if(playerTurn || enemy.isDead()) {
			return;
		}
		int damage = enemy.attack();
		int blockFactor = (int) (player.getDefense()*0.5f);
		int receivedDamage;
		if(blockFactor == 0) { //Avoid division by 0
			receivedDamage = damage;
			player.receiveDamage(damage);
		}
		else{
			receivedDamage = damage/(blockFactor)+1;
			player.receiveDamage(receivedDamage);
		}
		game.gainXP("Defense", receivedDamage*40);
		game.display("You receive " + (receivedDamage) + " damage.");
		game.updateStatsPanel();
		changeTurn();
	}
	
	/**
	 * Carries out the player's attack, damaging the mob based on the player's
	 * attack skill and giving them experience in attack and hit-points from their
	 * battle experience.
	 */
	public void playerAttack() {
		if(!playerTurn || player.isDead()) {
			return;
		}
		int damage = player.attack();
		enemy.receiveDamage(damage);
		game.gainXP("Attack", damage*40);
		game.gainXP("Hitpoints", damage*40);
		game.display(player.toString() + " deals " + damage + " damage.");
		game.display(enemy.toString() + "'s health: " + enemy.getHealth());
		changeTurn();
	}
	
	/**
	 * Makes the player attempt to run away from battle. If the enemy is aggroed,
	 * or the player fails to get away, the turn advances and the enemy attacks
	 * again. If not, the combat state is updated to indicate that the player
	 * managed to run away. 
	 */
	public void runAway() {
		if(!playerTurn) {
			return;
		}
		//Can't run away from Mobs actively pursuing the player
		if(enemy.isAggroed()) {
			couldRunAway = false;
			game.display("You can't run away from this aggroed mob. " + enemy + " attacks!");
		}
		else if(Randomizer.getRandomNumber() <= RUN_AWAY_CHANCE) {
			couldRunAway = true;
			game.display("You successfully got away!");
		}
		else {
			couldRunAway = false;
			game.display("You failed to get away. " + enemy + " attacks!");
		}
		changeTurn();
	}

	/**
	 * Checks whose turn it is, and if it is the enemy's turn, has
	 * them attack. Otherwise, checks if any participant has died.
	 * 
	 * @return The update result. If it's the enemy's turn to
	 * attack, this method call will return {@link CombatState#NO_CHANGES}
	 * and deaths will be checked on the next game update.
	 */
	public CombatState update() {
		/*
		 * If it's the enemy's turn but their time hasn't come to
		 * attack yet
		 */
		if(!Timer.isItThatTimeYet(nextMobAttackTime)) {
			return CombatState.NO_CHANGES;
		}
		else if(player.isDead())  {
			return CombatState.PLAYER_DIED;
		}
		else if(enemy.isDead()) {
			return CombatState.MOB_DIED;
		}
		else if(couldRunAway) {
			return CombatState.PLAYER_ESCAPED;
		}
		else if(!playerTurn) {
			/*
			 * Carry out the attack, on the next combat update if
			 * anybody died the game will be notified.
			 */
			mobAttack(); 
		}
		return CombatState.NO_CHANGES;
	}
	
	/**
	 * Makes the player attempt to use the specified item in combat.
	 * If the item could be used, change turn.
	 * 
	 * @param item The item to attempt to use.
	 */
	public void useItemInCombat(Item item) {
		if (!playerTurn || item == null) {
			return;
		}
		if(player.useItem(item)) {
			changeTurn();
		}
	}
	
	/**
	 * Sets the turn to the other participant. If the enemy is to attack next,
	 * don't allow them to attack for at least 1 second from now.
	 */
	private void changeTurn() {
		playerTurn = !playerTurn;
		if(!playerTurn) {
			nextMobAttackTime = Timer.getFutureTime(1);
		}
	}
}