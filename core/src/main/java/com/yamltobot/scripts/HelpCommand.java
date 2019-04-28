package com.yamltobot.scripts;

import com.yamltobot.core.commands.Command;

import java.util.ArrayList;

/**
 * 
 * HelpCommand script. Lists all other registered commands that are enabled.
 *
 * @author Justin Schaaf
 * @since 1.0.0
 *
 */
public class HelpCommand extends Script {
	
	public HelpCommand() {
		super();
	}

	@Override
	public void onScriptRun(ArrayList<String> args) {

		StringBuilder helpCommand = new StringBuilder();
		ArrayList<Command> commands = botHandler.getCommands();
		
		for (int i = 0; i < commands.size(); i++) {

			Command command = commands.get(i);
			String name = botHandler.getConfigHandler().getConfig().getString("prefix", "") + command.getName();
			String usage = command.getObject().getString("usage", null);
			String desc = command.getDesc();
			boolean enabled = command.getEnabled();
			
			if (enabled == false) continue;

			if (usage != null) name = usage;

			if (args.size() > 1) {

				if (command.getName().equalsIgnoreCase(args.get(1))) {

					helpCommand.append(botHandler.getConfigHandler().getCommand(args.get(0)).getArray("message").get(2).replaceAll("%cmd%", name).replaceAll("%desc%", desc));
					break;

				} else {
					continue;
				}

			} else {
				if (i == 0) helpCommand.append(botHandler.getConfigHandler().getCommand(args.get(0)).getArray("message").get(0) + "\n");
				helpCommand.append(botHandler.getConfigHandler().getCommand(args.get(0)).getArray("message").get(1).replaceAll("%cmd%", name).replaceAll("%desc%", desc));
				helpCommand.append("\n");
			}
			
		}

		if (!args.isEmpty()) if (helpCommand.toString().trim().length() == 0) {
			messageHandler.sendMessage("Sorry, the command you were looking for could not be found. ( ◡︵◡)");
			return;
		}

		messageHandler.sendMessage(botHandler.getVariableHandler().formatMessage(helpCommand.toString()));
		
	}

}
