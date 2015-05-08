package commands;

import java.util.HashMap;

/**
 * CommandList.java
 * JDK 1.8
 * Eclipse version: Luna Release (4.4.0)
 * @author Daniel Bischoff
 * Date created: 07.02.2015
 * Last change: 07.02.2015
 * Description: The controller for chat commands. Contains the HashMap that contains the commands as well as methods to manage them. Implementation of the "Command"-Pattern.
 */

public class CommandList {
	
	private HashMap <String, Command> commands = new HashMap <String, Command>();

	/**
	 * CommandList
	 * Constructor. Saves the available chat commands in a HashMap for later use.
	 */
	public CommandList() {
		commands.put("/exit", new ExitCommand());
		commands.put("/dance", new DanceCommand());
		commands.put("/help", new HelpCommand());
		commands.put("/char", new LoadCharacterCommand());
	}

	/**
	 * get
	 * Method to check if the user input really is a valid command.
	 * @param string is the command/string the user has typed in (user input).
	 * @return the command object, if the user input was valid. Otherwise "null".
	 */
	public Command get(String string) {
		return commands.get(string);
	}
	
	/**
	 * execute
	 * Searches the called command in the HasMap and executes it.
	 * @param string
	 */
	public void execute(String string) {
		commands.get(string).execute();
	}
	
	/**
	 * getOverview
	 * A list of all saved commands will be displayed on the console.
	 * @return a list of all chat commands as string.
	 */
	public String getOverview() {
		String overview = "";
		
			for (HashMap.Entry<String, Command> command : commands.entrySet()){
			  overview += "  " + command.getKey() + " (" + command.getValue().getDescription() + ") \n";
			}
			
		return overview;
	}
}
