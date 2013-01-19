package es.deusto.eside.programacion3.luffysurvival.states;

import java.awt.Font;

import org.lwjgl.opengl.GL11;
import org.newdawn.slick.Animation;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.KeyListener;
import org.newdawn.slick.MouseListener;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.UnicodeFont;
import org.newdawn.slick.font.effects.ColorEffect;
import org.newdawn.slick.font.effects.ShadowEffect;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.deusto.eside.programacion3.luffysurvival.LuffySurvival;
import es.deusto.eside.programacion3.luffysurvival.engine.FadeOutAnimation;
import es.deusto.eside.programacion3.luffysurvival.language.Locale;

/**
 * Pre-menú del juego
 * @author sergio
 *
 */
public class PreMainMenuState extends BasicGameState {

	/**
	 * Nos indica si es la primera vez que se pulsa la tecla
	 */
	private static boolean isFirstTime = true;
	/**
	 * DURATION: milisegundos
	 */
	private static final int DURATION = 4000;
	
	/**
	 * SKY_DURATION: milisegundos
	 */
	private static final int SKY_DURATION = 200;
	
	/**
	 *WAITING_TIME: (unix time) tiempo de espera para cambiar imagen
	 */	
	private static final int WAITING_TIME = 200;
	
	/**
	 *fontPath: indica la ruta del estilo de escritura del mensaje
	 */
	private static final String fontPath = "resources/fonts/OnePiece.ttf";
	
	/**
	 * stateID: estado del juego
	 */
	private int stateID;

	/**
	 * background: imagen de fondo
	 */
	private Image background;
	
	/**
	 * mainCharacters: animacion de aparecer y desaparecer de los personajes
	 */
	private FadeOutAnimation mainCharacters;

	/**
	 *mouseListener: listener de raton
	 */
	private MouseListener mouseListener;

	/**
	 * keyListener: listener del teclado
	 */
	private KeyListener keyListener;

	/**
	 * sky: animacion teclado
	 */
	private Animation sky;

	/**
	 * fpsFont: fuente para escribir
	 */
	private UnicodeFont fpsFont;
	/**
	 * timeWaiting: (unix time) tiempo de espera
	 */
	private int timeWaiting;
	/**
	 * logger: variable para loge
	 */
	private final Logger logger = LoggerFactory.getLogger(OpeningState.class);
	

	@Override
	public void enter(final GameContainer container, final StateBasedGame sb) {
		Input i = container.getInput();

		keyListener = initKeyListener(sb);
		i.addKeyListener(keyListener);
		mouseListener = initMouserListener(sb);
		i.addMouseListener(mouseListener);
		timeWaiting = 0;
	}

	@Override
	public void init(GameContainer gameContainer, StateBasedGame sb)
			throws SlickException {
		Font awtFont = new Font("Arial", Font.BOLD, 40);
	    fpsFont = new UnicodeFont(awtFont);
	    fpsFont.addAsciiGlyphs();
		fpsFont.addGlyphs(800, 800);
		fpsFont.getEffects().add(new ColorEffect(java.awt.Color.BLACK));
		fpsFont.getEffects().add(new ShadowEffect());
		fpsFont.loadGlyphs();
		initPreMenu();

	}
	/**
	 * Inicializa el menu con las imagenes
	 */
	private void initPreMenu() {
		try {
			this.background = new Image("resources/image/menu/background.png");
			this.mainCharacters = new FadeOutAnimation();
			this.mainCharacters.addFrame(new Image(
					"resources/image/menu/luffy.png"), DURATION);
			this.mainCharacters.addFrame(new Image(
					"resources/image/menu/zoro.png"), DURATION);
			this.mainCharacters.addFrame(new Image(
					"resources/image/menu/nami.png"), DURATION);
			this.mainCharacters.addFrame(new Image(
					"resources/image/menu/usopp.png"), DURATION);
			this.mainCharacters.addFrame(new Image(
					"resources/image/menu/sanji.png"), DURATION);
			this.mainCharacters.addFrame(new Image(
					"resources/image/menu/chopper.png"), DURATION);
			this.mainCharacters.addFrame(new Image(
					"resources/image/menu/robin.png"), DURATION);
			this.mainCharacters.addFrame(new Image(
					"resources/image/menu/franky.png"), DURATION);
			this.mainCharacters.addFrame(new Image(
					"resources/image/menu/brook.png"), DURATION);

			loadSky();

		} catch (SlickException e) {
			e.printStackTrace();
			this.logger.error("Exception in PreMainMenuState ");
			e.printStackTrace();
		}


	}

