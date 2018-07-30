package net.jusanov.yamltobot.core.setup;

import java.awt.Color;
import java.awt.Component;
import java.awt.Toolkit;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import net.jusanov.yamltobot.core.handler.ConfigHandler;

public class SetupWindow {

	public static void setupWindow() {
		
		JFrame window = new JFrame("Yaml to Bot | " + ConfigHandler.getString("name"));
		window.setVisible(true);
		window.setSize(Toolkit.getDefaultToolkit().getScreenSize().width / 2, Toolkit.getDefaultToolkit().getScreenSize().height / 2);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setResizable(false);
		window.setTitle("Yaml to Bot | " + ConfigHandler.getString("name"));
		window.setIconImage(new ImageIcon(ClassLoader.getSystemClassLoader().getResource("assets/icon.svg")).getImage());
		
		JPanel header = new JPanel();
		header.setSize(window.getSize().width, window.getSize().height / 10);
		header.setBackground(new Color(Integer.parseInt("283F50",16)));
		JLabel logo = new JLabel(new ImageIcon(ClassLoader.getSystemClassLoader().getResource("assets/icon.svg")));
		logo.setSize(window.getHeight(), window.getHeight());
		logo.setVisible(true);
		header.add(logo);
		header.setVisible(true);
		
		JPanel body = new JPanel();
		body.setSize(window.getSize().width, (window.getSize().height / 10) * 8);
		body.setBackground(new Color(Integer.parseInt("C2C8D1",16)));
		body.setVisible(true);
		
		JTextArea log = new JTextArea();
		log.setSize((window.getSize().width / 10) * 8, (window.getSize().height / 10) * 7);
		log.setBackground(new Color(Integer.parseInt("E0E3E7",16)));
		log.setAutoscrolls(true);
		log.setLineWrap(true);
		log.setVisible(true);
		log.setAlignmentY(Component.CENTER_ALIGNMENT);
		log.setEditable(false);
		body.add(log);
		
		JPanel footer = new JPanel();
		footer.setSize(window.getSize().width, window.getSize().height / 10);
		footer.setBackground(new Color(Integer.parseInt("8693A4",16)));
		footer.setVisible(true);
		
		window.add(header);
		window.add(body);
		window.add(footer);
		
		/*while (true) {
			
			try {
				FileInputStream logText = new FileInputStream(new File("YamlToBot/logs/log-latest.log"));
				log.setText(logText.toString());
				logText.close();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		}*/
		
	}
	
}
