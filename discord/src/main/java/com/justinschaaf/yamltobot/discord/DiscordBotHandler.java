package com.justinschaaf.yamltobot.discord;

import com.amihaiemil.eoyaml.YamlMapping;
import com.amihaiemil.eoyaml.YamlSequence;
import com.justinschaaf.yamltobot.core.common.Module;
import com.justinschaaf.yamltobot.core.handler.BotHandler;
import com.justinschaaf.yamltobot.core.handler.ConfigHandler;
import org.javacord.api.DiscordApi;
import org.javacord.api.DiscordApiBuilder;

import java.util.ArrayList;

/**
 * 
 * The primary class for setting up the DiscordBot
 * 
 * @author Justin Schaaf
 * @since 1.0.0
 *
 */
public class DiscordBotHandler extends BotHandler {
	
	public static DiscordApi api;
	static ArrayList<DiscordCommand> commands;

	public static void main(String[] args) {
		
		setModule(Module.DISCORD);
		setup();
		commands = loadDiscordCommands();

		api = new DiscordApiBuilder().setToken(getAuth("token")).login().join();
		api.addMessageCreateListener(new DiscordMessageHandler(commands));
		if (ConfigHandler.getString("activity", null) != null) api.updateActivity(ConfigHandler.getString("activity", null));

	}

	/**
	 *
	 * Loads the commands for Discord with the Discord-specific options
	 *
	 * @return an ArrayList of all the Discord commands
	 * @since 3.0.0
	 *
	 */
	public static ArrayList<DiscordCommand> loadDiscordCommands() {

		ArrayList<String> commandKeys = ConfigHandler.getCommands();
		ArrayList<DiscordCommand> commands = new ArrayList<DiscordCommand>();

		for (String commandName : commandKeys) {

			commands.add(new DiscordCommand(
					commandName,
					ConfigHandler.getCommandString(commandName, "description", "Generic Command"),
					ConfigHandler.getCommandArray(commandName,"message"),
					ConfigHandler.getCommandBoolean(commandName, "enabled", true),
					loadBuiltinCommand(ConfigHandler.getCommandString(commandName, "predefined-function", null)),
					generateDiscordEmbed(commandName)));

		}

		return commands;

	}

	/**
	 *
	 * Generate a Discord embed for the given command based off the values in the config file
	 *
	 * @param command the name of the command to generate an embed for
	 * @return the DiscordEmbed, or null if one is not defined
	 *
	 */
	private static DiscordEmbed generateDiscordEmbed(String command) {

		if (ConfigHandler.getCommandMappingBoolean(command, "embed", "enabled", false) == false) return null;

		String title = ConfigHandler.getCommandMappingString(command, "embed", "title", "YamlToBot Embed");
		ArrayList<String> desc = ConfigHandler.getCommandMappingArray(command, "embed", "description");
		String color = ConfigHandler.getCommandMappingString(command, "embed", "color", "283F50");
		String image = ConfigHandler.getCommandMappingString(command, "embed", "image", null);
		String url = ConfigHandler.getCommandMappingString(command, "embed", "url", null);
		Boolean enabled = ConfigHandler.getCommandMappingBoolean(command, "embed", "enabled", false);

		// Fields
		ArrayList<DiscordEmbedField> fields = new ArrayList<>();
		YamlSequence fieldSequence;
		try { fieldSequence = ConfigHandler.getCommand(command).yamlMapping("embed").yamlSequence("fields"); }
		catch (NullPointerException e) { fieldSequence = null; }

		if (!(fieldSequence == null)) for (int i = 0; i < fieldSequence.size(); i++) {

			YamlMapping field = fieldSequence.yamlMapping(i);

			// Set the field description
			ArrayList<String> fieldDescription = new ArrayList<String>();
			YamlSequence fieldDescriptionSequence = field.yamlSequence("description");
			for (int j = 0; j < fieldDescriptionSequence.size(); j++) fieldDescription.add(fieldDescriptionSequence.string(j).replaceAll("\"", ""));

			// Set whether or not the field is inline
			String inlineStr = field.string("inline");
			boolean inline = false;

			if (inlineStr.replaceAll("\"", "").equalsIgnoreCase("false")) inline = false;
			if (inlineStr.replaceAll("\"", "").equalsIgnoreCase("true")) inline = true;

			fields.add(new DiscordEmbedField(field.string("name").replaceAll("\"", ""), fieldDescription, inline));

		}

		// Author
		DiscordEmbedAuthor author = null;

		try {

			YamlMapping authorMapping = ConfigHandler.getCommand(command).yamlMapping("embed").yamlMapping("author");

			// Create variables for the author's metadata
			String name;
			String authorUrl;
			String avatar;

			// Try getting the author's metadata
			try { name = authorMapping.string("name").replaceAll("\"", ""); }
			catch (NullPointerException e) { name = null; }

			try { authorUrl = authorMapping.string("url").replaceAll("\"", ""); }
			catch (NullPointerException e) { authorUrl = null; }

			try { avatar = authorMapping.string("avatar").replaceAll("\"", ""); }
			catch (NullPointerException e) { avatar = null; }

			// Add the author to the embed
			if (name == null);
			else if (url == null || avatar == null) author = new DiscordEmbedAuthor(name);
			else author = new DiscordEmbedAuthor(name, authorUrl, avatar);

		} catch (NullPointerException e) {};

		return new DiscordEmbed(title, desc, color, image, url, fields, author, enabled);

	}

}
