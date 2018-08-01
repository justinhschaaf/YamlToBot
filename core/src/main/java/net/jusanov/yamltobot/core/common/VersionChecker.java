package net.jusanov.yamltobot.core.common;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class VersionChecker {

	public static boolean isUpdated() {
		
		ArrayList<String> latestVersion = split(getLatestVersion(), ".");
		ArrayList<String> currentVersion = split(Reference.version, ".");
		
		if (Integer.parseInt(latestVersion.get(0) + "") > Integer.parseInt(currentVersion.get(0) + "")) {
			return false;
		}
		if (Integer.parseInt(latestVersion.get(1) + "") > Integer.parseInt(currentVersion.get(1) + "")) {
			if (Integer.parseInt(latestVersion.get(0) + "") >= Integer.parseInt(currentVersion.get(0) + "")) {
				return false;
			}
		}
		if (Integer.parseInt(latestVersion.get(2) + "") > Integer.parseInt(currentVersion.get(2) + "")) {
			if (Integer.parseInt(latestVersion.get(0) + "") >= Integer.parseInt(currentVersion.get(0) + "")) {
				if (Integer.parseInt(latestVersion.get(1) + "") >= Integer.parseInt(currentVersion.get(1) + "")) {
					return false;
				}
			}
			
		}
		
		return true;
		
	}
	
	public static String getLatestVersion() {
		
		ArrayList<String> versions = getVersions();
		
		if (versions == null) return Reference.version;
			
		String greatestVersion = "0.0.0";
		
		for (int i = 0; i < versions.size(); i++) {
			
			String[] version = versions.get(i).split(".");
			String[] latest = greatestVersion.split(".");
			
			if (Integer.parseInt(version[0]) > Integer.parseInt(latest[0])) {
				greatestVersion = versions.get(i);
				continue;
			}
			if (Integer.parseInt(version[1]) > Integer.parseInt(latest[1])) {
				if (Integer.parseInt(version[0]) >= Integer.parseInt(latest[0])) {
					greatestVersion = versions.get(i);
					continue;
				}
			}
			if (Integer.parseInt(version[2]) > Integer.parseInt(latest[2])) {
				if (Integer.parseInt(version[0]) >= Integer.parseInt(latest[0])) {
					if (Integer.parseInt(version[1]) >= Integer.parseInt(latest[1])) {
						greatestVersion = versions.get(i);
						continue;
					}
				}
			}
			
		}
		
		return greatestVersion;
		
	}
	
	private static ArrayList<String> getVersions() {
		
		try {
			
			HttpURLConnection connection = (HttpURLConnection) new URL(Reference.apiReleases).openConnection();
			connection.addRequestProperty("User-Agent", "Mozilla/5.0");
			BufferedReader json = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			
			ArrayList<String> versions = new ArrayList<String>();
			String line;
			while ((line = json.readLine()) != null) {
				
				line.trim();
				
				if (line.startsWith("\"tag_name\"")) {
					versions.add(line.trim().replace("\"tag_name\"", "").replace("\"", ""));
				}
				
			}
			
			if (versions.isEmpty() == true) return null; else return versions;
			
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return null;
		
	}
	
	private static ArrayList<String> split(String str, String regex) {
		
		String num = "";
		ArrayList<String> returnStr = new ArrayList<String>();;
		
		for (int i = 0; i < str.length(); i++) {
			String chr = str.charAt(i) + "";
			if (chr.equalsIgnoreCase(regex)) {
				returnStr.add(num);
				num = "";
			} else {
				num += chr;
			}
		}
		
		returnStr.add(num);
		
		return returnStr;
		
	}
	
}
