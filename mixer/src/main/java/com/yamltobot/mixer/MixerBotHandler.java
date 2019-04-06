package com.yamltobot.mixer;

import com.mixer.api.MixerAPI;
import com.mixer.api.resource.MixerUser;
import com.mixer.api.resource.chat.MixerChat;
import com.mixer.api.resource.chat.events.IncomingMessageEvent;
import com.mixer.api.resource.chat.methods.AuthenticateMessage;
import com.mixer.api.resource.chat.replies.AuthenticationReply;
import com.mixer.api.resource.chat.replies.ReplyHandler;
import com.mixer.api.resource.chat.ws.MixerChatConnectable;
import com.mixer.api.services.impl.ChatService;
import com.mixer.api.services.impl.UsersService;
import com.yamltobot.core.commands.Command;
import com.yamltobot.core.common.Module;
import com.yamltobot.core.handler.BotHandler;
import com.yamltobot.core.handler.LogHandler;

import java.util.ArrayList;

/**
 *
 * The primary class for setting up the Mixer bot
 *
 * @author Justin Schaaf
 * @since 4.0.0
 *
 */
public class MixerBotHandler extends BotHandler {

    // Mixer API and bot
    static MixerAPI mixer;
    static MixerUser user;
    static MixerChat chat;
    static MixerChatConnectable chatConnectable;

    static MixerMessageHandler messageHandler;

    static ArrayList<Command> commands;

    public static void main(String[] args) {

        setModule(Module.TWITCH);
        setup();
        commands = loadCommands();

        try {

            mixer = new MixerAPI(getAuth("token"), getAuth("id"));
            user = mixer.use(UsersService.class).getCurrent().get();
            chat = mixer.use(ChatService.class).findOne(user.channel.id).get();
            chatConnectable = chat.connectable(mixer);
            authenticateChat();

            // Message Handler
            messageHandler = new MixerMessageHandler(commands, chatConnectable);
            chatConnectable.on(IncomingMessageEvent.class, event -> messageHandler.onIncomingMessage(event));

        } catch (Exception e) {

            e.printStackTrace();
            LogHandler.fatal("Something went wrong when logging into Mixer!");

        }

        logClientInfo();

    }

    private static void authenticateChat() {

        if (chatConnectable.connect()) {

            chatConnectable.send(AuthenticateMessage.from(user.channel, user, chat.authkey), new ReplyHandler<AuthenticationReply>() {

                public void onSuccess(AuthenticationReply reply) {
                    LogHandler.debug("Successfully authenticated Mixer Chat!");
                }

                public void onFailure(Throwable throwable) {
                    throwable.printStackTrace();
                }

            });

        }

    }

}
