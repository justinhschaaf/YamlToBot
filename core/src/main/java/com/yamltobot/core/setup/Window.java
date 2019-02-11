package com.yamltobot.core.setup;

import com.yamltobot.core.common.Module;
import com.yamltobot.core.common.Reference;
import com.yamltobot.core.common.VersionChecker;
import com.yamltobot.core.handler.ConfigHandler;
import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Scene;
import javafx.scene.web.WebView;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
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
public class Window extends JFrame {

    private Module module;

    public JPanel main;
    private JTabbedPane tabs;
    private JPanel tabLog;
    private JTextPane log;
    private JPanel tabConfig;
    private JPanel tabWiki;
    private JScrollPane tabAbout;
    private JTextPane aboutText;
    private JPanel toolbar;
    private JLabel logo;
    private JLabel version;
    private JFXPanel wikiview;

    /**
     *
     * Create YamlToBot's window
     *
     * @param module The module of the bot
     * @since 3.0.0
     *
     */
    public Window(Module module) {

        this.module = module;

        try {
            UIManager.setLookAndFeel("com.pagosoft.plaf.PgsLookAndFeel");
            SwingUtilities.updateComponentTreeUI(main);
        } catch(Exception e) {}

        registerFonts();
        setupTheme();
        updateLogText();
        updateVersionText();
        updateAboutText();
        loadWikiWebpage();

        setTitle("YamlToBot | " + ConfigHandler.getString("name", module.getName()));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setContentPane(main);
        setIconImage(Toolkit.getDefaultToolkit().getImage(ClassLoader.getSystemResource("assets/logo/logo64.png")));

        pack();

        /*tabs.addChangeListener(new ChangeListener() {

            public void stateChanged(ChangeEvent evt)
            {
                JTabbedPane TabbedPane = (JTabbedPane)evt.getSource();

                int tab = TabbedPane.getSelectedIndex();

                if (tab == 2) loadWikiWebpage();

            }

        });*/

    }

    /**
     *
     * Change the colors and other theme settings for YamlToBot
     *
     * @since 3.0.0
     *
     */
    private void setupTheme() {

        // Background Color
        main.setBackground(new Color(Integer.parseInt("555555", 16)));
        toolbar.setBackground(new Color(Integer.parseInt("555555", 16)));

        // Text Color
        version.setForeground(new Color(Integer.parseInt("F2F2F2", 16)));

    }

    /**
     *
     * Register Roboto, the primary font used by YamlToBot, with the Graphics Environment
     *
     * @since 3.0.0
     *
     */
    private void registerFonts() {

        try {

            File fontFile = new File(ClassLoader.getSystemResource("assets/font/Roboto-Regular.ttf").toURI());
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();

            Font font = Font.createFont(Font.TRUETYPE_FONT, fontFile.toURI().toURL().openStream());
            font = font.deriveFont(Font.PLAIN, 14);

            ge.registerFont(font);

            main.setFont(font);
            tabs.setFont(font);
            tabLog.setFont(font);
            tabConfig.setFont(font);
            version.setFont(font);
            tabWiki.setFont(font);
            tabAbout.setFont(font);
            aboutText.setFont(font);

        } catch (URISyntaxException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (FontFormatException e) {
            e.printStackTrace();
        }

    }

    /**
     *
     * Update the text for the log every second.
     *
     * @since 3.0.0
     *
     */
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

    /**
     *
     * Update the text for the "About" tab with the licenses for the different direct dependencies for YamlToBot
     *
     * @since 3.0.0
     *
     */
    private void updateAboutText() {

        try {

            String licenses = "";

            URI uri = ClassLoader.getSystemResource("assets/text/licenses.txt").toURI();
            String mainPath = Paths.get(uri).toString();
            Path path = Paths.get(mainPath);

            Object[] lines = Files.lines(path).toArray();

            for (Object lineObj : lines) {

                String line = lineObj.toString();
                licenses += line + "\n";

            }

            aboutText.setText(licenses);

        } catch (IOException e) {
            e.printStackTrace();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

    }

    /**
     *
     * Loads the webpage for the "Wiki" tab
     *
     * **Note: The wiki does not properly render for some reason.
     * My guess is that it is because GitHub imports its CSS files
     * from a different website rather than from github.com itself.**
     *
     * @since 3.0.0
     *
     */
    public void loadWikiWebpage() {

        Platform.runLater(() -> {
            WebView browser = new WebView();
            wikiview.setScene(new Scene(browser));
            browser.getEngine().load(Reference.wikiUrl);

            browser.getEngine().setJavaScriptEnabled(true);
            browser.applyCss();

        });

    }

}
