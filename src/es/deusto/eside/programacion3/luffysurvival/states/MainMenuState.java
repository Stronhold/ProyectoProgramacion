package es.deusto.eside.programacion3.luffysurvival.states;

import java.io.IOException;
import java.net.URL;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.GL11;
import org.newdawn.slick.Animation;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.FadeInTransition;

import de.matthiasmann.twl.Button;
import de.matthiasmann.twl.GUI;
import de.matthiasmann.twl.Widget;
import de.matthiasmann.twl.renderer.lwjgl.LWJGLRenderer;
import de.matthiasmann.twl.theme.ThemeManager;

import TWLSlick.BasicTWLGameState;
import TWLSlick.TWLInputAdapter;

import es.deusto.eside.programacion3.luffysurvival.LuffySurvival;
import es.deusto.eside.programacion3.luffysurvival.engine.FadeOutAnimation;
import es.deusto.eside.programacion3.luffysurvival.engine.Light;

public class MainMenuState extends BasicGameState {

	private int stateID;

	private Image background;
	private FadeOutAnimation mainCharacters;

	private Widget root;

	private LWJGLRenderer lwjglRenderer;

	private Object theme;

	private GUI gui;

	private TWLInputAdapter twlInputAdapter;

	private static final int DURATION = 4000;





	@Override
	public void init(GameContainer gameContainer, StateBasedGame arg1)
			throws SlickException {

		initGUI(gameContainer);
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

		// connect input
		twlInputAdapter = new TWLInputAdapter(gui, gc.getInput());
		gc.getInput().addPrimaryListener(twlInputAdapter);
		Button button = new Button();
		button.setText("Play");
		button.setTheme("button");
		button.setPosition(100, 100);

		twlInputAdapter.getGui().getRootPane()
				.setSize(LuffySurvival.WIDTH, LuffySurvival.HEIGHT);
		twlInputAdapter.getGui().getRootPane().add(button);
		button.adjustSize();

	}

	public MainMenuState(int stateID) {
		this.stateID = stateID;
	}

	@Override
	public void render(GameContainer container, StateBasedGame arg1, Graphics g)
			throws SlickException {
		g.drawImage(this.background, 0, 0);
		
		this.mainCharacters.draw(LuffySurvival.WIDTH
				- this.mainCharacters.getCurrentFrame().getWidth(), 0);
		twlInputAdapter.render();
	}

	@Override
	public void update(GameContainer arg0, StateBasedGame arg1, int delta)
			throws SlickException {

	}

	@Override
	public int getID() {
		return this.stateID;
	}

}
