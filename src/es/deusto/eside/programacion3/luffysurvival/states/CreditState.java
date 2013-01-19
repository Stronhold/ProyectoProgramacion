package es.deusto.eside.programacion3.luffysurvival.states;

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
import org.newdawn.slick.UnicodeFont;
import org.newdawn.slick.font.effects.ColorEffect;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;


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
	private boolean music = true;
	private boolean isFirstTime = true;
	private FadeOutAnimation right;
	private KeyListener keyListener;
	//private Music song;
	int WIDTH = 640;
	int HEIGTH = 480;	
	private UnicodeFont fpsFont;
	/**
	 * constructor del juego
	 * @param ordinal numero de estado
	 */
	public CreditState(int ordinal){
		this.stateID = ordinal;
	}

	/**
	 * devuelve el número de estado del juego
	 */
	public int getID() {
		return this.stateID;
	}


	@Override
	public void init(GameContainer arg0, StateBasedGame arg1)
			throws SlickException {
		
		this.right = new FadeOutAnimation();
		Image a = new Image("resources/image/Credit/Shanks.png");
		a = a.getScaledCopy(0.5f);
		Image b = new Image("resources/image/Credit/Ace.png");
		b = b.getScaledCopy(0.4f);
		Image c = new Image("resources/image/Credit/law.png");
		c = c.getScaledCopy(0.3f);
		//this.right = new Animation();
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
	//	song.play();
		keyListener = initKeyListener(sb);
		i.addKeyListener(keyListener);
	//	song.loop();

	}
	
	@Override
	public void leave(final GameContainer container, final StateBasedGame sb) {
		Input i = container.getInput();
		i.removeKeyListener(keyListener);
		isFirstTime = true;
	//	song.stop();
		music = true;
		timer = 0;
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
		g.setColor(Color.black);
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
			g.drawString(Locale.INSTANCE.getText("gameDone"), 100, 100);
		}
		if (timer > 3000 && timer<6000){
			g.drawString(Locale.INSTANCE.getText("Video"), 100, 150);
		}
		if(timer > 6000 && timer<9000){
			g.drawString(Locale.INSTANCE.getText("Sprite"), 100, 200);
			g.drawString(Locale.INSTANCE.getText("url"), 100, 220);
		}
		if(timer>9000 && timer<12000){
			g.drawString(Locale.INSTANCE.getText("done"), 100, 270);		
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
