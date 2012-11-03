package es.deusto.eside.programacion3.luffysurvival.states;

import org.lwjgl.opengl.GL11;
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
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.deusto.eside.programacion3.luffysurvival.LuffySurvival;
import es.deusto.eside.programacion3.luffysurvival.engine.FadeOutAnimation;

public class PreMainMenuState extends BasicGameState {

	private static final int DURATION = 4000;
	private static final String fontPath = "resources/fonts/OnePiece.ttf";
	
	private int stateID;

	private Image background;
	private FadeOutAnimation mainCharacters;

	private MouseListener mouseListener;
	private KeyListener keyListener;


	private UnicodeFont fpsFont;
	
	private final Logger logger = LoggerFactory.getLogger(OpeningState.class);

	@Override
	public void enter (final GameContainer container, final StateBasedGame sb) {
		Input i = container.getInput();

		keyListener = initKeyListener(sb);
		i.addKeyListener(keyListener);
		mouseListener = initMouserListener(sb);
		i.addMouseListener(mouseListener);
	}
	



	@Override
	public void init(GameContainer gameContainer, StateBasedGame sb)
			throws SlickException {
		initPreMenu();	
	}

	private void initPreMenu() {
		try {
			this.background = new Image("resources/image/menu/background.png");
			this.mainCharacters = new FadeOutAnimation();
			this.mainCharacters.addFrame(
					new Image("resources/image/menu/luffy.png"), DURATION);
			this.mainCharacters.addFrame(
					new Image("resources/image/menu/zoro.png"), DURATION);
			this.mainCharacters.addFrame(
					new Image("resources/image/menu/nami.png"), DURATION);
			this.mainCharacters.addFrame(
					new Image("resources/image/menu/usopp.png"), DURATION);
			this.mainCharacters.addFrame(
					new Image("resources/image/menu/sanji.png"), DURATION);
			this.mainCharacters.addFrame(new Image(
					"resources/image/menu/chopper.png"), DURATION);
			this.mainCharacters.addFrame(
					new Image("resources/image/menu/robin.png"), DURATION);
			this.mainCharacters.addFrame(new Image(
					"resources/image/menu/franky.png"), DURATION);
			this.mainCharacters.addFrame(
					new Image("resources/image/menu/brook.png"), DURATION);
			fpsFont = new UnicodeFont(fontPath, 25, true, false);
			fpsFont.addAsciiGlyphs();
			fpsFont.addGlyphs(400, 600);
			fpsFont.getEffects().add(new ColorEffect(java.awt.Color.WHITE));
			fpsFont.loadGlyphs();
		} catch (SlickException e) {
			e.printStackTrace();
			this.logger.error("Exception in PreMainMenuState " );
			e.printStackTrace();
		}	
		
		GL11.glPopAttrib();

	}



	public PreMainMenuState(int stateID) {
		this.stateID = stateID;
	}

	@Override
	public void render(GameContainer container, StateBasedGame arg1, Graphics g)
			throws SlickException {
		g.drawImage(this.background, 0, 0);
		
		this.mainCharacters.draw(LuffySurvival.WIDTH
				- this.mainCharacters.getCurrentFrame().getWidth(), 0);
		fpsFont.drawString(280.0F, 300.0F, "LØL", Color.red);
	}

	@Override
	public void update(GameContainer gameContainer, StateBasedGame sb, int delta)
			throws SlickException {

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
		
		logger.error("eliminado");
	}

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
				sb.enterState(GameState.MAIN_MENU_STATE.ordinal(),  new FadeOutTransition(), new FadeInTransition());

			}

			@Override
			public void inputStarted() {

			}
		};

		return kl;
	}
	
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
				sb.enterState(GameState.MAIN_MENU_STATE.ordinal(),  new FadeOutTransition(), new FadeInTransition());
				logger.error("Cambiando al menu principal");

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
