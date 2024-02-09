package pt.iscte.poo.starterpack;

import pt.iscte.poo.utils.Point2D;

public class Door extends GameElement {

	private String DRoom;
	private Point2D DPos;
	private String KeyID;

	public Door(int x, int y, String DRoom, int xdest, int ydest, String KeyID) {
		
		super(new Point2D(x,y), "DoorOpen", 1);
		this.DRoom=DRoom;
		this.DPos= new Point2D(xdest,ydest);
		this.KeyID=KeyID;

		if(KeyID!="") {
			setName("DoorClosed");
		}
	}
	
	public void setOpen() {
		setName("DoorOpen");
	}


	public String getKeyID() {
		return KeyID;
	}

	public String getDRoom() {
		return DRoom;
	}

	public Point2D getDPos() {
		return DPos;
	}

	public void setDPos(Point2D dPos) {
		DPos = dPos;
	}
}
