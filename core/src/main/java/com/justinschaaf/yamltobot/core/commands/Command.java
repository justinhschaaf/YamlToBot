package com.justinschaaf.yamltobot.core.commands;

/**
 * 
 * The primary class for predefined commands.
 * 
 * @author Justin Schaaf
 * @since 1.0.0
 *
 */
public abstract class Command {
	
	public Command() {
		
	}
	
	/**
	 * 
	 * The function to be executed when the command is executed
	 * 
	 * @param commandName The name of the command executed, e.g. help
	 * @return The string message that the command returns
	 * @since 1.0.0
	 * 
	 */
	public abstract String onCommandExecuted(String commandName);
	
}
