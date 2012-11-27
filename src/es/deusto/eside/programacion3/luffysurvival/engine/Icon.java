package es.deusto.eside.programacion3.luffysurvival.engine;

import java.util.ArrayList;
import java.util.List;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class Icon {

	public static final int ICON_WIDTH = 58;
	public static final int ICON_HEIGHT = 64;
	
	private Image image;
	
	private List<IconClickListener> listeners;
	
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
	
	public void addIconClickListener(final IconClickListener l) {
		listeners.add(l);		
	}
	
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
	
	public void draw(final float f, final float g) {
		image.draw(f,g);
	}
	
	

}
