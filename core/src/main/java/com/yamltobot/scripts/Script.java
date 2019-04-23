package com.yamltobot.scripts;

import com.yamltobot.core.handler.BotHandler;
import com.yamltobot.core.handler.MessageHandler;

import java.util.ArrayList;

/**
 *
 * The primary class for YamlToBot Scripting.
 *
 * @author Justin Schaaf
 * @since 1.0.0
 *
 */
public abstract class Script {

    /**
     * The {@link BotHandler} of the bot
     * @since 4.0.0
     */
    public static BotHandler botHandler;

    /**
     * The {@link MessageHandler} of the bot
     * @since 4.0.0
     */
    public static MessageHandler messageHandler;

    /**
     *
     * A YamlToBot Script
     *
     * @since 4.0.0
     *
     */
    public Script() {}

    /**
     *
     * Provide the bot's {@link BotHandler} and {@link MessageHandler} to the script
     *
     * @param botHandler The bot's {@link BotHandler}
     * @param messageHandler The bot's {@link MessageHandler}
     * @since 4.0.0
     *
     */
    public void setHandlers(BotHandler botHandler, MessageHandler messageHandler) {
        this.botHandler = botHandler;
        this.messageHandler = messageHandler;
    }

    /**
     *
     * The function to be executed when the command is run
     *
     * @param args The arguments passed to the script
     * @return The string message that the command returns
     * @since 1.0.0
     *
     */
    public abstract void onScriptRun(ArrayList<String> args);

}
