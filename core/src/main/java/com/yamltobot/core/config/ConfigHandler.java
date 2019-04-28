package com.yamltobot.core.config;

import com.amihaiemil.eoyaml.Yaml;
import com.amihaiemil.eoyaml.YamlMapping;
import com.amihaiemil.eoyaml.YamlSequence;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

/**
 *
 * The primary class for pulling information from the config
 *
 * @author Justin Schaaf
 * @since 1.0.0
 *
 */
public class ConfigHandler {

    /**
     * The config file to read from
     * @since 1.0.0
     */
    public File config;

    /**
     *
     * The primary class for pulling information from the config
     *
     * @param config The file to get information from
     * @since 4.0.0
     *
     */
    public ConfigHandler(File config) {
        this.config = config;
    }

    /*
     * GENERAL
     */

    /**
     *
     * Get the contents of the config file, as a {@link ConfigObject}
     *
     * @return The contents of the config file, as a {@link ConfigObject}
     * @since 4.0.0
     *
     */
    public ConfigObject getConfig() {

        try {

            YamlMapping mapping = Yaml.createYamlInput(new FileInputStream(config)).readYamlMapping();

            return new ConfigObject(mapping);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;

    }

    /*
     * COMMANDS
     */

    /**
     *
     * Gets the names of all the commands registered in the config
     *
     * @return An ArrayList<String> of all the command names
     * @since 1.0.0
     *
     */
    public ArrayList<String> getCommands() {

        YamlSequence commandYaml = getConfig().getConfigArray("commands").getSequence();

        if (commandYaml == null) return null;

        ArrayList<String> commands = new ArrayList<String>();
        for (int i = 0; i < commandYaml.size(); i++) {
            commands.add(commandYaml.yamlMapping(i).string("name").replace("\"", ""));
        }

        return commands;

    }

    /**
     *
     * Gets the index of the given command
     *
     * @param command The name of the command to get the index of
     * @return The index of the given command
     * @since 1.0.0
     *
     */
    public int getIndex(String command) {

        ArrayList<String> commands = getCommands();

        if (commands == null) return 0;

        int index = 0;
        for (int i = 0; i < commands.size(); i++) {
            if (commands.get(i).trim().equalsIgnoreCase(command.trim())) {
                return i;
            }
        }

        return index;

    }

    /**
     *
     * Gets all the Yaml data of the given command name
     *
     * @param command The name of the command to get the config object of
     * @return A {@link ConfigObject} of the command's config object, or null if the data is not found
     * @since 1.0.0
     *
     */
    public ConfigObject getCommand(String command) {

        return new ConfigObject(getConfig().getConfigArray("commands").getSequence().yamlMapping(getIndex(command)));

    }

}
