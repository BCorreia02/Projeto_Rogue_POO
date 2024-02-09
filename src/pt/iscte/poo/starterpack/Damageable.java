package pt.iscte.poo.starterpack;

public interface Damageable {
	
	public boolean isEnemy();
	
	public double getDamage();
	
	public void takeDamage(double damage);
	
	public double getHealth();

}
