package pt.iscte.poo.starterpack;
import pt.iscte.poo.utils.Point2D;

import java.util.ArrayList;
import java.util.List;

import pt.iscte.poo.gui.ImageMatrixGUI;

public class HealthBar {

	private int hearts, maxHearts;
	private double health, maxHealth;

	List<GameElement> colors = new ArrayList<>();

	public HealthBar() {

		this.maxHearts = 5;
		this.maxHealth = 10;

		calculateHearts(maxHealth,(int)health);
	}


	public void setHealth(double health) {

		if(this.health>=0) {

			this.health = health;
			double maxHealth = Math.max(health, this.maxHealth);

			calculateHearts(maxHealth, (int)health);
		}
	}

	public void setMaxHealth(double maxHealth, int health) {

		this.maxHealth = maxHealth;
		calculateHearts(maxHealth, health);
	}

	public void setMaxHearts(int maxHearts,int health) {

		this.maxHearts = maxHearts;
		calculateHearts(maxHealth,health);
	}

	private void calculateHearts(double maxHealth, int health) {

		double heartValue = maxHealth / maxHearts;
		this.hearts = (int) Math.ceil(health / heartValue);
	}

	public void loadhealthBar() {
		for(int i=0; i<=4; i++){
			colors.add(new Color(new Point2D(i,10),"Green"));
		}
		GameEngine.getInstance().addMultipleGuiElements(colors);
	}

	public void updateHealthBar(int health) {



		double heartValue = maxHealth / maxHearts;
		this.hearts = (int) Math.ceil(health / heartValue);

		if(this.hearts>=0) {

			double percentage = ((double)health)/maxHealth;

			int numHearts = (int)(percentage * maxHearts);

			System.out.println(numHearts + " ");

			for(int i=numHearts-1; i>=0; i--){
				colors.get(i).setName("Green");
			}

			for(int i=maxHearts-numHearts-1; i>=0; i--){
				colors.get(i).setName("Red");
			}
			
			if(health%2!=0 && health>0)
				colors.get(maxHearts-hearts).setName("RedGreen");

			GameEngine.getInstance().addMultipleGuiElements(colors);
		}
	}
}