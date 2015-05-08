package rooms;

import java.awt.Point;

/**
 * AbstractRoom.java
 * JDK 1.8
 * Eclipse version: Luna Release (4.4.0)
 * @author Daniel Bischoff
 * Date created: 18.12.2014
 * Last change: 22.12.2014
 * Description: Represents a abstract product (room). Implementation of the abstract factory pattern.
 */

public abstract class AbstractRoom {

	/**
	 * roomID as int.
	 */
	protected int roomID;
	
	/**
	 * Location of the room within the worldmap.
	 */
	private Point location = new Point();
	
	/**
	 * Room is already unlocked or not (if it is a magic room).
	 */
	protected boolean locked = false;
	
	/**
	 * roomID of the room at the north side.
	 */
	protected int north;
	
	/**
	 * roomID of the room at the east side.
	 */
	protected int east;
	
	/**
	 * roomID of the room at the south side.
	 */
	protected int south;
	
	/**
	 * roomID of the room at the west side.
	 */
	protected int west;
	
	/**
	 * AbstractRoom
	 * Constructor, sets the roomID of every new room instance.
	 * @param roomID
	 */
	public AbstractRoom(int roomID) {
		this.roomID = roomID;
	}
	
	public abstract boolean isMagicRoom();
	
	public abstract boolean unlock();
	
	public abstract boolean lock();
	
	public abstract boolean isLocked();
		
	public abstract int getRoomID();
	
	public abstract void setRoomID(int roomID);
	
	public abstract int getNorth();
	
	public abstract void setNorth(int north);
	
	public abstract int getEast();
	
	public abstract void setEast(int east);
	
	public abstract int getSouth();
	
	public abstract void setSouth(int south);
	
	public abstract int getWest();
	
	public abstract void setWest(int west);

	public Point getLocation() {
		return location;
	}

	public void setLocation(Point location) {
		this.location = location;
	}
	
	public void setLocation(double x, double y) {
		this.location.setLocation(x, y);
	}

}