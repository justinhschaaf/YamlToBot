package com.yamltobot.core.common;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * 
 * Class that checks if the jar is up-to-date
 * 
 * @author Justin Schaaf
 * @since 1.0.0
 *
 */
public class VersionChecker {

	/**
	 * 
	 * Check if the jar is up-to-date
	 * 
	 * @return True if up-to-date, else it returns false
	 * @since 1.0.0
	 * 
	 */
	public static boolean isUpdated() {
		
		ArrayList<String> latestVersion = splitVersion(getLatestVersion());
		ArrayList<String> currentVersion = splitVersion(Reference.version);
		
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
	
	/**
	 * 
	 * Gets the number of the latest version from the GitHub repository
	 * 
	 * @return The number of the latest version
	 * @since 1.0.0
	 * 
	 */
	public static String getLatestVersion() {
		
		ArrayList<String> versions = getVersions();

		if (versions == null) return Reference.version;
			
		String greatestVersion = "0.0.0";
		
		for (int i = 0; i < versions.size(); i++) {
			
			ArrayList<String> version = splitVersion(versions.get(i));
			ArrayList<String> latest = splitVersion(greatestVersion);
			
			if (Integer.parseInt(version.get(0)) > Integer.parseInt(latest.get(0))) {
				greatestVersion = versions.get(i);
				continue;
			}
			if (Integer.parseInt(version.get(1)) > Integer.parseInt(latest.get(1))) {
				if (Integer.parseInt(version.get(0)) >= Integer.parseInt(latest.get(0))) {
					greatestVersion = versions.get(i);
					continue;
				}
			}
			if (Integer.parseInt(version.get(2)) > Integer.parseInt(latest.get(2))) {
				if (Integer.parseInt(version.get(0)) >= Integer.parseInt(latest.get(0))) {
					if (Integer.parseInt(version.get(1)) >= Integer.parseInt(latest.get(1))) {
						greatestVersion = versions.get(i);
						continue;
					}
				}
			}
			
		}
		
		return greatestVersion;
		
	}
	
	/**
	 * 
	 * Gets the versions from the GitHub repository
	 * 
	 * @return ArrayList<String> of all version numbers
	 * @since 1.0.0
	 * 
	 */
	private static ArrayList<String> getVersions() {
		
		try {
			
			HttpURLConnection connection = (HttpURLConnection) new URL(Reference.apiReleases).openConnection();
			connection.addRequestProperty("User-Agent", "Mozilla/5.0");
			JsonReader reader = Json.createReader(new InputStreamReader(connection.getInputStream()));
			ArrayList<String> versions = new ArrayList<String>();
			List<JsonObject> json = reader.readArray().getValuesAs(JsonObject.class);

			for (JsonObject object : json) if (object.getBoolean("draft") == false && object.getBoolean("prerelease") == false) versions.add(object.getString("tag_name"));
			
			if (versions.isEmpty() == true) return null; else return versions;
			
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return null;
		
	}
	
	/**
	 * 
	 * Splits a string at the given regex. This is used here because the normal string split method is not 100% functional
	 * 
	 * @param str The string to split
	 * @return ArrayList<String> of the different elements
	 * @since 1.0.0
	 * 
	 */
	private static ArrayList<String> splitVersion(String str) {
		
		String num = "";
		ArrayList<String> returnStr = new ArrayList<String>();
		
		for (int i = 0; i < str.length(); i++) {
			String chr = str.charAt(i) + "";
			if (chr.equalsIgnoreCase(".")) {
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
