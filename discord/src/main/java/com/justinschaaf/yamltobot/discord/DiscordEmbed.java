package com.justinschaaf.yamltobot.discord;

import org.javacord.api.entity.message.embed.EmbedBuilder;

import java.awt.*;
import java.util.ArrayList;

/**
 *
 * The YamlToBot type for a Discord embed
 *
 * @author Justin Schaaf
 * @since 3.0.0
 *
 */
public class DiscordEmbed {

    private String title;
    private ArrayList<String> desc;
    private String color;
    private String image;
    private String url;
    private ArrayList<DiscordEmbedField> fields;
    private DiscordEmbedAuthor author;
    private Boolean enabled;

    /**
     *
     * The YamlToBot type for a Discord embed
     *
     * @param title The embed's title
     * @param desc The message that appears under the title
     * @param color The color of the bar that appears on the left of the embed. Currently hexadecimal only
     * @param image The URL of the image that should be displayed with the embed
     * @param url The URL that the embed should direct to when clicked
     * @param fields An ArrayList of the embed's fields
     * @param author The embed's author
     * @param enabled Whether or not the embed is enabled
     * @since 3.0.0
     *
     */
    public DiscordEmbed(String title, ArrayList<String> desc, String color, String image, String url, ArrayList<DiscordEmbedField> fields, DiscordEmbedAuthor author, Boolean enabled) {
        this.title = title;
        this.desc = desc;
        this.color = color;
        this.image = image;
        this.url = url;
        this.fields = fields;
        this.author = author;
        this.enabled = enabled;
    }

    /**
     *
     * Generate an embed from the information the embed was defined with.
     *
     * @since 3.0.0
     * @return The built Embed
     *
     */
    public EmbedBuilder generate() {

        if (!enabled) return null;

        EmbedBuilder embed = new EmbedBuilder();

        if (title != null) embed.setTitle(title);
        if (color != null) embed.setColor(new Color(Integer.parseInt(color.replaceAll("#", ""),16)));
        if (image != null) embed.setImage(image);
        if (url != null) embed.setUrl(url);

        if (desc != null) {
            StringBuilder messageBuilder = new StringBuilder();
            for (int i = 0; i < desc.size(); i++) messageBuilder.append(desc.get(i) + "\n");
            embed.setDescription(messageBuilder.toString());
        }

        if (fields != null) {

            for (DiscordEmbedField field : fields) {

                StringBuilder descBuilder = new StringBuilder();
                for (int i = 0; i < field.getDesc().size(); i++) descBuilder.append(field.getDesc().get(i) + "\n");

                embed.addField(field.getName(), descBuilder.toString(), false);

            }

        }

        if (author != null) {
            if (author.getAvatar() != null && author.getUrl() != null) embed.setAuthor(author.getName(), author.getUrl(), author.getAvatar());
            else embed.setAuthor(author.getName());
        }

        return embed;

    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public ArrayList<String> getDesc() {
        return desc;
    }

    public void setDesc(ArrayList<String> desc) {
        this.desc = desc;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public ArrayList<DiscordEmbedField> getFields() {
        return fields;
    }

    public void setFields(ArrayList<DiscordEmbedField> fields) {
        this.fields = fields;
    }

    public DiscordEmbedAuthor getAuthor() {
        return author;
    }

    public void setAuthor(DiscordEmbedAuthor author) {
        this.author = author;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

}
