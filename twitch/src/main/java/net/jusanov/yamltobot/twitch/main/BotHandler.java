package net.jusanov.yamltobot.twitch.main;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import org.jibble.pircbot.IrcException;

import net.jusanov.yamltobot.core.handler.ConfigHandler;
import net.jusanov.yamltobot.core.handler.LogHandler;
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

	public static void main(String[] args) {
		
		try {
			
			// Yaml to Bot Setup
			Setup.setupLogs();
			ConfigHandler.setConfig(new File("YamlToBot/config.yml"));
			Setup.setupDefaultConfig(new File("YamlToBot/config.yml"));
			SetupWindow frame = new SetupWindow();
			frame.setVisible(true);
			
			// Twitch Bot Setup
			TwitchBot bot = new TwitchBot(ConfigHandler.getString("name"));
			bot.setVerbose(true);
			bot.connect("irc.twitch.tv", 6667, ConfigHandler.getString("token"));
			
			// Join Channels
			ArrayList<String> channels = ConfigHandler.getArray("channels");
			for(int c = 0; c < channels.size(); c++) {
				
				String channel = channels.get(c);
				bot.joinChannel("#" + channel);
				LogHandler.debug("Channel " + channel + " joined!");
				
			}
			
		} catch (IOException | IrcException e) {
			e.printStackTrace();
		}
		
	}

}
