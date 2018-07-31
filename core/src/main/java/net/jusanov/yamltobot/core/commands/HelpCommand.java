package net.jusanov.yamltobot.core.commands;

import java.util.ArrayList;

import net.jusanov.yamltobot.core.handler.ConfigHandler;

public class HelpCommand extends Command {

	String commandName;
	ArrayList<String> args;
	
	public HelpCommand(String commandName, ArrayList<String> args) {
		super(commandName, args);
		
		this.commandName = commandName;
		this.args = args;
		
	}

	@Override
	public String onCommandExecuted() {
		
		StringBuilder helpCommand = new StringBuilder();
		ArrayList<String> commands = ConfigHandler.getCommands();
		helpCommand.append(ConfigHandler.getCommandArray(commandName, "message").get(0).replace("%0%", "") + "\n");
		
		for (int i = 0; i < commands.size(); i++) {
			
			String command = commands.get(i);
			String name = ConfigHandler.getString("prefix") + command;
			String desc = "Generic Command";
			
			if (ConfigHandler.getCommandString(command, "description") != null) {
				desc = ConfigHandler.getCommandString(command, "description");
			}
			
			helpCommand.append(ConfigHandler.getCommandArray(commandName, "message").get(1).replace("%1%", "").replace("%cmd%", name).replace("%desc%", desc));
			helpCommand.append("\n");
			
		}
		
		return helpCommand.toString();
		
	}

}
