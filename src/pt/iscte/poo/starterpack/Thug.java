package pt.iscte.poo.starterpack;

import pt.iscte.poo.utils.Direction;
import pt.iscte.poo.utils.Point2D;
import pt.iscte.poo.utils.Vector2D;

import java.lang.Math;
import java.util.List;

public class Thug extends GameElement implements Enemy{

	private double health=10;
	private int damage=3;

	public Thug(Point2D position) {
		super(position,"Thug",1);
	}

	public void setHealth(int health) {
		this.health += health;
	}

	public double getDamage() {
		if(Math.random()<0.3) {
			return this.damage;
		}
		else
			return 0;
	}

	public void takeDamage(double damage) {
		this.health-=damage;
	}

	public void move(){
		
		Point2D newPos= GameEngine.getInstance().EnemyDirection(GameEngine.getInstance().getHero().getPosition(), getPosition());
		
		if(GameEngine.getInstance().canMoveTo(newPos) && !GameEngine.getInstance().CheckObj(newPos, b -> b instanceof Door)){
			if(newPos.equals(GameEngine.getInstance().getHero().getPosition())) 
				GameEngine.getInstance().getHero().takeDamage(getDamage()*GameEngine.getInstance().getHero().getShield());
			else
				setPosition(newPos);
			}
	}
	
	public double getHealth() {
		return health;
	}

	@Override
	public boolean isEnemy() {
		// TODO Auto-generated method stub
		return true;
	}

}
