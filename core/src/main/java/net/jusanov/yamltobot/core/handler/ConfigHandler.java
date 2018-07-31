package net.jusanov.yamltobot.core.handler;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import com.amihaiemil.camel.Yaml;
import com.amihaiemil.camel.YamlMapping;
import com.amihaiemil.camel.YamlSequence;

public class ConfigHandler {

	public static File config;
	
	public static void setConfig(File file) {
		config = file;
	}
	
	// General
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
	
	// Commands
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
	
	public static int getIndex(String command) throws FileNotFoundException {
		
		ArrayList<String> commands = getCommands();
		
		int index = -1;
		for (int i = 0; i < commands.size(); i++) {
			if (commands.get(i).trim().equalsIgnoreCase(command.trim())) {
				return i;
			}
		}
		
		return index;
		
	}
	
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
	
	public static String getCommandString(String command, String key) {
		String configValue = getCommand(command).string(key).replace("\"", "");
		if (!configValue.isEmpty()) {
			return configValue;
		} else {
			return null;
		}
	}
	
	public static ArrayList<String> getCommandArray(String command, String key) {
		
		YamlSequence commandYaml = getCommand(command).yamlSequence(key);
		ArrayList<String> array = new ArrayList<String>();
		
		for (int i = 0; i < commandYaml.size(); i++) {
			array.add(commandYaml.string(i).replace("\"", ""));
		}
		
		return array;
		
	}
	
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
