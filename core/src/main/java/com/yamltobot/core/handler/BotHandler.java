package com.yamltobot.core.handler;

import com.amihaiemil.eoyaml.Yaml;
import com.yamltobot.core.commands.Command;
import com.yamltobot.builtincmds.BuiltinCommand;
import com.yamltobot.core.common.Module;
import com.yamltobot.core.common.Reference;
import com.yamltobot.core.common.VersionChecker;
import com.yamltobot.core.setup.SetupDefaultConfig;
import com.yamltobot.core.setup.Window;
import net.jusanov.utils.io.FileManager;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

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
     * The commands registered by the bot based on the config
     * @since 3.0.0
     */
	//static ArrayList<Command> commands; // Needs to be overwritten by Discord, but this is not possible via static methods and due to incompatible types (Command vs DiscordCommand)
	
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
		
		setupLogs();
		setupConfig();
		//commands = loadCommands(); // Needs to be overwritten by Discord, but this is not possible via static methods and due to incompatible types (Command vs DiscordCommand)
        VariableHandler.loadVariables();
		setupWindow();
		
	}

    /**
     *
     * Configure the logger for use in YamlToBot
     * @since 1.0.0
     *
     */
    private static void setupLogs() {

        long starttime = System.currentTimeMillis();

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

        LogHandler.debug("Setup logs in " + (System.currentTimeMillis() - starttime) + " milliseconds!");

    }

    /**
     * Load the config for YamlToBot and setup the default config based on module, if necessary
     * @since 2.0.0
     */
    private static void setupConfig() {

        long starttime = System.currentTimeMillis();

        File generalConfig = new File(Module.GENERAL.getDir() + "config.yml");
        File config = new File(module.getDir() + "config.yml");

        if ((config.exists() == false && generalConfig.exists() == false) || (config.exists() == false && generalConfig.exists() == true)) {

            if (module == Module.DISCORD) SetupDefaultConfig.setupDiscord(config);
            if (module == Module.TWITCH) SetupDefaultConfig.setupTwitch(config);
            else LogHandler.fatal("Invalid Module to generate default config for!");

            LogHandler.fatal("The config was not found!");

        }

        if (generalConfig.exists() == true) ConfigHandler.setConfig(generalConfig);
        else ConfigHandler.setConfig(config);

        LogHandler.debug("Setup config in " + (System.currentTimeMillis() - starttime) + " milliseconds!");

    }

    /**
     *
     * Load the commands defined in the config
     *
     * @return an ArrayList of all the commands found
     * @since 3.0.0
     *
     */
    public static ArrayList<Command> loadCommands() {

        long starttime = System.currentTimeMillis();

        ArrayList<String> commandKeys = ConfigHandler.getCommands();
        ArrayList<Command> commands = new ArrayList<Command>();
        ClassLoader cl = loadBuiltinCmds("YamlToBot/cmds/");

        for (String commandName : commandKeys) {

            commands.add(new Command(
                    commandName,
                    ConfigHandler.getCommandString(commandName, "description", "Generic Command"),
                    ConfigHandler.getCommandArray(commandName,"message"),
                    ConfigHandler.getCommandBoolean(commandName, "enabled", true),
                    loadBuiltinCommand(cl, ConfigHandler.getCommandString(commandName, "predefined-function", null))));

        }

        LogHandler.debug("Commands loaded in " + (System.currentTimeMillis() - starttime) + " milliseconds!");

        return commands;

    }

    /**
     *
     * Gets a predefined command from the given class, name, and args.
     *
     * @param cl The class loader to load command classes from (should be acquired through loadBuiltinCmds(dir))
     * @param cmdClass The class of the command, including the internal or external tag, e.g. %int%HelpCommand
     * @return The string message that the command returns
     * @since 1.0.0
     *
     */
    public static BuiltinCommand loadBuiltinCommand(ClassLoader cl, String cmdClass) {

        if (cmdClass == null) return null;

        try {

            Class<? extends BuiltinCommand> builtinCmdClass = null;

            // If the class name contains a ".", indicating an external package
            if (cmdClass.contains(".")) {
                builtinCmdClass = Class.forName(cmdClass, true, cl).asSubclass(BuiltinCommand.class);
            } else {
                builtinCmdClass = Class.forName("com.yamltobot.builtincmds." + cmdClass, true, cl).asSubclass(BuiltinCommand.class);
            }

            return (BuiltinCommand) builtinCmdClass.newInstance();

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
    public static ClassLoader loadBuiltinCmds(String dir) {

        File[] cmdRaw = new File(dir).listFiles();
        URL[] cmdUrls = new URL[cmdRaw.length];

        for (int i = 0; i < cmdRaw.length; i++) {
            try {
                cmdUrls[i] = cmdRaw[i].toURI().toURL();
                System.out.println(cmdRaw[i].getName());
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
        }

        return new URLClassLoader(cmdUrls, ClassLoader.getSystemClassLoader());

    }
	
	/**
	 * Setup the window
	 * @since 2.0.0
	 */
	private static void setupWindow() {

        long starttime = System.currentTimeMillis();

		Window frame = new Window(module);
		frame.setVisible(true);

        LogHandler.debug("Setup window in " + (System.currentTimeMillis() - starttime) + " milliseconds!");

	}

	public static void logClientInfo() {

	    String jVersion = System.getProperty("java.version");
        String dateOfRun = new SimpleDateFormat("yyyy.MM.dd-hh.mm.ss").format(new Date()).toString();
        String newUpdate = "";

        if (!VersionChecker.isUpdated()) newUpdate = "A NEW UPDATE IS AVAILABLE :: " + VersionChecker.getLatestVersion() + "\n";
        else if (Reference.prerelease) newUpdate = "YOU ARE USING A PRERELEASE VERSION. BUGS BEWARE! \n";

        LogHandler.debug("\n" + "----- YamlToBot Info -----" + "\n"
                        + "YamlToBot Version :: " + Reference.version + "\n"
                        + newUpdate
                        + "Java Version :: " + jVersion + "\n"
                        + "Date of run :: " + dateOfRun + "\n"
                        + "--------------------------");

    }

    /**
     *
     * Gets an authentication config value
     *
     * @param key The authentication item to find
     * @return The authentication item
     * @since 2.0.0
     *
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
	
}
