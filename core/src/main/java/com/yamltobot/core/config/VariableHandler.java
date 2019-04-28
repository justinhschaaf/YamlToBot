package com.yamltobot.core.config;

import com.yamltobot.core.main.LogHandler;

import java.util.HashMap;
import java.util.Set;

/**
 *
 * The primary class for handling variables in YamlToBot
 *
 * @since 3.0.0
 * @author Justin Schaaf
 *
 */
public class VariableHandler {

    /**
     * The variable names and values the bot has registered
     * @since 3.0.0
     */
    private HashMap<String, String> variables = new HashMap<>();

    /**
     *
     * The primary class for handling variables in YamlToBot
     *
     * @param variables The {@link ConfigArray} of the variables defined in the config
     * @since 4.0.0
     *
     */
    public VariableHandler(ConfigArray variables) {
        loadVariables(variables);
    }

    /**
     *
     * Load the variables registered in the YamlToBot config
     *
     * @since 3.0.0
     *
     */
    public void loadVariables(ConfigArray variables) {

        long starttime = System.currentTimeMillis();

        for (int i = 0; i < variables.size(); i++) {

            ConfigObject var = variables.getConfigObject(i);

            String key = var.getString("key");
            String val = var.getString("value");

            if (key.contains(" ") || key.contains("\\") || key.contains("/") || key.contains(":") || key.contains("*") || key.contains("?") || key.contains("\"") || key.contains("<") || key.contains(">") || key.contains("|") || key.contains("'")) {
                LogHandler.error("Variable keys can't contain any of the following characters: \\ / : * ? \" ' < > |");
                continue;
            }

            this.variables.put(key, val);

        }

        LogHandler.debug("Variables loaded in " + (System.currentTimeMillis() - starttime) + " milliseconds!");

    }

    /**
     *
     * Format a message with the variables
     *
     * @param message The message to be formatted
     * @return the formatted message
     * @since 3.0.0
     *
     */
    public String formatMessage(String message) {

        Set<String> keys = variables.keySet();

        for (String key : keys) {
            String keyIndicator = "%" + key + "%";
            if (message.contains(keyIndicator)) message = message.replaceAll(keyIndicator, variables.get(key));
        }

        return message;

    }

    /**
     * @return The list of registered variables
     * @since 3.0.0
     */
    public HashMap<String, String> getVariables() {
        return variables;
    }

    /**
     * @param variables The new list of registered variables for the bot to use
     * @since 3.0.0
     */
    public void setVariables(HashMap<String, String> variables) {
        this.variables = variables;
    }

}
