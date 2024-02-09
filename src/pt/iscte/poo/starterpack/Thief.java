package pt.iscte.poo.starterpack;

import pt.iscte.poo.gui.ImageMatrixGUI;
import pt.iscte.poo.utils.Point2D;
import pt.iscte.poo.utils.Vector2D;

public class Thief extends GameElement implements Enemy{

	private double health=5;
	private int damage=0;
	public GameElement stolenItem=null;

	public Thief(Point2D position) {
		super(position,"Thief",1);
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
	

	public GameElement getStolenItem() {
		return stolenItem;
	}

	public void steal() {
		int i = (int)Math.random()*GameEngine.getInstance().getHero().getInventory().size();
		System.out.println(i);
		if(GameEngine.getInstance().getHero().getInventory()!=null && GameEngine.getInstance().getHero().getInventory().get(i)!=null) {
			stolenItem= GameEngine.getInstance().getHero().getInventory().get(i);
			ImageMatrixGUI.getInstance().removeImage(stolenItem);
			GameEngine.getInstance().getHero().getInventory().set(i,null);
		}
	}

	public void move(){

		Point2D newPos;

		if(stolenItem==null)	
			newPos= getPosition().plus(Vector2D.movementVector(getPosition(), GameEngine.getInstance().getHero().getPosition()));
		else
			newPos = getPosition().plus(Vector2D.movementVector(GameEngine.getInstance().getHero().getPosition(), getPosition()));

		if(GameEngine.getInstance().canMoveTo(newPos) && !GameEngine.getInstance().CheckObj(newPos, b -> b instanceof Door)){

			if(newPos.equals(GameEngine.getInstance().getHero().getPosition())) 
				steal();
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
