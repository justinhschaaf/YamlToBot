package com.yamltobot.core.common;

/**
 * 
 * The enum containing all data about the different modules
 * 
 * @author Justin Schaaf
 * @since 2.0.0
 *
 */
public enum Module {

	GENERAL("GENERAL", "YamlToBot/"),
	DISCORD("Discord", "YamlToBot/discord/"),
	TWITCH("Twitch", "YamlToBot/twitch/"),
	MIXER("Mixer", "YamlToBot/mixer/");
	
	private final String name;
	private final String dir;
	
	private Module (final String name, final String dir) {
		this.name = name;
		this.dir = dir;
	}
	
	public String getName() {
		return name;
	}

	public String getDir() {
		return dir;
	}
	
}
