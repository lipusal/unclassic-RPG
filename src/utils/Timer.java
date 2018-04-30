package utils;

/**
 * This utility class helps the game keep track of time. With this the game will know
 * when it has to update and can calculate time differences.
 */
public class Timer {
	private static final int FPS = 60;
	private static final double timePerTick = 1000000000 / FPS;
	private static long lastTime = System.nanoTime();
	
	/**
	 * Checks whether the game must update in order to achieve the desired
	 * frames per second as defined by @see {@link Timer#FPS}
	 * @return True if it's time to tick, False otherwise.
	 */
	public static boolean gameMustUpdate() {
		if((System.nanoTime() - lastTime) / timePerTick >= 1) {
			lastTime = System.nanoTime();
			return true;
		}
		else return false;
	}
	
	/**
	 * Calculates the amount of seconds elapsed since a given timestamp.
	 * @param startTime The timestamp from when to start counting, in nanoseconds.
	 * @return The difference in seconds between now and the provided start time.
	 */
	public static double secondsSince(long startTime) {
		return (System.nanoTime() - startTime) / 1000000000; 
	}
	
	/**
	 * Gets a future time for Entities to know when to update.
	 * @param secondsFromNow The number of seconds from now to mark.
	 * @return A timestamp in nanoseconds corresponding to the specified time.
	 */
	public static long getFutureTime(double secondsFromNow) {
		return (long) (System.nanoTime() + secondsFromNow * 1000000000);
	}
	
	/**
	 * Checks whether the provided timestamp is now or in the past.
	 * @param timestamp A timestamp in nanoseconds representing a moment.
	 * @return True if the system reports a time greater than or equal to the
	 * provided timestamp. False otherwise.
	 */
	public static boolean isItThatTimeYet(long timestamp) {
		return System.nanoTime() >= timestamp;
	}
	

}
