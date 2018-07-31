package net.jusanov.yamltobot.core.commands;

import java.util.ArrayList;

public abstract class Command {

	String commandName;
	ArrayList<String> args;
	
	public Command(String commandName, ArrayList<String> args) {
		this.commandName = commandName;
		this.args = args;
	}
	
	public abstract String onCommandExecuted();
	
}
