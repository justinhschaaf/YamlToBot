package net.jusanov.yamltobot.twitch.main;

import java.io.File;

import me.philippheuer.twitch4j.TwitchClient;
import me.philippheuer.twitch4j.TwitchClientBuilder;
import net.jusanov.yamltobot.core.handler.ConfigHandler;
import net.jusanov.yamltobot.core.setup.Setup;
import net.jusanov.yamltobot.core.setup.SetupWindow;

/**
 * 
 * The primary class for setting up the TwitchBot
 * 
 * @author Jusanov
 * @since 1.0.0
 *
 */
public class BotHandler {

	static TwitchClient bot;
	
	public static void main(String[] args) {
		
		// Yaml to Bot Setup
		Setup.setupLogs();
		ConfigHandler.setConfig(new File("YamlToBot/config.yml"));
		Setup.setupDefaultConfig("Twitch", new File("YamlToBot/config.yml"));
		SetupWindow frame = new SetupWindow();
		frame.setVisible(true);
		
		// Twitch Bot Setup
		bot = TwitchClientBuilder.init()
				.withClientId(ConfigHandler.getString("id"))
				.withClientSecret(ConfigHandler.getString("secret"))
				.withCredential(ConfigHandler.getString("token"))
				.connect();
		
		bot.getDispatcher().registerListener(new TwitchMessageHandler());
		
        for (String channel : ConfigHandler.getArray("channels")) {
            bot.getMessageInterface().joinChannel(channel.toLowerCase());
        }
		
	}

}
