package com.justinschaaf.yamltobot.core.handler;

import com.amihaiemil.eoyaml.Yaml;
import com.justinschaaf.yamltobot.core.commands.Command;
import com.justinschaaf.yamltobot.core.commands.builtin.BuiltinCommand;
import com.justinschaaf.yamltobot.core.common.Module;
import com.justinschaaf.yamltobot.core.setup.SetupDefaultConfig;
import com.justinschaaf.yamltobot.core.setup.Window;
import net.jusanov.utils.io.FileManager;

import javax.swing.*;
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
		setupWindow();
		
	}

    /**
     *
     * Configure the logger for use in YamlToBot
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

    /**
     * Load the config for YamlToBot and setup the default config based on module, if necessary
     * @since 2.0.0
     */
    private static void setupConfig() {

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

        ArrayList<String> commandKeys = ConfigHandler.getCommands();
        ArrayList<Command> commands = new ArrayList<Command>();

        for (String commandName : commandKeys) {

            commands.add(new Command(
                    commandName,
                    ConfigHandler.getCommandString(commandName, "description", "Generic Command"),
                    ConfigHandler.getCommandArray(commandName,"message"),
                    ConfigHandler.getCommandBoolean(commandName, "enabled", true),
                    loadBuiltinCommand(ConfigHandler.getCommandString(commandName, "predefined-function", null))));

        }

        return commands;

    }

    /**
     *
     * Gets a predefined command from the given class, name, and args.
     *
     * @param cmdClass The class of the command, including the internal or external tag, e.g. %int%HelpCommand
     * @return The string message that the command returns
     * @since 1.0.0
     *
     */
    public static BuiltinCommand loadBuiltinCommand(String cmdClass) {

        if (cmdClass == null) return null;

        try {

            Class<? extends BuiltinCommand> builtinCmdClass = null;
            if (cmdClass.contains("%int%")) {
                builtinCmdClass = Class.forName("com.justinschaaf.yamltobot.core.commands.builtin." + cmdClass.replace("%int%", "")).asSubclass(BuiltinCommand.class);
            } else if (cmdClass.contains("%ext%")) {
                ClassLoader classLoader = loadBuiltinCmds("YamlToBot/cmds/");
                builtinCmdClass = classLoader.loadClass(cmdClass.replace("%ext%", "")).asSubclass(BuiltinCommand.class);
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
    private static ClassLoader loadBuiltinCmds(String dir) {

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
	
	/**
	 * Setup the window
	 * @since 2.0.0
	 */
	private static void setupWindow() {

		JFrame window = new JFrame();
		window.setTitle("YamlToBot | " + ConfigHandler.getString("name", module.getName()));
		//window.setIconImage(Toolkit.getDefaultToolkit().getImage(BotHandler.class.getResource("/assets/icon/icon512.png")));
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setContentPane(new Window(module).main);
		window.pack();
		window.setVisible(true);

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
