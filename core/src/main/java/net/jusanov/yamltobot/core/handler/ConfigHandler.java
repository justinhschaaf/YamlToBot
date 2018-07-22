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

	public static final File config = new File("YamlToBot/config.yml");
	
	// General
	public static String getString(String key) {
		
		try {
			return Yaml.createYamlInput(new FileInputStream(config)).readYamlMapping().string(key);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return null;
		
	}
	
	public static ArrayList<String> getArray(String key) {
		
		try {
			
			YamlSequence channelsYaml = Yaml.createYamlInput(new FileInputStream(config)).readYamlMapping().yamlSequence(key);
			
			ArrayList<String> channels = new ArrayList<String>();
			for (int i = 0; i < channelsYaml.size(); i++) {
				channels.add(channelsYaml.string(i));
			}
			
			return channels;
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return null;
		
	}
	
	// Commands
	public static ArrayList<String> getCommands() {
		
		try {
			
			YamlSequence commandYaml = Yaml.createYamlInput(new FileInputStream(config)).readYamlMapping().yamlSequence("commands");
			
			ArrayList<String> commands = new ArrayList<String>();
			for (int i = 0; i < commandYaml.size(); i++) {
				commands.add(commandYaml.yamlMapping(i).string("name"));
			}
			
			return commands;
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return null;
		
	}
	
	public static int getIndex(String command) {
		
		try {
			
			YamlSequence commandYaml = Yaml.createYamlInput(new FileInputStream(config)).readYamlMapping()
			.yamlSequence("commands");
			
			int index = -1;
			for (int i = 0; i < commandYaml.size(); i++) {
				if (commandYaml.yamlMapping(i).string("name") == command) {
					index = i;
					break;
				}
			}
			
			return index;
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return -1;
		
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
		return getCommand(command).string(key);
	}
	
	public static ArrayList<String> getCommandArray(String command, String key) {
		
		YamlSequence commandYaml = getCommand(command).yamlSequence(key);
		ArrayList<String> array = new ArrayList<String>();
		
		for (int i = 0; i < commandYaml.size(); i++) {
			array.add(commandYaml.string(i));
		}
		
		return array;
		
	}
	
	public static boolean getEnabled(String command) {
		
		String enabled = getCommand(command).string("enabled");
		
		if (enabled.equalsIgnoreCase("false")) {
			return false;
		} else {
			return true;
		}
		
	}
	
}
