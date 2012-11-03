package es.deusto.eside.programacion3.luffysurvival.model;

import org.newdawn.slick.Animation;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;

public class MarineAnimation extends BasicPlayableCharacterAnimation{

	public MarineAnimation(){
		super();
	}
	@Override
	void initAttackAnimation() {
		SpriteSheet marine;
		try {
			marine = new SpriteSheet("resources/sprites/MarineShot.png", 90, 90);
			this.attackAnimation = new Animation();
			this.attackAnimation.setAutoUpdate(true);
			for (int frame = 0; frame < 3; frame++) {
				this.attackAnimation.addFrame(marine.getSprite(frame, 0), 100);
			}
			this.attackAnimation.addFrame(marine.getSprite(3, 0), 1000);
		} catch (SlickException e) {
			e.printStackTrace();
		}
	}

	@Override
	void initStandAnimation() {
		SpriteSheet marine;
		try {
			marine = new SpriteSheet("resources/sprites/marineMovement.png", 45, 90);
			this.standAnimation = new Animation();
			this.standAnimation.setAutoUpdate(true);
			for (int frame = 0; frame < 6; frame++) {
				this.standAnimation.addFrame(marine.getSprite(frame, 0), 100);
			}
		} catch (SlickException e) {
			e.printStackTrace();
		}
		
	}

	@Override
	void initSpecialAttackAnimation() {
		//It doesn't have special attack
	}

}
