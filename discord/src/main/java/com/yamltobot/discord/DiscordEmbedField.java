package com.yamltobot.discord;

import java.util.ArrayList;

/**
 *
 * A field for a Discord embed
 *
 * @author Justin Schaaf
 * @since 3.0.0
 *
 */
public class DiscordEmbedField {

    private String name;
    private ArrayList<String> desc;
    private Boolean inline;

    public DiscordEmbedField(String name, ArrayList<String> desc) {
        this(name, desc, false);
    }

    /**
     *
     * A field for a Discord embed
     *
     * @param name The name of the field
     * @param desc The body text of the field
     * @param inline Whether or not the field should be inline
     * @since 3.0.0
     *
     */
    public DiscordEmbedField(String name, ArrayList<String> desc, Boolean inline) {
        this.name = name;
        this.desc = desc;
        this.inline = inline;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<String> getDesc() {
        return desc;
    }

    public void setDesc(ArrayList<String> desc) {
        this.desc = desc;
    }

    public Boolean getInline() {
        return inline;
    }

    public void setInline(Boolean inline) {
        this.inline = inline;
    }

}
