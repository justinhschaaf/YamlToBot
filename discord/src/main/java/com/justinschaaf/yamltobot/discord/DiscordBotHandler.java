package com.justinschaaf.yamltobot.discord;

import org.javacord.api.DiscordApi;
import org.javacord.api.DiscordApiBuilder;

import com.justinschaaf.yamltobot.core.common.Module;
import com.justinschaaf.yamltobot.core.handler.BotHandler;
import com.justinschaaf.yamltobot.core.handler.ConfigHandler;

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

	public static void main(String[] args) {
		
		setModule(Module.DISCORD);
		setup();

		api = new DiscordApiBuilder().setToken(getAuth("token")).login().join();
		api.addMessageCreateListener(new DiscordMessageHandler());
		if (ConfigHandler.getString("activity", null) != null) api.updateActivity(ConfigHandler.getString("activity", null));

	}

}
