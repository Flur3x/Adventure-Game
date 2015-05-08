package game;

import rooms.AbstractRoom;

/**
 * Character.java
 * JDK 1.8
 * Eclipse version: Luna Release (4.4.0)
 * @author Daniel Bischoff
 * Date created: 19.12.2014
 * Last change: 22.12.2014
 * Description: Represents the player in this game. Saves his position and contains the methods to change it.
 */

public class Character {
	
	/**
	 * Current players position.
	 */
	private AbstractRoom currentRoom;
	
	/**
	 * Last players position.
	 */
	private AbstractRoom lastRoom = null;

	public AbstractRoom getCurrentRoom() {
		return currentRoom;
	}

	/**
	 * setCurrentRoom
	 * Changes the position.
	 * @param currentRoom is the room object where the position should be changed to.
	 */
	public void setCurrentRoom(AbstractRoom currentRoom) {
		this.currentRoom = currentRoom;
	}
	
	/**
	 * setLastRoom
	 * Changes the position where the player has been before.
	 * @param lastRoom is the room object of the last room.
	 */
	public void setLastRoom(AbstractRoom lastRoom) {
		this.lastRoom = lastRoom;
	}
	
	public AbstractRoom getLastRoom() {
		return lastRoom;
	}
}