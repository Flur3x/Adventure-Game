package game;

/**
 * Main.java
 * JDK 1.8
 * Eclipse version: Luna Release (4.4.0)
 * @author Daniel Bischoff
 * Date created: 11.11.2014
 * Last change: 24.01.2015
 * Description: Main class, creates an instance of the game.
 */

public class Main {
	
	/**
	 * main
	 * Calls startGame(); to create a new instance of the game.
	 * @param args
	 */
	public static void main(String[] args) {
		startGame();
	}

	/**
	 * startGame
	 * Creates an instance of the "Game" object.
	 */
	public static void startGame() {
		new Game();
	}
}