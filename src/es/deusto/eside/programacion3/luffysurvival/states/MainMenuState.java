package es.deusto.eside.programacion3.luffysurvival.states;

import java.io.IOException;
import java.net.URL;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.GL11;
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

public class MainMenuState extends BasicGameState {

	private static final long MILLISECONDS_PER_DAY = 86400;
	private int stateID;
	private Widget root;

	private LWJGLRenderer lwjglRenderer;

	private Object theme;

	private GUI gui;
	private TWLInputAdapter twlInputAdapter;

	private Button buttonInitGame;
	private Button buttonOption;
	private Button buttonCredits;

	public MainMenuState(int id) {
		this.stateID = id;
	}

	@Override
	public void enter(final GameContainer gameContainer, final StateBasedGame sb) {
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
		Image background = new Image(getBackground(MILLISECONDS_PER_DAY));
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

	private void initGUI(final GameContainer gc, final StateBasedGame sb) {
		initTWL(gc);
		twlInputAdapter = new TWLInputAdapter(gui, gc.getInput());
		
		createButtons();
		initButtonsEvent(sb);
		loadBackground();
	}

	private void  loadBackground() {
		final String background = getBackground(System.currentTimeMillis() / 1000);
	}

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

	private void initButtonsEvent(final StateBasedGame sb) {
		buttonInitGame.addCallback(new Runnable() {	
			@Override
			public void run() {
				sb.enterState(es.deusto.eside.programacion3.luffysurvival.states.GameState.GAME_PLAY_STATE.ordinal());
			}
		});
	}

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