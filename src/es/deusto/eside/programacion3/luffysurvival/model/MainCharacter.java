package es.deusto.eside.programacion3.luffysurvival.model;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Color;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.opengl.LoadableImageData;

/**
 * Define a los personajes que se utilizan en el juego
 * @author Sergio
 *
 */
public class MainCharacter extends BasicCharacter{

	/**
	 * Animacion de ataque final
	 */
	private Animation finalAttackAnimation;
	/**
	 * Constructor de la clase
	 * @param config direccion del fichero de propiedades
	 * @param name nombre del personaje
	 */
	public MainCharacter(String config, String name) {
		parse(config);
		current = standAnimation;
		this.name=name;
	}

	/**
	 * Metodo que lee linea por linea un fichero
	 * @param propertiesFile direccion del fichero
	 */
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
					else if(line.equals("ATTACK")){		
						loadAttackState(properties);
					}
					else{
						loadFinalAttackState(properties);
					}
					
			}
	
			// dispose all the resources after using them.
			fis.close();
			bis.close();
			dis.close();
	
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
	
		}
	
	}
	
	/**
	 * Carga las propiedades del ataque
	 * @param properties array de las propiedades
	 */
	private void loadFinalAttackState(final String[] properties) {

		String spriteSheetUrl = "";
		int damage, money, cost, frames = 0, frameWidth = 0, frameHeight = 0, time = 0;
		Color alpha = null;
		for (String property : properties) {
			String[] tokens = property.split(":");
			if (tokens[0].equalsIgnoreCase("URL")) {
				spriteSheetUrl = tokens[1].trim();
			} else if (tokens[0].equalsIgnoreCase("DAMAGE")) {
				damage = Integer.parseInt(tokens[1].trim());
			}else if (tokens[0].equalsIgnoreCase("Money")) {
				money = Integer.parseInt(tokens[1].trim());
			}else if (tokens[0].equalsIgnoreCase("Cost")) {
				cost = Integer.parseInt(tokens[1].trim());
			}else if (tokens[0].equalsIgnoreCase("frames")) {
				frames = Integer.parseInt(tokens[1].trim());
			}else if (tokens[0].equalsIgnoreCase("FRAME_WIDTH")) {
				frameWidth = Integer.parseInt(tokens[1].trim());
			}else if (tokens[0].equalsIgnoreCase("FRAME_HEIGHT")) {
				frameHeight = Integer.parseInt(tokens[1].trim());
			}else if (tokens[0].equalsIgnoreCase("time")) {
				time  = Integer.parseInt(tokens[1].trim());
			} else if (tokens[0].equalsIgnoreCase("COLOR_ALPHA")) {
				String[] colorComponents = tokens[1].split(",");
				int r = Integer.parseInt(colorComponents[0].trim());
				int g = Integer.parseInt(colorComponents[1].trim());
				int b = Integer.parseInt(colorComponents[2].trim());
				alpha = new Color(r, g, b);
			}
		} 
		SpriteSheet sheet;
		try {
			sheet = new SpriteSheet(
					spriteSheetUrl, frameWidth, frameHeight,  alpha);
			finalAttackAnimation = new Animation();
			this.finalAttackAnimation.setAutoUpdate(true);
			for (int i = 0; i < frames ; i++) {
				this.finalAttackAnimation.addFrame(sheet.getSprite(i, 0), time);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}

