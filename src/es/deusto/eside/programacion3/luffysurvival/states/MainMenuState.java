package es.deusto.eside.programacion3.luffysurvival.states;

import java.io.IOException;
import java.net.URL;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.GL11;
import org.newdawn.slick.Animation;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.MouseListener;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.GameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;

import TWLSlick.TWLInputAdapter;
import de.matthiasmann.twl.Button;
import de.matthiasmann.twl.GUI;
import de.matthiasmann.twl.Widget;
import de.matthiasmann.twl.renderer.lwjgl.LWJGLRenderer;
import de.matthiasmann.twl.theme.ThemeManager;
import es.deusto.eside.programacion3.luffysurvival.LuffySurvival;
import es.deusto.eside.programacion3.luffysurvival.language.Locale;

/**
 * Menu del juego
 * @author sergio
 *
 */
public class MainMenuState extends BasicGameState {

	/**
	 * MILLISECONDS_PER_DAY: esta en unix time, un día son 86400 milisegundos
	 */
	private static final long MILLISECONDS_PER_DAY = 86400;
	/**
	 * stateID: numero de estado en el que se encuentra
	 */
	private int stateID;
	/**
	 * Widget padre
	 */
	private Widget root;

	/**
	 * LWGLRenderer contexto de renderizado 
	 */
	private LWJGLRenderer lwjglRenderer;
	/**
	 * theme tema usado para la escritura
	 */
	private Object theme;

	/**
	 * gui intefaz de usuario
	 */
	private GUI gui;
	/**
	 * twlInputoAdapter: gestiona las entradas de los botones
	 */
	private TWLInputAdapter twlInputAdapter;

	/**
	 * buttonInitGame: boton de inicio
	 */
	private Button buttonInitGame;
	/**
	 * buttonOption: boton de opciones
	 */
	private Button buttonOption;
	/**
	 * buttonCredits: boton de creditos
	 */
	private Button buttonCredits;

	private Image background;
	/**
	 * Constructor de MainMenuState
	 * @param id el id del estado
	 */
	public MainMenuState(int id) {
		this.stateID = id;
	}

	@Override
	public void enter(final GameContainer gameContainer, final StateBasedGame sb) {
		try {
			gameContainer.setFullscreen(LuffySurvival.p.isFullScreen());
		} catch (SlickException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		gameContainer.getInput().addPrimaryListener(twlInputAdapter);
	}

	@Override
	public void leave(final GameContainer gameContainer, final StateBasedGame sb) {
		gameContainer.getInput().removeListener(twlInputAdapter);
	}

	@Override
	public void init(GameContainer gameContainer, StateBasedGame sb)
			throws SlickException {
		initGUI(gameContainer, sb);

	}

	@Override
	public void render(GameContainer arg0, StateBasedGame arg1, Graphics g)
			throws SlickException {
		 
		g.drawImage(background, 0,0);
		twlInputAdapter.render();

	}

	@Override
	public void update(GameContainer arg0, StateBasedGame arg1, int arg2)
			throws SlickException {

	}


	@Override
	public int getID() {
		return this.stateID;
	}

	/**
	 * 
	 * @param gc: ventana del juego
	 * @param sb: lleva el control de los estados del juego
	 */
	private void initGUI(final GameContainer gc, final StateBasedGame sb) {
		initTWL(gc);
		twlInputAdapter = new TWLInputAdapter(gui, gc.getInput());
		
		createButtons();
		initButtonsEvent(sb);
		try {
			loadBackground();
		} catch (SlickException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Carga el fondo del estado en función de la hora
	 * @throws SlickException 
	 */
	private void  loadBackground() throws SlickException {
		background = new Image(getBackground(System.currentTimeMillis() / 1000));
	}

	/**
	 * 
	 * @param time tiempo en formato de Unix
	 * @return devuelve la dirección de la imagen
	 */
	String getBackground(final long time) {
		long offset = time % MILLISECONDS_PER_DAY ;
		String location = "resources/image/menu/backgounds/";
		if (offset <= MILLISECONDS_PER_DAY / 4) {
			location += "1.jpg";
			
		} else if ( offset <=  MILLISECONDS_PER_DAY / 2 )	{
			location += "2.jpg";
		} else if (offset <= MILLISECONDS_PER_DAY / 4 * 3) {
			location += "3.jpg";
		} else {
			location += "4.jpg";
		}
		
		return location;
	}

	/**
	 * Inicializa los botones del juego
	 * @param sb: maneja el estado del juego
	 */
	private void initButtonsEvent(final StateBasedGame sb) {
		buttonInitGame.addCallback(new Runnable() {	
			@Override
			public void run() {
				sb.enterState(es.deusto.eside.programacion3.luffysurvival.states.GameState.GAME_PLAY_STATE.ordinal());
			}
		});
		buttonCredits.addCallback(new Runnable() {
			
			@Override
			public void run() {
				sb.enterState(es.deusto.eside.programacion3.luffysurvival.states.GameState.CREDIT_STATE.ordinal());		
			}
		});
		buttonOption.addCallback(new Runnable() {
			
			@Override
			public void run() {
				sb.enterState(es.deusto.eside.programacion3.luffysurvival.states.GameState.OPTIONS_STATE.ordinal());
			}
		});
	}

	/**
	 * Crea los botones de opciones
	 */
	private void createButtons() {
		buttonInitGame = new Button();
		buttonInitGame.setText(Locale.INSTANCE.getText("start"));
		buttonInitGame.setTheme("button");
		buttonInitGame.setPosition(50, 50);

		twlInputAdapter.getGui().getRootPane()
				.setSize(LuffySurvival.WIDTH, LuffySurvival.HEIGHT);
		twlInputAdapter.getGui().getRootPane().add(buttonInitGame);
		buttonInitGame.adjustSize();

		buttonOption = new Button();
		buttonOption.setText(Locale.INSTANCE.getText("opcion"));
		buttonOption.setTheme("button");
		buttonOption.setPosition(50, 100 + buttonInitGame.getHeight());

		twlInputAdapter.getGui().getRootPane()
				.setSize(LuffySurvival.WIDTH, LuffySurvival.HEIGHT);
		twlInputAdapter.getGui().getRootPane().add(buttonOption);
		buttonOption.adjustSize();

		buttonCredits = new Button();
		buttonCredits.setText(Locale.INSTANCE.getText("credit"));
		buttonCredits.setTheme("button");
		buttonCredits.setPosition(50, 150 + buttonInitGame.getHeight() + buttonOption.getHeight());
		
		twlInputAdapter.getGui().getRootPane()
				.setSize(LuffySurvival.WIDTH, LuffySurvival.HEIGHT);
		twlInputAdapter.getGui().getRootPane().add(buttonCredits);
		buttonCredits.adjustSize();		
	}

	/**
	 * Inicializa el tema de escritura
	 * @param gc: ventana del juego
	 */
	private void initTWL(GameContainer gc) {
		root = new Widget();
		root.setTheme("");

		// save Slick's GL state while loading the theme
		GL11.glPushAttrib(GL11.GL_ALL_ATTRIB_BITS);
		try {
			lwjglRenderer = new LWJGLRenderer();
			theme = ThemeManager.createThemeManager(new URL(
					"file:resources/theme/LuffySurvivalTheme.xml"),
					lwjglRenderer);
			gui = new GUI(root, lwjglRenderer);
			gui.applyTheme((ThemeManager) theme);
		} catch (LWJGLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			// restore Slick's GL state
			GL11.glPopAttrib();
		}

	}

}