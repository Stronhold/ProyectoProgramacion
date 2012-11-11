package es.deusto.eside.programacion3.luffysurvival.states;

import java.util.Map;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.tiled.TiledMap;

/**
 * Estado de inicio de partida.
 * @author sergio
 *
 */
public class GamePlayState extends BasicGameState {

	private static final String MAP_LOCATION = "resources/maps/level/1/mapa.tmx";
	private int stateId;
	private TiledMap map;
	
	public GamePlayState(int ordinal) {
		this.stateId = ordinal;
	}

	@Override
	public void init(GameContainer arg0, StateBasedGame arg1)
			throws SlickException {
		this.map = new TiledMap(MAP_LOCATION);
	}

	@Override
	public void render(GameContainer gc, StateBasedGame ab, Graphics g)
			throws SlickException {
		
		this.map.render(0, 0);


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
