package game;

/**
 * Settings.java
 * JDK 1.8
 * Eclipse version: Luna Release (4.4.0)
 * @author Daniel Bischoff
 * Date created: 17.11.2014
 * Last change: 07.02.2015
 * Description: Contains all game settings as static attributes.
 */

public class Settings {
	
	private static final String gameTitle = "Adventure Game";
	private static Game controller;
	private static final int guiWidth = 842;
	private static final int guiHeigth = 679;
	private static final int mapSizeX = 17;
	private static final int mapSizeY = 11;
	private static final int roomWidth = 48;
	private static final int roomHeigth = 48;
	private static final int mapWidth = (mapSizeX * roomWidth);
	private static final int mapHeight = (mapSizeY * roomHeigth);
	private static String worldFilePath = "/worlds/";
	private static String worldFile = "/worlds/sleepingdragon.txt";
	private static String tmpWorldFile = "/worlds/sleepingdragon.txt";
	private static final String questions = "/questions/default_questions.txt";
	private static final String textFileHelp = "/textfiles/help.txt";
	private static final String textFileAuthors = "/textfiles/authors.txt";
	private static final String graphicBackground = "/graphics/tiles/background.png";
	private static final String graphicNormalRoom = "/graphics/tiles/normal.png";
	private static final String graphicMagicRoom = "/graphics/tiles/magic.png";
	private static String characterGraphic = "/graphics/characters/luke.png";
	private static final String[] characters = {"Luke", "Lilly"};
	private static String charactersFilePath = "/graphics/characters/";
	private static final String charactersImageFormat = ".png";
	
	public static String getGameTitle() {
		return gameTitle;
	}
	
	public static String getWorldFile() {
		return worldFile;
	}
	
	public static void setWorldFile(String worldFile) {
		Settings.worldFile = worldFile;
	}
	
	public static String getWorldFilePath() {
		return worldFilePath;
	}
	
	public static String getCharactersFilePath() {
		return charactersFilePath;
	}

	public static String getGraphicBackground() {
		return graphicBackground;
	}

	public static String getGraphicRoomA() {
		return graphicNormalRoom;
	}

	public static String getGraphicRoomB() {
		return graphicMagicRoom;
	}

	public static int getMapSizeX() {
		return mapSizeX;
	}

	public static int getMapSizeY() {
		return mapSizeY;
	}

	public static int getRoomWidth() {
		return roomWidth;
	}

	public static int getRoomHeight() {
		return roomHeigth;
	}

	public static int getGuiWidth() {
		return guiWidth;
	}

	public static int getGuiHeigth() {
		return guiHeigth;
	}

	public static String getTextFileHelp() {
		return textFileHelp;
	}

	public static String getTextFileAuthors() {
		return textFileAuthors;
	}

	public static String getCharacterGraphic() {
		return characterGraphic;
	}

	public static void setCharacterGraphic(String characterGraphic) {
		Settings.characterGraphic = characterGraphic;
	}

	public static int getMapWidth() {
		return mapWidth;
	}

	public static int getMapHeight() {
		return mapHeight;
	}

	public static String getTmpWorldFile() {
		return tmpWorldFile;
	}

	public static void setTmpWorldFile(String tmpWorldFile) {
		Settings.tmpWorldFile = tmpWorldFile;
	}

	public static String[] getCharacters() {
		return characters;
	}

	public static String getCharactersImageFormat() {
		return charactersImageFormat;
	}

	public static Game getController() {
		return controller;
	}

	public static void setController(Game controller) {
		Settings.controller = controller;
	}

	public static String getQuestions() {
		return questions;
	}
}