	/**
	 * Carga animacion del cielo
	 */
	private void loadSky() {
		String source = "resources/sprites/intro/sky/sky";
		sky = new Animation();
		try {
			for (int i = 1; i < 24; i++) {
				Image temp = new Image(source + i + ".png");

				sky.addFrame(temp.getSubImage(373, 0, 177, 129), SKY_DURATION);
			}
		} catch (SlickException e) {
			e.printStackTrace();
		}

	}

	/**
	 * constructor de la clase
	 * @param stateID: estado del programa
	 */
	public PreMainMenuState(int stateID) {
		this.stateID = stateID;
	}

	@Override
	public void render(GameContainer container, StateBasedGame arg1, Graphics g)
			throws SlickException {
		sky.getCurrentFrame().draw(0,0,4);
		g.drawImage(this.background, 0, 0);

		this.mainCharacters.draw(LuffySurvival.WIDTH
				- this.mainCharacters.getCurrentFrame().getWidth(), 0);

		g.drawString(Locale.INSTANCE.getText("click"), 190, 300);
	}

	@Override
	public void update(GameContainer gameContainer, StateBasedGame sb, int delta)
			throws SlickException {
		sky.update(delta);
		timeWaiting += delta;
		//if (timeWaiting > WAITING_TIME) {
			//sb.enterState(GameState.OPENING_STATE.ordinal(), new FadeOutTransition(), new FadeInTransition());
		//}
	}

	@Override
	public int getID() {
		return this.stateID;
	}

	@Override
	public void leave(final GameContainer container, final StateBasedGame sb) {
		Input i = container.getInput();

		i.removeKeyListener(keyListener);
		i.removeMouseListener(mouseListener);

	}


	/**
	 * KeyListener
	 * @param sb estado
	 * @return
	 */
	private KeyListener initKeyListener(final StateBasedGame sb) {
		KeyListener kl = new KeyListener() {
			@Override
			public void inputEnded() {
			}

			@Override
			public boolean isAcceptingInput() {
				return true;
			}

			@Override
			public void setInput(Input arg0) {
			}

			@Override
			public void keyPressed(int arg0, char arg1) {
			}

			@Override
			public void keyReleased(int arg0, char arg1) {
				if(isFirstTime){
					sb.enterState(GameState.MAIN_MENU_STATE.ordinal(),
							new FadeOutTransition(), new FadeInTransition());
					isFirstTime = false;
				}
			}

			@Override
			public void inputStarted() {

			}
		};

		return kl;
	}

	/**
	 * MouseListener
	 * @param sb estado
	 * @return
	 */
	private MouseListener initMouserListener(final StateBasedGame sb) {
		return new MouseListener() {

			@Override
			public void setInput(Input arg0) {
			}

			@Override
			public boolean isAcceptingInput() {
				return true;
			}

			@Override
			public void inputStarted() {

			}

			@Override
			public void inputEnded() {

			}

			@Override
			public void mouseWheelMoved(int arg0) {

			}

			@Override
			public void mouseReleased(int arg0, int arg1, int arg2) {
				if(isFirstTime){
					sb.enterState(GameState.MAIN_MENU_STATE.ordinal(),
							new FadeOutTransition(), new FadeInTransition());
					isFirstTime = false;
				}
			}

			@Override
			public void mousePressed(int arg0, int arg1, int arg2) {
			}

			@Override
			public void mouseMoved(int arg0, int arg1, int arg2, int arg3) {

			}

			@Override
			public void mouseDragged(int arg0, int arg1, int arg2, int arg3) {

			}

			@Override
			public void mouseClicked(int arg0, int arg1, int arg2, int arg3) {

			}
		};
	}
}
