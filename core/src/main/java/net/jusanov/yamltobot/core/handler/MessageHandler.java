package net.jusanov.yamltobot.core.handler;

import java.util.ArrayList;

import net.jusanov.yamltobot.core.commands.BuiltinCommandHandler;
import net.jusanov.yamltobot.core.common.Module;

/**
 * 
 * The primary class for handling and formatting messages
 * 
 * @author Jusanov
 * @since 2.0.0
 *
 */
public abstract class MessageHandler {

	/**
	 * 
	 * Basic tasks to be executed when a message is executed
	 * 
	 * @param channel The channel where this message was found.
	 * @param author The author of the message.
	 * @param message The message itself.
	 * @since 2.0.0
	 * 
	 */
	public void logMessage(String channel, String author, String message) {
		
		LogHandler.info("[" + channel + "]" + " " + author + ": " + message);
		
		String command = getCommandByMessage(message);
		
		if (command != null) {
			
			LogHandler.debug("Command " + command + " detected!");
			
		}
		
	}
	
	/**
	 * 
	 * Properly handle a message to implement all the configuration options of YamlToBot
	 * 
	 * @param module The module that this is handling a message for.
	 * @param channel The channel where this message was found.
	 * @param author The author of the message.
	 * @param message The message itself.
	 * @return What the command returns, or null if the command is not found.
	 * @since 2.0.0
	 * 
	 */
	public String handleMessage(Module module, String channel, String author, String message) {
		
		logMessage(channel, author, message);
		
		String command = getCommandByMessage(message);
		
		if (command != null) {
			
			if (ConfigHandler.getCommandBoolean(command, "enabled", true)) {

				if (predefinedFunctionEnabled(command)) return predefinedFunction(command);
				else return command(command);
				
			}
			
		}
		
		return null;
		
	}
	
	public boolean embedEnabled(String command) {
		
		return ConfigHandler.getCommandMappingBoolean(command, "embed", "enabled", false);
		
	}
	
	public boolean predefinedFunctionEnabled(String command) {
		
		if (ConfigHandler.getCommandString(command, "predefined-function", null) != null) return true;
		else return false;
		
	}
	
	public String getCommandByMessage(String message) {
		
		ArrayList<String> commands = ConfigHandler.getCommands();
		
		for (String command : commands) {
			
			if (message.startsWith(ConfigHandler.getString("prefix", "") + command)) return command;
			
		}
		
		return null;
		
	}
	
	public String command(String command) {

		StringBuilder messageBuilder = new StringBuilder();
		ArrayList<String> message = ConfigHandler.getCommandArray(command, "message");
		
		for (int i = 0; i < message.size(); i++) messageBuilder.append(message.get(i) + "\n");
		
		return messageBuilder.toString();
		
	}
	
	public String predefinedFunction(String command) {
		try {
			return BuiltinCommandHandler.getCommand(ConfigHandler.getCommandString(command, "predefined-function", null), command, new ArrayList<String>()).toString();
		} catch (SecurityException e) {
			e.printStackTrace();
		}
		return null;
	}
	
}
