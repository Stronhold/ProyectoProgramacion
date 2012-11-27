package es.deusto.eside.programacion3.luffysurvival.states;

import java.io.IOException;
import java.net.URL;
import java.util.Map;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.GL11;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.tiled.TiledMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import TWLSlick.TWLInputAdapter;
import de.matthiasmann.twl.Button;
import de.matthiasmann.twl.ColumnLayout;
import de.matthiasmann.twl.Container;
import de.matthiasmann.twl.GUI;
import de.matthiasmann.twl.ScrollPane;
import de.matthiasmann.twl.Widget;
import de.matthiasmann.twl.renderer.lwjgl.LWJGLRenderer;
import de.matthiasmann.twl.theme.ThemeManager;
import es.deusto.eside.programacion3.luffysurvival.LuffySurvival;
import es.deusto.eside.programacion3.luffysurvival.engine.ContainerList;
import es.deusto.eside.programacion3.luffysurvival.engine.Icon;
import es.deusto.eside.programacion3.luffysurvival.engine.IconClickListener;

/**
 * Estado de inicio de partida.
 * 
 * @author sergio
 * 
 */
public class GamePlayState extends BasicGameState {

	private static final String MAP_LOCATION = "resources/maps/level/1/mapa.tmx";

	private final Logger logger = LoggerFactory.getLogger(GamePlayState.class);

	private int stateId;
	private TiledMap map;
	/**
	 * Widget padre
	 */
	private Widget root;

	/**
	 * LWGLRenderer contexto de renderizado
	 */
	private LWJGLRenderer lwjglRenderer;
	/**
	 * theme: tema usado para la escritura
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

	private ContainerList list;

	private ScrollPane actionsContainer;

	public GamePlayState(int ordinal) {
		this.stateId = ordinal;
	}

	@Override
	public void init(GameContainer gc, StateBasedGame sb) throws SlickException {
		this.map = new TiledMap(MAP_LOCATION);
		initGUI(gc, sb);
		
		initPlayerSelector();
	}
	
	private void initPlayerSelector(){
		list = new ContainerList();
		list.setX(0);
		list.setY(0);
		Icon luffyIcon = new Icon("resources/theme/listContainer/luffyFace.png");
		luffyIcon.addIconClickListener(new IconClickListener() {

			@Override
			public void onClick() {
				logger.error("Luffy");
			}
		});
		list.addIcon(luffyIcon);
		Icon namiIcon = new Icon("resources/theme/listContainer/namiFace.png");
		namiIcon.addIconClickListener(new IconClickListener() {

			@Override
			public void onClick() {
				logger.error("Nami");
			}
		});
		list.addIcon(namiIcon);

		Icon zoroIcon = new Icon("resources/theme/listContainer/zoroFace.png");
		zoroIcon.addIconClickListener(new IconClickListener() {

			@Override
			public void onClick() {
				logger.error("Zoro");
			}
		});
		list.addIcon(zoroIcon);
		
		Icon usoppIcon = new Icon("resources/theme/listContainer/usoppFace.png");
		usoppIcon.addIconClickListener(new IconClickListener() {

			@Override
			public void onClick() {
				logger.error("Usopp");
			}
		});
		list.addIcon(usoppIcon);
		
		Icon sanjiIcon = new Icon("resources/theme/listContainer/sanjiFace.png");
		sanjiIcon.addIconClickListener(new IconClickListener() {

			@Override
			public void onClick() {
				logger.error("sanji");
			}
		});
		list.addIcon(sanjiIcon);
		
		Icon chopperIcon = new Icon("resources/theme/listContainer/chopperFace.png");
		chopperIcon.addIconClickListener(new IconClickListener() {

			@Override
			public void onClick() {
				logger.error("Chopper");
			}
		});
		list.addIcon(chopperIcon);
		
		Icon robinIcon = new Icon("resources/theme/listContainer/nicoRobbinFace.png");
		robinIcon.addIconClickListener(new IconClickListener() {

			@Override
			public void onClick() {
				logger.error("Robbin");
			}
		});
		list.addIcon(robinIcon);
		
		Icon frankyIcon = new Icon("resources/theme/listContainer/frankyFace.png");
		frankyIcon.addIconClickListener(new IconClickListener() {

			@Override
			public void onClick() {
				logger.error("Franky");
			}
		});
		list.addIcon(frankyIcon);
		
		Icon brookIcon = new Icon("resources/theme/listContainer/brookFace.png");
		brookIcon.addIconClickListener(new IconClickListener() {

			@Override
			public void onClick() {
				logger.error("Brook");
			}
		});
		list.addIcon(brookIcon);
	}

	private void initGUI(final GameContainer gc, final StateBasedGame sb) {
		initTWL(gc);

		twlInputAdapter = new TWLInputAdapter(gui, gc.getInput());
		twlInputAdapter.getGui().getRootPane()
				.setSize(LuffySurvival.WIDTH, LuffySurvival.HEIGHT);

		Button b = new Button();
		b.setPosition(0, 0);
		b.setText("Mierda");
		b.adjustSize();
		Widget content = new Widget();
		content.add(b);
		content.adjustSize();
		// twlInputAdapter.getGui().getRootPane().add(b);
		ColumnLayout cl = new ColumnLayout();
		this.actionsContainer = new ScrollPane(content);
		actionsContainer.setTheme("scrollpane");
		actionsContainer.setPosition(0, 0);
		cl.setSize(100, 200);
		actionsContainer.setSize(600, 400);
		actionsContainer.adjustSize();
		actionsContainer.setClip(true);
		twlInputAdapter.getGui().getRootPane().add(actionsContainer);
		b.adjustSize();
		actionsContainer.adjustSize();

	}

	/**
	 * Inicializa el tema de escritura
	 * 
	 * @param gc
	 *            : ventana del juego
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

	@Override
	public void render(GameContainer gc, StateBasedGame ab, Graphics g)
			throws SlickException {
		this.map.render(0, 0);
		list.draw();
		twlInputAdapter.render();

	}

	@Override
	public void update(GameContainer gc, StateBasedGame sb, int arg2)
			throws SlickException {
		Input input = gc.getInput();

		int mouseX = input.getMouseX();
		int mouseY = input.getMouseY();
		if (input.isMousePressed(Input.MOUSE_LEFT_BUTTON)) {
			list.update(mouseX, mouseY);
		}
	}

	@Override
	public int getID() {
		return this.stateId;
	}

}
