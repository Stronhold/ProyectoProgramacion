package es.deusto.eside.programacion3.luffysurvival.model;

import org.newdawn.slick.Animation;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;

public class LuffyAnimation  extends BasicPlayableCharacterAnimation{
	
	private final static String STAND_SPRITE_SHEET = "resources/sprites/Luffy/LuffyStatic.png";
	private final static int STAND_WIDTH = 45;
	private final static int STAND_HEIGHT = 90;

	public LuffyAnimation() {
		super();	
	}

	@Override
	void initStandAnimation()  {

		SpriteSheet luffy;
		try {
			luffy = new SpriteSheet(
					STAND_SPRITE_SHEET, STAND_WIDTH, STAND_HEIGHT);
			this.standAnimation = new Animation();
			this.standAnimation.setAutoUpdate(true);
			for (int frame = 0; frame < 6; frame++) {
				this.standAnimation.addFrame(luffy.getSprite(frame, 0), 100);
			}
			standAnimation.addFrame(luffy.getSprite(6, 0), 1000);
		} catch (SlickException e) {
			e.printStackTrace();
		}
	}
	
	



	@Override
	void initAttackAnimation() {
		SpriteSheet luffy;
		try {
			luffy = new SpriteSheet(
					"resources/sprites/Luffy/LuffyAtackFixed.png", 67, 90);
			this.attackAnimation = new Animation();
			this.attackAnimation.setAutoUpdate(true);
			for (int frame = 0; frame < 11; frame++) {
				this.attackAnimation.addFrame(luffy.getSprite(frame, 0), 150);
			}
		} catch (SlickException e) {
			e.printStackTrace();
		}				
	}


	@Override
	void initSpecialAttackAnimation() {
		SpriteSheet luffy;
		try {
			luffy = new SpriteSheet(
					"resources/sprites/Luffy/LuffyFinalAttack.png", 180, 90);
			this.specialAttackAnimation = new Animation();
			this.specialAttackAnimation.setAutoUpdate(true);
			for (int frame = 0; frame < 22; frame++) {
				this.specialAttackAnimation.addFrame(luffy.getSprite(frame, 0), 150);
			}
		} catch (SlickException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
				
	}

}
