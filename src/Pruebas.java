import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

import es.deusto.eside.programacion3.luffysurvival.model.LuffyAnimation;
import es.deusto.eside.programacion3.luffysurvival.model.MarineAnimation;
import es.deusto.eside.programacion3.luffysurvival.model.NamiAnimattion;


public class Pruebas extends BasicGame {

	private LuffyAnimation luffyAnimation;
	private NamiAnimattion namiAnimattion;
	private MarineAnimation marineAnimation;
	public Pruebas(String title) {
		super(title);
		// TODO Auto-generated constructor stub
	}
	public Pruebas(){
		super("Prueba");
	}

	public static void main(String[] args) throws SlickException {
		// TODO Auto-generated method stub
		AppGameContainer container = 	new AppGameContainer(new Pruebas(), 640, 480, false);
		container.start();
	}

	@Override
	public void render(GameContainer container, Graphics g) throws SlickException {
		// TODO Auto-generated method stub
		g.drawAnimation(luffyAnimation.getAttackAnimation(), 0, 90);
		g.drawAnimation(luffyAnimation.getStandAnimation(), 67, 90);
		g.drawAnimation(luffyAnimation.getSpecialAttackAnimation(), 112, 90);
		g.drawAnimation(namiAnimattion.getaStand(), 0, 0);
		g.drawAnimation(namiAnimattion.getAttack(), 45, 0);
		g.drawAnimation(namiAnimattion.getSuperAtack(), 202, 0);
		g.drawAnimation(marineAnimation.getAttackAnimation(),0 , 180);
		g.drawAnimation(marineAnimation.getStandAnimation(),90 , 180);

	}

	@Override
	public void init(GameContainer container) throws SlickException {
		// TODO Auto-generated method stub
		luffyAnimation = new LuffyAnimation();
		namiAnimattion = new NamiAnimattion();
		marineAnimation = new MarineAnimation();
	}

	@Override
	public void update(GameContainer container, int delta) throws SlickException {
		
	}

}
