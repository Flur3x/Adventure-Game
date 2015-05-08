package commands;

/**
 * ExitCommand.java
 * JDK 1.8
 * Eclipse version: Luna Release (4.4.0)
 * @author Daniel Bischoff
 * Date created: 07.02.2015
 * Last change: 07.02.2015
 * Description: A single chat command that closes the application. Implementation of the "Command"-Pattern.
 */

public class ExitCommand implements Command {

	/**
	 * execute
	 * Closes the application.
	 */
	@Override
	public void execute() {
		System.exit(0);
	}

	/**
	 * getDescription
	 * This method only contains the individual command description.
	 * @return the command description as string.
	 */
	@Override
	public String getDescription() {
		return "to close the game";
	}
}
