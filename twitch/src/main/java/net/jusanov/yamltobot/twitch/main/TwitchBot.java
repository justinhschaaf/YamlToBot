package net.jusanov.yamltobot.twitch.main;

import java.util.ArrayList;

import org.jibble.pircbot.PircBot;

import net.jusanov.yamltobot.core.commands.HelpCommand;
import net.jusanov.yamltobot.core.handler.ConfigHandler;
import net.jusanov.yamltobot.core.handler.LogHandler;

public class TwitchBot extends PircBot {
	
	public TwitchBot(String botname) {
		
		this.setName(botname);
		this.isConnected();
		return;
		
	}
	
	public void onMessage(String channel, String sender, String login, String hostname, String message) {
		
		LogHandler.info("[" + channel + "]" + sender + ": " + message);
		
		
		ArrayList<String> commands = ConfigHandler.getCommands();
		
		for (int i = 0; i < commands.size(); i++) {
			
			String command = commands.get(i);
			
			if (message.startsWith(ConfigHandler.getString("prefix") + command)) {
				
				LogHandler.debug("Command " + command + " detected!");
				
				if (ConfigHandler.getCommandBoolean(command, "enabled", true) == false) {
					
					continue;
					
				} else {
					
					if (ConfigHandler.getCommandBoolean(command, "builtin", false) == true) {
						
						if (ConfigHandler.getCommandString(command, "predefined-function").equals("HelpCommand")) {
							HelpCommand builtinCommand = new HelpCommand(command, new ArrayList<String>()); // Help Command has no args
							sendMessage(channel, builtinCommand.onCommandExecuted());
						}
						
					} else {
					
						StringBuilder messageBuilder = new StringBuilder();
						
						ArrayList<String> returnMessage = ConfigHandler.getCommandArray(command, "message");
						
						for (int j = 0; j < returnMessage.size(); j++) {
							
							messageBuilder.append(returnMessage.get(j).replace("%" + i + "%", "") + "\n");
							
						}
						
						sendMessage(channel, messageBuilder.toString());
					
					}
					
				}
				
			}
			
		}
		
	}

}
