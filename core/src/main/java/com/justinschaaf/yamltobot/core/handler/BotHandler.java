package com.justinschaaf.yamltobot.core.handler;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.amihaiemil.camel.Yaml;
import com.justinschaaf.yamltobot.core.common.Module;
import com.justinschaaf.yamltobot.core.setup.SetupDefaultConfig;
import com.justinschaaf.yamltobot.core.setup.SetupWindow;

import net.jusanov.utils.io.FileManager;

/**
 * 
 * The primary class for generating bots
 * 
 * @author Justin Schaaf
 * @since 2.0.0
 *
 */
public abstract class BotHandler {

	/**
	 * The module of this bot
	 * @since 2.0.0
	 */
	static Module module;
	
	/**
	 * Sets the module of this bot
	 * @param thismodule The module that this bot is
	 * @since 2.0.0
	 */
	public static void setModule(Module thismodule) {
		module = thismodule;
	}
	
	/**
	 * Setup the bot
	 * @since 2.0.0
	 */
	public static void setup() {
		
		File generalConfig = new File(Module.GENERAL.getDir() + "config.yml");
		File config = new File(module.getDir() + "config.yml");
		
		setupLogs();
		
		if (config.exists() == false && generalConfig.exists() == false) setupConfig(config);
		else if (config.exists() == false && generalConfig.exists() == true) setupConfig(config); // Still sets up config because credentials are needed.
		
		if (generalConfig.exists() == true) ConfigHandler.setConfig(generalConfig);
		else ConfigHandler.setConfig(config);
		
		setupWindow();
		
	}
	
	/**
	 * Gets an authentication config value
	 * @param key The authentication item to find
	 * @return The authentication item
	 * @since 2.0.0
	 */
	public static String getAuth(String key) {
		
		try {
			
			String value = Yaml.createYamlInput(new FileInputStream(module.getDir() + "config.yml")).readYamlMapping().string(key);
			
			if (value != null) return value.replace("\"", "");
			else return null;
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return null;
		
	}
	
	/**
	 * Setup the window
	 * @since 2.0.0
	 */
	private static void setupWindow() {
		SetupWindow frame = new SetupWindow(module);
		frame.setVisible(true);
	}
	
	/**
	 * Setup the default config based on module
	 * @param config The config file
	 * @since 2.0.0
	 */
	private static void setupConfig(File config) {
		if (module == Module.DISCORD) SetupDefaultConfig.setupDiscord(config);
		if (module == Module.TWITCH) SetupDefaultConfig.setupTwitch(config);
		else LogHandler.fatal("Invalid Module to generate default config for!");
		LogHandler.fatal("The config was not found!");
	}
	
	/**
	 * 
	 * Configure the logger for use in YamlToBot
	 * @param module 
	 * @since 1.0.0
	 * 
	 */
	private static void setupLogs() {
		
		DateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd-hh.mm.ss");
		String dateOfRun = dateFormat.format(new Date()).toString();
		
		File logDir = new File(module.getDir() + "logs");
		logDir.mkdirs();
		final File logFile = (new File(logDir, "log-" + dateOfRun + ".log"));
		final File latestLog = (new File(logDir, "log-latest.log"));

		LogHandler.setOutputLog(latestLog);
		
		Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {

	        public void run() {
	            try {
	            	Thread.sleep(1000);
					FileManager.moveFile(latestLog, logFile);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
	        }
	        
	    }));
		
		LogHandler.debug("Logs setup!");
		
	}
	
}
