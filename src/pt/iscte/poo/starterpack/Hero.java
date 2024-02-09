package pt.iscte.poo.starterpack;

import pt.iscte.poo.utils.Point2D;

import java.util.List;
import java.util.function.Predicate;
import java.util.ArrayList;

import pt.iscte.poo.gui.ImageMatrixGUI;
import pt.iscte.poo.utils.Direction;

public class Hero extends GameElement implements Damageable, Through{

	private List<GameElement> inventory;
	private HealthBar healthbar;
	private double damage=1;
	private double health=10;
	private final int maxHealth=10;
	private int heals=0;
	private double shield=1;
	boolean poisoned=false;


	public boolean isPoisoned() {
		return poisoned;
	}

	public void setPoisoned(boolean poisoned) {
		this.poisoned = poisoned;
	}

	public void setShield(int shield) {
		this.shield = shield;
	}

	public double getShield() {
		return shield;
	}

	public Hero() {
		super(new Point2D(1,1), "Hero", 2);
		this.inventory= new ArrayList<>();
		this.healthbar= new HealthBar();
		for(int i=0; i<3; i++) {
			this.inventory.add(null);
		}

		healthbar.setHealth(9);

	}

	public HealthBar getHealthbar() {
		return healthbar;
	}

	public void setHealthbar(HealthBar healthbar) {
		this.healthbar = healthbar;
	}


	void updateInvImages(){
		for(GameElement a: inventory) {
			ImageMatrixGUI.getInstance().addImage(a);
		}
		ImageMatrixGUI.getInstance().update();
	}

	public void heal(){
		for(int i=1; i<=5; i++) {
			if(this.health<maxHealth && heals <=10) {
				this.health+=1;
				heals++;
			}
		}
	}

	public void setInventory(List<GameElement> inventory) {
		this.inventory = inventory;
	}

	public List<GameElement> getInventory() {
		return inventory;
	}

	public Door getDoor(List<GameElement> list){
		Door aux= new Door(0,0,"",0,0,"");
		for(GameElement a: list) {
			if(a instanceof Door) {
				aux= (Door)a;
			}
		}
		return aux;
	}

	public boolean findKey(List<GameElement> list){
		for(GameElement a: this.inventory) {
			if(a instanceof Key) {
				if(((Key) a).getID().equals(getDoor(list).getKeyID())) {
					((Key) a).setUsed(true);
					return true;
				}
			}
		}
		return false;
	}


	public boolean unlockDoor(List<GameElement> list){
		for(GameElement a: list) {
			if(a instanceof Door) {
				if(a.getName().equals("DoorClosed")) {
					if(findKey(list)){
						((Door)a).setOpen();
						return true;
					}else 
						return false;
				}
				return true;
			}
		}
		return false;
	}

	public void pickItem(Point2D pos, List<GameElement> elements, Predicate<GameElement> pred) {
		for(GameElement b: elements)	{
			if(pred.test(b)) {
				GameEngine.getInstance().updateInvItems(b);
				if(b instanceof Sword)
					this.damage=2;
				if(b instanceof Armor)
					this.shield=0.5;
				
				GameEngine.getInstance().getCurRoom().getTileList().remove(b);
			}
		}
		
	}

	public void Atack(Point2D pos, List<GameElement> elements, Predicate<GameElement> pred) {
		for(GameElement b: elements)	{
			if(pred.test(b))
				((Damageable)b).takeDamage(damage);
		}
	}

	public void changeRoom(List<GameElement> elements) {
		
		setPosition(getDoor(elements).getDPos());
		GameEngine.getInstance().setRoom(getDoor(elements).getDRoom());
		GameEngine.getInstance().openDoors();
		GameEngine.getInstance().sendImagesToGUI();
	}
	
	


	public void move(Direction direction) {

		Point2D newPosition = super.getPosition().plus(direction.asVector());
		List<GameElement> elements= GameEngine.getInstance().getElementsInPos(newPosition) ;

		if(GameEngine.getInstance().canMoveTo(newPosition)){
			
			setPosition(newPosition);
			
			if(GameEngine.getInstance().CheckObj(newPosition, b -> b instanceof Door)) {
				if(unlockDoor(elements)) 
					changeRoom(elements);
			}

			if(GameEngine.getInstance().CheckObj(newPosition, b -> b instanceof Collectable))
				pickItem(newPosition, elements, b -> b instanceof Collectable);

			if(GameEngine.getInstance().CheckObj(newPosition, b -> b instanceof Treasure)) 
				GameEngine.getInstance().winGame();
		}

		else if(GameEngine.getInstance().CheckObj(newPosition, b -> b instanceof Damageable))
			Atack(newPosition, elements, b -> b instanceof Damageable);
		
		if(isPoisoned())
			health--;
		
		GameEngine.getInstance().moverInimigos(direction);
	}
	
	
	public void setDamage(double damage) {
		this.damage = damage;
	}

	public double getHealth() {
		return health;
	}

	public double getDamage() {
		return damage;
	}

	public void takeDamage(double health) {
		this.health -= health;

	}

	@Override
	public boolean isEnemy() {
		// TODO Auto-generated method stub
		return true;
	}




}
