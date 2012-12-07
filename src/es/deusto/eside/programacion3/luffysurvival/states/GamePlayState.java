package es.deusto.eside.programacion3.luffysurvival.states;


import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.GL11;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Polygon;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.tiled.TiledMap;

import TWLSlick.TWLInputAdapter;
import de.matthiasmann.twl.ColumnLayout;
import de.matthiasmann.twl.GUI;
import de.matthiasmann.twl.ScrollPane;
import de.matthiasmann.twl.Widget;
import de.matthiasmann.twl.renderer.lwjgl.LWJGLRenderer;
import de.matthiasmann.twl.theme.ThemeManager;
import es.deusto.eside.programacion3.luffysurvival.LuffySurvival;
import es.deusto.eside.programacion3.luffysurvival.engine.ContainerList;
import es.deusto.eside.programacion3.luffysurvival.engine.Icon;
import es.deusto.eside.programacion3.luffysurvival.engine.IconClickListener;
import es.deusto.eside.programacion3.luffysurvival.model.BasicCharacter;
import es.deusto.eside.programacion3.luffysurvival.model.Entity;
import es.deusto.eside.programacion3.luffysurvival.model.MainCharacter;

/**
 * Estado de inicio de partida.
 * 
 * @author sergio
 * 
 */
public class GamePlayState extends BasicGameState {

	/**
	 * Direccion del mapa del nivel 1
	 */
	private static final String MAP_LOCATION = "resources/maps/level/1/mapa.tmx";

	/**
	 * Numero de filas 
	 */
	private static final int ROWS = 3;
	/**
	 * Numero de columnas
	 */
	private static final int COLUMNS = 6;
	/**
	 * Numero del estado del juego
	 */
	private int stateId;
	
	private List<MainCharacter> enemyList;
	
	private int money = 1000;
	/**
	 * Mapa del nivel 1
	 */
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
	/**
	 * Contenedor de los personajes
	 */
	private ContainerList list;
	/**
	 * Contenedor de los personajes
	 */
	private ScrollPane actionsContainer;
	/**
	 * Indica si un personaje se puede añadir
	 */
	private boolean playerAdd;
	/**
	 * Color de si un espacio está ocupado
	 */
	private Color PLACE_OCCUPIED = new Color(255, 0, 0, 120);
	/**
	 * Color de un espacio libre
	 */
	private Color PLACE_FREE = new Color(0, 255, 255, 120);
	/**
	 * Indica los espacios ya ocupados
	 */
	private Entity [][] entities;
	/**
	 * rectangulos del suelo
	 */
	private List<Polygon> polygonList;
	/**
	 * Personaje seleccionado para colocar
	 */
	private  MainCharacter selectedPlayableCharacter;
	/**
	 * Contiene los personajes que no se han colocado
	 */
	private HashMap<String, MainCharacter> charactersPlaced;

	private Icon delete;
	
	/**
	 * Constructor
	 * @param ordinal indica el estado en el que se encuentra
	 */
	public GamePlayState(int ordinal) {
		this.stateId = ordinal;
	}

	private void initEnemies(){
		enemyList = new ArrayList<MainCharacter>();
		enemyList.add(new MainCharacter("resources/sprites/Marine/marine.txt", "Marine"));
	}
	
	@Override
	public void init(GameContainer gc, StateBasedGame sb) throws SlickException {
		this.map = new TiledMap(MAP_LOCATION);
	//	initGUI(gc, sb);
		initPlayerSelector();
		initAreaCharacter();
		initPlayableCharacters();
		initEnemies();
		entities = new Entity[3][6];
		for (int i = 0; i < ROWS; i++)
			for (int j = 0; j < COLUMNS; j++)
				entities[i][j] = null;
	}
	
	/**
	 * Inicializa los personajes del juego
	 */
	private void initPlayableCharacters(){
		charactersPlaced = new HashMap<String, MainCharacter>();
		charactersPlaced.put("Luffy", new MainCharacter("resources/sprites/Luffy/luffy.txt" , "Luffy"));
		charactersPlaced.put("Zoro", new MainCharacter("resources/sprites/Zoro/Zoro.txt", "Zoro"));
		charactersPlaced.put("Nami", new MainCharacter("resources/sprites/Nami/Nami.txt", "Nami"));
		charactersPlaced.put("Usopp", new MainCharacter("resources/sprites/Usopp/Usopp.txt" , "Usopp"));
		charactersPlaced.put("Sanji", new MainCharacter("resources/sprites/Sanji/Sanji.txt", "Sanji"));
		charactersPlaced.put("Chopper", new MainCharacter("resources/sprites/Chopper/Chopper.txt", "Chopper"));
		charactersPlaced.put("Robin", new MainCharacter("resources/sprites/NicoRobin/Nico.txt", "Robin"));
		charactersPlaced.put("Franky", new MainCharacter("resources/sprites/Brook/Brook.txt", "Brook"));
		charactersPlaced.put("Brook", new MainCharacter("resources/sprites/Franky/Franky.txt", "Franky"));
	}
	

