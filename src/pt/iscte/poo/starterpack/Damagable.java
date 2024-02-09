package pt.iscte.poo.starterpack;

import pt.iscte.poo.utils.Point2D;

public class Damagable extends GameElement implements Damageable {
	
	private boolean isEnemy;
	private double health;
	private int damage;
	private int heals;
	private long attackProb;
	private long moveProb;
	
	public Damagable(Point2D position, String name, int layer) {
		super(position, name, layer);
		
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean isEnemy() {
		// TODO Auto-generated method stub
		return isEnemy;
	}

	@Override
	public double getDamage() {
		// TODO Auto-generated method stub
		return damage;
	}

	@Override
	public void takeDamage(double damage) {
		this.health -=damage;		
	}

	@Override
	public double getHealth() {
		// TODO Auto-generated method stub
		return this.health;
	}

}
