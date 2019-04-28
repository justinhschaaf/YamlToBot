package com.yamltobot.core.config;

import com.amihaiemil.eoyaml.YamlMapping;
import com.amihaiemil.eoyaml.YamlSequence;

import java.util.ArrayList;

/**
 *
 * A YamlToBot Configuration Object, based off of a {@link YamlMapping}
 *
 * @author Justin Schaaf
 * @since 4.0.0
 *
 */
public class ConfigObject {

    /**
     * The {@link YamlMapping} to read data from
     * @since 4.0.0
     */
    private YamlMapping mapping;

    /**
     *
     * A YamlToBOt Configuration Object, based off of a {@link YamlMapping}
     *
     * @param mapping The {@link YamlMapping} to read values from
     * @since 4.0.0
     *
     */
    public ConfigObject(YamlMapping mapping) {
        this.mapping = mapping;
    }

    /**
     * @return This {@link ConfigObject}'s {@link YamlMapping}
     * @since 4.0.0
     */
    public YamlMapping getMapping() {
        return mapping;
    }

    /**
     * @param mapping The new {@link YamlMapping} for this {@link ConfigObject}
     * @since 4.0.0
     */
    public void setMapping(YamlMapping mapping) {
        this.mapping = mapping;
    }

    /**
     *
     * Gets a string value from the config
     *
     * @param key The key to take the value from
     * @return The string value of the given key, or null if the key isn't found
     * @since 4.0.0
     *
     */
    public String getString(String key) {
        return getString(key, null);
    }

    /**
     *
     * Gets a string value from the config
     *
     * @param key The key to take the value from
     * @param defaultValue The default value
     * @return The string value of the given key, or the default value if the key isn't found
     * @since 1.0.0
     *
     */
    public String getString(String key, String defaultValue) {

        String value = new String();

        try {
            value = this.mapping.string(key);
        } catch (NullPointerException e) {
            value = defaultValue;
        }

        if (value != null) return value.replace("\"", "");
        else return defaultValue;

    }

    /**
     *
     * Gets an array from the config
     *
     * @param key The key to take the value from
     * @return An ArrayList<String> of the values of the given key, or null if the key isn't found
     * @since 1.0.0
     *
     */
    public ArrayList<String> getArray(String key) {

        ArrayList<String> array = new ArrayList<String>();
        YamlSequence sequence = null;

        try {
            sequence = this.mapping.yamlSequence(key);
        } catch (NullPointerException e) {
            return null;
        }

        for (int i = 0; i < sequence.size(); i++) {
            array.add(sequence.string(i).replace("\"", ""));
        }

        return array;

    }

    /**
     *
     * Gets a boolean value from the config
     *
     * @param key The key to take the value from
     * @return The boolean value of the given key, or false if the key isn't found
     * @since 4.0.0
     *
     */
    public boolean getBoolean(String key) {
        return getBoolean(key, false);
    }

    /**
     *
     * Gets a boolean value from the config
     *
     * @param key The key to take the value from
     * @param defaultValue The default value
     * @return The boolean value of the given key, or the default value if the key isn't found
     * @since 1.0.0
     *
     */
    public boolean getBoolean(String key, boolean defaultValue) {

        String value = getString(key, Boolean.toString(defaultValue));

        if (value == null) return defaultValue;

        return Boolean.parseBoolean(value);

    }

    /**
     *
     * Gets an int value from the config
     *
     * @param key The key to take the value from
     * @return The int value of the given key, or 0 if the key isn't found
     * @since 4.0.0
     *
     */
    public int getInt(String key) {
        return getInt(key, 0);
    }

    /**
     *
     * Gets an int value from the config
     *
     * @param key The key to take the value from
     * @param defaultValue The default value
     * @return The int value of the given key, or the default value if the key isn't found
     * @since 4.0.0
     *
     */
    public int getInt(String key, int defaultValue) {

        String value = getString(key, Integer.toString(defaultValue));

        if (value == null) return defaultValue;

        return Integer.parseInt(value);

    }

    /**
     *
     * Gets a {@link ConfigObject} from this ConfigObject
     *
     * @param key The key to take the value from
     * @return The {@link ConfigObject} of the given key, or null if the key isn't found
     * @since 4.0.0
     *
     */
    public ConfigObject getConfigObject(String key) {

        YamlMapping mapping = null;

        try {
            mapping = this.mapping.yamlMapping(key);
        } catch (NullPointerException e) {
            return null;
        }

        return new ConfigObject(mapping);

    }

    /**
     *
     * Gets a {@link ConfigArray} from this ConfigObject
     *
     * @param key The key to take the value from
     * @return The {@link ConfigArray} of the given key, or null if the key isn't found
     * @since 4.0.0
     *
     */
    public ConfigArray getConfigArray(String key) {

        YamlSequence sequence = null;

        try {
            sequence = this.mapping.yamlSequence(key);
        } catch (NullPointerException e) {
            return null;
        }

        return new ConfigArray(sequence);

    }

}
