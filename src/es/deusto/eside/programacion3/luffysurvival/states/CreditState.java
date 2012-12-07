package es.deusto.eside.programacion3.luffysurvival.states;

import org.newdawn.slick.BasicGame;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
/**
 * En esta clase se visualizaran los creditos del juego
 * @author sergio
 *
 */

public class CreditState extends BasicGameState{

	/**
	 * stateID estado del juego
	 */
	private int stateID;
	
	/**
	 * constructor del juego
	 * @param ordinal numero de estado
	 */
	public CreditState(int ordinal){
		this.stateID = ordinal;
	}

	/**
	 * devuelve el número de estado del juego
	 */
	public int getID() {
		return this.stateID;
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

}
