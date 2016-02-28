package game;

import java.io.BufferedReader;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Random;
import java.util.Set;

import rooms.AbstractRoom;
import rooms.NormalRoom;
import rooms.MagicRoom;
import rooms.NormalRoomFactory;
import rooms.MagicRoomFactory;
import rooms.Rooms;
import view.GameView;
import commands.CommandList;

/**
 * Game.java
 * JDK 1.8
 * Eclipse version: Luna Release (4.4.0)
 * @author Daniel Bischoff
 * Date created: 11.11.2014
 * Last change: 07.02.2015
 * Description: Initializes the game, handles all operations.
 */

public class Game extends Observable {
	
	/**
	 * Initializes the GUI.
	 */
	private GameView view;
	
	/**
	 * HashMap that contains all room objects.
	 */
	private Rooms rooms;
	
	/**
	 * Character object which contains the character position.
	 */
	private Character player;
	
	/**
	 * Initialize NormalRoomFactory to create normal rooms.
	 */
	private NormalRoomFactory normalFactory;
	
	/**
	 * Initialize MagicRoomFactory to create magic rooms.
	 */
	private MagicRoomFactory magicFactory;
	
	/*
	 * Saves roomIDs of all rooms that have been assigned to an absolute location already (for game initialization only).
	 */
	private ArrayList<Integer> checked = new ArrayList<Integer>();
	
	/*
	 * Loads the HashMap for chat commands ("Command"-Pattern).
	 */
	private CommandList commandList = new CommandList();

	/**
	 * Game
	 * Constructor, initializes a completely new game.
	 */
	public Game() {
		Settings.setController(this);
		view = new GameView();
		rooms = new Rooms();
		player = new Character();
		normalFactory = NormalRoomFactory.getInstance();
		magicFactory = MagicRoomFactory.getInstance();
		importWorld(Settings.getWorldFile()); // Import all normal rooms and their connections from the world file
		createMagicRooms(); // Let's convert some rooms to magic rooms!
		setCoordinates(); // Assign an absolute location (Point Class) for each room object
		createPlayer(); // Set a random starting position for the player
		this.addObserver(view); // Set the GUI as Observer
		showGameData(); // Developer debug routine to check if the game has been completely initalized
		view.loadGUI(); // Load the GUI completely (it was only initalized to show error messages before)
		updatePlayerOnMap(); // Tell all Observers (GUI) to draw the current player position
		consoleOutput("The game has started! Type in /help to show all available chat commands."); // Displays text on the GUI console
	}

	/**
	 * importWorld
	 * Reads the selected world file and calls the methods to initialize the rooms and their conections.
	 * @param worldPath includes the name and path of the world file.
	 */
	private void importWorld(String worldPath) {	
		System.out.println(worldPath);
		String line;
				
		// Import & read the world file twice, once to initialize the rooms and once to connect them
		for (int i = 0; i < 2; i++) {

			InputStream worldFileStream = this.getClass().getResourceAsStream(worldPath);
	    	BufferedReader br = new BufferedReader(new InputStreamReader(worldFileStream));
			
			// Read the world file line by line, split the Strings and call the room initialization methods
		    try {
		        if (worldFileStream != null) {       
		        	br.readLine(); // Skip the first line
					while ((line = br.readLine()) != null) {
						if (i == 0) {
							String[] splittedLine = line.split(":");
							int roomID = Integer.parseInt(splittedLine[0]);
							initRoom(roomID);
						} else if (i == 1) {
							String[] splittedLine = line.split(":");
							int roomID = Integer.parseInt(splittedLine[0]);
							int north = Integer.parseInt(splittedLine[1]);
							int east = Integer.parseInt(splittedLine[2]);
							int south = Integer.parseInt(splittedLine[3]);
							int west = Integer.parseInt(splittedLine[4]);
							initRoomConnections(roomID, north, east, south, west);
						}
					}           
		        }
			} catch (IOException e1) {
				System.err.println("Couldn't read the world file!");
				e1.printStackTrace();
			} catch (NumberFormatException e2) {
				System.err.println("The world file contains an unknown format!" + worldPath);
				e2.printStackTrace();
				showError("The world file contains an unknown format!");
				System.exit(0);
			} finally {
			    try {
					br.close();
					worldFileStream.close();
				} catch (IOException e1) {
					System.err.println("Couldn't finish the world file import!");
					e1.printStackTrace();
				}
			}
		}
	}

