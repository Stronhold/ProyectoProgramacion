package es.deusto.eside.programacion3.luffysurvival.model;

import org.newdawn.slick.Input;

/**
 * Implementada por los objetos que se mostrarán en pantalla
 * @author Sergio
 *
 */
public interface Entity {
	/**
	 * Dibuja
	 */
	void draw();
	void update(boolean pressed, int x, int y, int delta);

}
