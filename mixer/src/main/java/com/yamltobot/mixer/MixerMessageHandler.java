package com.yamltobot.mixer;

import com.mixer.api.resource.chat.events.IncomingMessageEvent;
import com.mixer.api.resource.chat.methods.ChatSendMethod;
import com.mixer.api.resource.chat.ws.MixerChatConnectable;
import com.yamltobot.core.commands.Command;
import com.yamltobot.core.common.Module;
import com.yamltobot.core.handler.MessageHandler;

import java.util.ArrayList;

/**
 *
 * The primary class for handling messages in Mixer
 *
 * @since 4.0.0
 * @author Justin Schaaf
 *
 */
public class MixerMessageHandler extends MessageHandler {

    /**
     * The {@link MixerChatConnectable} the bot is using
     * @since 4.0.0
     */
    private static MixerChatConnectable chatConnectable;

    /**
     * The latest event that occurred
     * @since 4.0.0
     */
    private static IncomingMessageEvent latestEvent;

    /**
     *
     * The primary class for handling messages in Mixer
     *
     * @param commands an ArrayList of all the loaded commands
     * @param chatConnectable The bot's {@link MixerChatConnectable}
     * @since 4.0.0
     *
     */
    public MixerMessageHandler(ArrayList<Command> commands, MixerChatConnectable chatConnectable) {
        super(commands);
        this.chatConnectable = chatConnectable;
    }

    /**
     *
     * The function to execute once a message is sent
     *
     * @param event The {@link IncomingMessageEvent} of the message
     * @since 4.0.0
     *
     */
    public void onIncomingMessage(IncomingMessageEvent event) {

        String msg = handleMessage(Module.MIXER, event.data.channel + "", event.data.userName, event.data.asString());
        if (msg != null) chatConnectable.send(ChatSendMethod.of(msg));

    }

    @Override
    public void sendMessage(String message) {
        chatConnectable.send(ChatSendMethod.of(message));
    }

}
