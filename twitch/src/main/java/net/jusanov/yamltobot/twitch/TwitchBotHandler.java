package net.jusanov.yamltobot.twitch;

import me.philippheuer.twitch4j.TwitchClient;
import me.philippheuer.twitch4j.TwitchClientBuilder;
import net.jusanov.yamltobot.core.common.Module;
import net.jusanov.yamltobot.core.handler.BotHandler;
import net.jusanov.yamltobot.core.handler.ConfigHandler;

/**
 * 
 * The primary class for setting up the TwitchBot
 * 
 * @author Jusanov
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
