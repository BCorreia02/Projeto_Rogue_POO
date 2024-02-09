package pt.iscte.poo.starterpack;

import pt.iscte.poo.utils.Point2D;

public class Key extends GameElement implements Collectable{
	
	private String ID;
	private boolean used;

	public Key(int x, int y, String ID) {
		super(new Point2D(x,y),"Key",1);
		this.ID=ID;
	}
	
	public String getID() {
		return this.ID;
	}
	
	public void setUsed(boolean used) {
		this.used = used;
	}

	public boolean isUsed(){
		return used;
	}

}
