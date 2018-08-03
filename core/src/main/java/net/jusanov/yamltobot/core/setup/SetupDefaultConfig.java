package net.jusanov.yamltobot.core.setup;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

import com.amihaiemil.camel.Yaml;
import com.amihaiemil.camel.YamlMapping;

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
	 * Create the default config file and add data to it
	 * 
	 * @param config The config file to write to
	 * @since 1.0.0
	 * 
	 */
	public static void setupDefaultConfig(File config) {
		
		try {
			
			YamlMapping commandPing = Yaml.createYamlMappingBuilder()
					.add("name", "\"ping\"")
					.add("description", "\"Play Ping Pong!\"")
					.add("enabled", "\"true\"")
					.add("builtin", "\"false\"")
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
			
			YamlMapping yaml = Yaml.createYamlMappingBuilder()
					.add("name", "\"MyFirstBot\"")
					.add("token", "123456789012345678")
					.add("id", "876543210987654321") // ID option only used for TwitchBot
					.add("secret", "135798642135798642") // Secret option only used for TwitchBot
					.add("prefix", "\"::\"")
					.add("commands", Yaml.createYamlSequenceBuilder().add(commandPing).add(commandHelp).build())
					.add("channels", Yaml.createYamlSequenceBuilder().add("Jusanov").build()) // Channels option only used for TwitchBot
					.add("activity", "YamlToBot") // Activity option only used for DiscordBot
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
	
}
