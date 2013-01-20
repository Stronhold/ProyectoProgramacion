package es.deusto.eside.programacion3.luffysurvival.states;

import java.awt.Font;
import java.awt.event.KeyEvent;

import org.newdawn.slick.Animation;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.KeyListener;
import org.newdawn.slick.Music;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.UnicodeFont;
import org.newdawn.slick.font.effects.ColorEffect;
import org.newdawn.slick.font.effects.OutlineEffect;
import org.newdawn.slick.font.effects.ShadowEffect;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;

import TWLSlick.TWLInputAdapter;
import de.matthiasmann.twl.GUI;
import de.matthiasmann.twl.Widget;
import de.matthiasmann.twl.renderer.lwjgl.LWJGLRenderer;


import es.deusto.eside.programacion3.luffysurvival.LuffySurvival;
import es.deusto.eside.programacion3.luffysurvival.engine.FadeOutAnimation;
import es.deusto.eside.programacion3.luffysurvival.language.Locale;
/**
 * En esta clase se visualizaran los creditos del juego
 * @author sergio
 *
 */

public class CreditState extends BasicGameState{

	private int timer = 0;
	/**
	 * stateID estado del juego
	 */
	private int stateID;

	/**
	 * Indica si es la primera vez que pincha
	 */
	private boolean isFirstTime = true;
	
	/**
	 * Animacion
	 */
	private FadeOutAnimation right;
	
	/**
	 * KeyListener
	 */
	private KeyListener keyListener;
	//private Music song;
	
	/**
	 * Ancho
	 */
	int WIDTH = 640;
	
	/**
	 * Alto
	 */
	int HEIGTH = 480;
	

	/**
	 * fpsFont: fuente para escribir
	 */
	private UnicodeFont fpsFont;
	
	/**
	 * Cancion
	 */
	private Music song;
	
	
	/**
	 * constructor del juego
	 * @param ordinal numero de estado
	 */
	public CreditState(int ordinal){
		this.stateID = ordinal;
	}

	/**
	 * devuelve el numero de estado del juego
	 */
	public int getID() {
		return this.stateID;
	}


	@Override
	public void init(GameContainer arg0, StateBasedGame arg1)
			throws SlickException {
	    Font awtFont = new Font("Arial", Font.BOLD, 24);
	    fpsFont = new UnicodeFont(awtFont);
		//fpsFont = new UnicodeFont(fontPath, 16, true, false);
		fpsFont.addAsciiGlyphs();
		fpsFont.addGlyphs(800, 800);
		fpsFont.getEffects().add(new ColorEffect(java.awt.Color.WHITE));
		fpsFont.getEffects().add(new ShadowEffect());
		fpsFont.loadGlyphs();
		this.right = new FadeOutAnimation();
		Image a = new Image("resources/image/Credit/Shanks.png");
		a = a.getScaledCopy(0.5f);
		Image b = new Image("resources/image/Credit/Ace.png");
		b = b.getScaledCopy(0.4f);
		Image c = new Image("resources/image/Credit/law.png");
		c = c.getScaledCopy(0.3f);
		song = new Music("resources/Music/Credit.ogg");
		this.right.addFrame(a, 4000);
		this.right.addFrame(b, 4000);
		this.right.addFrame(c, 4000);
		//if(music==true){
		/*	try {
				//song = new Music("resources/Music/serialKiller.ogg");
			} catch (SlickException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		//	music = false;
		//}*/
	}
	@Override
	public void enter(final GameContainer container, final StateBasedGame sb) {
		Input i = container.getInput();
		if(LuffySurvival.p.isMusic() == true){
			song.loop();
		}
		keyListener = initKeyListener(sb);
		i.addKeyListener(keyListener);
	}
	
	@Override
	public void leave(final GameContainer container, final StateBasedGame sb) {
		Input i = container.getInput();
		i.removeKeyListener(keyListener);
		isFirstTime = true;
		song.stop();
		timer = 0;
	}


	/**
	 * Key listener
	 * @param sb estado del juego
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

	@Override
	public void render(GameContainer arg0, StateBasedGame sb, Graphics g)
			throws SlickException {
		if(timer>0 && timer < 5000){
			Image a = new Image("resources/image/credit/Wallpaper1.png");
			a = a.getScaledCopy(WIDTH, HEIGTH);
			g.drawImage(a, 0, 0);
		}
		if(timer>5000 && timer < 10000){
			Image a = new Image("resources/image/credit/Wallpaper2.png");
			a = a.getScaledCopy(WIDTH, HEIGTH);
			g.drawImage(a, 0, 0);
		}
		if(timer>10000){
			Image a = new Image("resources/image/credit/Wallpaper3.png");
			a = a.getScaledCopy(WIDTH, HEIGTH);
			g.drawImage(a, 0, 0);
		}
	
		if(right.getFrame()==0){
			this.right.draw( 640-right.getWidth(), 480-right.getHeight());
		}
		if(right.getFrame()==1){
			this.right.draw(690-right.getWidth(), 480-right.getHeight());
		}
		if(right.getFrame()==2 ){
			this.right.draw(710-right.getWidth(), 480-right.getHeight());
		}
		if(timer> 0 && timer < 3000){
			fpsFont.drawString(100, 100, Locale.INSTANCE.getText("gameDone"));
			fpsFont.drawString(125, 140, "Slick2D");
		}
		if (timer > 3000 && timer<6000){
			fpsFont.drawString(100, 150, Locale.INSTANCE.getText("Video"));
			fpsFont.drawString(125, 190, "Regiosk8 (youtube)");
		}
		if(timer > 6000 && timer<9000){
			fpsFont.drawString(100, 200, Locale.INSTANCE.getText("Sprite"));
			fpsFont.drawString(125, 240,Locale.INSTANCE.getText("url") );
		}
		if(timer>9000 && timer<12000){
			fpsFont.drawString(100, 250, Locale.INSTANCE.getText("done"));
			fpsFont.drawString(125, 290, "Sergio Ausín");		
		}
		if(timer>15000){
			timer = 0;
		}
	}


	@Override
	public void update(GameContainer arg0, StateBasedGame sb, int delta)
			throws SlickException {
		timer += delta;
		
	}

}
