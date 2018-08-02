package net.jusanov.yamltobot.core.commands;

public abstract class Command {
	
	public Command() {
		
	}
	
	public abstract String onCommandExecuted(String commandName);
	
}
