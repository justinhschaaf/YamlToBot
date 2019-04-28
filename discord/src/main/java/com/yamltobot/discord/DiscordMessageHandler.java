package com.yamltobot.discord;

import com.yamltobot.core.commands.Command;
import com.yamltobot.core.main.MessageHandler;
import org.javacord.api.event.message.MessageCreateEvent;
import org.javacord.api.listener.message.MessageCreateListener;

import java.util.ArrayList;

/**
 * 
 * The primary class for handling messages and commands in Discord
 * 
 * @author Justin Schaaf
 * @since 1.0.0
 *
 */
public class DiscordMessageHandler extends MessageHandler implements MessageCreateListener {

	/**
	 * The list of registered commands
	 * @since 3.0.0
	 */
	private static ArrayList<DiscordCommand> discordCommands;

	/**
	 * The latest event that occurred
	 * @since 4.0.0
	 */
	private static MessageCreateEvent latestEvent;

	/**
	 *
	 * The primary class for handling messages in Discord
	 *
	 * @param commands An ArrayList of all loaded commands
	 * @param discordCommands an ArrayList of the loaded DiscordCommands
	 * @since 3.0.0
	 *
	 */
	public DiscordMessageHandler(ArrayList<Command> commands, ArrayList<DiscordCommand> discordCommands) {
		super(commands);
		this.discordCommands = discordCommands;
	}

	/**
	 *
	 * The function to be executed once a message is sent
	 *
	 * @param event The {@link MessageCreateEvent} of the message
	 * @since 1.0.0
	 *
	 */
	public void onMessageCreate(MessageCreateEvent event) {

		this.latestEvent = event;

		logMessage(event.getServer().get().getName().toString() + ", " + event.getChannel().asServerTextChannel().get().getName(), event.getMessage().getAuthor().getDisplayName(), event.getMessage().getContent());

		DiscordCommand command = this.getCommandByMessage(event.getMessage().getContent());
		if (command == null) return;

		if (command.getEnabled()) {

			if (command.getIsEmbedEnabled()) event.getChannel().sendMessage(command.getEmbed().generate());
			else event.getChannel().sendMessage(DiscordBotHandler.getVariableHandler().formatMessage(command.execute(getArgsByMessage(command, event.getMessage().getContent()))));

		}
		
	}

	@Override
	public DiscordCommand getCommandByMessage(String message) {

		for (DiscordCommand command : discordCommands) {

			if (message.startsWith(DiscordBotHandler.getConfigHandler().getConfig().getString("prefix", "") + command.getName())) return command;

		}

		return null;

	}

	@Override
	public void sendMessage(String message) {
		latestEvent.getChannel().sendMessage(message);
	}

}
