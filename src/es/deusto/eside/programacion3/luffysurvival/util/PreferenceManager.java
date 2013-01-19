package es.deusto.eside.programacion3.luffysurvival.util;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class PreferenceManager {

	 static boolean music;
	 static boolean fullScreen;
	
	public PreferenceManager(){
		 try{
			  // Open the file that is the first 
			  // command line parameter
			  FileInputStream fstream = new FileInputStream("resources/Option/option.txt");
			  // Get the object of DataInputStream
			  DataInputStream in = new DataInputStream(fstream);
			  BufferedReader br = new BufferedReader(new InputStreamReader(in));
			  String sFullScreen = br.readLine();
			  String sVolume = br.readLine();
			  sVolume = sVolume.replaceFirst("activatedVolume: ", "");
			  sFullScreen = sFullScreen.replaceFirst("activatedFullScreen: ", "");
			  sFullScreen.trim();
			  sVolume.trim();
			  music = Boolean.parseBoolean(sVolume);
			  fullScreen = Boolean.parseBoolean(sFullScreen);
			  //Close the input stream
			  in.close();
			    }catch (Exception e){//Catch exception if any
			  System.err.println("Error: " + e.getMessage());
			  }
	}

	/**
	 * @return the music
	 */
	public boolean isMusic() {
		return music;
	}

	/**
	 * @param music the music to set
	 */
	public  static void setMusic(boolean music) {
		PreferenceManager.music = music;
	}

	/**
	 * @return the fullScreen
	 */
	public boolean isFullScreen() {
		return fullScreen;
	}

	/**
	 * @param fullScreen the fullScreen to set
	 */
	public static void setFullScreen(boolean fullScreen) {
		PreferenceManager.fullScreen = fullScreen;
	}
}
