package commands;

import game.Settings;

/**
 * HelpCommand.java
 * JDK 1.8
 * Eclipse version: Luna Release (4.4.0)
 * @author Daniel Bischoff
 * Date created: 07.02.2015
 * Last change: 07.02.2015
 * Description: A single chat command that displays a string on the console. Implementation of the "Command"-Pattern.
 */

public class HelpCommand implements Command {

	/**
	 * execute
	 * Displays all available commands on the console.
	 */
	@Override
	public void execute() {
		Settings.getController().showAllCommands();;
	}

	/**
	 * getDescription
	 * This method only contains the individual command description.
	 * @return the command description as string.
	 */
	@Override
	public String getDescription() {
		return "shows all available commands";
	}
}
