package es.deusto.eside.programacion3.luffysurvival.states;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.Random;
import java.util.Set;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.GL11;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Polygon;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.tiled.TiledMap;
import org.newdawn.slick.util.Log;

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
import es.deusto.eside.programacion3.luffysurvival.engine.ImageClickListener;
import es.deusto.eside.programacion3.luffysurvival.model.BasicCharacter;
import es.deusto.eside.programacion3.luffysurvival.model.BasicEnemy;
import es.deusto.eside.programacion3.luffysurvival.model.Entity;
import es.deusto.eside.programacion3.luffysurvival.model.MainCharacter;

/**
 * Estado de inicio de partida.
 * 
 * @author sergio
 * 
 */
public class GamePlayState extends BasicGameState {

	private static final int ENEMY_PER_SECOND = 5000;

	/**
	 * Direccion del mapa del nivel 1
	 */
	private static final String MAP_LOCATION = "resources/maps/level/1/mapa1.tmx";

	/**
	 * Numero de filas
	 */
	private static final int ROWS = 3;
	/**
	 * Numero de columnas
	 */
	private static final int COLUMNS = 6;

	private static final float CENTER = 176;
	/**
	 * Numero del estado del juego
	 */
	private int stateId;

	/**
	 * Dinero
	 */
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
	private Entity[][] entities;

	private boolean[][] moveEnable;

	/**
	 * rectangulos del suelo
	 */
	private List<Polygon> polygonList;

	/**
	 * Personaje seleccionado para colocar
	 */
	private MainCharacter selectedPlayableCharacter;

	/**
	 * Contiene los personajes que no se han colocado
	 */
	private HashMap<String, MainCharacter> charactersPlaced;

	private ArrayList<BasicEnemy> enemi;

	private int lastMovement;

	private int time = 0;
	
	private float y=0;

	/**
	 * Constructor
	 * 
	 * @param ordinal
	 *            indica el estado en el que se encuentra
	 */
	public GamePlayState(int ordinal) {
		this.stateId = ordinal;
	}

	public int lastEnemy;

	private boolean isGameOver;

	private Image gameOver;
	
	private int timeGameOverMovement;
	
	 private Rectangle gameOverRect;
	 
	 private float colorTrans = 0;



	@Override
	public void init(GameContainer gc, StateBasedGame sb) throws SlickException {
		timeGameOverMovement = 0;
		y = 0;
		lastEnemy = ENEMY_PER_SECOND;
		lastMovement = 0;
		this.map = new TiledMap(MAP_LOCATION);
		enemi = new ArrayList<BasicEnemy>();
		// initGUI(gc, sb);
		initPlayerSelector();
		initAreaCharacter();
		initPlayableCharacters();
		entities = new Entity[3][7];
		moveEnable = new boolean[3][7];
		for (int i = 0; i < ROWS; i++)
			for (int j = 0; j < COLUMNS; j++) {
				entities[i][j] = null;
				moveEnable[i][j] = true;
			}
		gameOver = new Image("resources/image/gameover.png",new Color(4,4,4));
		gameOverRect = new Rectangle(0, 0, gc.getWidth(), gc.getHeight());
	}
	
	@Override
	public void enter(final GameContainer container, final StateBasedGame sb) {
		isGameOver = false;
	}

	/**
	 * Inicializa los personajes del juego
	 */
	private void initPlayableCharacters() {
		charactersPlaced = new HashMap<String, MainCharacter>();
		charactersPlaced.put("Luffy", new MainCharacter(
				"resources/sprites/Luffy/luffy.txt", "Luffy"));
		charactersPlaced.put("Zoro", new MainCharacter(
				"resources/sprites/Zoro/Zoro.txt", "Zoro"));
		charactersPlaced.put("Nami", new MainCharacter(
				"resources/sprites/Nami/Nami.txt", "Nami"));
		charactersPlaced.put("Usopp", new MainCharacter(
				"resources/sprites/Usopp/Usopp.txt", "Usopp"));
		charactersPlaced.put("Sanji", new MainCharacter(
				"resources/sprites/Sanji/Sanji.txt", "Sanji"));
		charactersPlaced.put("Chopper", new MainCharacter(
				"resources/sprites/Chopper/Chopper.txt", "Chopper"));
		charactersPlaced.put("Robin", new MainCharacter(
				"resources/sprites/NicoRobin/Nico.txt", "Robin"));
		charactersPlaced.put("Franky", new MainCharacter(
				"resources/sprites/Franky/Franky.txt", "Franky"));
		charactersPlaced.put("Brook", new MainCharacter(
				"resources/sprites/Brook/Brook.txt", "Brook"));
		addEventsPlayableCharacters();
	}

