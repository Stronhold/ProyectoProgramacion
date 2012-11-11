package es.deusto.eside.programacion3.luffysurvival.states;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
/**
 * Parte del menú desde la que se controlarán las opciones del juego
 * @author sergio
 *
 */

public class OptionState extends BasicGameState {

	/**
	 * stateId: indica el estado del programa
	 */
	private int stateId;

	/**
	 * Constructor
	 * @param ordinal: número de estado del programa 
	 */
	public OptionState(int ordinal) {
		this.stateId = ordinal;	
	}

	@Override
	public void init(GameContainer arg0, StateBasedGame arg1)
			throws SlickException {
		// TODO Auto-generated method stub

	}

	@Override
	public void render(GameContainer arg0, StateBasedGame arg1, Graphics arg2)
			throws SlickException {
		// TODO Auto-generated method stub

	}

	@Override
	public void update(GameContainer arg0, StateBasedGame arg1, int arg2)
			throws SlickException {
		// TODO Auto-generated method stub

	}

	@Override
	public int getID() {
		return this.stateId;
	}

}