	/**
	 * initRoom
	 * Constructs a room object for each room.
	 * @param roomID, the roomID that is defined within the world file.
	 */
	private void initRoom(int roomID) {	
		NormalRoom room = (NormalRoom) normalFactory.createRoom(roomID); // RoomA = Normal room
		room.getLocation().setLocation(0, 0); // Init the location
		rooms.put(roomID, room); // Add room to the HashMap
	}
	
	/**
	 * initRoomConnections
	 * Fills each room object with ID's of it's neighbor rooms.
	 * @param roomID, ID of the selected room
	 * @param north, ID of the room at the north side
	 * @param east, ID of the room at the east side
	 * @param south, ID of the room at the south side
	 * @param west, ID of the room at the west side
	 */
	private void initRoomConnections(int roomID, int north, int east, int south, int west) {
		NormalRoom room = (NormalRoom) rooms.get(roomID); // RoomA = Normal room
		room.setNorth(north);
		room.setEast(east);
		room.setSouth(south);
		room.setWest(west);
	}
	
	
	/**
	 * getRooms
	 * @return the object that contains the rooms HashMap.
	 */
	public Rooms getRooms() {
		return this.rooms;	
	}
	
	/**
	 * setCoordinates
	 * Calculates the location (x,y) for each room object that has been created before.
	 */
	private void setCoordinates() {
		
		// 1. Begin with the first room and set Point (location) to the highest value possible (depending on max. amount of rooms).
		ArrayList<Integer> keys = new ArrayList<Integer> (rooms.keySet());
		int	randomKey = keys.get(new Random().nextInt(keys.size()));
		
		AbstractRoom room = rooms.get(randomKey);
		room.setLocation(rooms.size(), rooms.size());
		
		// 2. Tunnel through each neighbors and set their Points (setLocation) aswell.
		checked.add(room.getRoomID()); // Mark this room as "checked" to avoid an infinite loop.
		checkNeighborRooms(room);
		
		// 3. Find the lowest Y and the lowest X within all rooms.
		int lowestX = rooms.size();
		int lowestY = rooms.size();
		
		for (AbstractRoom selectedRoom : rooms.values()) {
			if ((int) selectedRoom.getLocation().getX() < lowestX) {
				lowestX = (int) selectedRoom.getLocation().getX();
			}
			
			if ((int) selectedRoom.getLocation().getY() < lowestY) {
				lowestY = (int) selectedRoom.getLocation().getY();
			}	
		}
		
		// 4. Calculate the distance between the lowest X/Y and the location 1/1.
		int distanceX = lowestX-1;
		int distanceY = lowestY-1;
				
		// 5. Normalize all room locations by moving them all together, so that the lowest Y = 1 and the lowest X = 1.
		for (AbstractRoom selectedRoom : rooms.values()) {
			int x = (int) selectedRoom.getLocation().getX();
			int y = (int) selectedRoom.getLocation().getY();
			selectedRoom.setLocation((x-distanceX), (y-distanceY));
		}
	}
	
