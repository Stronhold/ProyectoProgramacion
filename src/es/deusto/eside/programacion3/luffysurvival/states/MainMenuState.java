package es.deusto.eside.programacion3.luffysurvival.states;

import org.lwjgl.opengl.GL11;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

public class MainMenuState extends BasicGameState {

	private int stateID;

	
	  private float xrot;            // X Rotation ( NEW )
	    private float yrot;            // Y Rotation ( NEW )
	    private float zrot;            // Z Rotation ( NEW )


	//    private AVDecoder mm = new AVDecoder("C:\\msys\\devel\\lwjgl-mm\\test\\test-avi.avi");
	@Override
	public void init(GameContainer gameContainer, StateBasedGame arg1)
			throws SlickException {
		// TODO Auto-generated method stub
        		
	}

	public MainMenuState(int stateID )
    {
        this.stateID = stateID;
    }
	
	@Override
	public void render(GameContainer arg0, StateBasedGame arg1, Graphics arg2)
			throws SlickException {
		// TODO Auto-generated method stub

		GL11.glColor3f(1.0f, 1.0f, 1.0f);
	    GL11.glBegin(GL11.GL_LINES);
	    GL11.glLineWidth(25.0f);
	    GL11.glVertex2f(10, 100);
	    GL11.glVertex2f(600, 100);
	    GL11.glEnd();
	
	     GL11.glBegin(GL11.GL_QUADS); 
         GL11.glColor3f(1.0f, 0.0f, 0.0f);
         GL11.glVertex2f(50.0f, 0.0f);
         GL11.glVertex2f(1.0f, 0.0f);
         GL11.glVertex2f(50.0f, 150.0f);
         GL11.glVertex2f(1.0f, 150.0f);
         GL11.glEnd();
	}

	@Override
	public void update(GameContainer arg0, StateBasedGame arg1, int arg2)
			throws SlickException {
		// TODO Auto-generated method stub

	}

	@Override
	public int getID() {
		return this.stateID;
	}

}
