package net.jusanov.yamltobot.discord.main;

import java.io.File;
import org.javacord.api.DiscordApi;
import org.javacord.api.DiscordApiBuilder;
import net.jusanov.yamltobot.core.handler.ConfigHandler;
import net.jusanov.yamltobot.core.setup.Setup;
import net.jusanov.yamltobot.core.setup.SetupWindow;

public class BotHandler {

	static DiscordApi api;
	
	public static void main(String[] args) {
		
		// Yaml to Bot Setup
		Setup.setupLogs();
		ConfigHandler.setConfig(new File("YamlToBot/config.yml"));
		Setup.setupDefaultConfig(new File("YamlToBot/config.yml"));
		SetupWindow.setupWindow();
		
		// Discord Bot Setup
		api = new DiscordApiBuilder().setToken(ConfigHandler.getString("token")).login().join();
		api.addMessageCreateListener(new MessageHandler());
		api.updateActivity(ConfigHandler.getString("activity"));

	}

}