	private void addEventsPlayableCharacters() {
		Set<Entry<String, MainCharacter>> keyValue = charactersPlaced
				.entrySet();
		for (Entry<String, MainCharacter> e : keyValue) {

			e.getValue().addImageClickListenerAttack(new ImageClickListener() {

				@Override
				public void onClick(Object sender) {

					MainCharacter temp = (MainCharacter) sender;
					if (temp.isFinalAttackReady()
							&& money >= temp.getCostSuper()) {
						money = money - temp.getCostSuper();
						temp.setCurrent(temp.getFinalAttackAnimation());
						temp.setContextMenu(false);
						temp.setFinalAttackReady(false);
						Log.error("Queda: " + money + " doblones");
						int i = 0;
						int j = 0;
						boolean found = false;
						while (i < entities.length && !found) {
							j = 0;
							while (j < 2 && !found) {
								if (temp.equals((MainCharacter) entities[i][j])) {
									found = true;
								} else {
									j++;
								}
							}
							if (!found) {
								i++;
							}
						}
						if (i < entities.length) {
							if (j + 1 < entities[i].length
									&& entities[i][j + 1] != null
									&& entities[i][j + 1] instanceof BasicEnemy) {
								BasicEnemy temp1 = (BasicEnemy) entities[i][j + 1];
								temp1.setLife(temp1.getLife()
										- temp.getFinalDamage());
								if (temp1.getLife() <= 0) {
									temp1 = null;
								}

							}
							if ((j + 2 < entities[i].length)
									&& entities[i][j + 2] != null
									&& entities[i][j + 2] instanceof BasicEnemy) {
								BasicEnemy temp1 = (BasicEnemy) entities[i][j + 2];
								temp1.setLife(temp1.getLife()
										- temp.getFinalDamage());
								if (temp1.getLife() <= 0) {
									temp1 = null;
								}
							}
						}
					}

				}
			});

			e.getValue().addImageClickListenerDelete(new ImageClickListener() {

				@Override
				public void onClick(Object sender) {
					Log.error("Personaje borrado");
					MainCharacter temp = (MainCharacter) sender;
					charactersPlaced.put(temp.getName(), temp);
					temp.getX();
					temp.getY();
					for (int i = 0; i < entities.length; i++) {
						for (int j = 0; j < entities[i].length; j++) {
							if (entities[i][j] == temp) {
								entities[i][j] = null;
								return;
							}
						}
					}
				}
			});
		}
	}

