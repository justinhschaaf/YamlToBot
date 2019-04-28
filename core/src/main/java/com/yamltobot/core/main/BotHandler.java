package com.yamltobot.core.main;

import com.justinschaaf.yaosja.FileManager;
import com.yamltobot.core.commands.Command;
import com.yamltobot.core.common.Module;
import com.yamltobot.core.common.Reference;
import com.yamltobot.core.common.VersionChecker;
import com.yamltobot.core.config.ConfigHandler;
import com.yamltobot.core.config.ConfigObject;
import com.yamltobot.core.config.SetupDefaultConfig;
import com.yamltobot.core.config.VariableHandler;
import com.yamltobot.core.gui.Window;
import com.yamltobot.scripts.Script;

import java.io.File;
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
	 * The {@link Module} of this bot
	 * @since 2.0.0
	 */
	private static Module module;

    /**
     * The {@link Command} list registered by the bot based on the config
     * @since 4.0.0
     */
	private static ArrayList<Command> commands;

    /**
     * The Script loader used by the bot. This is set when the bot loads commands.
     * @since 4.0.0
     */
    private static ClassLoader scriptLoader;

    /**
     * The {@link BotHandler} of the bot
     * @since 4.0.0
     */
    private static BotHandler botHandler;

    /**
     * The {@link MessageHandler} of the bot
     * @since 4.0.0
     */
    private static MessageHandler messageHandler;

    /**
     * The {@link ConfigHandler} of the bot
     * @since 4.0.0
     */
    private static ConfigHandler configHandler;

    /**
     * The {@link VariableHandler} of the bot
     * @since 4.0.0
     */
    private static VariableHandler variableHandler;

    /**
	 * Setup the bot
	 * @since 2.0.0
	 */
	public static void setup(Module thisModule, BotHandler botHandler, MessageHandler messageHandler) {

	    BotHandler.botHandler = botHandler;
	    BotHandler.messageHandler = messageHandler;

	    setModule(thisModule);
		
		setupLogs();
		setupConfig();
		setCommands(loadCommands()); // Discord uses their own command variable
        setupVariables();
		setupWindow();
        logClientInfo();
		
	}

	/*
	 *
	 * █    █▀▀█ █▀▀▀ █▀▀
     * █    █  █ █ ▀█ ▀▀█
     * █▄▄█ ▀▀▀▀ ▀▀▀▀ ▀▀▀
	 *
	 */

    /**
     *
     * Configure the {@link LogHandler} for use in YamlToBot
     * @since 1.0.0
     *
     */
    private static void setupLogs() {

        long starttime = System.currentTimeMillis();

        DateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd-hh.mm.ss");
        String dateOfRun = dateFormat.format(new Date()).toString();

        File logDir = new File(getModule().getDir() + "logs");
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
     * Logs info about the current YamlToBot client
     * @since 3.0.0
     */
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

    /*
     *
     * █▀▀█ █  █ ▀█▀
     * █ ▄▄ █  █  █
     * █▄▄█ ▀▄▄▀ ▄█▄
     *
     */

    /**
     * Setup the window
     * @since 2.0.0
     */
    private static void setupWindow() {

        long starttime = System.currentTimeMillis();

        Window frame = new Window(getModule());
        frame.setVisible(true);

        LogHandler.debug("Setup window in " + (System.currentTimeMillis() - starttime) + " milliseconds!");

    }

    /*
     *
     * █▀▀█ █▀▀█ █▀▀▄ █▀▀  ▀  █▀▀▀ 　 ▄▀▀▄ 　 █▀▀█ █▀▄▀█ █▀▀▄ █▀▀
     * █    █  █ █  █ █▀▀ ▀█▀ █ ▀█ 　 ▀▄ ▄ 　 █    █ █ █ █  █ ▀▀█
     * █▄▄█ ▀▀▀▀ ▀  ▀ ▀   ▀▀▀ ▀▀▀▀ 　 █▄▀▄ 　 █▄▄█ █   █ █▄▄▀ ▀▀▀
     *
     */

    /**
     * Load the config for YamlToBot and gui the default config based on module, if necessary
     * @since 2.0.0
     */
    private static void setupConfig() {

        long starttime = System.currentTimeMillis();

        File generalConfig = new File(Module.GENERAL.getDir() + "config.yml");
        File config = new File(getModule().getDir() + "config.yml");

        if ((config.exists() == false && generalConfig.exists() == false) || (config.exists() == false && generalConfig.exists() == true)) {

            if (getModule() == Module.DISCORD) SetupDefaultConfig.setupDiscord(config);
            if (getModule() == Module.TWITCH) SetupDefaultConfig.setupTwitch(config);
            if (getModule() == Module.MIXER) SetupDefaultConfig.setupMixer(config);
            else LogHandler.fatal("Invalid Module to generate default config for!");

            LogHandler.fatal("The config was not found!");

        }

        if (generalConfig.exists() == true) configHandler = new ConfigHandler(generalConfig);
        else configHandler = new ConfigHandler(config);

        LogHandler.debug("Setup config in " + (System.currentTimeMillis() - starttime) + " milliseconds!");

    }

    /**
     *
     * Load the commands defined in the config
     *
     * @return an ArrayList of all the commands found, as a {@link Command}
     * @since 3.0.0
     *
     */
    public static ArrayList<Command> loadCommands() {

        long starttime = System.currentTimeMillis();

        ArrayList<String> commandKeys = getConfigHandler().getCommands();
        ArrayList<Command> commands = new ArrayList<Command>();
        setScriptLoader(loadScriptLoader(Module.SCRIPT.getDir()));

        for (String commandName : commandKeys) {

            ConfigObject command = getConfigHandler().getCommand(commandName);

            commands.add(new Command(
                    commandName,
                    command.getString("description", "Generic Command"),
                    command.getArray("message"),
                    command.getBoolean("enabled", true),
                    loadScript(getScriptLoader(), command.getString("script", null)),
                    command));

        }

        LogHandler.debug("Commands loaded in " + (System.currentTimeMillis() - starttime) + " milliseconds!");

        return commands;

    }

    /**
     *
     * Gets a {@link Script} from the given class, name, and args.
     *
     * @param cl The {@link ClassLoader} to load command classes from (should be acquired through {@link #loadScriptLoader(String)}); will default to the BotHandler's {@link #scriptLoader}
     * @param cmdClass The class of the script
     * @return The script that was loaded, or null if an error occurred or no script was defined.
     * @since 1.0.0
     *
     */
    public static Script loadScript(ClassLoader cl, String cmdClass) {

        if (cmdClass == null) return null;

        if (cl == null) cl = getScriptLoader();

        try {

            Class<? extends Script> scriptClass = null;

            // If the class name contains a ".", indicating an external package
            if (cmdClass.contains(".")) {
                scriptClass = Class.forName(cmdClass, true, cl).asSubclass(Script.class);
            } else {
                scriptClass = Class.forName("com.yamltobot.scripts." + cmdClass, true, cl).asSubclass(Script.class);
            }

            Script script = (Script) scriptClass.getDeclaredConstructor().newInstance();
            script.setHandlers(getBotHandler(), getMessageHandler());
            return script;

        } catch (Exception e) { // There are so many exceptions that this is just easier
            e.printStackTrace();
        }

        return null;

    }

    /**
     *
     * Creates the {@link #scriptLoader} to load scripts from the given directory.
     *
     * @param dir Where to load the classes from
     * @return The {@link ClassLoader} used to load scripts
     * @since 1.0.0
     *
     */
    public static ClassLoader loadScriptLoader(String dir) {

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

    public static void setupVariables() {

        setVariableHandler(new VariableHandler(getConfigHandler().getConfig().getConfigArray("variables")));

    }

	/*
	 *
	 * █▀▀█ █▀▀ ▀▀█▀▀ 　 ▄▀▀▄ 　 █▀▀▀█ █▀▀ ▀▀█▀▀
     * █ ▄▄ █▀▀   █   　 ▀▄ ▄ 　 ▀▀▀▄▄ █▀▀   █
     * █▄▄█ ▀▀▀   ▀   　 █▄▀▄ 　 █▄▄▄█ ▀▀▀   ▀
	 *
	 */

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

        return new ConfigHandler(new File(getModule().getDir() + "config.yml")).getConfig().getString(key);


    }

    /**
     * @return This bot's {@link Module}
     * @since 4.0.0
     */
    public static Module getModule() {
        return module;
    }

    /**
     * @param module The bot's {@link Module}
     * @since 4.0.0
     */
    public static void setModule(Module module) {
        BotHandler.module = module;
    }

    /**
     * @note The Discord module uses its own command list that has the "embed" property set. It can be accessed through DiscordBotHandler
     * @return A list of the bot's registered commands
     * @since 4.0.0
     */
    public static ArrayList<Command> getCommands() {
        return commands;
    }

    /**
     * @note The Discord module uses its own command list that has the "embed" property set. It can be accessed through DiscordBotHandler
     * @param commands The list of registered commands
     * @since 4.0.0
     */
    public static void setCommands(ArrayList<Command> commands) {
        BotHandler.commands = commands;
    }

    /**
     * @return The {@link ClassLoader} for scripts. Loads from the "YamlToBot/scripts/" folder
     * @since 4.0.0
     */
    public static ClassLoader getScriptLoader() {
        return scriptLoader;
    }

    /**
     * @param scriptLoader The {@link ClassLoader} the bot should use for scripts
     * @since 4.0.0
     */
    public static void setScriptLoader(ClassLoader scriptLoader) {
        BotHandler.scriptLoader = scriptLoader;
    }

    /**
     * @return This bot's {@link BotHandler}
     * @since 4.0.0
     */
    public static BotHandler getBotHandler() {
        return botHandler;
    }

    /**
     * @return This bot's {@link MessageHandler}
     * @since 4.0.0
     */
    public static MessageHandler getMessageHandler() {
        return messageHandler;
    }

    /**
     * @return This bot's {@link ConfigHandler}
     * @since 4.0.0
     */
    public static ConfigHandler getConfigHandler() {
        return configHandler;
    }

    /**
     * @param configHandler The bot's new {@link ConfigHandler}
     * @since 4.0.0
     */
    public static void setConfigHandler(ConfigHandler configHandler) {
        BotHandler.configHandler = configHandler;
    }

    /**
     * @return This bot's {@link VariableHandler}
     * @since 4.0.0
     */
    public static VariableHandler getVariableHandler() {
        return variableHandler;
    }

    /**
     * @param variableHandler The bot's new {@link VariableHandler}
     * @since 4.0.0
     */
    public static void setVariableHandler(VariableHandler variableHandler) {
        BotHandler.variableHandler = variableHandler;
    }

}
