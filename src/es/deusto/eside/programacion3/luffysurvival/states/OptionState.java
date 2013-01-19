package es.deusto.eside.programacion3.luffysurvival.states;

import java.awt.event.MouseListener;
import java.io.IOException;
import java.net.URL;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Drawable;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.SharedDrawable;
import org.newdawn.slick.GameContainer;
import de.matthiasmann.twl.Button;
import de.matthiasmann.twl.Color;
import de.matthiasmann.twl.GUI;
import de.matthiasmann.twl.Rect;
import de.matthiasmann.twl.Widget;
import de.matthiasmann.twl.renderer.lwjgl.LWJGLRenderer;
import de.matthiasmann.twl.theme.ThemeManager;
import es.deusto.eside.programacion3.luffysurvival.LuffySurvival;
import es.deusto.eside.programacion3.luffysurvival.language.Locale;
import java.io.*;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.UnicodeFont;
import org.newdawn.slick.font.effects.ColorEffect;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import TWLSlick.TWLInputAdapter;
/**
 * Parte del menú desde la que se controlarán las opciones del juego
 * @author sergio
 *
 */

public class OptionState extends BasicGameState {

	private Button accept;
	private Button cancel;
	private UnicodeFont fpsFont;
	/**
	 * stateId: indica el estado del programa
	 */
	private int stateId;
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
	private static final String fontPath = "resources/fonts/OnePiece.ttf";
	Image background;
	Image check;
	Image uncheck;
	boolean activatedVolume = true;
	boolean activatedFullScreen = false;
	/**
	 * Constructor
	 * @param ordinal: número de estado del programa 
	 */
	public OptionState(int ordinal) {
		this.stateId = ordinal;	
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
			check = new Image("resources/image/Option/checkbox_checked.png");
			uncheck = new Image("resources/image/Option/checkbox_unchecked.png");
			check = check.getScaledCopy(50, 50);
			uncheck = uncheck.getScaledCopy(50, 50);
			background = new Image("resources/image/menu/backgounds/1.jpg");
			background = background.getScaledCopy(LuffySurvival.WIDTH, LuffySurvival.HEIGHT);
			initGUI(gameContainer, sb);
			fpsFont = new UnicodeFont(fontPath, 40, true, false);
			fpsFont.addAsciiGlyphs();
			fpsFont.addGlyphs(400, 600);
			fpsFont.getEffects().add(new ColorEffect(java.awt.Color.WHITE));
			fpsFont.loadGlyphs();
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
		
		}
		
		private void initButtonsEvent(final StateBasedGame sb) {
			cancel.addCallback(new Runnable() {
				
				@Override
				public void run() {
					sb.enterState(es.deusto.eside.programacion3.luffysurvival.states.GameState.MAIN_MENU_STATE.ordinal());					
				}
			});
			accept.addCallback(new Runnable() {
				
				@Override
				public void run() {
						  // Create file 
						  FileWriter fstream;
						try {
							fstream = new FileWriter("resources/Option/option.txt");
							BufferedWriter out = new BufferedWriter(fstream);
							if(activatedFullScreen == true){
								String temp = "activatedFullScreen: true";
								out.write(temp);
								out.newLine();
							}
							else{
								String temp = "activatedFullScreen: false";
								out.write(temp);
								out.newLine();
							}
							if(activatedVolume == true){
								String temp = "activatedVolume: true";
								out.write(temp);
								out.newLine();
							}
							else{
								String temp = "activatedVolume: false";
								out.write(temp);
								out.newLine();
							}
							 out.close();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						sb.enterState(es.deusto.eside.programacion3.luffysurvival.states.GameState.MAIN_MENU_STATE.ordinal());					
				}
			});
		}

		private void createButtons() {
			accept = new Button();
			accept.setText(Locale.INSTANCE.getText("accept"));
			accept.setTheme("button");
			accept.setPosition(50, 400);
			twlInputAdapter.getGui().getRootPane()
					.setSize(LuffySurvival.WIDTH, LuffySurvival.HEIGHT);
			twlInputAdapter.getGui().getRootPane().add(accept);
			accept.adjustSize();
			
			cancel = new Button();
			cancel.setText(Locale.INSTANCE.getText("cancel"));
			cancel.setTheme("button");
			cancel.setPosition(400, 400);

			twlInputAdapter.getGui().getRootPane()
					.setSize(LuffySurvival.WIDTH, LuffySurvival.HEIGHT);
			twlInputAdapter.getGui().getRootPane().add(cancel);
			cancel.adjustSize();
			
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



	@Override
	public void render(GameContainer arg0, StateBasedGame sb, Graphics g)
			throws SlickException {	
		org.newdawn.slick.Color black = new org.newdawn.slick.Color(0, 0, 0, 200);
		org.newdawn.slick.Color white = new org.newdawn.slick.Color(255, 255, 255, 255);
		//Rect r1 = new Rect(3, LuffySurvival.HEIGHT-71 , 5, 5);
		//Rect r2 = new Rect(LuffySurvival.WIDTH-71, 3 , 5, 5);

		Rectangle rect = new Rectangle(38, 38, LuffySurvival.WIDTH-76, LuffySurvival.HEIGHT-76);
//		g.setFont(theme);
		
		g.drawImage(background, 0, 0);
		g.setColor(white);
		g.fillRect(33, 33 ,5, LuffySurvival.HEIGHT-71);
		g.fillRect(LuffySurvival.WIDTH-38,38 ,5, LuffySurvival.HEIGHT-76);
		g.fillRect(33,33 ,LuffySurvival.WIDTH-66, 5);
		g.fillRect(33,LuffySurvival.HEIGHT-38 ,LuffySurvival.WIDTH-66, 5);
		g.setColor(black);
		g.fill(rect);
		Image title = new Image("resources/image/Option/title.png");
		g.drawImage(title, LuffySurvival.WIDTH/2 - title.getWidth()/2, 0);
		//g.drawString(Locale.INSTANCE.getText("Option"), 280, 30);
		fpsFont.drawString(250, 15, Locale.INSTANCE.getText("Option"));
		//80, 193
		g.setColor(white);
		fpsFont.drawString(80, 193, Locale.INSTANCE.getText("fullScreen"));
		if(activatedFullScreen == false){
			g.drawImage(uncheck, 350, 198);
		}
		else{
			g.drawImage(check, 350, 198);
		}
		fpsFont.drawString(80, 293, Locale.INSTANCE.getText("volume"));
		if(activatedVolume == true){
			g.drawImage(check, 230, 298);
		}
		else{
			g.drawImage(uncheck, 230, 298);
		}
		twlInputAdapter.render();
	}

	@Override
	public void update(GameContainer gc, StateBasedGame arg1, int arg2)
			throws SlickException {
		Input input = gc.getInput();
		int positionX = input.getMouseX();
		int positionY = input.getMouseY();
		if(positionX>350 && positionX < 400 && positionY > 198 && positionY < 248 && input.isMousePressed(Input.MOUSE_LEFT_BUTTON)){
			activatedFullScreen = !activatedFullScreen;
		}
		if(positionX>230 && positionX<280 && positionY>298 && positionY<348 && input.isMousePressed(Input.MOUSE_LEFT_BUTTON)){
			activatedVolume = !activatedVolume;
		}

	}

	@Override
	public int getID() {
		return this.stateId;
	}

}
