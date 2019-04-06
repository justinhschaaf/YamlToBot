package com.yamltobot.mixer;

import com.mixer.api.resource.chat.events.IncomingMessageEvent;
import com.mixer.api.resource.chat.methods.ChatSendMethod;
import com.mixer.api.resource.chat.ws.MixerChatConnectable;
import com.yamltobot.core.commands.Command;
import com.yamltobot.core.common.Module;
import com.yamltobot.core.handler.MessageHandler;

import java.util.ArrayList;

public class MixerMessageHandler extends MessageHandler {

    private MixerChatConnectable chatConnectable;

    /**
     *
     * The primary class for handling messages in Twitch
     *
     * @param commands an ArrayList of all the loaded commands
     * @param chatConnectable The bot's chat connectable
     * @since 3.0.0
     *
     */
    public MixerMessageHandler(ArrayList<Command> commands, MixerChatConnectable chatConnectable) {
        super(commands);
        this.chatConnectable = chatConnectable;
    }

    public void onIncomingMessage(IncomingMessageEvent event) {

        String msg = handleMessage(Module.MIXER, event.data.channel + "", event.data.userName, event.data.asString());
        if (msg != null) chatConnectable.send(ChatSendMethod.of(msg));

    }

}
