package es.deusto.eside.programacion3.luffysurvival.states;

import java.io.IOException;
import java.net.URL;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.GL11;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.MouseListener;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.GameState;
import org.newdawn.slick.state.StateBasedGame;

import TWLSlick.TWLInputAdapter;
import de.matthiasmann.twl.Button;
import de.matthiasmann.twl.GUI;
import de.matthiasmann.twl.Widget;
import de.matthiasmann.twl.renderer.lwjgl.LWJGLRenderer;
import de.matthiasmann.twl.theme.ThemeManager;
import es.deusto.eside.programacion3.luffysurvival.LuffySurvival;

public class MainMenuState extends BasicGameState {

	private int stateID;
	private Widget root;

	private LWJGLRenderer lwjglRenderer;

	private Object theme;

	private GUI gui;
	private TWLInputAdapter twlInputAdapter;

	public MainMenuState(int id) {
		this.stateID = id;
	}

	@Override
	public void enter(final GameContainer gameContainer, final StateBasedGame sb) {
		// connect input
		gameContainer.getInput().addPrimaryListener(twlInputAdapter);

	}

	@Override
	public void init(GameContainer gameContainer, StateBasedGame sb)
			throws SlickException {
		initGUI(gameContainer);

	}

	@Override
	public void render(GameContainer arg0, StateBasedGame arg1, Graphics arg2)
			throws SlickException {
		twlInputAdapter.render();

	}

	@Override
	public void update(GameContainer arg0, StateBasedGame arg1, int arg2)
			throws SlickException {
		// TODO Auto-generated method stub

	}

	@Override
	public int getID() {
		return this.stateID;
	}

	private void initGUI(GameContainer gc) {
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

		twlInputAdapter = new TWLInputAdapter(gui, gc.getInput());

		Button button = new Button();
		button.setText("Play");
		button.setTheme("button");
		button.setPosition(100, 100);

		twlInputAdapter.getGui().getRootPane()
				.setSize(LuffySurvival.WIDTH, LuffySurvival.HEIGHT);
		twlInputAdapter.getGui().getRootPane().add(button);
		button.adjustSize();

	}

}
