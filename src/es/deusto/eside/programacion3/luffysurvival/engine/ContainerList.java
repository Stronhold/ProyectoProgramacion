package es.deusto.eside.programacion3.luffysurvival.engine;

import java.util.ArrayList;
import java.util.List;

import org.newdawn.slick.Image;
import org.newdawn.slick.Renderable;
import org.newdawn.slick.SlickException;

public class ContainerList implements Renderable {
	
	private Image background;
	private Image upArrow;
	private Image downArrow;
	
	
	private static  final int ICON_X_POSITION = 28;
	private static  final int ICON_Y_POSITION = 9;

	private static final int ICONS_PER_LINE = 4;


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

	private List<Icon> icons;
	
	private int currentRow;
	
	public ContainerList() {
		currentRow = 0;
		icons = new ArrayList<Icon>();
		try {
			background = new Image("resources/theme/listContainer/background.png");
			upArrow = new Image("resources/theme/listContainer/upArrow.png").getScaledCopy(15, 15);
			downArrow = upArrow.getFlippedCopy(false, true);
		} catch (SlickException e) {
			e.printStackTrace();
		}
	}


	public void addIcon(final Icon a) {
		icons.add(a);
	}

	public void draw () {
		draw(x,y);
	}
	@Override
	public void draw(float x, float y) {
		background.draw(x,y);	
		for (int i = currentRow  * ICONS_PER_LINE; i < currentRow * ICONS_PER_LINE + ICONS_PER_LINE && i < icons.size(); i++) {
			int relativePosition = i - currentRow  * ICONS_PER_LINE;
			icons.get(i).draw(ICON_X_POSITION + x + relativePosition * Icon.ICON_WIDTH , ICON_Y_POSITION + y);
		}
		upArrow.draw(9 + x, 23 +y);
		downArrow.draw(9 + x, 42 +y);

	}


	public void update(int mouseX, int mouseY) {
		if (mouseX >= 9 + x && mouseX <= upArrow.getWidth() + 9 + x
				&& mouseY >= 23 + y && mouseY <= upArrow.getHeight() + 23 +y
				&& currentRow != 0) {
			currentRow--;
		}
		if (mouseX >= 9 + x && mouseX <= upArrow.getWidth() + 9 + x
				&& mouseY >= 42 + y && mouseY <= upArrow.getHeight() + 42 +y 
				&& currentRow <  (icons.size() /  ICONS_PER_LINE) && icons.size() > ICONS_PER_LINE * (currentRow + 1)   ) {

			currentRow++;
		}	
		for (int i = currentRow  * ICONS_PER_LINE; i < currentRow * ICONS_PER_LINE + ICONS_PER_LINE && i < icons.size(); i++) {
			int relativePosition = i - currentRow  * ICONS_PER_LINE;
			if (icons.get(i).update(mouseX, mouseY, ICON_X_POSITION + x + relativePosition * Icon.ICON_WIDTH , ICON_Y_POSITION + y)) {
				break;
			}
		}
	}

}
