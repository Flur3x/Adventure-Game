package rooms;

/**
 * NormalRoom.java
 * JDK 1.8
 * Eclipse version: Luna Release (4.4.0)
 * @author Daniel Bischoff
 * Date created: 18.12.2014
 * Last change: 22.12.2014
 * Description: Represents a room of the type "normal room".
 */

public class NormalRoom extends AbstractRoom {

	public NormalRoom(int roomID) {
		super(roomID);
	}

	@Override
	public boolean isMagicRoom() {
		return false;
	}
	
	@Override
	public boolean lock() {
		locked = true;
		return locked;
	}

	@Override
	public boolean unlock() {
		locked = false;
		return locked;
	}
	
	@Override
	public boolean isLocked() {
		return locked;
	}
	
	@Override
	public int getRoomID() {
		return roomID;
	}

	@Override
	public void setRoomID(int roomID) {
		super.roomID = roomID;		
	}

	@Override
	public int getNorth() {
		return north;
	}

	@Override
	public void setNorth(int north) {
		super.north = north;
	}

	@Override
	public int getEast() {
		return east;
	}

	@Override
	public void setEast(int east) {
		super.east = east;	
	}

	@Override
	public int getSouth() {
		return south;
	}

	@Override
	public void setSouth(int south) {
		super.south = south;
	}

	@Override
	public int getWest() {
		return west;
	}

	@Override
	public void setWest(int west) {
		super.west = west;
	}
}