package es.deusto.eside.programacion3.luffysurvival.model;

import org.newdawn.slick.Animation;

public abstract class BasicPlayableCharacterAnimation {
	

	Animation attackAnimation;
	Animation specialAttackAnimation;
	Animation standAnimation;

	
	public Animation getAttackAnimation() {
		return attackAnimation;
	}

	public Animation getSpecialAttackAnimation() {
		return specialAttackAnimation;
	}

	public Animation getStandAnimation() {
		return standAnimation;
	}

	public BasicPlayableCharacterAnimation() {
		loadAnimations();
	}

	private void loadAnimations() {
		this.attackAnimation = new Animation();
		this.specialAttackAnimation = new Animation();
		this.standAnimation = new Animation();
		initAttackAnimation();
		initSpecialAttackAnimation();
		initStandAnimation();
	}

	abstract void initAttackAnimation();
	
	abstract void initStandAnimation();
	
	abstract void initSpecialAttackAnimation();
	
	

}
