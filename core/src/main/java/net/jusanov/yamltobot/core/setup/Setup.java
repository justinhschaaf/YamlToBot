package net.jusanov.yamltobot.core.setup;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import net.jusanov.utils.io.FileManager;
import net.jusanov.yamltobot.core.handler.LogHandler;

public class Setup {

	public static void setupLogs() {
		
		DateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd-hh.mm.ss");
		String dateOfRun = dateFormat.format(new Date()).toString();
		
		File logDir = new File("YamlToBot/logs");
		logDir.mkdirs();
		File logFile = (new File(logDir, "log-" + dateOfRun + ".log"));
		File latestLog = (new File(logDir, "log-latest.log"));

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
	
	public static void setupDefaultConfig(File config) {
		
		if (config.exists() == false) {
			
			SetupDefaultConfig.setupDefaultConfig(config);
			LogHandler.warn("The config was not found!");
			Runtime.getRuntime().exit(0);
			
		}
		
	}
	
}
