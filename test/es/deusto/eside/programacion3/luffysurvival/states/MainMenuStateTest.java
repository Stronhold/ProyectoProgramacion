package es.deusto.eside.programacion3.luffysurvival.states;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class MainMenuStateTest {
	
	private MainMenuState state;

	@Before
	public void setUp() throws Exception {
		state = new MainMenuState(GameState.MAIN_MENU_STATE.ordinal());
	}

	@After
	public void tearDown() throws Exception {
		state = null;
	}

	@Test
	public void testGetBackground() {
		String image = state.getBackground(21600);
		assertTrue(image.equals("resources/image/menu/backgounds/1.jpg"));

		image = state.getBackground(21600 * 2);
		assertTrue(image.equals("resources/image/menu/backgounds/2.jpg"));

		image = state.getBackground(21600 * 3 );
		assertTrue(image.equals("resources/image/menu/backgounds/3.jpg"));

		image = state.getBackground(21600 * 4);
		assertTrue(image.equals("resources/image/menu/backgounds/1.jpg"));
		
		image = state.getBackground(21600 * 4 - 1);
		assertTrue(image.equals("resources/image/menu/backgounds/4.jpg"));
		
		image = System.currentTimeMillis() + "";
	}

}
