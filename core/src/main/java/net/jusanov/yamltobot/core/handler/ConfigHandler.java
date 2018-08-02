package net.jusanov.yamltobot.core.handler;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import com.amihaiemil.camel.Yaml;
import com.amihaiemil.camel.YamlMapping;
import com.amihaiemil.camel.YamlSequence;

/**
 * 
 * The primary class for pulling information from the config
 * 
 * @author Jusanov
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
	 * Gets a string value from the config
	 * 
	 * @param key The key to take the value from
	 * @return The string value of the given key, or null if the key isn't found
	 * @since 1.0.0
	 * 
	 */
	public static String getString(String key) {
		
		try {
			return Yaml.createYamlInput(new FileInputStream(config)).readYamlMapping().string(key).replace("\"", "");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return null;
		
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
		
		try {
			
			YamlSequence yaml = Yaml.createYamlInput(new FileInputStream(config)).readYamlMapping().yamlSequence(key);
			
			ArrayList<String> array = new ArrayList<String>();
			for (int i = 0; i < yaml.size(); i++) {
				array.add(yaml.string(i).replace("\"", ""));
			}
			
			return array;
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return null;
		
	}

	/**
	 * 
	 * Gets a boolean value from the config
	 * 
	 * @param key The key to take the value from
	 * @return The boolean value of the given key, or the default value if the key isn't found
	 * @since 1.0.0
	 * 
	 */
	public static boolean getBoolean(String key, boolean defaultVal) {
		
		String value = getString(key);
		
		if (value.equalsIgnoreCase("false".replace("\"", "")) && defaultVal == true) {
			return false;
		}
		if (value.equalsIgnoreCase("true".replace("\"", "")) && defaultVal == false) {
			return true;
		}
		return defaultVal;
		
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
		
		int index = -1;
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
			YamlMapping commandYaml;
			
			commandYaml = Yaml.createYamlInput(new FileInputStream(config)).readYamlMapping().yamlSequence("commands").yamlMapping(getIndex(command));
			
			return commandYaml;
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
	 * @return The string value of the given key
	 * @since 1.0.0
	 * 
	 */
	public static String getCommandString(String command, String key) {
		String configValue = getCommand(command).string(key).replace("\"", "");
		if (!configValue.isEmpty()) {
			return configValue;
		} else {
			return null;
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
		
		YamlSequence commandYaml = getCommand(command).yamlSequence(key);
		ArrayList<String> array = new ArrayList<String>();
		
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
	 * @return The boolean value of the given key, or null if the key is not found
	 * @since 1.0.0
	 * 
	 */
	public static boolean getCommandBoolean(String command, String key, boolean defaultVal) {
		
		String value = getCommandString(command, key);
		
		if (value.equalsIgnoreCase("false".replace("\"", "")) && defaultVal == true) {
			return false;
		}
		if (value.equalsIgnoreCase("true".replace("\"", "")) && defaultVal == false) {
			return true;
		}
		return defaultVal;
		
	}
	
}
