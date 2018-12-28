package com.justinschaaf.yamltobot.core.setup;

import com.justinschaaf.yamltobot.core.common.Module;
import com.justinschaaf.yamltobot.core.common.Reference;
import com.justinschaaf.yamltobot.core.common.VersionChecker;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Timer;
import java.util.TimerTask;

/**
 *
 * The main class for the window
 *
 * @author Justin Schaaf
 * @since 3.0.0
 *
 */
public class Window {

    private Module module;

    private JTabbedPane tabs;
    private JPanel tabLog;
    private JPanel tabConfig;
    private JLabel logo;
    private JLabel version;
    private JTextPane log;
    private JPanel tabSettings;
    public JPanel main;

    public Window(Module module) {

        this.module = module;

        updateLogText();
        updateVersionText();

    }

    private void updateLogText() {
        Timer timer = new Timer();

        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                try {

                    BufferedReader logText = new BufferedReader(new InputStreamReader(new FileInputStream(new File(module.getDir() + "logs/log-latest.log"))));

                    StringBuilder text = new StringBuilder();
                    String line;

                    while ((line = logText.readLine()) != null) {
                        text.append(line + "\n");
                    }

                    logText.close();
                    log.setText(text.toString());

                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }, 0, 1000);

    }

    /**
     *
     * Checks the jar's version vs the release version and updates text accordingly. Also implements the mouse listener for when the text is clicked.
     *
     * @since 1.0.0
     *
     */
    private void updateVersionText() {

        String versionStr = Reference.version + " | ";

        if (VersionChecker.isUpdated() == false) {
            version.setText(versionStr + "A new update is available: " + VersionChecker.getLatestVersion());
            version.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent arg0) {
                    try {
                        Desktop.getDesktop().browse(new URL(Reference.releasesURL).toURI());
                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (URISyntaxException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
        else if (Reference.prerelease == true) version.setText(versionStr + "You are using a pre-release build.");
        else version.setText(versionStr + "Your jar is up to date.");

    }

}
