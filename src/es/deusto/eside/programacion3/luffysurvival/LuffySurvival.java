package es.deusto.eside.programacion3.luffysurvival;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import es.deusto.eside.programacion3.luffysurvival.language.Locale;
import es.deusto.eside.programacion3.luffysurvival.states.GamePlayState;
import es.deusto.eside.programacion3.luffysurvival.states.GameState;
import es.deusto.eside.programacion3.luffysurvival.states.MainMenuState;
import es.deusto.eside.programacion3.luffysurvival.states.OptionState;

public class LuffySurvival extends StateBasedGame {

	public LuffySurvival(String name) {
		super(name);

		this.addState(new MainMenuState(GameState.MAIN_MENU_STATE.ordinal()));
		this.addState(new GamePlayState(GameState.GAME_PLAY_STATE.ordinal()));
		this.addState(new OptionState(GameState.OPTIONS_STATE.ordinal()));
		this.enterState(GameState.MAIN_MENU_STATE.ordinal());
	}

	@Override
	public void initStatesList(GameContainer gameContainer)
			throws SlickException {
		this.getState(GameState.GAME_PLAY_STATE.ordinal()).init(gameContainer,
				this);
		this.getState(GameState.MAIN_MENU_STATE.ordinal()).init(gameContainer,
				this);
		this.getState(GameState.OPTIONS_STATE.ordinal()).init(gameContainer,
				this);

	}

	public static void main(String[] args) {
		try {
			AppGameContainer app = new AppGameContainer(new LuffySurvival(
					Locale.getText("title")));

			app.setDisplayMode(800, 600, false);
			app.setTargetFrameRate(30);
			app.start();
		} catch (SlickException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
