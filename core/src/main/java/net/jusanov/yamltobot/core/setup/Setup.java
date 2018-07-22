package net.jusanov.yamltobot.core.setup;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import net.jusanov.utils.io.FileManager;
import net.jusanov.yamltobot.core.handler.ConfigHandler;
import net.jusanov.yamltobot.core.handler.LogHandler;

public class Setup {

	public static void setupLogs() {
		
		DateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd-hh.mm.ss");
		String dateOfRun = dateFormat.format(new Date()).toString();
		
		File logDir = new File("YamlToBot/logs");
		logDir.mkdirs();
		File logFile = (new File(logDir, "log-" + dateOfRun + ".log"));
		File latestLog = (new File(logDir, "log-latest.log"));
		

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
		
	}
	
	public static void setupDefaultConfig(File defaultConfig) {
		
		File config = ConfigHandler.config;
		
		if (config.exists() == false) {
			
			try {
				Files.copy(defaultConfig.toPath(), new FileOutputStream(config));
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			LogHandler.log.fatal("The config was not found!");
			Runtime.getRuntime().exit(0);
			
		}
		
	}
	
}