	/**
	 * Inicializa la selección de personajes
	 */
	private void initPlayerSelector() {
		// TO-DO: añadir en este método la modificación de dinero
		list = new ContainerList();
		list.setX(0);
		list.setY(0);
		Icon luffyIcon = new Icon("resources/theme/listContainer/luffyFace.png");
		luffyIcon.addIconClickListener(new IconClickListener() {

			@Override
			public void onClick() {
				if (!playerAdd) {
					MainCharacter temp = charactersPlaced.get("Luffy");
					if (temp != null && money >= temp.getCost()) {
						money = money - temp.getCost();
						charactersPlaced.remove("Luffy");
						if (temp != null) {
							selectedPlayableCharacter = temp;
							playerAdd = true;
							Log.error("Personaje creado, nos queda este dinero: "
									+ money);
						}
					} else {
						Log.error("No hay dinero suficiente para el personaje");
					}
				}
			}
		});
		list.addIcon(luffyIcon);
		Icon namiIcon = new Icon("resources/theme/listContainer/namiFace.png");
		namiIcon.addIconClickListener(new IconClickListener() {

			@Override
			public void onClick() {
				if (!playerAdd) {
					MainCharacter temp = charactersPlaced.get("Nami");
					if (temp != null && money >= temp.getCost()) {
						charactersPlaced.remove("Nami");
						money = money - temp.getCost();
						if (temp != null) {
							selectedPlayableCharacter = temp;
							playerAdd = true;
						}
					}
				} else {
					Log.error("No hay dinero suficiente para el personaje");
				}
			}
		});
		list.addIcon(namiIcon);

		Icon zoroIcon = new Icon("resources/theme/listContainer/zoroFace.png");
		zoroIcon.addIconClickListener(new IconClickListener() {

			@Override
			public void onClick() {
				if (!playerAdd) {
					MainCharacter temp = charactersPlaced.get("Zoro");
					if (temp != null && money >= temp.getCost()) {
						charactersPlaced.remove("Zoro");
						money = money - temp.getCost();
						if (temp != null) {
							selectedPlayableCharacter = temp;
							playerAdd = true;
						}
					}
				}
			}
		});
		list.addIcon(zoroIcon);

		Icon usoppIcon = new Icon("resources/theme/listContainer/usoppFace.png");
		usoppIcon.addIconClickListener(new IconClickListener() {

			@Override
			public void onClick() {
				if (!playerAdd) {
					MainCharacter temp = charactersPlaced.get("Usopp");
					if (temp != null && money >= temp.getCost()) {
						money = money - temp.getCost();
						charactersPlaced.remove("Usopp");
						if (temp != null) {
							selectedPlayableCharacter = temp;
							playerAdd = true;
						}
					}
				}
			}
		});
		list.addIcon(usoppIcon);

		Icon sanjiIcon = new Icon("resources/theme/listContainer/sanjiFace.png");
		sanjiIcon.addIconClickListener(new IconClickListener() {

			@Override
			public void onClick() {
				if (!playerAdd) {
					MainCharacter temp = charactersPlaced.get("Sanji");
					if (temp != null && money >= temp.getCost()) {
						money = money - temp.getCost();
						charactersPlaced.remove("Sanji");
						if (temp != null) {
							selectedPlayableCharacter = temp;
							playerAdd = true;
						}
					}
				}
			}
		});
		list.addIcon(sanjiIcon);

		Icon chopperIcon = new Icon(
				"resources/theme/listContainer/chopperFace.png");
		chopperIcon.addIconClickListener(new IconClickListener() {

			@Override
			public void onClick() {
				if (!playerAdd) {
					MainCharacter temp = charactersPlaced.get("Chopper");
					if (temp != null && money >= temp.getCost()) {
						money = money - temp.getCost();
						charactersPlaced.remove("Chopper");
						if (temp != null) {
							selectedPlayableCharacter = temp;
							playerAdd = true;
						}
					}
				}
			}
		});
		list.addIcon(chopperIcon);

		Icon robinIcon = new Icon(
				"resources/theme/listContainer/nicoRobbinFace.png");
		robinIcon.addIconClickListener(new IconClickListener() {

			@Override
			public void onClick() {
				if (!playerAdd) {
					MainCharacter temp = charactersPlaced.get("Robin");
					if (temp != null && money >= temp.getCost()) {
						money = money - temp.getCost();
						charactersPlaced.remove("Robin");
						if (temp != null) {
							selectedPlayableCharacter = temp;
							playerAdd = true;
						}
					}
				}
			}
		});
		list.addIcon(robinIcon);

		Icon frankyIcon = new Icon(
				"resources/theme/listContainer/frankyFace.png");
		frankyIcon.addIconClickListener(new IconClickListener() {

			@Override
			public void onClick() {
				if (!playerAdd) {
					MainCharacter temp = charactersPlaced.get("Franky");
					if (temp != null && money >= temp.getCost()) {
						charactersPlaced.remove("Franky");
						money = money - temp.getCost();
						if (temp != null) {
							selectedPlayableCharacter = temp;
							playerAdd = true;
						}
					}
				}
			}
		});
		list.addIcon(frankyIcon);

		Icon brookIcon = new Icon("resources/theme/listContainer/brookFace.png");
		brookIcon.addIconClickListener(new IconClickListener() {

			@Override
			public void onClick() {
				if (!playerAdd) {
					MainCharacter temp = charactersPlaced.get("Brook");
					if (temp != null && money >= temp.getCost()) {
						charactersPlaced.remove("Brook");
						money = money - temp.getCost();
						if (temp != null) {
							selectedPlayableCharacter = temp;
							playerAdd = true;
						}
					}
				}
			}
		});
		list.addIcon(brookIcon);
	}

