package com.yamltobot.core.handler;

import com.amihaiemil.eoyaml.YamlMapping;
import com.amihaiemil.eoyaml.YamlSequence;

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
    private static HashMap<String, String> variables = new HashMap<>();

    /**
     *
     * Load the variables registered in the YamlToBot config
     *
     * @since 3.0.0
     *
     */
    public static void loadVariables() {

        long starttime = System.currentTimeMillis();

        YamlSequence yaml = ConfigHandler.getSequence("variables");

        if (yaml == null) return;

        for (int i = 0; i < yaml.size(); i++) {

            YamlMapping var = yaml.yamlMapping(i);

            try {

                String key = var.string("key").replaceAll("\"", "");
                String val = var.string("value").replaceAll("\"", "");

                if (key.contains(" ") || key.contains("\\") || key.contains("/") || key.contains(":") || key.contains("*") || key.contains("?") || key.contains("\"") || key.contains("<") || key.contains(">") || key.contains("|") || key.contains("'")) {
                    LogHandler.error("Variable keys can't contain any of the following characters: \\ / : * ? \" ' < > |");
                    continue;
                }

                variables.put(key, val);

            } catch (NullPointerException e) {
                e.printStackTrace();
            }

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
    public static String formatMessage(String message) {

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
    public static HashMap<String, String> getVariables() {
        return variables;
    }

    /**
     * @param variables The new list of registered variables for the bot to use
     * @since 3.0.0
     */
    public static void setVariables(HashMap<String, String> variables) {
        VariableHandler.variables = variables;
    }

}
