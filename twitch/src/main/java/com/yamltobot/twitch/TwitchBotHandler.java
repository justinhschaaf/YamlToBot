package com.yamltobot.twitch;

import com.github.philippheuer.credentialmanager.domain.OAuth2Credential;
import com.github.philippheuer.events4j.EventManager;
import com.github.twitch4j.TwitchClient;
import com.github.twitch4j.TwitchClientBuilder;
import com.yamltobot.core.common.Module;
import com.yamltobot.core.main.BotHandler;

/**
 *
 * The primary class for setting up the TwitchBot
 *
 * @author Justin Schaaf
 * @since 1.0.0
 *
 */
public class TwitchBotHandler extends BotHandler {

    /**
     * The Twitch bot
     * @since 1.0.0
     */
    static TwitchClient bot;

    /**
     * The Bot's {@link EventManager}
     * @since 4.0.0
     */
    static EventManager eventManager;

    public static void main(String[] args) {

        eventManager = new EventManager();

        setup(Module.TWITCH, new TwitchBotHandler(), new TwitchMessageHandler(getCommands(), eventManager));

        bot = TwitchClientBuilder.builder()
                .withEventManager(eventManager)
                .withEnableChat(true)
                .withClientId(getAuth("id"))
                .withClientSecret(getAuth("secret"))
                .withChatAccount(new OAuth2Credential("twitch", getAuth("token")))
                .build();

        bot.getEventManager().registerListener(new TwitchMessageHandler(getCommands(), eventManager));

        for (String channel : getConfigHandler().getConfig().getArray("channels")) {
            bot.getChat().joinChannel(channel.toLowerCase());
        }

    }

}
