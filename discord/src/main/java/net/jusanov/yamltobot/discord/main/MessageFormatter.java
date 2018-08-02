package net.jusanov.yamltobot.discord.main;

import java.util.ArrayList;
import java.util.Iterator;
import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.entity.emoji.KnownCustomEmoji;
import org.javacord.api.entity.permission.Role;

/**
 * 
 * The primary class for formatting Discord Messages
 * 
 * @author Jusanov
 * @since 1.0.0
 *
 */
public class MessageFormatter {
	
	/**
	 * 
	 * The function for formatting a discord message's custom parameters
	 * :<text>: represents a custom emoji
	 * @<text> represents a user mention tag
	 * #<text> represents a channel's mention tag
	 * ^<text> represents a role's mention tag
	 * 
	 * @param message The message to format
	 * @since 1.0.0
	 *
	 */
	public static String formatMessage(String message) {
		
		// Custom Emojis
		if (message.contains(":")) {
			
			ArrayList<Integer> indexes = getIndexes(message, ':', ':');
			String emoteTag = message.substring(indexes.get(0), indexes.get(1)).replaceAll(":", "");
			Iterator<KnownCustomEmoji> possibleEmojis = BotHandler.api.getCustomEmojisByNameIgnoreCase(emoteTag).iterator();
			String emoji = new String();
			
			while (possibleEmojis.hasNext()) {
				emoji = possibleEmojis.next().asCustomEmoji().get().getMentionTag();
			}
			
			message.replaceAll(":" + emoteTag + ":", emoji);
			
		}
		
		// Mention User
		if (message.contains("@")) {
			
			ArrayList<Integer> indexes = getIndexes(message, '@', ' ');
			String mentionTag = message.substring(indexes.get(0), indexes.get(1)).trim().replaceAll("@", "");
			String mentionedUser = BotHandler.api.getCachedUserByDiscriminatedNameIgnoreCase(mentionTag).get().getMentionTag();
			message.replaceAll("@" + mentionTag, mentionedUser);
			
		}
		
		// Mention Channel
		if (message.contains("#")) {
			
			ArrayList<Integer> indexes = getIndexes(message, '#', ' ');
			String mentionTag = message.substring(indexes.get(0), indexes.get(1)).trim().replaceAll("#", "");
			Iterator<TextChannel> mentionedChannel = BotHandler.api.getTextChannelsByName(mentionTag).iterator();
			String channel = new String();
			
			while (mentionedChannel.hasNext()) {
				channel = mentionedChannel.next().asServerTextChannel().get().getMentionTag();
			}
			
			message.replaceAll("#" + mentionTag, channel);
			
		}
		
		// Mention Role -- use ^ because @ signifies user
		if (message.contains("^")) {
			
			ArrayList<Integer> indexes = getIndexes(message, '^', ' ');
			String mentionTag = message.substring(indexes.get(0), indexes.get(1)).trim().replaceAll("^", "");
			Iterator<Role> mentionedRole = BotHandler.api.getRolesByNameIgnoreCase(mentionTag).iterator();
			String role = new String();
			
			while (mentionedRole.hasNext()) {
				role = mentionedRole.next().getMentionTag();
			}
			
			message.replaceAll("^" + mentionTag, role);
			
		}
		
		return message;
		
	}
	
	private static ArrayList<Integer> getIndexes(String string, char start, char end) {
		
		int beginIndex = -1;
		int endIndex = -1;
		
		for (int i = 0; i < string.length(); i++) {
			
			if (string.charAt(i) == start) {
				if (beginIndex == -1) {
					beginIndex = i;
					continue;
				}
			}
			if (string.charAt(i) == end) {
				if (endIndex == -1) {
					endIndex = i;
					continue;
				}
			}
			
		}
		
		ArrayList<Integer> toReturn = new ArrayList<Integer>();
		toReturn.add(beginIndex);
		toReturn.add(endIndex);
		
		return toReturn;
		
	}
	
}