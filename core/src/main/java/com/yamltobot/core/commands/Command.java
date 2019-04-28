package com.yamltobot.core.commands;

import com.yamltobot.core.config.ConfigObject;
import com.yamltobot.scripts.Script;

import java.util.ArrayList;

/**
 *
 * A standard YamlToBot command
 *
 * @author Justin Schaaf
 * @since 3.0.0
 *
 */
public class Command {

    /**
     * The command's name
     * @since 3.0.0
     */
    private String name;

    /**
     * The command's description
     * @since 3.0.0
     */
    private String desc;

    /**
     * The command's message
     * @since 3.0.0
     */
    private ArrayList<String> msg;

    /**
     * Whether or not the command is enabled
     * @since 3.0.0
     */
    private Boolean enabled;

    /**
     * Get the script the command will execute when run
     * @since 4.0.0
     */
    private Script script;

    /**
     * The {@link ConfigObject} of the command
     * @since 4.0.0
     */
    private ConfigObject object;

    /**
     *
     * Define a command
     *
     * @param name The name of the command
     * @param desc A description of what the command does
     * @param msg The message the command returns
     * @param enabled Whether or not the command is enabled
     * @param script The script to be executed
     * @param object The {@link ConfigObject} associated with the command
     * @since 3.0.0
     *
     */
    public Command(String name, String desc, ArrayList<String> msg, Boolean enabled, Script script, ConfigObject object) {
        this.name = name;
        this.desc = desc;
        this.msg = msg;
        this.enabled = enabled;
        this.script = script;
        this.object = object;
    }

    /**
     *
     * Execute the command and get it's return message
     *
     * @param args The arguments that were executed with the command
     * @return The command's return message
     * @since 3.0.0
     *
     */
    public String execute(ArrayList<String> args) {

        if (script != null) {

            args.add(0, name);
            script.onScriptRun(args);
            return null; // The script will send the message rather than returning one

        }

        else {

            StringBuilder messageBuilder = new StringBuilder();
            for (int i = 0; i < msg.size(); i++) messageBuilder.append(msg.get(i) + "\n");
            return messageBuilder.toString();

        }

    }

    /**
     *
     * Get whether or not the command uses a script
     *
     * @return true if a builtin function is used
     *
     */
    public Boolean getUsesScript() {
        if (script != null) return true;
        else return false;
    }

    /**
     * @return The command's name
     * @since 3.0.0
     */
    public String getName() {
        return name;
    }

    /**
     * @param name The command's name
     * @since 3.0.0
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return The command's description
     * @since 3.0.0
     */
    public String getDesc() {
        return desc;
    }

    /**
     * @param desc The command's description
     * @since 3.0.0
     */
    public void setDesc(String desc) {
        this.desc = desc;
    }

    /**
     * @return The command's msg
     * @since 3.0.0
     */
    public ArrayList<String> getMsg() {
        return msg;
    }

    /**
     * @param msg The command's msg
     * @since 3.0.0
     */
    public void setMsg(ArrayList<String> msg) {
        this.msg = msg;
    }

    /**
     * @return Whether or not the command is enabled
     * @since 3.0.0
     */
    public Boolean getEnabled() {
        return enabled;
    }

    /**
     * @param enabled Whether or not the command is enabled
     * @since 3.0.0
     */
    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    /**
     * @return The script the command executes once it is run
     * @since 4.0.0
     */
    public Script getScript() {
        return script;
    }

    /**
     * @param script The script the command executes once it is run
     * @since 4.0.0
     */
    public void setScript(Script script) {
        this.script = script;
    }

    /**
     * @return The {@link ConfigObject} of the command
     * @since 4.0.0
     */
    public ConfigObject getObject() {
        return object;
    }

    /**
     * @param object The new {@link ConfigObject} of the command
     * @since 4.0.0
     */
    public void setObject(ConfigObject object) {
        this.object = object;
    }
}
