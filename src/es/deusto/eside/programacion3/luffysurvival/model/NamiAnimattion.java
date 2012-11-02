package es.deusto.eside.programacion3.luffysurvival.model;

import org.newdawn.slick.Animation;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;

public class NamiAnimattion {

	private Animation attack;
	private Animation aStand;
	private Animation superAtack;
	
	public Animation getaStand() {
		return aStand;
	}

	
	public Animation getAttack() {
		return attack;
	}


	public NamiAnimattion() {
		this.attack = new Animation();
		this.superAtack = new Animation();
		this.aStand = new Animation();
		
		try {
			this.initStandAnimation();
			this.initHitAnimation();
			//initFinalAnimation();
		} catch (SlickException e) {
			e.printStackTrace();
		}
	}

	public void initStandAnimation() throws SlickException {
		this.aStand = new Animation();

		SpriteSheet nami = new SpriteSheet(
				"resources/sprites/namiStand.png", 45, 90);
		this.aStand = new Animation();
		this.aStand.setAutoUpdate(true);
		for (int frame = 0; frame < 2; frame++) {
			this.aStand.addFrame(nami.getSprite(frame, 0), 100);
		}
		aStand.addFrame(nami.getSprite(2, 0), 1000);

	}
	public void initHitAnimation() throws SlickException {
		this.attack = new Animation();

		SpriteSheet nami = new SpriteSheet(
				"resources/sprites/namiattack.png", 157, 90);
		this.attack = new Animation();
		this.attack.setAutoUpdate(true);
		for (int frame = 0; frame < 14; frame++) {
			this.attack.addFrame(nami.getSprite(frame, 0), 150);
		}
	}
	/*public void initFinalAnimation() throws SlickException {
		this.superAtack = new Animation();

		SpriteSheet nami = new SpriteSheet(
				"resources/sprites/LuffyFinalAttack.png", 180, 90);
		this.superAtack = new Animation();
		this.superAtack.setAutoUpdate(true);
		for (int frame = 0; frame < 22; frame++) {
			this.superAtack.addFrame(nami.getSprite(frame, 0), 150);
		}
	}*/


	public Animation getSuperAtack() {
		return superAtack;
	}

}
