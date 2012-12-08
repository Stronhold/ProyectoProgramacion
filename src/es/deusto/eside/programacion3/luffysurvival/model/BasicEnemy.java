package es.deusto.eside.programacion3.luffysurvival.model;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Color;
import org.newdawn.slick.SpriteSheet;

public class BasicEnemy extends BasicCharacter{
	
	public BasicEnemy(String config, String name) {
		super(config,name);
	}
	protected void parse(String propertiesFile) {
		File file = new File(propertiesFile);
		FileInputStream fis = null;
		BufferedInputStream bis = null;
		DataInputStream dis = null;
	
		try {
			fis = new FileInputStream(file);
	
			// Here BufferedInputStream is added for fast reading.
			bis = new BufferedInputStream(fis);
			dis = new DataInputStream(bis);
	
			// dis.available() returns 0 if the file does not have more lines.
			while (dis.available() != 0) {
	
					String line = dis.readLine();
					String [] properties = new String [9];
					for(int i=0;i<properties.length;i++)
						properties[i] = dis.readLine();
					if (line.equals("STAND")){					
						loadStandState(properties);
					}
					else{		
						loadAttackState(properties);
					}
					
			}
	
			// dispose all the resources after using them.
			fis.close();
			bis.close();
			dis.close();
			current = this.standAnimation;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
	
		}
	
	}
}
