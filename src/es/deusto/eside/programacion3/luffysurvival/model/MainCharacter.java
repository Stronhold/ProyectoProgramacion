package es.deusto.eside.programacion3.luffysurvival.model;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Color;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.deusto.eside.programacion3.luffysurvival.engine.ImageClickListener;

/**
 * Define a los personajes que se utilizan en el juego
 * 
 * @author Sergio
 * 
 */
public class MainCharacter extends BasicCharacter {
	private Logger log = LoggerFactory.getLogger(BasicCharacter.class);
	private Image delete;
	private Image superAttack;
	private List<ImageClickListener> listenerDelete;
	private List<ImageClickListener> listenerAttack;
	private static final int  X_POSITION = 10;
	private static final int Y_POSITION_DELETE = 60;
	private static final int Y_POSITION_ATTACK = 20;
	private boolean finalAttackReady;
	private int costSuper;


	/**
	 * Animacion de ataque final
	 */
	private Animation finalAttackAnimation;
	
	private boolean contextMenu;

	/**
	 * Constructor de la clase
	 * 
	 * @param config
	 *            direccion del fichero de propiedades
	 * @param name
	 *            nombre del personaje
	 */
	public MainCharacter(String config, String name) {
		super(config, name);
		listenerDelete = new ArrayList<ImageClickListener>();
		listenerAttack = new ArrayList<ImageClickListener>();
		contextMenu = false;
		finalAttackReady = true;
		try {
			delete = new Image("resources/image/delete.png");
			delete=delete.getScaledCopy(20, 20);
		} catch (SlickException e) {
			e.printStackTrace();
		}
		try {
			superAttack = new Image("resources/image/final.png");
			superAttack = superAttack.getScaledCopy(20, 20);
		} catch (SlickException e) {
			e.printStackTrace();
		}
	}




	public Animation getFinalAttackAnimation() {
		return finalAttackAnimation;
	}


	public boolean isFinalAttackReady() {
		return finalAttackReady;
	}


	public void setFinalAttackReady(boolean finalAttackReady) {
		this.finalAttackReady = finalAttackReady;
	}


	/**
	 * Metodo que lee linea por linea un fichero
	 * 
	 * @param propertiesFile
	 *            direccion del fichero
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
				String[] properties = new String[9];
				for (int i = 0; i < properties.length; i++)
					properties[i] = dis.readLine();
				if (line.equals("STAND")) {
					loadStandState(properties);
				} else if (line.equals("ATTACK")) {
					loadAttackState(properties);
				} else {
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

	public int getCostSuper() {
		return costSuper;
	}


	/**
	 * Carga las propiedades del ataque
	 * 
	 * @param properties
	 *            array de las propiedades
	 */
	private void loadFinalAttackState(final String[] properties) {

		String spriteSheetUrl = "";
		int damage, money, frames = 0, frameWidth = 0, frameHeight = 0, time = 0;
		Color alpha = null;
		for (String property : properties) {
			String[] tokens = property.split(":");
			if (tokens[0].equalsIgnoreCase("URL")) {
				spriteSheetUrl = tokens[1].trim();
			} else if (tokens[0].equalsIgnoreCase("DAMAGE")) {
				damage = Integer.parseInt(tokens[1].trim());
			} else if (tokens[0].equalsIgnoreCase("Money")) {
				money = Integer.parseInt(tokens[1].trim());
			} else if (tokens[0].equalsIgnoreCase("Cost")) {
				costSuper = Integer.parseInt(tokens[1].trim());
			} else if (tokens[0].equalsIgnoreCase("frames")) {
				frames = Integer.parseInt(tokens[1].trim());
			} else if (tokens[0].equalsIgnoreCase("FRAME_WIDTH")) {
				frameWidth = Integer.parseInt(tokens[1].trim());
			} else if (tokens[0].equalsIgnoreCase("FRAME_HEIGHT")) {
				frameHeight = Integer.parseInt(tokens[1].trim());
			} else if (tokens[0].equalsIgnoreCase("time")) {
				time = Integer.parseInt(tokens[1].trim());
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
			sheet = new SpriteSheet(spriteSheetUrl, frameWidth, frameHeight,
					alpha);
			finalAttackAnimation = new Animation();
			this.finalAttackAnimation.setAutoUpdate(true);
			for (int i = 0; i < frames; i++) {
				this.finalAttackAnimation.addFrame(sheet.getSprite(i, 0), time);
			}
			this.finalAttackAnimation.addFrame(this.finalAttackAnimation.getImage(this.getFinalAttackAnimation().getFrameCount() - 1), 100);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	//x e y son las coordenadas en las que se ha pinchado
	@Override
	public void update(boolean pressed, int mouseX, int mouseY, int delta) {
		boolean result = false;
		if (current.equals(finalAttackAnimation) && current.getFrame()==current.getFrameCount()-1) {
			current=standAnimation;
			finalAttackAnimation.restart();
			finalAttackReady = true;
		}
		
		if (pressed) {
			log.error("X: " + this.x + " Y: " + this.y + " Mouse x: " + mouseX
					+ " Mouse Y: " + mouseY);
			/* Si nos salimos es que no esta dentro */
			if ( mouseX  <= this.x + current.getCurrentFrame().getWidth()
					&& mouseX >=this. x &&
					mouseY >= this.y &&
					 mouseY <= this.y + current.getCurrentFrame().getHeight()) {
				Color color = current.getCurrentFrame().getColor(
						(int) (mouseX - this.x), (int) (mouseY - this.y));
				log.error("Dentro de " + this.getName());
				if (color.a == 1) {
					log.error("Clicked");
					contextMenu = true;
				}
			}
			//Delete image
			if (mouseX <= this.x + current.getCurrentFrame().getWidth() - X_POSITION + delete.getWidth() && mouseX >=this.x + current.getCurrentFrame().getWidth() - X_POSITION &&
					mouseY<= this.y + Y_POSITION_DELETE + delete.getHeight() && mouseY>= this.y + Y_POSITION_DELETE){
				for (ImageClickListener l : listenerDelete) {
					l.onClick(this);
				}
				result = true;
			}
			//Super attack image
			if (mouseX <= this.x + current.getCurrentFrame().getWidth() - X_POSITION + delete.getWidth() && mouseX >=this.x + current.getCurrentFrame().getWidth() - X_POSITION &&
					mouseY <= this.y + Y_POSITION_ATTACK + delete.getHeight() && mouseY >= this.y + Y_POSITION_ATTACK){
				for (ImageClickListener l : listenerAttack) {
					l.onClick(this);
				}
				result = true;
			}
		}
		
		
		
	}
	
	public Image getSuperAttack() {
		return superAttack;
	}

	@Override
	public void draw() {
		current.draw(x, y);
		if(contextMenu){
			delete.draw(x+current.getCurrentFrame().getWidth() - X_POSITION , y+Y_POSITION_DELETE);	
			superAttack.draw(x + current.getCurrentFrame().getWidth() -  X_POSITION, y+Y_POSITION_ATTACK);
		}
	}
	
	public void addImageClickListenerDelete(final ImageClickListener l) {
		listenerDelete.add(l);		
	}
	
	public void addImageClickListenerAttack(final ImageClickListener l){
		listenerAttack.add(l);
	}


	public void setContextMenu(boolean contextMenu) {
		this.contextMenu = contextMenu;
	}
	
	
	
	

}
