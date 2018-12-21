package com.justinschaaf.yamltobot.core.commands;

import java.util.ArrayList;

import com.justinschaaf.yamltobot.core.handler.ConfigHandler;

/**
 * 
 * Predefined help command. Lists all other registered commands that are enabled.
 * 
 * @author Justin Schaaf
 * @since 1.0.0
 *
 */
public class HelpCommand extends Command {
	
	public HelpCommand() {
		super();
	}

	@Override
	public String onCommandExecuted(String commandName) {
		
		StringBuilder helpCommand = new StringBuilder();
		ArrayList<String> commands = ConfigHandler.getCommands();
		helpCommand.append(ConfigHandler.getCommandArray(commandName, "message").get(0) + "\n");
		
		for (int i = 0; i < commands.size(); i++) {
			
			String command = commands.get(i);
			String name = ConfigHandler.getString("prefix", "") + command;
			String usage = ConfigHandler.getCommandString(command, "usage", null);
			String desc = "Generic Command";
			boolean enabled = ConfigHandler.getCommandBoolean(command, "enabled", true);
			
			if (enabled == false) continue;
			
			if (usage != null) name = usage;
			
			if (ConfigHandler.getCommandString(command, "description", null) != null) {
				desc = ConfigHandler.getCommandString(command, "description", null);
			}
			
			helpCommand.append(ConfigHandler.getCommandArray(commandName, "message").get(1).replace("%cmd%", name).replace("%desc%", desc));
			helpCommand.append("\n");
			
		}
		
		return helpCommand.toString();
		
	}

}
