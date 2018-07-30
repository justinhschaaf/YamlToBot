package net.jusanov.yamltobot.core.handler;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class LogHandler {
	
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
	
	static LogLevel minLevel = LogLevel.ALL;
	static DateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd-hh.mm.ss");
	static File output = new File("YamlToBot/logs/log-latest.log");
	
	public static void setOutputLog(File outputLog) {
		output = outputLog;
	}
	
	public static void setLowestLevel(LogLevel level) {
		minLevel = level;
	}
	
	public static void log(LogLevel level, Object text) {
		if (level.getLevel() >= minLevel.getLevel()) {
			try {
				
				String timestamp = dateFormat.format(new Date()).toString();
				BufferedWriter logger = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(output)));
				
				logger.write(level.getLevel() + " " + timestamp + " " + level.getName() + " " + text + "\n");
				
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
	
	public static void all(Object text) {
		log(LogLevel.ALL, text);
	}
	
	public static void debug(Object text) {
		log(LogLevel.DEBUG, text);
	}
	
	public static void info(Object text) {
		log(LogLevel.INFO, text);
	}
	
	public static void warn(Object text) {
		log(LogLevel.WARN, text);
	}
	
	public static void error(Object text) {
		log(LogLevel.ERROR, text);
	}
	
	public static void fatal(Object text) {
		log(LogLevel.FATAL, text);
		Runtime.getRuntime().exit(0);
	}

	//public static final org.slf4j.Logger log = LoggerFactory.getLogger(LogHandler.class);
	
}
