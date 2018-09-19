package com.justinschaaf.yamltobot.twitch;

import com.justinschaaf.yamltobot.core.common.Module;
import com.justinschaaf.yamltobot.core.handler.MessageHandler;

import me.philippheuer.twitch4j.events.EventSubscriber;
import me.philippheuer.twitch4j.events.event.irc.ChannelMessageEvent;

/**
 * 
 * The primary class for handling messages and commands in Twitch
 * 
 * @author Justin Schaaf
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
