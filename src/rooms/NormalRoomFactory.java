package rooms;

/**
 * NormalRoomFactory.java
 * JDK 1.8
 * Eclipse version: Luna Release (4.4.0)
 * @author Daniel Bischoff
 * Date created: 19.12.2014
 * Last change: 22.12.2014
 * Description: Represents the factory for a normal room. Implementation with the Abstract Factory pattern.
 */

public class NormalRoomFactory extends AbstractFactory {
	
	/**
	 * Contains the instance of itself; Singleton pattern.
	 */
	private static NormalRoomFactory instance;
	
	/**
	 * RoomFactoryA
	 * Empty constructor; Singleton pattern.
	 */
	private NormalRoomFactory() {
		
	}
	
	/**
	 * getInstance
	 * Singleton; takes care that only one instance of this object exists at the same time.
	 * @return the object.
	 */
	public static NormalRoomFactory getInstance() {
		if (NormalRoomFactory.instance == null) {
			NormalRoomFactory.instance = new NormalRoomFactory();
		}
		return instance;
	}

	/**
	 * createRoom
	 * Creates a new room object.
	 */
	@Override
	public AbstractRoom createRoom(int roomID) {
		
		NormalRoom room = new NormalRoom(roomID);
		return room;
	}
}