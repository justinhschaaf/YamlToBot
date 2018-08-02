package net.jusanov.yamltobot.core.handler;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 
 * The primary class for handling the logs
 * 
 * @author Jusanov
 * @since 1.0.0
 *
 */
public class LogHandler {
	
	/**
	 * 
	 * Enum containing all the different default log levels
	 * 
	 * @author Jusanov
	 * @since 1.0.0
	 *
	 */
	public enum LogLevel {
		
		ALL("ALL", 0),
		DEBUG("DEBUG", 1),
		INFO("INFO", 2),
		WARN("WARN", 3),
		ERROR("ERROR", 4),
		FATAL("FATAL", 5);

		private final String name;
		private final int level;
		
		private LogLevel(final String name, final int level) {
			this.name = name;
			this.level = level;
		}

		public String getName() {
			return name;
		}

		public int getLevel() {
			return level;
		}
		
	}
	
	/**
	 * The minimum log level to track output from
	 * @since 1.0.0
	 */
	static LogLevel minLevel = LogLevel.ALL;
	
	/**
	 * The formatting for the timestamp in the log
	 * @since 1.0.0
	 */
	static DateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd-hh.mm.ss");
	
	/**
	 * The default log output file
	 * @since 1.0.0
	 */
	static File output = new File("YamlToBot/logs/log-latest.log");
	
	/**
	 * Set the output log file
	 * @param outputLog The file to output the log to
	 * @since 1.0.0
	 */
	public static void setOutputLog(File outputLog) {
		output = outputLog;
	}
	
	/**
	 * Set the lowest log level
	 * @param level The lowest LogLevel to track
	 * @since 1.0.0
	 */
	public static void setLowestLevel(LogLevel level) {
		minLevel = level;
	}
	
	/**
	 * Log a string of text at the given level
	 * 
	 * @param level The level to log at
	 * @param text The string of text to log
	 * @since 1.0.0
	 * 
	 */
	public static void log(LogLevel level, Object text) {
		if (level.getLevel() >= minLevel.getLevel()) {
			try {

				StringBuilder old = new StringBuilder();
				
				if (output.exists()) {
				
					BufferedReader oldLog = new BufferedReader(new InputStreamReader(new FileInputStream(output)));
					String line = new String();
					
					while ((line = oldLog.readLine()) != null) {
						old.append(line + "\n");
					}
					
					oldLog.close();
				
				}
				
				String timestamp = dateFormat.format(new Date()).toString();
				BufferedWriter logger = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(output)));
				
				if (output.exists()) logger.write(old.toString());
				logger.write(timestamp + " " + level.getName() + " " + text + "\n");
				
				logger.close();
				
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			return;
		}
	}
	
	/**
	 * Logs a string of text at the "ALL" level
	 * @param text The string of text to log
	 * @since 1.0.0
	 */
	public static void all(Object text) {
		log(LogLevel.ALL, text);
	}
	
	/**
	 * Logs a string of text at the "DEBUG" level
	 * @param text The string of text to log
	 * @since 1.0.0
	 */
	public static void debug(Object text) {
		log(LogLevel.DEBUG, text);
	}
	
	/**
	 * Logs a string of text at the "INFO" level
	 * @param text The string of text to log
	 * @since 1.0.0
	 */
	public static void info(Object text) {
		log(LogLevel.INFO, text);
	}
	
	/**
	 * Logs a string of text at the "WARN" level
	 * @param text The string of text to log
	 * @since 1.0.0
	 */
	public static void warn(Object text) {
		log(LogLevel.WARN, text);
	}
	
	/**
	 * Logs a string of text at the "ERROR" level
	 * @param text The string of text to log
	 * @since 1.0.0
	 */
	public static void error(Object text) {
		log(LogLevel.ERROR, text);
	}
	
	/**
	 * Logs a string of text at the "FATAL" level and exits runtime because what happened is fatal to the application
	 * @param text The string of text to log
	 * @since 1.0.0
	 */
	public static void fatal(Object text) {
		log(LogLevel.FATAL, text);
		Runtime.getRuntime().exit(0);
	}
	
}
