package pt.iscte.poo.starterpack;

import java.util.List;

public class Room {
	
	private List<GameElement> tileList;
	private String name;
	
	public List<GameElement> getTileList() {
		return tileList;
	}

	public String getName() {
		return name;
	}

	public Room(String name, List<GameElement> tileList) {
		this.tileList = tileList;
		this.name=name;
	}

}
