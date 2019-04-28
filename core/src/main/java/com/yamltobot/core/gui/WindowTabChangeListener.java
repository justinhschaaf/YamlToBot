package com.yamltobot.core.gui;

import com.yamltobot.core.common.Reference;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;

public class WindowTabChangeListener implements ChangeListener {

    @Override
    public void stateChanged(ChangeEvent event) {

        JTabbedPane tabs = (JTabbedPane) event.getSource();

        // Wiki Tab
        if (tabs.getSelectedIndex() == 1) {

            tabs.setSelectedIndex(0);

            try {
                Desktop.getDesktop().browse(URI.create(Reference.wikiURL));
                tabs.setSelectedIndex(0);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

        // Donate Tab
        if (tabs.getSelectedIndex() == 3) {

            tabs.setSelectedIndex(0);

            try {
                Desktop.getDesktop().browse(URI.create(Reference.donateURL));
                tabs.setSelectedIndex(0);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

    }

}
