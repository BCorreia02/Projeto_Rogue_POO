package pt.iscte.poo.starterpack;

import pt.iscte.poo.utils.Point2D;

public class Color extends GameElement{

	public Color(Point2D position, String name) {
		super(position, name, 1);
	}

	public String getColor() {
		return getName();
	}

	public void setColor(String color) {
		setName(color);
	}

}
