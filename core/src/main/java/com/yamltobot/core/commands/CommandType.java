package com.yamltobot.core.commands;

import com.yamltobot.core.common.Module;

/**
 * @author Justin Schaaf
 * @since 2.0.0
 */
public enum CommandType {

	NORMAL("normal"),
	PREDEFINED("predefined"),
	EMBED("embed", Module.DISCORD);
	
	private final String name;
	private final Module module;
	
	private CommandType(String name) {
		this(name, Module.GENERAL);
	}
	
	private CommandType(String name, Module module) {
		this.name = name;
		this.module = module;
	}
	
	public String getName() {
		return name;
	}
	
	public Module getModule() {
		return module;
	}
	
}
