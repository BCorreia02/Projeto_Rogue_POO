package pt.iscte.poo.starterpack;

import java.util.List;
import java.lang.Math;

import pt.iscte.poo.utils.Direction;
import pt.iscte.poo.utils.Point2D;

public class Bat extends GameElement implements Enemy{

	private double health=3;
	private int damage=1;
	private int heals;
	
	

	public Bat(Point2D position) {
		super(position, "Bat", 1);
	}

	public void Heal(int health) {
		this.health += health;
	}

	public double getDamage(){
		if(Math.random()<0.5) {	
			if(heals<=3) {
				heals++;
				Heal(1);	
			}
			return damage;			
		}else 
			return 0;
	}

	public void takeDamage(double damage) {
		this.health-=damage;
	}

	public void move() {
		
		Point2D newPos;
		
		if(Math.random()<=0.5)
			newPos= GameEngine.getInstance().EnemyDirection(GameEngine.getInstance().getHero().getPosition(), getPosition());
		else
			newPos= getPosition().plus(Direction.random().asVector());		
		if(GameEngine.getInstance().canMoveTo(newPos)){
			if(newPos.equals(GameEngine.getInstance().getHero().getPosition()))
				GameEngine.getInstance().getHero().takeDamage(damage*GameEngine.getInstance().getHero().getShield());
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
