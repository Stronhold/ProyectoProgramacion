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

public class BasicCharacter implements Entity {

	/**
	 * Animacion de estar parado
	 */
	protected Animation standAnimation;
	/**
	 * Animacion de ataque
	 */
	private Animation attackAnimation;
	/**
	 * coordenadas
	 */
	protected float x;
	/**
	 * coordenadas
	 */
	protected float y;
	/**
	 * Animacion en la que el personaje se encuentra actualmente
	 */
	public Animation current;
	/**
	 * Nombre del personaje del juego
	 */
	protected String name;
	
	protected int money, cost, costAttack;

	public BasicCharacter() {
		super();
	}

	public int getMoney() {
		return money;
	}

	public int getCost() {
		return cost;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getHeight() {
		return standAnimation.getHeight();
	}

	public int getWidth() {
		return standAnimation.getWidth();
	}

	public float getX() {
		return x;
	}

	public void setX(float x) {
		this.x = x;
	}

	public float getY() {
		return y;
	}
	


	public void setY(float y) {
		this.y = y;
	}

	/**
	 * Metodo que lee linea por linea un fichero
	 * @param propertiesFile direccion del fichero
	 */
	public BasicCharacter(String config, String nombre){
		parse(config);
		current = standAnimation;
		this.name= nombre;
	}
	
	public Animation getStandAnimation() {
		return standAnimation;
	}

	public Animation getAttackAnimation() {
		return attackAnimation;
	}

	public void setCurrent(Animation current) {
		this.current = current;
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
					
					if (line.equals("STAND")){
						for(int i=0;i<properties.length;i++)
							properties[i] = dis.readLine();
						loadStandState(properties);
					}
					else if(line.equals("ATTACK")){
						for(int i=0;i<properties.length;i++)
							properties[i] = dis.readLine();
						loadAttackState(properties);
					}
					else{
						for(int i=0;i<properties.length;i++)
							properties[i] = dis.readLine();
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
	 * Carga las propiedades de la animacion de estar parado
	 * @param properties array de las propiedades
	 */
	protected void loadStandState(final String[] properties) {
	
		String spriteSheetUrl = "";
		int damage, money, frames = 0, frameWidth = 0, frameHeight = 0, time = 0;
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
			standAnimation = new Animation();
			this.standAnimation.setAutoUpdate(true);
			for (int i = 0; i < frames -1 ; i++) {
				this.standAnimation.addFrame(sheet.getSprite(i, 0), time);
			}
			//this.standAnimation.addFrame(sheet.getSprite(frames -1, 0), TIME_DOING_NOTHING);
	
		} catch (Exception e) {
			e.printStackTrace();
			
		}
	}

	/**
	 * Carga las propiedades del ataque
	 * @param properties array de las propiedades
	 */
	protected void loadAttackState(final String[] properties) {
	
		String spriteSheetUrl = "";
		int damage, frames = 0, frameWidth = 0, frameHeight = 0, time = 0;
		Color alpha = null;
		for (String property : properties) {
			String[] tokens = property.split(":");
			if (tokens[0].equalsIgnoreCase("URL")) {
				spriteSheetUrl = tokens[1].trim();
			} else if (tokens[0].equalsIgnoreCase("DAMAGE")) {
				damage = Integer.parseInt(tokens[1].trim());
			}else if (tokens[0].equalsIgnoreCase("Money")) {
				money = Integer.parseInt(tokens[1].trim());
				costAttack = Integer.parseInt(tokens[1].trim());
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
			attackAnimation = new Animation();
			this.attackAnimation.setAutoUpdate(true);
			for (int i = 0; i < frames; i++) {
				this.attackAnimation.addFrame(sheet.getSprite(i, 0), time);
			}
	
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public Animation getCurrent() {
		return current;
	}

	public void setStandAnimation(Animation standAnimation) {
		this.standAnimation = standAnimation;
	}

	public void setAttackAnimation(Animation attackAnimation) {
		this.attackAnimation = attackAnimation;
	}

	@Override
	public void draw() {
		current.draw(x, y);
	}

	@Override
	public  void update(boolean pressed, int x, int y, int delta) {
		
	}

	@Override
	public void setX(int x) {
		this.x = x;
	}

	@Override
	public void setY(int y) {
		this.y = y;
		
	}

}