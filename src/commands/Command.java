package commands;

/**
 * Command.java
 * JDK 1.8
 * Eclipse version: Luna Release (4.4.0)
 * @author Daniel Bischoff
 * Date created: 07.02.2015
 * Last change: 07.02.2015
 * Description: The interface that is used for all chat commands. Implementation of the "Command"-Pattern.
 */

public interface Command {
	
	/**
	 * execute
	 * The concrete function(s) of the command are defined in this method.
	 */
	public void execute();
	
	/**
	 * getDescription
	 * This method only contains the individual command description.
	 * @return the command description as string.
	 */
	public String getDescription();
}
