package net.jusanov.yamltobot.discord;

import org.javacord.api.DiscordApi;
import org.javacord.api.DiscordApiBuilder;

import net.jusanov.yamltobot.core.common.Module;
import net.jusanov.yamltobot.core.handler.BotHandler;
import net.jusanov.yamltobot.core.handler.ConfigHandler;

/**
 * 
 * The primary class for setting up the DiscordBot
 * 
 * @author Jusanov
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
		api.updateActivity(ConfigHandler.getString("activity"));

	}

}
