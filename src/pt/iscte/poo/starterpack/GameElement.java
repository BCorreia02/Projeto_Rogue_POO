package pt.iscte.poo.starterpack;

import pt.iscte.poo.utils.Point2D;
import pt.iscte.poo.gui.ImageTile;

/**
 * @author berna
 *
 */

public abstract class GameElement implements ImageTile{


	private String name;
	private Point2D position;
	private final int layer;



	public GameElement(Point2D position, String name, int layer) {
		this.position = position;
		this.name=name;
		this.layer=layer;
	}
	
	public Point2D getPosition() {
		return position;
	}

	public void setPosition(Point2D position) {
		this.position = position;
	}
	
	public void setName(String name) {
		this.name=name;
	}

	public String getName() {
		return name;
	}
	
	public int getLayer() {
		return layer;
	}
}