package rooms;

/**
 * MagicRoomFactory.java
 * JDK 1.8
 * Eclipse version: Luna Release (4.4.0)
 * @author Daniel Bischoff
 * Date created: 19.12.2014
 * Last change: 22.12.2014
 * Description: Represents the factory for a magic room. Implementation with the Abstract Factory pattern.
 */

public class MagicRoomFactory extends AbstractFactory {

	/**
	 * Contains the instance of itself; Singleton pattern.
	 */
	private static MagicRoomFactory instance;
	
	/**
	 * RoomFactoryB
	 * Empty constructor; Singleton pattern.
	 */
	private MagicRoomFactory() {
		
	}
	
	/**
	 * getInstance
	 * Singleton; takes care that only one instance of this object exists.
	 * @return the object
	 */
	public static MagicRoomFactory getInstance() {
		if (MagicRoomFactory.instance == null) {
			MagicRoomFactory.instance = new MagicRoomFactory();
		}
		return instance;
	}
	
	@Override
	public AbstractRoom createRoom(int roomID) {

		MagicRoom room = new MagicRoom(roomID);
		return room;
	}
}