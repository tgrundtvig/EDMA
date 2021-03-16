package org.abstractica.edma.compiler;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;

public class UserSettings
{
	private static UserSettings instance;
	private static boolean DEBUG = false; 
	private String SETTINGS_FILE_PATH = "settings.cfg";
	
	private String MYSQL_URL = "localhost";
	private String MYSQL_USER = "";
	private String MYSQL_PASS = "";
	private int MYSQL_PORT = 3306;
	
	public static void main(String[] args)
	{
		// TODO remove this when all testing is done.
		UserSettings.DEBUG = true;
		try
		{
			UserSettings settings = new UserSettings("src/edma/compiler/settings.cfg");
			settings.loadUserSettings();
		}
		catch(IOException | ParseException e)
		{
			e.printStackTrace();
		}
	}
	
	
	public UserSettings() throws IOException, ParseException
	{
		loadUserSettings();
	}
	
	public UserSettings(String settingsFilePath) throws IOException, ParseException
	{
		SETTINGS_FILE_PATH = settingsFilePath;
		loadUserSettings();
	}
	
	public String getMySqlUsername()
	{
		return MYSQL_USER;
	}
	
	public String getMySqlPassword()
	{
		return MYSQL_PASS;
	}
	
	public String getMySqlURL()
	{
		return MYSQL_URL;
	}
	
	public int getMySqlPort()
	{
		return MYSQL_PORT;
	}
	
	/**
	 * Loads the user settings from the given config file.
	 * @return true if the config file was found, and successfully parsed. 
	 * Returns false if the config file was not found (and then default settings
	 * are used).
	 * @throws IOException if an I/O error occurs while reading the config file 
	 */
	public boolean loadUserSettings() throws IOException, ParseException
	{
		File file = new File(SETTINGS_FILE_PATH);
		try
		{
			FileReader fileReader = new FileReader(file);
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			String line;
			int lineNum = 1;
			do 
			{
				line = bufferedReader.readLine();
				if (DEBUG)
				{
					System.out.println("UserSettings: read line: " + line);
				}
				if (line != null)
				{
					line = line.trim();
					if (!line.isEmpty() && !line.startsWith("#"))
					{
						String[] pair = line.split("=", 2);
						setOption(pair, lineNum);						
					}
					++lineNum;
				}
			} while (line != null);
			
			return true;
		}
		catch(FileNotFoundException e)
		{
			//e.printStackTrace();
			System.out.println("No settings file '"+SETTINGS_FILE_PATH+"' found. Default settings are used.");
			return false;
		}
	}
	
	private void setOption(String pair[], int lineNum) throws ParseException
	{
		String parseExceptionText = "Bad option in settings-file '"+SETTINGS_FILE_PATH+
                "' at line "+lineNum+".";
		if (pair.length != 2)
		{
			throw new ParseException(parseExceptionText, lineNum);
		}
		String option = pair[0].trim();
		String value = pair[1].trim();
		if (DEBUG)
		{
			System.out.println("UserSettings: Setting: '"+option+"' = '"+value+"'");
		}
		switch (option)
		{
			case "mysql.url":
				MYSQL_URL = value;
				break;
			case "mysql.username":
				MYSQL_USER = value;
				break;
			case "mysql.password":
				MYSQL_PASS = value;
				break;
			case "mysql.port":
				int port = Integer.parseInt(value);
				if (port < 1024 && port > 65536)
				{
					throw new ParseException("Line "+lineNum+": Port number must "+
					"be between 1025 and 65536", lineNum);
				}
				MYSQL_PORT = port;
			default:
				throw new ParseException("Unknown option '"+option+"' in file '"+
						SETTINGS_FILE_PATH+"' on line "+lineNum+".", lineNum);
		}
	}
}
