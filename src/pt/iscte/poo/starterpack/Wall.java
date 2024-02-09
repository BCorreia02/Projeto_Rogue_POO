package pt.iscte.poo.starterpack;

import pt.iscte.poo.utils.Point2D;

public class Wall extends GameElement implements Through{
	
	public Wall(Point2D position){
		super(position,"Wall",1);
	}

}
