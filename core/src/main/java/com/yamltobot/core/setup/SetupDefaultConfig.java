package com.yamltobot.core.setup;

import com.amihaiemil.eoyaml.YamlMapping;
import com.amihaiemil.eoyaml.YamlMappingBuilder;
import com.amihaiemil.eoyaml.Yaml;

import java.io.*;

/**
 * 
 * The primary class for setting up the default config
 * 
 * @author Justin Schaaf
 * @since 1.0.0
 *
 */
public class SetupDefaultConfig {
	
	/**
	 * 
	 * Setup the default config file for Discord
	 * 
	 * @param config The file to write the config to
	 * @since 1.0.0
	 * 
	 */
	public static void setupDiscord(File config) {
		
		try {
			
			YamlMapping yaml = setup()
					.add("activity", "YamlToBot")
					.build();
			
			BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(config)));
			bufferedWriter.write(yaml.toString());
			bufferedWriter.close();
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	/**
	 * 
	 * Setup the default config file for Twitch
	 * 
	 * @param config The file to write the config to
	 * @since 1.0.0
	 * 
	 */
	public static void setupTwitch(File config) {
		
		try {
			
			YamlMapping yaml = setup()
					.add("id", "876543210987654321")
					.add("secret", "135798642135798642")
					.add("channels", Yaml.createYamlSequenceBuilder().add("jusanov").build())
					.build();
			
			BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(config)));
			bufferedWriter.write(yaml.toString());
			bufferedWriter.close();
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

	/**
	 *
	 * Setup the default config file for Mixer
	 *
	 * @param config The file to write the config to
	 * @since 4.0.0
	 *
	 */
	public static void setupMixer(File config) {

		try {

			YamlMapping yaml = setup()
					.add("id", "876543210987654321")
					.build();

			BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(config)));
			bufferedWriter.write(yaml.toString());
			bufferedWriter.close();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	
	/**
	 * 
	 * Create the default config data
	 * 
	 * @since 1.0.0
	 * 
	 */
	private static YamlMappingBuilder setup() {
		
		YamlMapping commandPing = Yaml.createYamlMappingBuilder()
				.add("name", "\"ping\"")
				.add("description", "\"Play Ping Pong!\"")
				.add("message", Yaml.createYamlSequenceBuilder().add("\"pong!\"").build())
				.build();
		
		YamlMapping commandHelp = Yaml.createYamlMappingBuilder()
				.add("name", "\"help\"")
				.add("description", "\"Shows a list of commands.\"")
				.add("enabled", "\"true\"")
				.add("predefined-function", "\"HelpCommand\"")
				.add("message", Yaml.createYamlSequenceBuilder().add("\"Commands:\"").add("\"%cmd% | %desc%\"").add("%cmd% --  %desc%").build())
				.add("usage", "::help <command name>")
				.build();
		
		YamlMappingBuilder yaml = Yaml.createYamlMappingBuilder()
				.add("name", "\"MyFirstBot\"")
				.add("token", "123456789012345678")
				.add("prefix", "\"::\"")
				.add("commands", Yaml.createYamlSequenceBuilder().add(commandPing).add(commandHelp).build());
		
		return yaml;
		
	}
	
}
