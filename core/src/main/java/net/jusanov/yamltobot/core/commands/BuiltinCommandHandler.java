package net.jusanov.yamltobot.core.commands;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;

public class BuiltinCommandHandler {
	
	public static Object getCommand(String cmdClass, String name, ArrayList<String> args) {
		
		try {
			
			Class<? extends Command> builtinCmdClass = null;
			if (cmdClass.contains("%int%")) {
				builtinCmdClass = Class.forName("net.jusanov.yamltobot.core.commands." + cmdClass.replace("%int%", "")).asSubclass(Command.class);
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
