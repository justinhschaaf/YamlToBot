package net.jusanov.yamltobot.core.commands;

import java.io.File;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class BuiltinCommandHandler {
	
	private static HashMap<String, Class<? extends Command>> commands;
	
	public static void initializeCommands() {
		
		try {
			
			List<File> jars = new ArrayList<File>();
			File[] libs = new File("YamlToBot/libs/").listFiles();
			
			for (int i = 0; i < libs.length; i++) {
				
				File lib = libs[i];
				if (lib.getName().endsWith(".jar")) {
					jars.add(lib);
				}
				
			}
			
			URL[] urls = new URL[jars.size()+1];
			ClassLoader classLoader = new URLClassLoader(urls);
			
			urls[0] = new File("net/jusanov/yamltobot/").toURI().toURL();
			
			for(int i = 1; i <= jars.size(); i++) {
				urls[i] = jars.get(i-1).toURI().toURL();
			}
			
			classLoader = new URLClassLoader(urls, ClassLoader.getSystemClassLoader());
			HashMap<String, Class<? extends Command>> commands = new HashMap<String, Class<? extends Command>>();
			
			for (int i = 0; i < urls.length; i++) {
				
				String[] cmdPathArray = urls[i].getFile().split("/");
				String cmdName = cmdPathArray[cmdPathArray.length - 1];
				cmdName = cmdName.substring(0, cmdName.length() - 6);
				Class<? extends Command> cmdClass = classLoader.loadClass(urls[i].toString()).asSubclass(Command.class);
				
				commands.put(cmdName, cmdClass);
				
			}
			
			BuiltinCommandHandler.commands = commands;
			
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
	}
	
	public static Object getCommandByName(String name, ArrayList<String> args) {
		
		try {
			System.out.println("0");
			Constructor<? extends Command> commandConstructor = commands.get(name).getConstructor();
			System.out.println("1");
			Method onCommandExecuted = commands.get(name).getMethod("onCommandExecuted");
			System.out.println("2");
			return onCommandExecuted.invoke(commandConstructor.newInstance());
			
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		}
		
		return null;
		
	}
	
}
