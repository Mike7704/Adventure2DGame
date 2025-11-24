package main;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class Config {

	GamePanel gamePanel;
	
	public Config(GamePanel gamePanel) {
		this.gamePanel = gamePanel;
	}
	
	public void saveConfig() {
		try {
			BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(getExecutionPath() + "/config.txt"));
			
			// Save settings
			bufferedWriter.write("fullscreen:" + String.valueOf(gamePanel.fullscreen));
			bufferedWriter.newLine();
			bufferedWriter.write("musicVolume:" + String.valueOf(gamePanel.getMusic().volume));
			bufferedWriter.newLine();
			bufferedWriter.write("soundVolume:" + String.valueOf(gamePanel.getSoundEffect().volume));
			
			bufferedWriter.close();
					
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void loadConfig() {
	    try {
	        // Read settings
	    	BufferedReader bufferedReader = new BufferedReader(new FileReader(getExecutionPath() + "/config.txt"));
	        String line;
	    	
	        while ((line = bufferedReader.readLine()) != null) {
	            String[] parts = line.split(":");
	            if (parts.length == 2) {  // Check if the line has the expected format
	                String key = parts[0];
	                String value = parts[1];

	                switch (key) {
	                    case "fullscreen":
	                        gamePanel.fullscreen = Boolean.parseBoolean(value);
	                        break;
	                    case "musicVolume":
	                        gamePanel.getMusic().volume = Float.parseFloat(value);
	                        break;
	                    case "soundVolume":
	                        gamePanel.getSoundEffect().volume = Float.parseFloat(value);
	                        break;
	                }
	            }
	        }
	        
	        bufferedReader.close();
	        
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	}
	
	/**
     * Get the file path of the executable jar file to modify external files
     * @return The file path of the executable jar file
     */
	public static String getExecutionPath() {
	    String absolutePath = null;
		try {
			absolutePath = Main.class.getProtectionDomain().getCodeSource().getLocation().getPath();
			absolutePath = absolutePath.substring(0, absolutePath.lastIndexOf("/"));
		    absolutePath = absolutePath.replace("%20"," ");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return absolutePath;
	}
}
