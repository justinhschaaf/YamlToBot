package com.yamltobot.core.commands;

import com.yamltobot.core.common.Module;

/**
 * @author Justin Schaaf
 * @since 2.0.0
 */
public enum CommandType {

	NORMAL("normal"),
	SCRIPT("script"),
	EMBED("embed", Module.DISCORD);

	/**
	 * The command type's name
	 * @since 2.0.0
	 */
	private final String name;

	/**
	 * The module the command type can be used with
	 * @since 2.0.0
	 */
	private final Module module;

	/**
	 *
	 * A type of command
	 *
	 * @param name The name of the command type
	 * @since 2.0.0
	 *
	 */
	private CommandType(String name) {
		this(name, Module.GENERAL);
	}

	/**
	 *
	 * A type of command
	 *
	 * @param name The name of the command type
	 * @param module The module this command type works exclusively with
	 * @since 2.0.0
	 *
	 */
	private CommandType(String name, Module module) {
		this.name = name;
		this.module = module;
	}

	/**
	 * @return The name of the command type
	 * @since 2.0.0
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return The module this command exclusively works with
	 * @since 2.0.0
	 */
	public Module getModule() {
		return module;
	}
	
}
