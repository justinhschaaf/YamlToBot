package net.jusanov.yamltobot.core.setup;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

import com.amihaiemil.camel.Yaml;
import com.amihaiemil.camel.YamlMapping;

public class SetupDefaultConfig {

	public static void setupDefaultConfig(File config) {
		
		try {
			
			YamlMapping commandPing = Yaml.createYamlMappingBuilder()
					.add("name", "\"ping\"")
					.add("enabled", "\"true\"")
					.add("message", Yaml.createYamlSequenceBuilder().add("\"pong!\"").build())
					.build();
			
			YamlMapping yaml = Yaml.createYamlMappingBuilder()
					.add("name", "\"MyFirstBot\"")
					.add("token", "123456789012345678")
					.add("prefix", "\"::\"")
					.add("commands", Yaml.createYamlSequenceBuilder().add(commandPing).build())
					.add("channels", Yaml.createYamlSequenceBuilder().add("Jusanov").build()) // Channels option only used for TwitchBot
					.add("activity", "YamlToBot") // Status option only used for DiscordBot
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
