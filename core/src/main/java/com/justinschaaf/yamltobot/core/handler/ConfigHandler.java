package com.justinschaaf.yamltobot.core.handler;

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
	public static File config;
	
	/**
	 * Sets the config file
	 * @param file the file to set the config file to
	 * @since 1.0.0
	 */
	public static void setConfig(File file) {
		config = file;
	}
	
	/*
	 * GENERAL
	 */

	/**
	 *
	 * Gets a YamlSequence from the config
	 *
	 * @param key The key to take the value from
	 * @return The requested YamlSequence, or null if the key isn't found
	 * @since 3.0.0
	 *
	 */
	public static YamlSequence getSequence(String key) {

		YamlSequence yaml = null;

		try {
			yaml = Yaml.createYamlInput(new FileInputStream(config)).readYamlMapping().yamlSequence(key);
		} catch (NullPointerException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return yaml;

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
	public static String getString(String key, String defaultValue) {
		
		String value = new String();
		
		try {
			value = Yaml.createYamlInput(new FileInputStream(config)).readYamlMapping().string(key);
		} catch (NullPointerException e) {
			value = defaultValue;
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		if (value != null) return value.replace("\"", "");
		else return defaultValue;
		
	}

	/**
	 * 
	 * Gets an array from the config
	 * 
	 * @param key The key to take the value from
	 * @return An ArrayList<String> of the values of the given key, or an empty array if the key isn't found
	 * @since 1.0.0
	 * 
	 */
	public static ArrayList<String> getArray(String key) {

		ArrayList<String> array = new ArrayList<String>();
		YamlSequence yaml = null;
		
		try {
			 yaml = Yaml.createYamlInput(new FileInputStream(config)).readYamlMapping().yamlSequence(key);
		} catch (NullPointerException e) {
			return array;
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		for (int i = 0; i < yaml.size(); i++) {
			array.add(yaml.string(i).replace("\"", ""));
		}
		
		return array;
		
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
	public static boolean getBoolean(String key, boolean defaultValue) {
		
		String defaultValueStr = new String();
		if (defaultValue == true) defaultValueStr = "true";
		else if (defaultValue == false) defaultValueStr = "false";
		String value = getString(key, defaultValueStr);
		
		if (value == null) return defaultValue;
		
		if (value.equalsIgnoreCase("false".replace("\"", "")) && defaultValue == true) {
			return false;
		}
		if (value.equalsIgnoreCase("true".replace("\"", "")) && defaultValue == false) {
			return true;
		}
		
		return defaultValue;
		
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
	public static ArrayList<String> getCommands() {
		
		try {
			
			YamlSequence commandYaml = Yaml.createYamlInput(new FileInputStream(config)).readYamlMapping().yamlSequence("commands");
			
			if (commandYaml == null) return null;
			
			ArrayList<String> commands = new ArrayList<String>();
			for (int i = 0; i < commandYaml.size(); i++) {
				commands.add(commandYaml.yamlMapping(i).string("name").replace("\"", ""));
			}
			
			return commands;
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return null;
		
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
	public static int getIndex(String command) {
		
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
	 * @param command The name of the command to get the index of
	 * @return A YamlMapping of the command's values, or null if the data is not found
	 * @since 1.0.0
	 * 
	 */
	public static YamlMapping getCommand(String command) {
		
		try {
			
			return Yaml.createYamlInput(new FileInputStream(config)).readYamlMapping().yamlSequence("commands").yamlMapping(getIndex(command));

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
		
	}
	
	/**
	 * 
	 * Gets a command's string value from the given key
	 * 
	 * @param command The name of the command to get the string value from
	 * @param key The key to take the value from
	 * @param defaultValue The default value
	 * @return The string value of the given key
	 * @since 1.0.0
	 * 
	 */
	public static String getCommandString(String command, String key, String defaultValue) {
		
		String value;
		
		try {
			value = getCommand(command).string(key);
		} catch (NullPointerException e) {
			value = defaultValue;
		}
		
		if (value != null) {
			return value.replace("\"", "");
		} else {
			return defaultValue;
		}
		
	}
	
	/**
	 * 
	 * Gets a command's array from the given key
	 * 
	 * @param command The name of the command to get the array from
	 * @param key The key to take the value from
	 * @return An ArrayList<String> of the values of the given key, or an empty array if the key is not found
	 * @since 1.0.0
	 * 
	 */
	public static ArrayList<String> getCommandArray(String command, String key) {

		ArrayList<String> array = new ArrayList<String>();
		YamlSequence commandYaml;
		
		try {
			commandYaml = getCommand(command).yamlSequence(key);
		} catch (NullPointerException e) {
			return array;
		}
		
		for (int i = 0; i < commandYaml.size(); i++) {
			array.add(commandYaml.string(i).replace("\"", ""));
		}
		
		return array;
		
	}
	
	/**
	 * 
	 * Gets a command's boolean value from the given key
	 * 
	 * @param command The name of the command to get the boolean value from
	 * @param key The key to take the value from
	 * @param defaultValue The default value
	 * @return The boolean value of the given key, or null if the key is not found
	 * @since 1.0.0
	 * 
	 */
	public static boolean getCommandBoolean(String command, String key, boolean defaultValue) {

		String defaultValueStr = new String();
		if (defaultValue == true) defaultValueStr = "true";
		else if (defaultValue == false) defaultValueStr = "false";
		String value = getCommandString(command, key, defaultValueStr);
		
		if (value == null) return defaultValue;
		
		if (value.equalsIgnoreCase("false".replace("\"", "")) && defaultValue == true) {
			return false;
		}
		if (value.equalsIgnoreCase("true".replace("\"", "")) && defaultValue == false) {
			return true;
		}
		
		return defaultValue;
		
	}
	
	
	/**
	 * 
	 * Gets a command's string value from the given key
	 * 
	 * @param command The name of the command to get the string value from
	 * @param yamlMapping The YamlMapping key to be getting the value from
	 * @param key The key to take the value from
	 * @param defaultValue The default value
	 * @return The string value of the given key
	 * @since 1.0.0
	 * 
	 */
	public static String getCommandMappingString(String command, String yamlMapping, String key, String defaultValue) {
		
		String value;
		
		try {
			value = getCommand(command).yamlMapping(yamlMapping).string(key);
		} catch (NullPointerException e) {
			value = defaultValue;
		}
		
		if (value != null) {
			return value.replace("\"", "");
		} else {
			return value;
		}
	}
	
	/**
	 * 
	 * Gets a command's array from the given key
	 * 
	 * @param command The name of the command to get the array from
	 * @param yamlMapping The YamlMapping key to be getting the value from
	 * @param key The key to take the value from
	 * @return An ArrayList<String> of the values of the given key, or an empty array if the key is not found
	 * @since 1.0.0
	 * 
	 */
	public static ArrayList<String> getCommandMappingArray(String command, String yamlMapping, String key) {

		ArrayList<String> array = new ArrayList<String>();
		YamlSequence commandYaml;
		
		try {
			commandYaml = getCommand(command).yamlMapping(yamlMapping).yamlSequence(key);
		} catch (NullPointerException e) {
			return array;
		}
		
		
		for (int i = 0; i < commandYaml.size(); i++) {
			array.add(commandYaml.string(i).replace("\"", ""));
		}
		
		return array;
		
	}
	
	/**
	 * 
	 * Gets a command's boolean value from the given key
	 * 
	 * @param command The name of the command to get the boolean value from
	 * @param yamlMapping The YamlMapping key to be getting the value from
	 * @param key The key to take the value from
	 * @param defaultValue The default value
	 * @return The boolean value of the given key, or null if the key is not found
	 * @since 1.0.0
	 * 
	 */
	public static boolean getCommandMappingBoolean(String command, String yamlMapping, String key, boolean defaultValue) {

		String defaultValueStr = new String();
		if (defaultValue == true) defaultValueStr = "true";
		else if (defaultValue == false) defaultValueStr = "false";
		
		String value = getCommandMappingString(command, yamlMapping, key, defaultValueStr);
		
		if (value == null) return defaultValue;
		
		if (value.equalsIgnoreCase("false".replace("\"", "")) && defaultValue == true) {
			return false;
		}
		if (value.equalsIgnoreCase("true".replace("\"", "")) && defaultValue == false) {
			return true;
		}
		
		return defaultValue;
		
	}
	
}
