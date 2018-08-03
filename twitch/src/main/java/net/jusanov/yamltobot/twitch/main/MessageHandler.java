package net.jusanov.yamltobot.twitch.main;

import java.util.ArrayList;

import me.philippheuer.twitch4j.events.EventSubscriber;
import me.philippheuer.twitch4j.events.event.irc.ChannelMessageEvent;
import net.jusanov.yamltobot.core.commands.BuiltinCommandHandler;
import net.jusanov.yamltobot.core.handler.ConfigHandler;
import net.jusanov.yamltobot.core.handler.LogHandler;

/**
 * 
 * The primary class for handling messages and commands in Twitch
 * 
 * @author Justin
 * @since 1.0.0
 *
 */
public class MessageHandler {
	
    @EventSubscriber
    public void onChannelMessage(ChannelMessageEvent event) {

		LogHandler.info("[" + event.getChannel().getDisplayName() + "]" + " " + event.getUser().getDisplayName() + ": " + event.getMessage());
		
		ArrayList<String> commands = ConfigHandler.getCommands();
		
		for (int i = 0; i < commands.size(); i++) {
			
			String command = commands.get(i);
			
			if (event.getMessage().startsWith(ConfigHandler.getString("prefix") + command)) {
				
				LogHandler.debug("Command " + command + " detected!");
				
				if (ConfigHandler.getCommandBoolean(command, "enabled", true) == false) {
					
					continue;
					
				} else {
					
					if (ConfigHandler.getCommandBoolean(command, "builtin", false) == true) {
						
						try {
							event.sendMessage(BuiltinCommandHandler.getCommand(ConfigHandler.getCommandString(command, "predefined-function"), command, new ArrayList<String>()).toString());
						} catch (SecurityException e) {
							e.printStackTrace();
						}
						
					} else {
						
						StringBuilder messageBuilder = new StringBuilder();
						
						ArrayList<String> message = ConfigHandler.getCommandArray(command, "message");
						
						for (int j = 0; j < message.size(); j++) {
							
							messageBuilder.append(message.get(j).replace("%" + i + "%", "") + "\n");
							
						}
						
						event.sendMessage(messageBuilder.toString());
						
					}
					
				}
				
			}
			
		}
		
    }
    
}
