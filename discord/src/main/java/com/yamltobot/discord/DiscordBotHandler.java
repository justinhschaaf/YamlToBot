package com.yamltobot.discord;

import com.yamltobot.core.common.Module;
import com.yamltobot.core.config.ConfigArray;
import com.yamltobot.core.config.ConfigObject;
import com.yamltobot.core.main.BotHandler;
import com.yamltobot.core.main.LogHandler;
import org.javacord.api.DiscordApi;
import org.javacord.api.DiscordApiBuilder;

import java.util.ArrayList;

/**
 * 
 * The primary class for setting up the Discord bot
 * 
 * @author Justin Schaaf
 * @since 1.0.0
 *
 */
public class DiscordBotHandler extends BotHandler {

	/**
	 * The Discord bot
	 * @since 1.0.0
	 */
	private static DiscordApi bot;

	/**
	 * The registered {@link DiscordCommand} list
	 * @since 3.0.0
	 */
	private static ArrayList<DiscordCommand> discordCommands;

	public static void main(String[] args) {

		setup(Module.DISCORD, new DiscordBotHandler(), new DiscordMessageHandler(getCommands(), discordCommands));
		setDiscordCommands(loadDiscordCommands());

		bot = new DiscordApiBuilder().setToken(getAuth("token")).login().join();
		bot.addMessageCreateListener(new DiscordMessageHandler(getCommands(), discordCommands));
		if (getConfigHandler().getConfig().getString("activity", null) != null) bot.updateActivity(getConfigHandler().getConfig().getString("activity", null));

	}

	/**
	 *
	 * Loads the commands for Discord with the Discord-specific options
	 *
	 * @return an ArrayList of all the {@link DiscordCommand}s found
	 * @since 3.0.0
	 *
	 */
	public static ArrayList<DiscordCommand> loadDiscordCommands() {

		long starttime = System.currentTimeMillis();

		ArrayList<String> commandKeys = getConfigHandler().getCommands();
		ArrayList<DiscordCommand> commands = new ArrayList<DiscordCommand>();
		setScriptLoader(loadScriptLoader(Module.SCRIPT.getDir()));

		for (String commandName : commandKeys) {

			ConfigObject command = getConfigHandler().getCommand(commandName);

			commands.add(new DiscordCommand(
					commandName,
					command.getString("description", "Generic Command"),
					command.getArray("message"),
					command.getBoolean("enabled", true),
					loadScript(getScriptLoader(), command.getString("script", null)),
					command,
					generateDiscordEmbed(command)));

		}

		LogHandler.debug("Discord commands loaded in " + (System.currentTimeMillis() - starttime) + " milliseconds!");

		return commands;

	}

	/**
	 *
	 * Generate a {@link DiscordEmbed} for the given command based off the values in the config file
	 *
	 * @param command the name of the command to generate an embed for
	 * @return the DiscordEmbed, or null if one is not defined
	 * @since 3.0.0
	 *
	 */
	public static DiscordEmbed generateDiscordEmbed(ConfigObject command) {

		if (command.getConfigObject("embed").getBoolean("enabled", false) == false) return null;

		ConfigObject embedObject = command.getConfigObject("embed");

		String title = embedObject.getString("title", "YamlToBot Embed");
		ArrayList<String> desc = embedObject.getArray("description");
		String color = embedObject.getString("color", "#555555");
		String image = embedObject.getString("image", null);
		String url = embedObject.getString("url", null);
		Boolean enabled = embedObject.getBoolean("enabled", false);

		// Fields
		ArrayList<DiscordEmbedField> fields = new ArrayList<>();
		ConfigArray fieldArray = embedObject.getConfigArray("fields");

		if (!(fieldArray == null)) for (int i = 0; i < fieldArray.size(); i++) {

			ConfigObject field = fieldArray.getConfigObject(i);

			fields.add(new DiscordEmbedField(field.getString("name"), field.getArray("description"), field.getBoolean("inline", false)));

		}

		// Author
		DiscordEmbedAuthor author = new DiscordEmbedAuthor(
				embedObject.getConfigObject("author").getString("name"),
				embedObject.getConfigObject("author").getString("url"),
				embedObject.getConfigObject("author").getString("avatar"));

		return new DiscordEmbed(title, desc, color, image, url, fields, author, enabled);

	}

	/**
	 * @return The Discord {@link #bot}
	 * @since 4.0.0
	 */
	public static DiscordApi getBot() {
		return bot;
	}

	/**
	 * @param bot The new Discord {@link #bot}
	 * @since 4.0.0
	 */
	public static void setBot(DiscordApi bot) {
		DiscordBotHandler.bot = bot;
	}

	/**
	 * @return The registered {@link DiscordCommand} list
	 * @since 4.0.0
	 */
	public static ArrayList<DiscordCommand> getDiscordCommands() {
		return discordCommands;
	}

	/**
	 * @param discordCommands The new {@link DiscordCommand} list
	 * @since 4.0.0
	 */
	public static void setDiscordCommands(ArrayList<DiscordCommand> discordCommands) {
		DiscordBotHandler.discordCommands = discordCommands;
	}

}
