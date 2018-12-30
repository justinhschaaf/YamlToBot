package com.justinschaaf.yamltobot.discord;

import com.justinschaaf.yamltobot.core.commands.Command;
import com.justinschaaf.yamltobot.core.commands.builtin.BuiltinCommand;

import java.util.ArrayList;

/**
 *
 * Discord implementation of a YamlToBot command
 *
 * @author Justin Schaaf
 * @since 3.0.0
 *
 */
public class DiscordCommand extends Command {

    private DiscordEmbed embed;

    /**
     *
     * Define a command for Discord
     *
     * @param name The name of the command
     * @param desc A description of what the command does
     * @param msg The message the command returns
     * @param enabled Whether or not the command is enabled
     * @param builtin The builtin command to be executed
     * @param embed The embed to return
     * @since 3.0.0
     *
     */
    public DiscordCommand(String name, String desc, ArrayList<String> msg, Boolean enabled, BuiltinCommand builtin, DiscordEmbed embed) {
        super(name, desc, msg, enabled, builtin);
        this.embed = embed;
    }

    /**
     *
     * Get whether or not this command uses an embed rather than a normal message
     *
     * @return true if an embed is defined
     * @since 3.0.0
     *
     */
    public Boolean getIsEmbedEnabled() {
        if (embed == null) return false;
        if (embed.getEnabled() == true) return true;
        else return false;
    }

    public DiscordEmbed getEmbed() {
        return embed;
    }

    public void setEmbed(DiscordEmbed embed) {
        this.embed = embed;
    }

}
