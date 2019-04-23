package com.yamltobot.discord;

import com.yamltobot.core.commands.Command;
import com.yamltobot.scripts.Script;

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

    /**
     * The command's embed
     * @since 3.0.0
     */
    private DiscordEmbed embed;

    /**
     *
     * Define a command for Discord
     *
     * @param name The name of the command
     * @param desc A description of what the command does
     * @param msg The message the command returns
     * @param enabled Whether or not the command is enabled
     * @param script The script to be executed
     * @param embed The embed to return
     * @since 3.0.0
     *
     */
    public DiscordCommand(String name, String desc, ArrayList<String> msg, Boolean enabled, Script script, DiscordEmbed embed) {
        super(name, desc, msg, enabled, script);
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

    /**
     * @return This command's embed.
     * @since 3.0.0
     */
    public DiscordEmbed getEmbed() {
        return embed;
    }

    /**
     * @param embed The new embed this command should be using
     * @since 3.0.0
     */
    public void setEmbed(DiscordEmbed embed) {
        this.embed = embed;
    }

}