	/**
	 * checkNeighborRooms
	 * Checks every room for neighbors and their own neighbors as well, to set the locations.
	 * @param room contains the initial room to start with.
	 */
	private void checkNeighborRooms(AbstractRoom room) {
		if (room.getNorth() != 0 && !checked.contains(room.getNorth())) {
			AbstractRoom northRoom = rooms.get(room.getNorth());
			northRoom.setLocation(room.getLocation().getX(), (room.getLocation().getY()-1));
			checked.add(northRoom.getRoomID()); // Mark this room as "checked" to avoid an infinite loop
			checkNeighborRooms(northRoom); // Now, check this room's neighbors
		}
		
		if (room.getEast() != 0 && !checked.contains(room.getEast())) {
			AbstractRoom eastRoom = rooms.get(room.getEast());
			eastRoom.setLocation((room.getLocation().getX()+1), room.getLocation().getY());
			checked.add(eastRoom.getRoomID());
			checkNeighborRooms(eastRoom);
		}
		
		if (room.getSouth() != 0 && !checked.contains(room.getSouth())) {
			AbstractRoom southRoom = rooms.get(room.getSouth());
			southRoom.setLocation(room.getLocation().getX(), (room.getLocation().getY()+1));
			checked.add(southRoom.getRoomID());
			checkNeighborRooms(southRoom);
		}
		
		if (room.getWest() != 0 && !checked.contains(room.getWest())) {
			AbstractRoom westRoom = rooms.get(room.getWest());
			westRoom.setLocation((room.getLocation().getX()-1), room.getLocation().getY());
			checked.add(westRoom.getRoomID());
			checkNeighborRooms(westRoom);
		}
	}
	
	/**
	 * createPlayer
	 * Creates the player at a random position. 
	 */
	private void createPlayer() {
		Random rnd = new Random();
		Integer[] roomIDs = rooms.keySet().toArray(new Integer[0]);
		Integer roomID = roomIDs[rnd.nextInt(roomIDs.length)];

		// As long as random room is a magic room, search for another room
		while (rooms.get(roomID).isMagicRoom()) {
			roomID = roomIDs[rnd.nextInt(roomIDs.length)];
		}
		
		// Finally, set the player position
		player.setCurrentRoom(rooms.get(roomID));
	}
	
	/**
	 * getQuestion
	 * Imports the questions file, saves all lines and returns one random line as result.
	 * @param questionsFile is the name and path of the file that contains all questions and answers (for magic rooms).
	 * @return the final question and answer a String line.
	 */
	private String getQuestion(String questionsFile) {
		InputStream fileURL = getClass().getResourceAsStream(questionsFile);

		if (fileURL == null) {	
			System.err.println("Couldn't find file: " + questionsFile);
		}
		
		BufferedReader br = null;;
		String charset = "UTF-8";
		ArrayList<String> list = new ArrayList<String>(); // Contains all lines of the file (all questions and answers)
		String line = ""; // Contains the temporary line (needed for the FileReader)
		
		// Try to find & read the questions file
		try {
			br = new BufferedReader(new InputStreamReader(fileURL, charset));
		} catch (UnsupportedEncodingException e) {
			System.err.println("The encoding of the question file is not supported!");
			e.printStackTrace();
		}
		
		// Read the questions file line by line
	    try {
	    	br.readLine(); // Skip the first line
			while ((line = br.readLine()) != null) {
					list.add(line);
			}
		} catch (IOException e1) {
			System.err.println("Couldn't read the question file!");
			e1.printStackTrace();
		} finally {
		    try {
				br.close();
			} catch (IOException e1) {
				System.err.println("Couldn't finish the question file import!");
				e1.printStackTrace();
			}
		}
	    
	    String randomLine = list.get(new Random().nextInt(list.size())); // Contains the random selected question/answer for the return value
	    
		return randomLine;
	}
	
