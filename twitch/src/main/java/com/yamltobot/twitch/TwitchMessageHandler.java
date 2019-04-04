package com.yamltobot.twitch;

import com.github.philippheuer.events4j.EventManager;
import com.github.twitch4j.chat.events.channel.ChannelMessageEvent;
import com.yamltobot.core.commands.Command;
import com.yamltobot.core.common.Module;
import com.yamltobot.core.handler.MessageHandler;

import java.util.ArrayList;

/**
 * 
 * The primary class for handling messages and commands in Twitch
 * 
 * @author Justin Schaaf
 * @since 1.0.0
 *
 */
public class TwitchMessageHandler extends MessageHandler {

    /**
     *
     * The primary class for handling messages in Twitch
     *
     * @param commands an ArrayList of all the loaded commands
     * @param eventManager The bot's event manager
     * @since 3.0.0
     *
     */
    public TwitchMessageHandler(ArrayList<Command> commands, EventManager eventManager) {
        super(commands);
        eventManager.onEvent(ChannelMessageEvent.class).subscribe(event -> onChannelMessage(event));
    }

    public void onChannelMessage(ChannelMessageEvent event) {

    	String msg = handleMessage(Module.TWITCH, event.getChannel().getName(), event.getUser().getName(), event.getMessage());
    	if (msg != null) event.getTwitchChat().sendMessage(event.getChannel().getName(), msg);
		
    }
    
}
