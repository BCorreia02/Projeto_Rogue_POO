package pt.iscte.poo.starterpack;

import java.util.List;

import pt.iscte.poo.utils.Point2D;

public class Skeleton extends GameElement implements Enemy{

	private double health=5;
	private int damage=1;

	public Skeleton(Point2D position) {
		super(position,"Skeleton",1);
	}

	public void setHealth(double health) {
		this.health += health;
	}

	public void move() {
		Point2D newPos= GameEngine.getInstance().EnemyDirection(GameEngine.getInstance().getHero().getPosition(), getPosition());
		if((GameEngine.getInstance().getTurns()%2==0)) {
			if(GameEngine.getInstance().canMoveTo(newPos)){
				if(newPos.equals(GameEngine.getInstance().getHero().getPosition()))
					GameEngine.getInstance().getHero().takeDamage(damage*GameEngine.getInstance().getHero().getShield());
				else
					setPosition(newPos);
			}	
		}
	}

	public double getHealth() {
		return health;
	}

	public void takeDamage(double damage) {
		this.health-=damage;
	}

	@Override
	public boolean isEnemy() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public double getDamage() {
		return damage;
	}

}
