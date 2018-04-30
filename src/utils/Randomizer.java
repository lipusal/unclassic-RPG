package utils;

import java.util.Random;

/**
 * Class designed to be used for random or probability-based events. The game game constantly
 * generates new random numbers when it updates, but other other classes can also request a
 * new random number.
 */
public class Randomizer {

	private static Random r = new Random();
	private static double randomNumber = newNumber();
	
	public static double getRandomNumber() {
		return randomNumber;
	}
	
	/**
	 * Generates a new random number.
	 * @return The generated random number, between 0.0 and 1.0 inclusive.
	 */
	public static double newNumber() {
		randomNumber = r.nextDouble();	//Double between 0.0 and 1.0
		return randomNumber;
	}
}
