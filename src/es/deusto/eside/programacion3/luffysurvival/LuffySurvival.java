package es.deusto.eside.programacion3.luffysurvival;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import es.deusto.eside.programacion3.luffysurvival.language.Locale;
import es.deusto.eside.programacion3.luffysurvival.states.CreditState;
import es.deusto.eside.programacion3.luffysurvival.states.GamePlayState;
import es.deusto.eside.programacion3.luffysurvival.states.GameState;
import es.deusto.eside.programacion3.luffysurvival.states.MainMenuState;
import es.deusto.eside.programacion3.luffysurvival.states.PreMainMenuState;
import es.deusto.eside.programacion3.luffysurvival.states.OpeningState;
import es.deusto.eside.programacion3.luffysurvival.states.OptionState;
import es.deusto.eside.programacion3.luffysurvival.util.PreferenceManager;

/**
 * Clase principal del juego
 * @author sergio
 *
 */
public class LuffySurvival extends StateBasedGame {
	
	/**
	 * WIDTH: ancho de la venta
	 */
	public static final int WIDTH = 640;
	/**
	 * HEIGHT: alto de la ventana
	 */
	public static final int HEIGHT = 480;
	
	/**
	 * Indica las opciones del juego
	 */
	public static PreferenceManager p;

	/**
	 * constructor del juego 
	 * @param name: nombre del juego
	 */
	public LuffySurvival(String name) {
		super(name);	
		p = new PreferenceManager();
	}

	@Override
	public void initStatesList(GameContainer gameContainer)
			throws SlickException {
		this.addState(new OpeningState(GameState.OPENING_STATE.ordinal()));
		this.addState(new PreMainMenuState(GameState.PREMAIN_MENU_STATE.ordinal()));
		this.addState(new MainMenuState(GameState.MAIN_MENU_STATE.ordinal()));
		this.addState(new GamePlayState(GameState.GAME_PLAY_STATE.ordinal()));
		this.addState(new CreditState(GameState.CREDIT_STATE.ordinal()));
		this.addState(new OptionState(GameState.OPTIONS_STATE.ordinal()));		
	}
/**
 * Método principal del juego
 * @param args: argumentos del programa
 */
	public static void main(String[] args) {
		try {
			AppGameContainer app = new AppGameContainer(new LuffySurvival(
					Locale.INSTANCE.getText("title")));
			app.setDisplayMode(WIDTH, HEIGHT, false);
			app.setFullscreen(p.isFullScreen());
			app.setShowFPS(false);
			app.setVSync(true);
			app.start();
		} catch (SlickException e) {
			e.printStackTrace();
		}
	}

}