	/**
	 * createMagicRooms
	 * Converts N rooms to magic rooms, by replacing the object RoomA to RoomB.
	 */
	private void createMagicRooms() {
		for (int i = 0; i < 2; i++) {

			Integer[] roomIDs = rooms.keySet().toArray(new Integer[0]);
			Integer roomID = roomIDs[new Random().nextInt(roomIDs.length)];

			if (rooms.get((int) roomID).isMagicRoom()) { // If the selected room is a magic room already, start the loop again
				if (i != 0) {
						i = i - 1;
					}
				continue;
			} else {
				int north =	rooms.get(roomID).getNorth(); // Transfer important data from RoomA to the new RoomB
				int east =	rooms.get(roomID).getEast();
				int south =	rooms.get(roomID).getSouth();
				int west =	rooms.get(roomID).getWest();
				MagicRoom room = (MagicRoom) magicFactory.createRoom((int) roomID); // Initalize new RoomB = Magic room
				rooms.put(roomID, room); // Save the new magic room on the map
				room.setNorth(north); // Set the same variables as for the previous object (RoomA)
				room.setEast(east);
				room.setSouth(south);
				room.setWest(west);
				
				String randomQuestion = getQuestion(Settings.getQuestions()); // Import one random question/answer combination
				String[] splittedLine = randomQuestion.split(":"); // Split the question from the answer
				
				((MagicRoom) rooms.get(roomID)).setQuestion(splittedLine[0]); // Set the question for this room
				((MagicRoom) rooms.get(roomID)).setAnswer(splittedLine[1]); // Set the answer for this room
				((MagicRoom) rooms.get(roomID)).lock(); // Lock the room, so that it's locked till the question could be answered
			}
		}
	}
	
	/**
	 * showGameData
	 * Developer test routine to check if the game has been completely initalized.
	 */
	private void showGameData() {
		rooms.showAllRooms(); // Test if all rooms have been imported (show all imported room ID's)
		System.out.println();
		System.out.println(rooms.size() + " rooms have been created!");
		System.out.print("All room id's: "); rooms.showAllRoomIDs(); // Test if all roomIDs can be found
		System.out.println("Player position: " + player.getCurrentRoom().getRoomID()); // Check the payer position	
		System.out.println();
	}

	/**
	 * updatePlayerOnMap
	 * Notify all observers that the player object has been changed.
	 */
	private void updatePlayerOnMap() {
        if (countObservers()>0){
            setChanged(); 
            notifyObservers(player); 
        }
	}
	
	/**
	 * buttonEvent
	 * Handles the ActionEvents caused by direction button clicks.
	 * @param direction contains the int value for the direction (1-4, north to west).
	 */
	public void buttonEvent(int direction) {
		if(direction == 1) { 
            System.out.println("Button click!");
			int nextRoom = player.getCurrentRoom().getNorth();
    		checkRoom(nextRoom);
		} else if (direction == 2) { 
			System.out.println("Button click!"); 
			int nextRoom = player.getCurrentRoom().getEast();
    		checkRoom(nextRoom);
		} else if (direction == 3) {
			System.out.println("Button click!"); 
			int nextRoom = player.getCurrentRoom().getSouth();
    		checkRoom(nextRoom);
		} else if (direction == 4) {
			System.out.println("Button click!"); 
			int nextRoom = player.getCurrentRoom().getWest();
    		checkRoom(nextRoom);
		}
	}
	
	/**
	 * checkRoom
	 * Checks if the selected room does exists and if yes, calls the walkTo-method.
	 * @param nextRoom contains the roomID.
	 */
	public void checkRoom(int nextRoom) {
		if (nextRoom == 0) {
			System.out.println("There's no other room in this direction!");
			consoleOutput("There's no other room in this direction!");
		} else {
			walkTo(nextRoom);
		}
	}

