package net.jusanov.yamltobot.twitch.main;

import java.util.ArrayList;

import org.jibble.pircbot.PircBot;

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
		
		for(int i = 0; i < commands.size(); i++) {
			
			String command = commands.get(i);
			
			if(message.startsWith(ConfigHandler.getString("prefix") + command)) {
				
				LogHandler.debug("Command " + command + " detected!");
				
				if(ConfigHandler.getEnabled(command) == false) {
					
					continue;
					
				} else {
					
					StringBuilder messageBuilder = new StringBuilder();
					
					ArrayList<String> returnMessage = ConfigHandler.getCommandArray(command, "message");
					
					for (int j = 0; j < returnMessage.size(); j++) {
						
						messageBuilder.append(returnMessage.get(j) + "\n");
						
					}
					
					sendMessage(channel, messageBuilder.toString());
					
				}
				
			}
			
		}
		
	}

}
