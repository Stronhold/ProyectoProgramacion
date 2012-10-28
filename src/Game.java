import org.newdawn.slick.Animation;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.geom.Polygon;
import org.newdawn.slick.tiled.TiledMap;
import org.newdawn.slick.Music;

public class Game extends BasicGame{

	private float playerX=160;
	private float playerY=240;
	private BlockMap map;	
	private Animation playerLeft;
	private Animation playerRight;
	private Animation playerDown;
	private Animation playerUp;
	private Animation current;

	private Polygon playerPoly;
	
	public Game(){
		super("Juego con colisiones");
	}
	@Override
	public void render(GameContainer container, Graphics g) throws SlickException {
		// TODO Auto-generated method stub
		BlockMap.tmap.render(0, 0);
		g.drawAnimation(current, playerX, playerY);
	//	g.draw(playerPoly);
	}

	@Override
	public void init(GameContainer container) throws SlickException {
		// TODO Auto-generated method stub
		container.setVSync(true);
		Color c = new Color(0, 64, 128);
		SpriteSheet sheet = new SpriteSheet("resources/sprites/wooman.png",16,32,c);
		map = new BlockMap("data/map1.tmx");		
		playerLeft = new Animation();
		playerLeft.setAutoUpdate(false);
		//sheet.getSubImage(x, y, width, height)
		
		for (int frame=0;frame<3;frame++) {
			playerLeft.addFrame(sheet.getSprite(frame,0), 150);
			//player.addFrame(sheet.getSubImage(184 + frame * 18, 54, 18, 32), 150);
		}
		playerPoly = new Polygon(new float[]{
				playerX,playerY,
				playerX+16,playerY,
				playerX+16,playerY+32,
				playerX,playerY+32}
		);	
		
		playerDown = new Animation();
		playerDown.setAutoUpdate(false);
		playerUp = new Animation();
		playerUp.setAutoUpdate(false);
		
		for (int frame=0;frame<3;frame++) {
			playerUp.addFrame(sheet.getSubImage(106+18*frame, 0, 18, 32), 150);
		}
		
		
		for (int frame=0;frame<3;frame++) {
			playerDown.addFrame(sheet.getSubImage(52+18*frame, 0, 18, 32), 150);
		}

		
		playerRight = new Animation();
		playerRight.setAutoUpdate(false);
		for (int frame=0;frame<3;frame++) {
			playerRight.addFrame(sheet.getSprite(frame,0).getFlippedCopy(true, false), 150);
			//player.addFrame(sheet.getSubImage(184 + frame * 18, 54, 18, 32), 150);
		}
		current = playerRight;

	}

	@Override
	public void update(GameContainer container, int delta) throws SlickException {
		// TODO Auto-generated method stub
		if (container.getInput().isKeyDown(Input.KEY_RIGHT)) {
			current = playerRight;
			current.setAutoUpdate(true);
			playerX++;
			playerPoly.setX(playerX);
			if(entityCollisionWith()){
				playerX--;
				playerPoly.setX(playerX);
			}
		}
		else if (container.getInput().isKeyDown(Input.KEY_UP)) {
			current = playerUp;
			current.setAutoUpdate(true);
			playerY--;
			playerPoly.setY(playerY);
			if(entityCollisionWith()){
				playerY++;
				playerPoly.setY(playerY);
				
			}
		}
		else if (container.getInput().isKeyDown(Input.KEY_DOWN)) {
			current = playerDown;
			playerY++;
			current.setAutoUpdate(true);
			playerPoly.setY(playerY);
			if(entityCollisionWith()){
				playerY--;
				playerPoly.setY(playerY);
			}
		}
		else if (container.getInput().isKeyDown(Input.KEY_LEFT)) {
			current = playerLeft;
			playerX--;
			current.setAutoUpdate(true);
			playerPoly.setX(playerX);
			if (entityCollisionWith()){
				playerX++;
				playerPoly.setX(playerX);
			}
		}		
		else
			current.setAutoUpdate(false);
			
	}
	public boolean entityCollisionWith() throws SlickException {
		for (int i = 0; i < BlockMap.entities.size(); i++) {
			Block entity1 = (Block) BlockMap.entities.get(i);
			if (playerPoly.intersects(entity1.poly)) {
				return true;
			}       
		}       
		return false;
	}

	public static void main (String [] args) throws SlickException{
		AppGameContainer container = 	new AppGameContainer(new Game(), 640, 480, false);
			container.start();
	}

}
