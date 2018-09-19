package com.justinschaaf.yamltobot.twitch;

import com.justinschaaf.yamltobot.core.common.Module;
import com.justinschaaf.yamltobot.core.handler.BotHandler;
import com.justinschaaf.yamltobot.core.handler.ConfigHandler;

import me.philippheuer.twitch4j.TwitchClient;
import me.philippheuer.twitch4j.TwitchClientBuilder;

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
	
	public static void main(String[] args) {
		
		setModule(Module.TWITCH);
		setup();
		
		bot = TwitchClientBuilder.init()
				.withClientId(getAuth("id"))
				.withClientSecret(getAuth("secret"))
				.withCredential(getAuth("token"))
				.connect();
		
		bot.getDispatcher().registerListener(new TwitchMessageHandler());
		
        for (String channel : ConfigHandler.getArray("channels")) {
            bot.getMessageInterface().joinChannel(channel.toLowerCase());
        }
		
	}

}
