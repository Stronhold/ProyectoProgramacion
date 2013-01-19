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

/**
 * Crea un personaje
 * @author Sergio
 *
 */
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
	
	protected int moneyAttack, cost, costAttack;
	protected int life;	
	private int damageNormalAttack;
	public BasicCharacter() {
		super();
	}

	public int getLife() {
		return life;
	}

	public void setLife(int life) {
		this.life = life;
	}

	public int getMoney() {
		return moneyAttack;
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
		int  frames = 0, money = 0, frameWidth = 0, frameHeight = 0, time = 0;
		Color alpha = null;
		for (String property : properties) {
			String[] tokens = property.split(":");
			if (tokens[0].equalsIgnoreCase("URL")) {
				spriteSheetUrl = tokens[1].trim();
			} else if (tokens[0].equalsIgnoreCase("DAMAGE")) {
				damageNormalAttack = Integer.parseInt(tokens[1].trim());
			}else if (tokens[0].equalsIgnoreCase("Money")) {
				moneyAttack = Integer.parseInt(tokens[1].trim());
			}else if (tokens[0].equalsIgnoreCase("cost")) {
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
	

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((attackAnimation == null) ? 0 : attackAnimation.hashCode());
		result = prime * result + cost;
		result = prime * result + costAttack;
		result = prime * result + ((current == null) ? 0 : current.hashCode());
		result = prime * result + damageNormalAttack;
		result = prime * result + life;
		result = prime * result + moneyAttack;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result
				+ ((standAnimation == null) ? 0 : standAnimation.hashCode());
		result = prime * result + Float.floatToIntBits(x);
		result = prime * result + Float.floatToIntBits(y);
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof BasicCharacter)) {
			return false;
		}
		BasicCharacter other = (BasicCharacter) obj;
		if (attackAnimation == null) {
			if (other.attackAnimation != null) {
				return false;
			}
		} else if (!attackAnimation.equals(other.attackAnimation)) {
			return false;
		}
		if (cost != other.cost) {
			return false;
		}
		if (costAttack != other.costAttack) {
			return false;
		}
		if (current == null) {
			if (other.current != null) {
				return false;
			}
		} else if (!current.equals(other.current)) {
			return false;
		}
		if (damageNormalAttack != other.damageNormalAttack) {
			return false;
		}
		if (life != other.life) {
			return false;
		}
		if (moneyAttack != other.moneyAttack) {
			return false;
		}
		if (name == null) {
			if (other.name != null) {
				return false;
			}
		} else if (!name.equals(other.name)) {
			return false;
		}
		if (standAnimation == null) {
			if (other.standAnimation != null) {
				return false;
			}
		} else if (!standAnimation.equals(other.standAnimation)) {
			return false;
		}
		if (Float.floatToIntBits(x) != Float.floatToIntBits(other.x)) {
			return false;
		}
		if (Float.floatToIntBits(y) != Float.floatToIntBits(other.y)) {
			return false;
		}
		return true;
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

	public int getDamageNormalAttack() {
		return damageNormalAttack;
	}


	@Override
	public void setY(int y) {
		this.y = y;
		
	}

}