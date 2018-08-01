package net.jusanov.yamltobot.core.setup;

import java.awt.Color;
import java.awt.Desktop;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextPane;

import net.jusanov.yamltobot.core.common.VersionChecker;
import net.jusanov.yamltobot.core.handler.ConfigHandler;

public class SetupWindow extends JFrame {

	private JPanel contentPane;

	public SetupWindow() {
		setTitle("YamlToBot | " + ConfigHandler.getString("name"));
		setIconImage(Toolkit.getDefaultToolkit().getImage(SetupWindow.class.getResource("/assets/icon.png")));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		setContentPane(contentPane);
		GridBagLayout gbl_contentPane = new GridBagLayout();
		gbl_contentPane.columnWidths = new int[]{0, 0};
		gbl_contentPane.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0};
		gbl_contentPane.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gbl_contentPane.rowWeights = new double[]{0.0, 0.0, 1.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		contentPane.setLayout(gbl_contentPane);
		
		JPanel header = new JPanel();
		FlowLayout flowLayout = (FlowLayout) header.getLayout();
		flowLayout.setVgap(0);
		flowLayout.setHgap(0);
		header.setForeground(new Color(Integer.parseInt("FEFEFE",16)));
		header.setBackground(new Color(Integer.parseInt("283F50",16)));
		GridBagConstraints gbc_header = new GridBagConstraints();
		gbc_header.anchor = GridBagConstraints.NORTH;
		gbc_header.gridheight = 2;
		gbc_header.fill = GridBagConstraints.HORIZONTAL;
		gbc_header.gridx = 0;
		gbc_header.gridy = 0;
		contentPane.add(header, gbc_header);
		
		JLabel label = new JLabel("");
		label.setIcon(new ImageIcon(SetupWindow.class.getResource("/assets/logo.png")));
		header.add(label);
		
		JTextArea log = new JTextArea();
		log.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {

				try {
					
					BufferedReader logText = new BufferedReader(new InputStreamReader(new FileInputStream(new File("YamlToBot/logs/log-latest.log"))));
					
					StringBuilder text = new StringBuilder();
					String line;
					
					while ((line = logText.readLine()) != null) {
						text.append(line);
					}

					logText.close();
					log.setText(text.toString());
					
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
				
			}
		});
		log.setFont(new Font("Monospaced", Font.PLAIN, 18));
		log.setEditable(false);
		log.setText("LOG");
		GridBagConstraints gbc_log = new GridBagConstraints();
		gbc_log.gridheight = 3;
		gbc_log.fill = GridBagConstraints.BOTH;
		gbc_log.gridx = 0;
		gbc_log.gridy = 2;
		contentPane.add(log, gbc_log);
		
		JPanel footer = new JPanel();
		FlowLayout flowLayout_1 = (FlowLayout) footer.getLayout();
		flowLayout_1.setVgap(0);
		flowLayout_1.setHgap(0);
		footer.setForeground(new Color(Integer.parseInt("FEFEFE",16)));
		footer.setBackground(new Color(Integer.parseInt("8693A4",16)));
		GridBagConstraints gbc_footer = new GridBagConstraints();
		gbc_footer.anchor = GridBagConstraints.SOUTH;
		gbc_footer.fill = GridBagConstraints.HORIZONTAL;
		gbc_footer.gridx = 0;
		gbc_footer.gridy = 5;
		contentPane.add(footer, gbc_footer);
		
		JTextPane version = new JTextPane();
		version.setEditable(false);
		version.setForeground(new Color(Integer.parseInt("FEFEFE",16)));
		version.setBackground(new Color(Integer.parseInt("8693A4",16)));
		version.setFont(new Font("Tahoma", Font.BOLD, 15));
		version.setText(net.jusanov.yamltobot.core.common.Reference.version);
		footer.add(version);
		
		JTextPane update = new JTextPane();
		update.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				if (VersionChecker.isUpdated() == false) {
					try {
						Desktop.getDesktop().browse(new URL(net.jusanov.yamltobot.core.common.Reference.releasesURL).toURI());
					} catch (MalformedURLException e) {
						e.printStackTrace();
					} catch (IOException e) {
						e.printStackTrace();
					} catch (URISyntaxException e) {
						e.printStackTrace();
					}
				}
			}
		});
		update.setEditable(false);
		update.setForeground(new Color(Integer.parseInt("FEFEFE",16)));
		update.setBackground(new Color(Integer.parseInt("8693A4",16)));
		update.setFont(new Font("Tahoma", Font.PLAIN, 18));
		update.setText(updateText());
		footer.add(update);
	}
	
	private String updateText() {
		
		if (VersionChecker.isUpdated() == false) return "A new update is available: " + VersionChecker.getLatestVersion();
		else return "Your jar is up to date.";
		
	}
	
}	