	/*
	 * /** Inicializa el contenedor
	 * 
	 * @param gc ventana
	 * 
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
		// twlInputAdapter.render();
		if (playerAdd) {
			drawAreaCharacter(g);
		}
		String temp = "$" + money;
		g.drawString(temp, 500, 10);
		for (int i = 0; i < entities.length; i++) {
			for (int j = 0; j < entities[i].length; j++) {
				if (entities[i][j] != null) {
					entities[i][j].draw();
				}
			}
		}

		if (isGameOver) {
			g.fill(gameOverRect);
			gameOver.draw(CENTER,y);
		}
		if(timeGameOverMovement>=1000&& colorTrans<148){
			Log.error("Tiempo" + timeGameOverMovement);
			y+= 35;
			colorTrans += 37;
			Color black = new Color(0, 0, 0, colorTrans);
			g.setColor(black);
			g.fill(gameOverRect);
			gameOver.draw(CENTER, y);
			timeGameOverMovement = 0;
		}
	}

	/**
	 * Dibuja los espacios del juego
	 * 
	 * @param g
	 *            contexto gráfico
	 */
	private void drawAreaCharacter(Graphics g) {
		int counter = 0;
		for (Polygon p : polygonList) {
			if (this.entities[counter / 2][counter % 2] != null) {
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
	public void initAreaCharacter() {
		polygonList = new ArrayList<Polygon>();
		for (int i = 0; i < 2; i++) {
			Polygon a = new Polygon();
			a.addPoint(135 + i * 90, 180);
			a.addPoint(135 + i * 90 + 90, 180);
			a.addPoint(90 + i * 90 + 90, 180 + 90);
			a.addPoint(90 + i * 90, 180 + 90);
			polygonList.add(a);
		}
		for (int i = 0; i < 2; i++) {
			Polygon a = new Polygon();
			a.addPoint(90 + i * 90, 180 + 90);
			a.addPoint(90 + i * 90 + 90, 180 + 90);
			a.addPoint(45 + i * 90 + 90, 180 + 90 * 2);
			a.addPoint(45 + i * 90, 180 + 90 * 2);
			polygonList.add(a);
		}

		for (int i = 0; i < 2; i++) {
			Polygon a = new Polygon();
			a.addPoint(45 + i * 90, 180 + 90 * 2);
			a.addPoint(45 + i * 90 + 90, 180 + 90 * 2);
			a.addPoint(0 + i * 90 + 90, 180 * 2 + 90);
			a.addPoint(0 + i * 90, 180 * 2 + 90);
			polygonList.add(a);
		}
	}

	@Override
	public void update(GameContainer gc, StateBasedGame sb, int delta)
			throws SlickException {
		if(isGameOver && y<140){
			timeGameOverMovement += timeGameOverMovement + delta;
		}
		if (!isGameOver) {
			if (entities[0][0] instanceof BasicEnemy
					|| entities[1][0] instanceof BasicEnemy
					|| entities[2][0] instanceof BasicEnemy) {
				for (int i = 0; i < entities.length; i++)
					for (int j = 0; j < entities[i].length; j++)
						entities[i][j] = null;
				isGameOver = true;
			}

			if (this.lastMovement > 0
					&& this.lastMovement >= ENEMY_PER_SECOND / 2) {

				lastMovement = ENEMY_PER_SECOND / 2 - lastMovement;
				for (int i = 0; i < entities.length; i++) {
					for (int j = 0; j < entities[i].length; j++) {
						moveEnable[i][j] = (entities[i][j] == null) ? true
								: false;
						if (j > 0 && moveEnable[i][j - 1] == true
								&& entities[i][j] instanceof BasicEnemy) {
							moveEnable[i][j] = true;
						}
					}
				}
				addEnemy();

			}
			if (lastMovement < 0 && lastMovement > -ENEMY_PER_SECOND / 2) {
				lastMovement -= delta;
				for (int i = 0; i < entities.length; i++) {
					for (int j = 0; j < entities[i].length; j++) {
						if (j - 1 >= 0 && entities[i][j - 1] == null
								&& entities[i][j] != null
								&& entities[i][j] instanceof BasicEnemy
								&& moveEnable[i][j - 1]) {
							int x = 0;
							if (i == 0) {
								x = j * 90 + 135 - entities[i][j].getWidth();
							} else if (i == 1) {
								x = j * 90 + 90 - entities[i][j].getWidth();
							} else if (i == 2) {
								x = j * 90 + 45 - entities[i][j].getWidth();
							}
							float f = (90f / (ENEMY_PER_SECOND / 2f))
									* lastMovement;
							x = (int) (x + f);

							entities[i][j].setX(x);
							((BasicEnemy) entities[i][j]).setAnimation(2);
						}
					}

				}
			} else if (lastMovement < 0) {
				for (int i = 0; i < entities.length; i++) {
					for (int j = 0; j < entities[i].length; j++) {

						if (j - 1 >= 0 && entities[i][j - 1] == null
								&& entities[i][j] != null
								&& entities[i][j] instanceof BasicEnemy) {

							int x = 0;
							if (i == 0) {
								x = (j - 1) * 90 + 135
										- entities[i][j].getWidth();
							} else if (i == 1) {
								x = (j - 1) * 90 + 90
										- entities[i][j].getWidth();
							} else if (i == 2) {
								x = (j - 1) * 90 + 45
										- entities[i][j].getWidth();
							}
							entities[i][j].setX(x);
							((BasicEnemy) entities[i][j]).setAnimation(1);
							entities[i][j - 1] = entities[i][j];
							entities[i][j] = null;
						}
					}

				}
				lastMovement = 0;
			} else {

				lastMovement += delta;
			}

			Input input = gc.getInput();
			enterInput(input);
			int mouseX = input.getMouseX();
			int mouseY = input.getMouseY();
			boolean leftButtonPressed = input
					.isMousePressed(Input.MOUSE_LEFT_BUTTON);

			if (leftButtonPressed) {
				list.update(mouseX, mouseY);

			}

			if (this.playerAdd) {
				int counter = 0;
				for (Polygon p : polygonList) {

					if (p.contains(mouseX, mouseY) && leftButtonPressed

					&& entities[counter / 2][counter % 2] == null) {

						entities[counter / 2][counter % 2] = this.selectedPlayableCharacter;
						setPlayableCharacterPosition(
								this.selectedPlayableCharacter, counter / 2,
								counter % 2);
						this.selectedPlayableCharacter = null;
						this.playerAdd = false;
						return;
					}
					counter++;
				}

			} else {
				for (int i = 0; i < entities.length; i++) {
					for (int j = 0; j < entities[i].length; j++) {
						if (entities[i][j] != null) {
							entities[i][j].update(leftButtonPressed, mouseX,
									mouseY, delta);
						}
					}
				}
			}
			time += delta;
			if (time > 1000) {
				time = 0;
				for (int i = 0; i < entities.length; i++) {
					for (int j = 0; j < 2; j++) {
						if (entities[i][j] instanceof MainCharacter) {
							MainCharacter temp = (MainCharacter) entities[i][j];
							if ((entities[i][j + 1] instanceof BasicEnemy)
									&& temp.getFinalAttackAnimation() != temp
											.getCurrent()) {
								BasicEnemy tempEnemy = (BasicEnemy) entities[i][j + 1];
								temp.setCurrent(temp.getAttackAnimation());
								temp.setLife(temp.getLife()
										- tempEnemy.getDamageNormalAttack());
								tempEnemy.setCurrent(tempEnemy
										.getAttackAnimation());
								money = money + temp.getMoney();
								tempEnemy.setLife(tempEnemy.getLife()
										- temp.getDamageNormalAttack());
								if (tempEnemy.getLife() <= 0) {
									entities[i][j + 1] = null;
								}
								if (temp.getLife() <= 0) {
									entities[i][j] = null;
								}

							} else if (entities[i][j + 2] instanceof BasicEnemy
									&& temp.getFinalAttackAnimation() != temp
											.getCurrent()) {
								BasicEnemy tempEnemy2 = (BasicEnemy) entities[i][j + 2];
								temp.setCurrent(temp.getAttackAnimation());
								tempEnemy2.setCurrent(tempEnemy2
										.getAttackAnimation());
								money = money + temp.getMoney();
								if (entities[i][j + 1] == null) {
									temp.setLife(temp.getLife()
											- tempEnemy2
													.getDamageNormalAttack());
								}
								tempEnemy2.setLife(tempEnemy2.getLife()
										- temp.getDamageNormalAttack());
								if (tempEnemy2.getLife() <= 0) {
									entities[i][j + 2] = null;
								}
								if (temp.getLife() <= 0) {
									entities[i][j] = null;
								}
							} else if (temp.getCurrent() != temp
									.getFinalAttackAnimation()) {
								temp.setCurrent(temp.getStandAnimation());
							}
						}
					}
				}
			}
		} else {
		
		}

	}

	private void addEnemy() {
		BasicEnemy marine = new BasicEnemy(
				"resources/sprites/Marine/marine.txt", "marine");
		enemi.add(marine);
		Random randomGenerator = new Random();

		int row = randomGenerator.nextInt(3);

		if (entities[row][5] == null) {
			int x = 0;
			int y = 0;
			if (row == 0) {
				x = 6 * 90 + 135 - marine.getWidth();
				y = 180 + 90 - marine.getHeight();
			} else if (row == 1) {
				x = 6 * 90 + 90 - marine.getWidth();
				y = 180 + 90 * 2 - marine.getHeight();
			} else if (row == 2) {
				x = 6 * 90 + 45 - marine.getWidth();
				y = 180 + 90 * 3 - marine.getHeight();
			}
			marine.setX(x);
			marine.setY(y);
			entities[row][6] = marine;
		}

	}

	/**
	 * Coloca el personaje
	 * 
	 * @param selectedPlayableCharacter2
	 *            personaje elegido para colocar
	 * @param i
	 *            posicion
	 * @param j
	 *            posicion
	 */
	private void setPlayableCharacterPosition(
			BasicCharacter selectedPlayableCharacter2, int i, int j) {
		if (i == 0) {
			selectedPlayableCharacter2.setX(90 + j * 90);
			selectedPlayableCharacter2
					.setY(180 + 90 - selectedPlayableCharacter2.getHeight());

		} else if (i == 1) {
			selectedPlayableCharacter2.setX(45 + j * 90);
			selectedPlayableCharacter2
					.setY(180 + 90 * 2 - selectedPlayableCharacter2.getHeight());

		} else if (i == 2) {
			selectedPlayableCharacter2.setX(0 + j * 90);
			selectedPlayableCharacter2
					.setY(180 + 90 * 3 - selectedPlayableCharacter2.getHeight());
		}

	}

	private void enterInput(Input i) {

	}

	@Override
	public int getID() {
		return this.stateId;
	}

}
