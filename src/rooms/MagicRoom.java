package rooms;

/**
 * MagicRoom.java
 * JDK 1.8
 * Eclipse version: Luna Release (4.4.0)
 * @author Daniel Bischoff
 * Date created: 18.12.2014
 * Last change: 22.12.2014
 * Description: Represents a room of the type "magic room". The player has to answer a random question in order to walk to the position of this object.
 */

public class MagicRoom extends AbstractRoom {
	
	/**
	 * Contains the question that must be answered by the player.
	 */
	private String question;
	
	/**
	 * Contains the answer of the question of this room.
	 */
	private String answer;

	public MagicRoom(int roomID) {
		super(roomID);
	}
	
	@Override
	public boolean isMagicRoom() {
		return true;
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
	
	public String getQuestion() {
		return question;
	}

	public void setQuestion(String question) {
		this.question = question;		
	}

	public String getAnswer() {
		return answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
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