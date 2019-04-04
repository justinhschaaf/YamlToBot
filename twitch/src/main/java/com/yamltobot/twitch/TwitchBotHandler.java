package com.yamltobot.twitch;

import com.github.philippheuer.credentialmanager.domain.OAuth2Credential;
import com.github.philippheuer.events4j.EventManager;
import com.github.twitch4j.TwitchClient;
import com.github.twitch4j.TwitchClientBuilder;
import com.yamltobot.core.commands.Command;
import com.yamltobot.core.common.Module;
import com.yamltobot.core.handler.BotHandler;
import com.yamltobot.core.handler.ConfigHandler;

import java.util.ArrayList;

/**
 * 
 * The primary class for setting up the TwitchBot
 * 
 * @author Justin Schaaf
 * @since 1.0.0
 *
 */
public class TwitchBotHandler extends BotHandler {

	static TwitchClient bot;
	static EventManager eventManager;
	static ArrayList<Command> commands;

	public static void main(String[] args) {
		
		setModule(Module.TWITCH);
		setup();
		commands = loadCommands();

		eventManager = new EventManager();

		bot = TwitchClientBuilder.builder()
				.withEventManager(eventManager)
				.withEnableChat(true)
				.withClientId(getAuth("id"))
				.withClientSecret(getAuth("secret"))
				.withChatAccount(new OAuth2Credential("twitch", getAuth("token")))
				.build();
		
		bot.getEventManager().registerListener(new TwitchMessageHandler(commands, eventManager));
		
        for (String channel : ConfigHandler.getArray("channels")) {
            bot.getChat().joinChannel(channel.toLowerCase());
        }

		logClientInfo();
		
	}

}
