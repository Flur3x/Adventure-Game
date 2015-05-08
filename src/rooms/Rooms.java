package rooms;

import java.util.Collection;
import java.util.HashMap;
import java.util.Set;

/**
 * Rooms.java
 * JDK 1.8
 * Eclipse version: Luna Release (4.4.0)
 * @author Daniel Bischoff
 * Date created: 19.12.2014
 * Last change: 27.12.2014
 * Description: Contains the HashMap with all room objects as well as the methods to process the HashMap.
 */

public class Rooms {
	
	private HashMap<Integer, AbstractRoom> rooms = new HashMap<Integer, AbstractRoom>();
	
	public void put(int roomID, AbstractRoom room) {
		rooms.put(roomID, room);
	}
	
	public AbstractRoom get(int roomID) {
		return rooms.get(roomID);
	}
	
	public Collection<AbstractRoom> values() {
		return rooms.values();
	}
	
	public Set<Integer> keySet() {
		return rooms.keySet();
	}
	
	public int size() {
		return rooms.size();
	}
	
	public HashMap<Integer, AbstractRoom> getHashMap() {
		return rooms;
	}
	
	/**
	 * showAllRoomIDs
	 * Shows all room ids on the internal console.
	 */
	public void showAllRoomIDs() {
		System.out.println(this.keySet());
	}
	
	/**
	 * showAllRooms
	 * Shows all rooms and their variables on the internal console. 
	 */
	public void showAllRooms() {
		for (AbstractRoom room : this.values()) {
			System.out.print(room.roomID + ":" + room.north + ":" + room.east + ":" + room.south + ":" + room.west + " Coordinates: " + (int) room.getLocation().getX() + "/" + (int) room.getLocation().getY());
				if (room.isMagicRoom()) {
					System.out.print(" " + ((MagicRoom)room).getQuestion() + " " + ((MagicRoom)room).getAnswer());
				}
			System.out.println();
		}
	}
}