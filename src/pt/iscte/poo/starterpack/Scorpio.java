package pt.iscte.poo.starterpack;

import pt.iscte.poo.utils.Point2D;

public class Scorpio extends GameElement implements Enemy {
	
	private double health=2;
	private int damage=1;

	public Scorpio(Point2D position) {
		super(position,"Scorpio",1);
	}

	public void setHealth(double health) {
		this.health += health;
	}

	public void move() {
		
		Point2D newPos= GameEngine.getInstance().EnemyDirection(GameEngine.getInstance().getHero().getPosition(), getPosition());
			
			if(GameEngine.getInstance().canMoveTo(newPos) && !GameEngine.getInstance().CheckObj(newPos, b -> b instanceof Door) ){
				if(newPos.equals(GameEngine.getInstance().getHero().getPosition()))
					GameEngine.getInstance().getHero().setPoisoned(true);
				else
					setPosition(newPos);
					
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
