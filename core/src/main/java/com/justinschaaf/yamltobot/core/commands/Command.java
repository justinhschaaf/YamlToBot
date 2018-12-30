package com.justinschaaf.yamltobot.core.commands;

import com.justinschaaf.yamltobot.core.commands.builtin.BuiltinCommand;

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

    private String name;
    private String desc;
    private ArrayList<String> msg;
    private Boolean enabled;
    private BuiltinCommand builtin;

    /**
     *
     * Define a command
     *
     * @param name The name of the command
     * @param desc A description of what the command does
     * @param msg The message the command returns
     * @param enabled Whether or not the command is enabled
     * @param builtin The builtin command to be executed
     * @since 3.0.0
     *
     */
    public Command(String name, String desc, ArrayList<String> msg, Boolean enabled, BuiltinCommand builtin) {
        this.name = name;
        this.desc = desc;
        this.msg = msg;
        this.enabled = enabled;
        this.builtin = builtin;
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
        if (builtin != null) return builtin.onCommandExecuted(name, args);
        else {
            StringBuilder messageBuilder = new StringBuilder();
            for (int i = 0; i < msg.size(); i++) messageBuilder.append(msg.get(i) + "\n");
            return messageBuilder.toString();
        }
    }

    /**
     *
     * Get whether or not the command uses a builtin function
     *
     * @return true if a builtin function is used
     *
     */
    public Boolean getIsBuiltin() {
        if (builtin != null) return true;
        else return false;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public ArrayList<String> getMsg() {
        return msg;
    }

    public void setMsg(ArrayList<String> msg) {
        this.msg = msg;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public BuiltinCommand getBuiltin() {
        return builtin;
    }

    public void setBuiltin(BuiltinCommand builtin) {
        this.builtin = builtin;
    }

}