	/**
	 * Inicializa la selección de personajes
	 */
	private void initPlayerSelector(){
		list = new ContainerList();
		list.setX(0);
		list.setY(0);
		Icon luffyIcon = new Icon("resources/theme/listContainer/luffyFace.png");
		luffyIcon.addIconClickListener(new IconClickListener() {

			@Override
			public void onClick() {
				if (!playerAdd){
					MainCharacter temp = charactersPlaced.remove("Luffy");
					if (temp != null) {
						selectedPlayableCharacter = temp;
						playerAdd = true;
					}
				}
			}
		});
		list.addIcon(luffyIcon);
		Icon namiIcon = new Icon("resources/theme/listContainer/namiFace.png");
		namiIcon.addIconClickListener(new IconClickListener() {

			@Override
			public void onClick() {
				if (!playerAdd){
					MainCharacter temp= charactersPlaced.remove("Nami");
					if (temp != null) {
						selectedPlayableCharacter = temp;
						playerAdd = true;
					}
				}
			}
		});
		list.addIcon(namiIcon);

		Icon zoroIcon = new Icon("resources/theme/listContainer/zoroFace.png");
		zoroIcon.addIconClickListener(new IconClickListener() {

			@Override
			public void onClick() {
				if(!playerAdd){
					MainCharacter temp = charactersPlaced.remove("Zoro");
					if (temp != null) {
						selectedPlayableCharacter = temp;
						playerAdd = true;
					}
				}
			}
			});
		list.addIcon(zoroIcon);
		
		Icon usoppIcon = new Icon("resources/theme/listContainer/usoppFace.png");
		usoppIcon.addIconClickListener(new IconClickListener() {

			@Override
			public void onClick() {
				if(!playerAdd){
					MainCharacter temp = charactersPlaced.remove("Usopp");
					if (temp != null) {
						selectedPlayableCharacter = temp;
						playerAdd = true;
				}
			}
		}
		});
		list.addIcon(usoppIcon);
		
		Icon sanjiIcon = new Icon("resources/theme/listContainer/sanjiFace.png");
		sanjiIcon.addIconClickListener(new IconClickListener() {

			@Override
			public void onClick() {
				if(!playerAdd){
					MainCharacter temp = charactersPlaced.remove("Sanji");
					if (temp != null) {
						selectedPlayableCharacter = temp;
						playerAdd = true;
					}
				}
			}
		});
		list.addIcon(sanjiIcon);
		
		Icon chopperIcon = new Icon("resources/theme/listContainer/chopperFace.png");
		chopperIcon.addIconClickListener(new IconClickListener() {

			@Override
			public void onClick() {
					if(!playerAdd){
						MainCharacter temp = charactersPlaced.remove("Chopper");
						if (temp != null) {
							selectedPlayableCharacter = temp;
							playerAdd = true;
						}
					}
			}
		});
		list.addIcon(chopperIcon);
		
		Icon robinIcon = new Icon("resources/theme/listContainer/nicoRobbinFace.png");
		robinIcon.addIconClickListener(new IconClickListener() {

			@Override
			public void onClick() {
				if(!playerAdd){
					MainCharacter temp = charactersPlaced.remove("Robin");
					if (temp != null) {
						selectedPlayableCharacter = temp;
						playerAdd = true;
					}
				}
			}
		});
		list.addIcon(robinIcon);
		
		Icon frankyIcon = new Icon("resources/theme/listContainer/frankyFace.png");
		frankyIcon.addIconClickListener(new IconClickListener() {

			@Override
			public void onClick() {
				if(!playerAdd){
					MainCharacter temp = charactersPlaced.remove("Brook");
					if (temp != null) {
						selectedPlayableCharacter = temp;
						playerAdd = true;
					}
				}
			}
		});
		list.addIcon(frankyIcon);
		
		Icon brookIcon = new Icon("resources/theme/listContainer/brookFace.png");
		brookIcon.addIconClickListener(new IconClickListener() {

			@Override
			public void onClick() {
				if (!playerAdd){
					MainCharacter temp = charactersPlaced.remove("Franky");
					if (temp != null) {
						selectedPlayableCharacter = temp;
						playerAdd = true;
					}
				}
			}
		});
		list.addIcon(brookIcon);
	}

