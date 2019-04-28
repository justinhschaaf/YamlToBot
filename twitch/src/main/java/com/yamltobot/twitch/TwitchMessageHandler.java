package com.yamltobot.twitch;

import com.github.philippheuer.events4j.EventManager;
import com.github.twitch4j.chat.events.channel.ChannelMessageEvent;
import com.yamltobot.core.commands.Command;
import com.yamltobot.core.common.Module;
import com.yamltobot.core.main.MessageHandler;

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
     * The latest event that occurred
     * @since 4.0.0
     */
    private static ChannelMessageEvent latestEvent;

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

    /**
     *
     * The function to execute once an event occurs
     *
     * @param event The {@link ChannelMessageEvent} of the message
     * @since 1.0.0
     *
     */
    public void onChannelMessage(ChannelMessageEvent event) {

        latestEvent = event;

    	String msg = handleMessage(Module.TWITCH, event.getChannel().getName(), event.getUser().getName(), event.getMessage());
    	if (msg != null) event.getTwitchChat().sendMessage(event.getChannel().getName(), msg);
		
    }

    @Override
    public void sendMessage(String message) {
        latestEvent.getTwitchChat().sendMessage(latestEvent.getChannel().getName(), message);
    }

}
