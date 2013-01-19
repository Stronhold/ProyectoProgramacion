package es.deusto.eside.programacion3.luffysurvival.engine;

import java.util.ArrayList;
import java.util.List;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

/**
 * Crea iconos
 * @author Sergio
 *
 */
public class Icon {

	/**
	 * ancho
	 */
	public static final int ICON_WIDTH = 58;
	/**
	 * Alto
	 */
	public static final int ICON_HEIGHT = 64;
	/**
	 * Imagen
	 */
	private Image image;
	/**
	 * Lista de listener
	 */
	private List<IconClickListener> listeners;
	/**
	 * coordenadas
	 */
	private int x, y;
	
	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}
	/**
	 * Constructor
	 * @param file direccion de la imagen
	 */
	public Icon(final String file) {
		try {
			image = new Image(file);
			if (image.getWidth() != ICON_WIDTH || image.getHeight() != ICON_HEIGHT) {
				image = image.getScaledCopy(ICON_WIDTH, ICON_HEIGHT);
			} 
		} catch (SlickException e) {
			e.printStackTrace();
		}
		listeners = new ArrayList<IconClickListener>();
	}
	/**
	 * Anade un listener al objeto
	 * @param l listener
	 */
	public void addIconClickListener(final IconClickListener l) {
		listeners.add(l);		
	}
	
	/**
	 * Indica si ha sido pinchado el icono
	 * @param mouseX coordenada x
	 * @param mouseY coordenada y
	 * @param posX coordenada x
	 * @param posY coordenada y
	 * @return
	 */
	public boolean update(final int mouseX, final int mouseY, int posX , int posY) {
		boolean result = false;
		
		if (mouseX >= posX && mouseX <= image.getWidth() + posX 	
				&& mouseY >= posY && mouseY <= image.getHeight() + posY) {
			for (IconClickListener l : listeners) {
				l.onClick();
			}
			result = true;
		}
		
		return result;
	}
	
	/**
	 * Dibuja
	 * @param f coordenada
	 * @param g cordenada
	 */
	public void draw(final float f, final float g) {
		image.draw(f,g);
	}
	
	

}
