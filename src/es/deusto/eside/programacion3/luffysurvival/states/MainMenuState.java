package es.deusto.eside.programacion3.luffysurvival.states;



import org.newdawn.slick.Animation;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;


import es.deusto.eside.programacion3.luffysurvival.LuffySurvival;


public class MainMenuState extends BasicGameState {

	private int stateID;


	private Image background;
	private Animation mainCharacters;

	private static final int DURATION = 3000;

	@Override
	public void init(GameContainer gameContainer, StateBasedGame arg1)
			throws SlickException {
		this.background = new Image("resources/image/menu/background.png");
		this.mainCharacters = new Animation();
		this.mainCharacters.addFrame(new Image("resources/image/menu/luffy.png"), DURATION);
		this.mainCharacters.addFrame(new Image("resources/image/menu/zoro.png"), DURATION);
		this.mainCharacters.addFrame(new Image("resources/image/menu/nami.png"), DURATION);
		this.mainCharacters.addFrame(new Image("resources/image/menu/usopp.png"), DURATION);
		this.mainCharacters.addFrame(new Image("resources/image/menu/sanji.png"), DURATION);
		this.mainCharacters.addFrame(new Image("resources/image/menu/chopper.png"), DURATION);
		this.mainCharacters.addFrame(new Image("resources/image/menu/robin.png"), DURATION);
		this.mainCharacters.addFrame(new Image("resources/image/menu/franky.png"), DURATION);
		this.mainCharacters.addFrame(new Image("resources/image/menu/brook.png"), DURATION);

	}

	
	public MainMenuState(int stateID) {
		this.stateID = stateID;
	}

	@Override
	public void render(GameContainer container, StateBasedGame arg1, Graphics g)
			throws SlickException { 
		
		g.drawImage(this.background,0, 0);	
		this.mainCharacters.draw(LuffySurvival.WIDTH - this.mainCharacters.getCurrentFrame().getWidth(), 0);
	}

	@Override
	public void update(GameContainer arg0, StateBasedGame arg1, int arg2)
			throws SlickException {
	
	}

	@Override
	public int getID() {
		return this.stateID;
	}




}
