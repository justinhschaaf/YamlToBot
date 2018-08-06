package net.jusanov.yamltobot.discord.main;

import org.javacord.api.event.message.MessageCreateEvent;
import org.javacord.api.listener.message.MessageCreateListener;

import net.jusanov.yamltobot.core.common.Module;
import net.jusanov.yamltobot.core.handler.MessageHandler;

/**
 * 
 * The primary class for handling messages and commands in Discord
 * 
 * @author Justin
 * @since 1.0.0
 *
 */
public class DiscordMessageHandler extends MessageHandler implements MessageCreateListener {

	public void onMessageCreate(MessageCreateEvent event) {
		
		String msg = this.handleMessage(Module.DISCORD, event.getServer().get().getName().toString() + ", " + event.getChannel().asServerTextChannel().get().getName(), event.getMessage().getAuthor().getDisplayName(), event.getMessage().getContent()).toString();
		if (msg != null) event.getChannel().sendMessage(msg);
		
	}

}
