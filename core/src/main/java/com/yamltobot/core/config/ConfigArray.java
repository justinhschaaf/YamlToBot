package com.yamltobot.core.config;

import com.amihaiemil.eoyaml.YamlMapping;
import com.amihaiemil.eoyaml.YamlSequence;

import java.util.ArrayList;

public class ConfigArray {

    /**
     * The {@link YamlSequence} to read data from
     * @since 4.0.0
     */
    private YamlSequence sequence;

    /**
     *
     * A YamlToBOt Configuration Object, based off of a {@link YamlSequence}
     *
     * @param sequence The {@link YamlSequence} to read values from
     * @since 4.0.0
     *
     */
    public ConfigArray(YamlSequence sequence) {
        this.sequence = sequence;
    }

    /**
     * @return This {@link ConfigArray}'s {@link YamlSequence}
     * @since 4.0.0
     */
    public YamlSequence getSequence() {
        return sequence;
    }

    /**
     * @param sequence The new {@link YamlSequence} for this {@link ConfigArray}
     * @since 4.0.0
     */
    public void setSequence(YamlSequence sequence) {
        this.sequence = sequence;
    }

    /**
     *
     * The number of elements found in this ConfigArray
     *
     * @return The number of elements found in this ConfigArray
     * @since 4.0.0
     *
     */
    public int size() {
        return sequence.size();
    }

    /**
     *
     * Gets a string value from the {@link ConfigArray}
     *
     * @param index The index to take the value from
     * @return The string value of the given index, or null if the index isn't found
     * @since 4.0.0
     *
     */
    public String getString(int index) {
        return getString(index, null);
    }

    /**
     *
     * Gets a string value from the {@link ConfigArray}
     *
     * @param index The index to take the value from
     * @param defaultValue The default value
     * @return The string value of the given index, or the default value if the index isn't found
     * @since 4.0.0
     *
     */
    public String getString(int index, String defaultValue) {

        String value = new String();

        try {
            value = this.sequence.string(index);
        } catch (NullPointerException e) {
            value = defaultValue;
        }

        if (value != null) return value.replace("\"", "");
        else return defaultValue;

    }

    /**
     *
     * Gets an array from the {@link ConfigArray}
     *
     * @param index The index to take the value from
     * @return An ArrayList<String> of the values of the given index, or null if the index isn't found
     * @since 4.0.0
     *
     */
    public ArrayList<String> getArray(int index) {

        ArrayList<String> array = new ArrayList<String>();
        YamlSequence sequence = null;

        try {
            sequence = this.sequence.yamlSequence(index);
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
     * Gets a boolean value from the {@link ConfigArray}
     *
     * @param index The index to take the value from
     * @return The boolean value of the given index, or false if the index isn't found
     * @since 4.0.0
     *
     */
    public boolean getBoolean(int index) {
        return getBoolean(index, false);
    }

    /**
     *
     * Gets a boolean value from the {@link ConfigArray}
     *
     * @param index The index to take the value from
     * @param defaultValue The default value
     * @return The boolean value of the given index, or the default value if the index isn't found
     * @since 4.0.0
     *
     */
    public boolean getBoolean(int index, boolean defaultValue) {

        String value = getString(index, Boolean.toString(defaultValue));

        if (value == null) return defaultValue;

        return Boolean.parseBoolean(value);

    }

    /**
     *
     * Gets an int value from the {@link ConfigArray}
     *
     * @param index The index to take the value from
     * @return The int value of the given index, or 0 if the index isn't found
     * @since 4.0.0
     *
     */
    public int getInt(int index) {
        return getInt(index, 0);
    }

    /**
     *
     * Gets an int value from the {@link ConfigArray}
     *
     * @param index The index to take the value from
     * @param defaultValue The default value
     * @return The int value of the given index, or the default value if the index isn't found
     * @since 4.0.0
     *
     */
    public int getInt(int index, int defaultValue) {

        String value = getString(index, Integer.toString(defaultValue));

        if (value == null) return defaultValue;

        return Integer.parseInt(value);

    }

    /**
     *
     * Gets a {@link ConfigObject} from this {@link ConfigArray}
     *
     * @param index The index to take the value from
     * @return The {@link ConfigObject} of the given index, or null if the index isn't found
     * @since 4.0.0
     *
     */
    public ConfigObject getConfigObject(int index) {

        YamlMapping mapping = null;

        try {
            mapping = this.sequence.yamlMapping(index);
        } catch (NullPointerException e) {
            return null;
        }

        return new ConfigObject(mapping);

    }

    /**
     *
     * Gets a {@link ConfigArray} from this {@link ConfigArray}
     *
     * @param index The index to take the value from
     * @return The {@link ConfigArray} of the given index, or null if the index isn't found
     * @since 4.0.0
     *
     */
    public ConfigArray getConfigArray(int index) {

        YamlSequence sequence = null;

        try {
            sequence = this.sequence.yamlSequence(index);
        } catch (NullPointerException e) {
            return null;
        }

        return new ConfigArray(sequence);

    }

}
