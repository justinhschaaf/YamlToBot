package net.jusanov.yamltobot.twitch;

import me.philippheuer.twitch4j.events.EventSubscriber;
import me.philippheuer.twitch4j.events.event.irc.ChannelMessageEvent;
import net.jusanov.yamltobot.core.common.Module;
import net.jusanov.yamltobot.core.handler.MessageHandler;

/**
 * 
 * The primary class for handling messages and commands in Twitch
 * 
 * @author Justin
 * @since 1.0.0
 *
 */
public class TwitchMessageHandler extends MessageHandler {
	
    @EventSubscriber
    public void onChannelMessage(ChannelMessageEvent event) {

    	String msg = this.handleMessage(Module.TWITCH, event.getChannel().getDisplayName(), event.getUser().getDisplayName(), event.getMessage());
    	if (msg != null) event.sendMessage(msg);
		
    }
    
}
