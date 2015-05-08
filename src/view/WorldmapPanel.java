package view;

import game.Character;
import game.Settings;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;

import rooms.AbstractRoom;
import rooms.Rooms;

/**
 * WorldmapPanel.java
 * JDK 1.8
 * Eclipse version: Luna Release (4.4.0)
 * @author Daniel Bischoff
 * Date created: 11.01.2015
 * Last change: 07.02.2015
 * Description: Represents the graphical worldmap, draws and redraws it.
 */
public class WorldmapPanel extends JPanel {
	
	private static final long serialVersionUID = 2942877426804635543L;
		
	/**
	 * How many rooms will be displayed in the vertical line.
	 */
	private int mapSizeY = Settings.getMapSizeY();
	
	/**
	 * How myn rooms will be displayed in the horizontal line.
	 */
	private int mapSizeX = Settings.getMapSizeX();
	
	/**
	 * What's the rooms heigth in pixels.
	 */
	private int roomHeigth = Settings.getRoomHeight();
	
	/**
	 * What's the rooms width in pixels.
	 */
	private int roomWidth = Settings.getRoomWidth();
	
	/**
	 * Contains the rooms HashMap.
	 */
	private Rooms rooms;
	
	/**
	 * Contains the JLabels for each map graphic.
	 */
	private JLayeredPane[][] map = new JLayeredPane[mapSizeY][mapSizeX];
	GridLayout gridLayout = new GridLayout(mapSizeY, mapSizeX);
	
	private ImageIcon imgBackground = createImageIcon(Settings.getGraphicBackground(), "Background image");
	private ImageIcon imgRoomA = createImageIcon(Settings.getGraphicRoomA(), "Normal room image");
	private ImageIcon imgRoomB = createImageIcon(Settings.getGraphicRoomB(), "Magic room image");
	private ImageIcon imgPlayer = createImageIcon(Settings.getCharacterGraphic(), "Player image");
	private JLabel playerLabel = new JLabel(imgPlayer);

	/**
	 * WorldmapPanel
	 * Constructor, intializes the worldmap.
	 * @param rooms contains the HashMap of all room objects.
	 */
	public WorldmapPanel (Rooms rooms) {
		this.setLayout(gridLayout);
		this.rooms = rooms;
		initWorldmap();
		drawWorldmap();
		playerLabel.setBounds(0, 0, roomWidth, roomHeigth);
		this.setPreferredSize(new Dimension(Settings.getMapWidth(), Settings.getMapHeight()));
	}
	
	/**
	 * initWorldmap
	 * Creates JLabels for background and all room types; fills the JLabel array "map".
	 */
	private void initWorldmap() {
		for (int y = 0; y < mapSizeY; y++) {
			for (int x = 0; x < mapSizeX; x++) {
				JLayeredPane layeredPane = new JLayeredPane();
				map[y][x] = layeredPane;
				map[y][x].setBounds(0, 0, roomWidth, roomHeigth);
				JLabel background = new JLabel(imgBackground);
				background.setBounds(0, 0, roomWidth, roomHeigth);
				map[y][x].add(background, 0);
			}
		}
		
		for (AbstractRoom room : rooms.values()) {
			if (room.isMagicRoom() == false) {
				JLabel roomA = new JLabel(imgRoomA);
				roomA.setBounds(0, 0, roomWidth, roomHeigth);
				map[room.getLocation().y][room.getLocation().x].removeAll();
				map[room.getLocation().y][room.getLocation().x].add(roomA, 0);
			} else {
				JLabel roomB = new JLabel(imgRoomB);
				roomB.setBounds(0, 0, roomWidth, roomHeigth);
				map[room.getLocation().y][room.getLocation().x].removeAll();
				map[room.getLocation().y][room.getLocation().x].add(roomB, 0);
			}
		}
	}
	
	
	/**
	 * drawWorldmap
	 * Adds all JLabels from the array "map" to the JPanel.
	 */
	public void drawWorldmap() {
		for (int y = 0; y < mapSizeY; y++) {
			for (int x = 0; x < mapSizeX; x++) {
				super.add(map[y][x]);
			}
		}
	}
	
	/**
	 * drawPlayer
	 * Replaces the room icons for the last and the new player position.
	 * @param player necessary to find out the player position.
	 */
	public void drawPlayer(Character player) {
		AbstractRoom lastRoom = player.getLastRoom();
		AbstractRoom currentRoom = player.getCurrentRoom();
					
		if (lastRoom != null) {	
			System.out.println("Previous position: " + lastRoom.getLocation().y + "/" + lastRoom.getLocation().x);

			map[lastRoom.getLocation().y][lastRoom.getLocation().x].remove(playerLabel);
			map[lastRoom.getLocation().y][lastRoom.getLocation().x].repaint();
		}
		
		map[currentRoom.getLocation().y][currentRoom.getLocation().x].add(playerLabel, 1);
		map[currentRoom.getLocation().y][currentRoom.getLocation().x].setPosition(playerLabel, 0);
		
		System.out.println("The player position has beend updated on the map!");
		System.out.println("Current position: " + currentRoom.getLocation().y + "/" + currentRoom.getLocation().x);
	}
	
	/**
	 * refreshCharacterGraphic
	 * Whenever a new character image has been imported, it needs to be replaced on the worldmap with this method.
	 */
	public void refreshCharacterGraphic() {
			this.imgPlayer = createImageIcon(Settings.getCharacterGraphic(), "Player image");
			this.playerLabel.setIcon(imgPlayer);
	}
	
	/**
	 * createImageIcon
	 * Imports an icon from the defined path.
	 * @param path to the icon.
	 * @param description of the icon.
	 * @return ImageIcon object.
	 */
	protected ImageIcon createImageIcon(String path, String description) {
		URL imgURL = getClass().getResource(path);

		if (imgURL != null) {
			return new ImageIcon(imgURL, description);
		} else {		
			System.err.println("Couldn't find file: " + path);
			return null;
		}
	}
}
