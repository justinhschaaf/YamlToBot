package net.jusanov.yamltobot.discord.main;

import java.io.File;
import org.javacord.api.DiscordApi;
import org.javacord.api.DiscordApiBuilder;

import net.jusanov.yamltobot.core.handler.ConfigHandler;
import net.jusanov.yamltobot.core.setup.Setup;

public class BotHandler {

	static DiscordApi api;
	
	public static void main(String[] args) {
		
		// Yaml to Bot Setup
		Setup.setupLogs();
		Setup.setupDefaultConfig(new File(ClassLoader.getSystemResource("defaultconfig.yml").getFile()));
		
		// Discord Bot Setup
		api = new DiscordApiBuilder().setToken(ConfigHandler.getToken()).login().join();
		api.addMessageCreateListener(new MessageHandler());

	}

}