	/**
	 * walkTo
	 * Checks the room where the player wants to got to. If it's magic, the player has to answer the question.
	 * @param nextRoom contains the roomID.
	 */
	private void walkTo(int nextRoom) {
		AbstractRoom room = rooms.get(nextRoom);
		
		if (room.isMagicRoom() && room.isLocked()) { // If magic room, let's answer the question first
			System.out.println(((MagicRoom) room).getQuestion());
			Boolean answered = view.getAnswer((MagicRoom) room);
			if (answered == true) {
				player.setLastRoom(player.getCurrentRoom());
				player.setCurrentRoom(room);
				updatePlayerOnMap();
				room.unlock();
				consoleOutput("Congratulations! You've successfully unlocked this magic room.");
			}
		} else { // Walk to the new room
			player.setLastRoom(player.getCurrentRoom());
			player.setCurrentRoom(room);
			updatePlayerOnMap();
		}
	}
	
	/**
	 * importTextFile
	 * Import a text file and return it as String.
	 * @param textFile contains the file path and name.
	 * @return the text file content as String.
	 */
	public String importTextFile(String textFile) {
		InputStream textFileStream = this.getClass().getResourceAsStream(textFile);
    	BufferedReader br = new BufferedReader(new InputStreamReader(textFileStream));
		StringBuffer buffer = new StringBuffer();
		String line;
		String finalString = null;

	    try {
	        if (textFileStream != null) {       
				while ((line = br.readLine()) != null) {
					buffer.append(line + "\n");
				}           
	        }
		} catch (IOException e1) {
			System.err.println("Couldn't read the text file!");
			e1.printStackTrace();
		} catch (NumberFormatException e2) {
			System.err.println("The text file contains an unknown format!" + textFile);
			e2.printStackTrace();
			showError("Couldn't load an internal file.");
			System.exit(0);
		} finally {
		    try {
		    	br.close();
				textFileStream.close();
			} catch (IOException e1) {
				System.err.println("Couldn't finish the text import!");
				e1.printStackTrace();
			}
		}
	    finalString = buffer.toString();
		return finalString;
	}

	/**
	 * resetGame
	 * Set the existing GUI to unvisible and start a new game.
	 */
	public void resetGame() {
		view.setVisible(false);
		Main.startGame();
	}

	/**
	 * closeGame
	 * Close the game.
	 */
	public void closeGame() {
		System.exit(0);
	}

	/**
	 * changeWorld
	 * Change the temporary world file to the file that the player has imported and restart the game with it.
	 * @param worldFile
	 */
	public void changeWorld(String worldFile) {
		Settings.setWorldFile(worldFile);
		resetGame();
	}

	/**
	 * changeCharacter
	 * Switch the character graphic during runtime.
	 * @param characterFile contains the image path and name.
	 */
	public void changeCharacter(String name, String characterFile) {
		Settings.setCharacterGraphic(characterFile);
    	view.refreshCharacterGraphic();
		System.out.println("Character loaded: " + characterFile);
    	consoleOutput("Character loaded: " + name);
	}

	public void consoleInput(String string) {
		if (string.startsWith("/")) {
			if (commandList.get(string) != null) {
				commandList.execute(string);
			} else {
				consoleOutput("Please use a valid command:\n" + getCommandOverview());
			}
		} else {
			consoleOutput(string);
		}
	}
	
	/**
	 * getCommandOverview
	 * Lists all chat commands on the console.
	 * @return a string of all available chat commands.
	 */
	public String getCommandOverview() {
		return commandList.getOverview();
	}
	
	/**
	 * showAllCommands
	 * Lists all chat commands on the chat/console.
	 */
	public void showAllCommands() {
		consoleOutput("Available commands:\n" + commandList.getOverview());
	}
	
	/**
	 * consoleOutput
	 * Sends a string to the view, which displays it on the console.
	 * @param string that should be displayed on the console.
	 */
	public void consoleOutput(String string) {
		view.consoleOutput(string);
	}
	
	/**
	 * showError
	 * Opens a error popup in the view with the defined string.
	 * @param string contains the error message.
	 */
	private void showError(String string) {
		view.showError(string);
	}
	
	/**
	 * loadCharacter
	 * Opens the character selection popup.
	 */
	public void loadCharacter() {
		view.loadCharacter();
	}
}