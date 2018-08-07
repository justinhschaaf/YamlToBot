package net.jusanov.yamltobot.core.setup;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

import com.amihaiemil.camel.Yaml;
import com.amihaiemil.camel.YamlMapping;
import com.amihaiemil.camel.YamlMappingBuilder;

/**
 * 
 * The primary class for setting up the default config
 * 
 * @author Jusanov
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
				.add("builtin", "\"true\"")
				.add("predefined-function", "\"%int%HelpCommand\"")
				.add("message", Yaml.createYamlSequenceBuilder().add("\"%0%Commands:\"").add("\"%1%%cmd% | %desc%\"").build())
				.build();
		
		YamlMappingBuilder yaml = Yaml.createYamlMappingBuilder()
				.add("name", "\"MyFirstBot\"")
				.add("token", "123456789012345678")
				.add("prefix", "\"::\"")
				.add("commands", Yaml.createYamlSequenceBuilder().add(commandPing).add(commandHelp).build());
		
		return yaml;
		
	}
	
}
