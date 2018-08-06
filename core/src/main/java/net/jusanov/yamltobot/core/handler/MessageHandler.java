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
	public String handleMessage (Module module, String channel, String author, String message) {
		
		LogHandler.info("[" + channel + "]" + " " + author + ": " + message);
		
		ArrayList<String> commands = ConfigHandler.getCommands();
		
		for (int i = 0; i < commands.size(); i++) {
			
			String command = commands.get(i);
			
			if (message.startsWith(ConfigHandler.getString("prefix") + command)) {
				
				LogHandler.debug("Command " + command + " detected!");
				
				if (ConfigHandler.getCommandBoolean(command, "enabled", true) == false) {
					
					continue;
					
				} else {
					
					if (ConfigHandler.getCommandBoolean(command, "builtin", false) == true) {
						
						try {
							return BuiltinCommandHandler.getCommand(ConfigHandler.getCommandString(command, "predefined-function"), command, new ArrayList<String>()).toString();
						} catch (SecurityException e) {
							e.printStackTrace();
						}
						
					} else {
						
						StringBuilder messageBuilder = new StringBuilder();
						
						ArrayList<String> newMessage = ConfigHandler.getCommandArray(command, "message");
						
						for (int j = 0; j < newMessage.size(); j++) {
							
							messageBuilder.append(newMessage.get(j).replace("%" + i + "%", "") + "\n");
							
						}
						
						return messageBuilder.toString();
					
						
					}
					
				}
				
			}
			
		}
		
		return null;
		
	}
	
}
