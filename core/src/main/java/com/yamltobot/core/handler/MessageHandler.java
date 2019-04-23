package com.yamltobot.core.handler;

import com.yamltobot.core.commands.Command;
import com.yamltobot.core.common.Module;

import java.util.ArrayList;

/**
 * 
 * The primary class for handling and formatting messages
 * 
 * @author Justin Schaaf
 * @since 2.0.0
 *
 */
public abstract class MessageHandler {

    static ArrayList<Command> commands;

    /**
     *
     * The primary class for handling messages in YamlToBot
     *
     * @param commands An ArrayList of all loaded commands
     * @since 3.0.0
     *
     */
    public MessageHandler(ArrayList<Command> commands) {
        this.commands = commands;
    }

	/**
	 * 
	 * Properly handle a message to implement all the configuration options of YamlToBot
	 * 
	 * @param module The module that this is handling a message for.
	 * @param channel The channel where this message was found.
	 * @param author The author of the message.
	 * @param message The message itself.
	 * @return What the command returns, or null if the command is not found.
	 * @since 2.0.0
	 * 
	 */
	public String handleMessage(Module module, String channel, String author, String message) {
		
		logMessage(channel, author, message);
		
		Command command = getCommandByMessage(message);
		
		if (command != null) {
			
			if (command.getEnabled()) {

				return VariableHandler.formatMessage(command.execute(getArgsByMessage(command, message)));
				
			}
			
		}
		
		return null;
		
	}

    /**
     *
     * Get a command's arguments from the input command and message
     *
     * @param command The command that was executed.
     * @param message The message that the user sent when executing the command.
     * @return an ArrayList of the arguments
     * @since 3.0.0
     *
     */
	public ArrayList<String> getArgsByMessage(Command command, String message) {

	    // Get rid of the command itself
	    message.replaceFirst(ConfigHandler.getString("prefix", "") + command.getName(), "");

	    // Setup vars
        ArrayList<String> args = new ArrayList<String>();
        String arg = "";
        Boolean isInQuotes = false;
        Boolean isInQuote = false;
        Boolean isInCode = false;

        for (int i = 0; i < message.length(); i++) {

            String chr = message.charAt(i) + "";

            // Check if the arg is in quotes
            if (chr.equalsIgnoreCase("\"")) {

                if (i != 0) {

                    if (!(message.charAt(i - 1) + "").equalsIgnoreCase("\\")) {

                        if (isInCode || isInQuote) {
                            arg += chr;
                            continue;
                        }

                        isInQuotes = !isInQuotes;

                    }

                } else isInQuotes = true;

            } else if (chr.equalsIgnoreCase("'")) {

                if (i != 0) {

                    if (!(message.charAt(i - 1) + "").equalsIgnoreCase("\\")) {

                        if (isInQuotes || isInCode) {
                            arg += chr;
                            continue;
                        }

                        isInQuote = !isInQuote;

                    }

                } else isInQuote = true;

            } else if (chr.equalsIgnoreCase("`")) {

                if (i != 0) {

                    if (!(message.charAt(i - 1) + "").equalsIgnoreCase("\\")) {

                        if (isInQuotes || isInQuote) {
                            arg += chr;
                            continue;
                        }

                        isInCode = !isInCode;

                    }

                } else {
                    isInCode = true;
                }

                if (!isInQuotes && !isInQuote) {
                    if (i + 3 >= message.length()) i += 2;
                    else while ((message.charAt(i + 1) + "").equalsIgnoreCase("`")) i += 1;
                }

            } else if (chr.equalsIgnoreCase(" ")) {

                if (!isInQuotes && !isInQuote && !isInCode) {

                    args.add(arg);
                    arg = "";

                } else arg += chr;

            } else {

                arg += chr;

            }

        }

        if (!arg.isEmpty()) args.add(arg);
        args.remove(0);

        return args;

    }

    /**
     *
     * Basic tasks to be executed when a message is executed
     *
     * @param channel The channel where this message was found.
     * @param author The author of the message.
     * @param message The message itself.
     * @since 2.0.0
     *
     */
    public void logMessage(String channel, String author, String message) {

        LogHandler.info("[" + channel + "]" + " " + author + ": " + message);

        Command command = getCommandByMessage(message);

        if (command != null) {

            LogHandler.debug("Command " + command.getName() + " detected!");

        }

    }

    /**
     *
     * Gets an executed command from the message it was run from
     *
     * @param message The message of the potential command
     * @return the Command that was executed
     * @since 2.0.0
     *
     */
    public Command getCommandByMessage(String message) {

        for (Command command : commands) {

            if (message.startsWith(ConfigHandler.getString("prefix", "") + command.getName())) return command;

        }

        return null;

    }

    /**
     *
     * Sends a message to the location of the last event. Intended for use by {@link com.yamltobot.scripts.Script}s
     *
     * @param message The message to send
     * @since 4.0.0
     *
     */
    public abstract void sendMessage(String message);
	
}
