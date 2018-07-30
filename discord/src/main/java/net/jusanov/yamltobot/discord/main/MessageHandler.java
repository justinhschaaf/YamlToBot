package net.jusanov.yamltobot.discord.main;

import java.util.ArrayList;

import org.javacord.api.event.message.MessageCreateEvent;
import org.javacord.api.listener.message.MessageCreateListener;

import net.jusanov.yamltobot.core.handler.ConfigHandler;
import net.jusanov.yamltobot.core.handler.LogHandler;

public class MessageHandler implements MessageCreateListener {

	public void onMessageCreate(MessageCreateEvent event) {
		
		LogHandler.info("[" + event.getChannel().toString() + ", " + event.getServer().get().getName() + "]" + event.getMessage().getAuthor().getDisplayName() + ": " + event.getMessage().getContent());
		
		ArrayList<String> commands = ConfigHandler.getCommands();
		
		for(int i = 0; i < commands.size(); i++) {
			
			String command = commands.get(i);
			
			if(event.getMessage().getContent().startsWith(ConfigHandler.getString("prefix") + command)) {
				
				LogHandler.debug("Command " + command + " detected!");
				
				if(ConfigHandler.getEnabled(command) == false) {
					
					continue;
					
				} else {
					
					StringBuilder messageBuilder = new StringBuilder();
					
					ArrayList<String> message = ConfigHandler.getCommandArray(command, "message");
					
					for (int j = 0; j < message.size(); j++) {
						
						messageBuilder.append(message.get(j) + "\n");
						
					}
					
					event.getChannel().sendMessage(MessageFormatter.formatMessage(messageBuilder.toString()));
					
				}
				
			}
			
		}
		
	}

}
