package com.justinschaaf.yamltobot.discord;

import java.awt.Color;
import java.util.ArrayList;

import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.event.message.MessageCreateEvent;
import org.javacord.api.listener.message.MessageCreateListener;

import com.amihaiemil.camel.YamlMapping;
import com.amihaiemil.camel.YamlSequence;
import com.justinschaaf.yamltobot.core.common.Module;
import com.justinschaaf.yamltobot.core.handler.ConfigHandler;
import com.justinschaaf.yamltobot.core.handler.MessageHandler;

/**
 * 
 * The primary class for handling messages and commands in Discord
 * 
 * @author Justin Schaaf
 * @since 1.0.0
 *
 */
public class DiscordMessageHandler extends MessageHandler implements MessageCreateListener {

	public void onMessageCreate(MessageCreateEvent event) {
		
		String command = this.getCommandByMessage(event.getMessage().getContent());
		
		if (command == null) return;
		
		if (this.embedEnabled(command)) {
			
			this.logMessage(event.getMessage().getServer().get().getName(), event.getMessage().getAuthor().getDisplayName(), event.getMessage().getContent());
			
			// Create the basic embed
			EmbedBuilder embed = new EmbedBuilder()
					.setTitle(ConfigHandler.getCommandMappingString(command, "embed", "title", "YamlToBot Embed"))
					.setColor(new Color(Integer.parseInt(ConfigHandler.getCommandMappingString(command, "embed", "color", "283F50"),16)));
			
			// Set the URL
			if (ConfigHandler.getCommandMappingString(command, "embed", "url", null) != null) embed.setUrl(ConfigHandler.getCommandMappingString(command, "embed", "url", null));
			
			// Set the description
			ArrayList<String> description = ConfigHandler.getCommandMappingArray(command, "embed", "description");
			StringBuilder messageBuilder = new StringBuilder();
			
			for (int i = 0; i < description.size(); i++) messageBuilder.append(description.get(i) + "\n");
			
			embed.setDescription(messageBuilder.toString());
			
			// Setup the fields
			YamlSequence fields;
			try {
				fields = ConfigHandler.getCommand(command).yamlMapping("embed").yamlSequence("fields");
			} catch (NullPointerException e) {
				fields = null;
			}
			
			if (!(fields == null)) for (int i = 0; i < fields.size(); i++) {
				
				YamlMapping field = fields.yamlMapping(i);
				
				// Set the field description
				StringBuilder fieldDescriptionBuilder = new StringBuilder();
				YamlSequence fieldDescription = field.yamlSequence("description");
				for (int j = 0; j < fieldDescription.size(); j++) fieldDescriptionBuilder.append(fieldDescription.string(j).replace("\"", "") + "\n");
				
				// Set whether or not the field is inline
				String inlineStr = field.string("inline");
				boolean inline = false;
				
				if (inlineStr.replace("\"", "").equalsIgnoreCase("false")) inline = false;
				if (inlineStr.replace("\"", "").equalsIgnoreCase("true")) inline = true;
				
				embed.addField(field.string("name").replace("\"", ""), fieldDescriptionBuilder.toString(), inline);
				
			}
			
			// Send the final embed
			event.getChannel().sendMessage(embed);
			
		} else {
			String msg = this.handleMessage(Module.DISCORD, event.getServer().get().getName().toString() + ", " + event.getChannel().asServerTextChannel().get().getName(), event.getMessage().getAuthor().getDisplayName(), event.getMessage().getContent()).toString();
			if (msg != null) event.getChannel().sendMessage(msg/*MessageFormatter.formatMessage(msg)*/);
		}
		
	}

}
