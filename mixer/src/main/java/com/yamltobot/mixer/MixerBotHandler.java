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
import com.yamltobot.core.common.Module;
import com.yamltobot.core.handler.BotHandler;
import com.yamltobot.core.handler.LogHandler;

/**
 *
 * The primary class for setting up the Mixer bot
 *
 * @author Justin Schaaf
 * @since 4.0.0
 *
 */
public class MixerBotHandler extends BotHandler {

    /**
     * The {@link MixerAPI} with the token and id of the bot
     * @since 4.0.0
     */
    private static MixerAPI mixer;

    /**
     * The Mixer chat user, as a {@link MixerUser}
     * @since 4.0.0
     */
    private static MixerUser user;

    /**
     * The Mixer chat, as a {@link MixerChat}
     * @since 4.0.0
     */
    private static MixerChat chat;

    /**
     * The {@link #chat}'s connectable, as a {@link MixerChatConnectable}
     * @since 4.0.0
     */
    private static MixerChatConnectable chatConnectable;

    public static void main(String[] args) {

        try {

            setMixer(new MixerAPI(getAuth("token"), getAuth("id")));
            setUser(mixer.use(UsersService.class).getCurrent().get());
            setChat(mixer.use(ChatService.class).findOne(user.channel.id).get());
            setChatConnectable(chat.connectable(mixer));

            // Setup here because the chatConnectable has to be set first
            setup(Module.MIXER, new MixerBotHandler(), new MixerMessageHandler(getCommands(), chatConnectable));

            authenticateChat();

            // Message Handler
            getChatConnectable().on(IncomingMessageEvent.class, event -> ((MixerMessageHandler) getMessageHandler()).onIncomingMessage(event));

        } catch (Exception e) {

            e.printStackTrace();
            LogHandler.fatal("Something went wrong when logging into Mixer!");

        }

    }

    /**
     * Authenticates the bot with Mixer chat
     * @since 3.0.0
     */
    private static void authenticateChat() {

        if (getChatConnectable().connect()) {

            getChatConnectable().send(AuthenticateMessage.from(user.channel, user, chat.authkey), new ReplyHandler<AuthenticationReply>() {

                public void onSuccess(AuthenticationReply reply) {
                    LogHandler.debug("Successfully authenticated Mixer Chat!");
                }

                public void onFailure(Throwable throwable) {
                    throwable.printStackTrace();
                }

            });

        }

    }

    /**
     * @return {@link #mixer}, the Mixer API
     * @since 4.0.0
     */
    public static MixerAPI getMixer() {
        return mixer;
    }

    /**
     * @param mixer The new {@link #mixer} api, as a {@link MixerAPI}
     * @since 4.0.0
     */
    public static void setMixer(MixerAPI mixer) {
        MixerBotHandler.mixer = mixer;
    }

    /**
     * @return The {@link #mixer} api's {@link MixerUser}, {@link #user}
     * @since 4.0.0
     */
    public static MixerUser getUser() {
        return user;
    }

    /**
     * @param user The new {@link MixerUser} for the bot
     * @since 4.0.0
     */
    public static void setUser(MixerUser user) {
        MixerBotHandler.user = user;
    }

    /**
     * @return The {@link MixerChat} the bot will connect to, {@link #chat}
     * @since 4.0.0
     */
    public static MixerChat getChat() {
        return chat;
    }

    /**
     * @param chat The new {@link MixerChat} for the bot
     * @since 4.0.0
     */
    public static void setChat(MixerChat chat) {
        MixerBotHandler.chat = chat;
    }

    /**
     * @return The {@link MixerChatConnectable} for {@link #chat}
     * @since 4.0.0
     */
    public static MixerChatConnectable getChatConnectable() {
        return chatConnectable;
    }

    /**
     * @param chatConnectable The new {@link MixerChatConnectable} for the bot
     * @since 4.0.0
     * @note You will have to re-add the {@link MixerMessageHandler} and authenticate chat again using {@link #authenticateChat()} if you change the chat connectable!
     */
    public static void setChatConnectable(MixerChatConnectable chatConnectable) {
        MixerBotHandler.chatConnectable = chatConnectable;
    }

}
