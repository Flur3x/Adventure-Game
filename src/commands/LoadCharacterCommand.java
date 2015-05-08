package commands;

import game.Settings;

/**
 * LoadCharacterCommand.java
 * JDK 1.8
 * Eclipse version: Luna Release (4.4.0)
 * @author Daniel Bischoff
 * Date created: 07.02.2015
 * Last change: 07.02.2015
 * Description: A single chat command that opens the character selection. Implementation of the "Command"-Pattern.
 */

public class LoadCharacterCommand implements Command {

	/**
	 * execute
	 * Opens the character selection.
	 */
	@Override
	public void execute() {		
		Settings.getController().loadCharacter();
	}

	/**
	 * getDescription
	 * This method only contains the individual command description.
	 * @return the command description as string.
	 */
	@Override
	public String getDescription() {
		return "to change your character";
	}
}