	/**
	 * Inicializa el contenedor 
	 * @param gc ventana
	 * @param sb estado del juego
	 */
	private void initGUI(final GameContainer gc, final StateBasedGame sb) {
		initTWL(gc);

		twlInputAdapter = new TWLInputAdapter(gui, gc.getInput());
		twlInputAdapter.getGui().getRootPane()
				.setSize(LuffySurvival.WIDTH, LuffySurvival.HEIGHT);
		Widget content = new Widget();
		ColumnLayout cl = new ColumnLayout();
		this.actionsContainer = new ScrollPane(content);
		actionsContainer.setTheme("scrollpane");
		actionsContainer.setPosition(0, 0);
		cl.setSize(100, 200);
		actionsContainer.setSize(600, 400);
		actionsContainer.adjustSize();
		actionsContainer.setClip(true);
		twlInputAdapter.getGui().getRootPane().add(actionsContainer);
		actionsContainer.adjustSize();

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

	@Override
	public void render(GameContainer gc, StateBasedGame ab, Graphics g)
			throws SlickException {
		this.map.render(0, 0);
		list.draw();
		//twlInputAdapter.render();
		if (playerAdd) {
			drawAreaCharacter(g);			 
		}
		
		for (int i = 0; i < entities.length; i++) {
			for (int j =0; j < entities[i].length; j++){
				if (entities[i][j] != null) {
					entities[i][j].draw();
				}
			}			
		}
	}

	/**
	 * Dibuja los espacios del juego
	 * @param g contexto gráfico
	 */
	private void drawAreaCharacter(Graphics g) {
		int counter = 0;
		for (Polygon p : polygonList) {
			if (this.entities[counter / 2] [counter % 2] != null){
				g.setColor(PLACE_OCCUPIED);
			} else {
				g.setColor(PLACE_FREE);
			}
			g.fill(p);

			g.draw(p);
			counter++;
		}
		
	}

	/**
	 * Inicializa los areas donde se desarrolla el juego
	 */
	public void initAreaCharacter(){
		polygonList = new ArrayList<Polygon>();
		for (int i = 0; i < 2; i++) {
			Polygon a = new Polygon();
			a.addPoint(135 + i *90, 180);
			a.addPoint(135 + i *90 +90, 180);
			a.addPoint(90 + i *90 +90, 180+90);
			a.addPoint(90 + i *90, 180 +90);
			polygonList.add(a);
		}
		for (int i = 0; i < 2; i++) {
			Polygon a = new Polygon();
			a.addPoint(90 + i *90, 180+90);
			a.addPoint(90 + i *90 +90, 180+90);
			a.addPoint(45 + i *90 +90, 180+90*2);
			a.addPoint(45 + i *90, 180 +90*2);
			polygonList.add(a);
		}
		
		for (int i = 0; i < 2; i++) {
			Polygon a = new Polygon();
			a.addPoint(45 + i *90, 180+90*2);
			a.addPoint(45 + i *90 +90, 180+90*2);
			a.addPoint(0 + i *90 +90, 180*2+90);
			a.addPoint(0 + i *90, 180*2 +90);
			polygonList.add(a);
		}
	}
	

	@Override
	public void update(GameContainer gc, StateBasedGame sb, int delta)
			throws SlickException {
		Input input = gc.getInput();
		enterInput(input);
		int mouseX = input.getMouseX();
		int mouseY = input.getMouseY();
		boolean leftButtonPressed = input.isMousePressed(Input.MOUSE_LEFT_BUTTON);
		if (leftButtonPressed) {
			list.update(mouseX, mouseY);
		
		}
		
		if (this.playerAdd) {
			int counter = 0;
			for (Polygon p : polygonList) {
				
				if (p.contains(mouseX, mouseY) && leftButtonPressed 
						
						&& entities[counter/2][counter % 2] == null)  {
				
					entities[counter/2][counter%2] = this.selectedPlayableCharacter;
					setPlayableCharacterPosition(this.selectedPlayableCharacter, counter/2, counter%2);
					this.selectedPlayableCharacter = null;
					this.playerAdd = false;
					return;
				}
				counter++;
			}
			
		}  else {
			for (int i = 0; i < entities.length; i++) {
				for (int j =0; j < entities[i].length; j++){
					if (entities[i][j] != null) {
						entities[i][j].update(leftButtonPressed, mouseX, mouseY, delta);
					}
				}			
			}
		}
	}
	
	/**
	 * Coloca el personaje
	 * @param selectedPlayableCharacter2 personaje elegido para colocar
	 * @param i posicion
	 * @param j posicion
	 */
	private void setPlayableCharacterPosition(
			BasicCharacter selectedPlayableCharacter2, int i, int j) {
		if (i ==0) {
			selectedPlayableCharacter2.setX(90 + j * 90);
			selectedPlayableCharacter2.setY(180 + 90 - selectedPlayableCharacter2.getHeight());

		} else if (i == 1){
			selectedPlayableCharacter2.setX(45 + j *90);
			selectedPlayableCharacter2.setY(180 + 90 *2 - selectedPlayableCharacter2.getHeight());

		} else if (i == 2) {
			selectedPlayableCharacter2.setX(0 + j *90);
			selectedPlayableCharacter2.setY(180 + 90 * 3 - selectedPlayableCharacter2.getHeight());
		}
				
	}
	
	private void enterInput(Input i){
		
	}

	@Override
	public int getID() {
		return this.stateId;
	}

}
