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
	SCRIPT("SCRIPT", "YamlToBot/scripts/"),
	DISCORD("Discord", "YamlToBot/discord/"),
	TWITCH("Twitch", "YamlToBot/twitch/"),
	MIXER("Mixer", "YamlToBot/mixer/");

	/**
	 * The name of the module
	 * @since 2.0.0
	 */
	private final String name;

	/**
	 * The module's directory in the YamlToBot folder
	 * @since 2.0.0
	 */
	private final String dir;

	/**
	 * A YamlToBot module
	 *
	 * @param name The name of the module
	 * @param dir The module's directory, including the YamlToBot folder
	 * @since 2.0.0
	 *
	 */
	Module (final String name, final String dir) {
		this.name = name;
		this.dir = dir;
	}

	/**
	 * @return The module's name
	 * @since 2.0.0
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return The module's directory, including the YamlToBot folder
	 * @since 2.0.0
	 */
	public String getDir() {
		return dir;
	}
	
}
