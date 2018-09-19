package com.justinschaaf.yamltobot.core.commands;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;

/**
 * 
 * The class for importing predefined commands.
 * 
 * @author Justin Schaaf
 * @since 1.0.0
 *
 */
public class BuiltinCommandHandler {
	
	/**
	 * 
	 * Gets a predefined command from the given class, name, and args.
	 * 
	 * @param cmdClass The class of the command, including the internal or external tag, e.g. %int%HelpCommand
	 * @param name The name of the command, e.g. help
	 * @param args The arguments of the command. Work In Progress.
	 * @return The string message that the command returns
	 * @since 1.0.0
	 * 
	 */
	public static Object getCommand(String cmdClass, String name, ArrayList<String> args) {
		
		try {
			
			Class<? extends Command> builtinCmdClass = null;
			if (cmdClass.contains("%int%")) {
				builtinCmdClass = Class.forName("com.justinschaaf.yamltobot.core.commands." + cmdClass.replace("%int%", "")).asSubclass(Command.class);
			} else if (cmdClass.contains("%ext%")) {
				ClassLoader classLoader = loadCmds("YamlToBot/cmds/");
				builtinCmdClass = classLoader.loadClass(cmdClass.replace("%ext%", "")).asSubclass(Command.class);
			}
			
			Command builtinCmdObj = (Command) builtinCmdClass.newInstance();
			
			return builtinCmdObj.onCommandExecuted(name);
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		
		return null;
		
	}
	
	/**
	 * 
	 * Loads command classes from the given directory.
	 * 
	 * @param dir Where to load the classes from
	 * @return The ClassLoader of all the commands loaded
	 * @since 1.0.0
	 * 
	 */
	private static ClassLoader loadCmds(String dir) {
		
		File[] cmdRaw = new File(dir).listFiles();
		URL[] cmdUrls = new URL[cmdRaw.length];
		
		for (int i = 0; i < cmdRaw.length; i++) {
			try {
				cmdUrls[i] = cmdRaw[i].toURI().toURL();
			} catch (MalformedURLException e) {
				e.printStackTrace();
			}
		}
		
		return new URLClassLoader(cmdUrls, ClassLoader.getSystemClassLoader());
		
	}
	
}
