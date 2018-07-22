package net.jusanov.yamltobot.twitch.main;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import org.jibble.pircbot.IrcException;

import net.jusanov.yamltobot.core.handler.ConfigHandler;
import net.jusanov.yamltobot.core.handler.LogHandler;
import net.jusanov.yamltobot.core.setup.Setup;

public class BotHandler {

	public static void main(String[] args) {
		
		try {
			
			// Yaml to Bot Setup
			Setup.setupLogs();
			Setup.setupDefaultConfig(new File(ClassLoader.getSystemResource("defaultconfig.yml").getFile()));
			
			// Twitch Bot Setup
			TwitchBot bot = new TwitchBot(ConfigHandler.getName());
			bot.setVerbose(true);
			bot.connect("irc.twitch.tv", 6667, ConfigHandler.getToken());
			
			// Join Channels
			ArrayList<String> channels = ConfigHandler.getChannels();
			for(int c = 0; c < channels.size(); c++) {
				
				String channel = channels.get(c);
				bot.joinChannel("#" + channel);
				LogHandler.log.debug("Channel " + channel + " joined!");
				
			}
			
		} catch (IOException | IrcException e) {
			e.printStackTrace();
		}
		
	}

}
