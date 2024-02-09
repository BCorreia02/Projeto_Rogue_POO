package pt.iscte.poo.starterpack;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
import java.util.function.Predicate;

import pt.iscte.poo.gui.ImageMatrixGUI;
import pt.iscte.poo.observer.Observed;
import pt.iscte.poo.observer.Observer;
import pt.iscte.poo.utils.Point2D;
import pt.iscte.poo.utils.Vector2D;
import pt.iscte.poo.utils.Direction;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.PrintWriter;


public class GameEngine implements Observer {

	public static final int GRID_HEIGHT = 11;
	public static final int GRID_WIDTH = 10;

	private static GameEngine INSTANCE = null;
	private ImageMatrixGUI gui = ImageMatrixGUI.getInstance();
	private List<GameElement> usedKeys = new ArrayList<>();
	private List<Room> rooms= new ArrayList<>();;
	private int turns;
	private String room = "rooms/room0.txt";
	private Hero hero = new Hero();
	private Score score;
	public int counter;

	public List<Room> getRooms() {
		return rooms;
	}

	public String getRoom() {
		return room;
	}

	public List<GameElement> getUsedKeys() {
		return usedKeys;
	}

	public Hero getHero() {
		return hero;
	}

	public <T> boolean CheckObj(Point2D p, Predicate<GameElement> pred) {
		for(GameElement game : getCurRoom().getTileList()) {
			if(p.equals(game.getPosition()) && pred.test(game)) 
				return true;
		}
		return false;
	}

	public boolean canMoveTo(Point2D p) {

		if(GameEngine.getInstance().CheckObj(p, b -> b instanceof Through)) return false;
		if(p.getX()>=10 || p.getY()>=10 ) return false;
		return true;
	}


	public static GameEngine getInstance() {
		if (INSTANCE == null)
			INSTANCE = new GameEngine();
		return INSTANCE;
	}

	private GameEngine() {		
		gui.registerObserver(this);
		gui.setSize(GRID_WIDTH, GRID_HEIGHT);
		gui.go();
	}


	public Point2D EnemyDirection(Point2D HeroPos, Point2D EnemyPos) {
		return EnemyPos.plus(Vector2D.movementVector(EnemyPos, HeroPos));
	}

	public void moverInimigos(Direction dir){
		for(GameElement a: getCurRoom().getTileList() ) {
			if(a instanceof Enemy) 
				((Enemy) a).move();

		}
	}



		public int getTurns() {
			return turns;
		}


		public Room getCurRoom(){
			Room aux=null;
			for (Room a : rooms){
				if(a.getName().equals(room)){
					aux=a;
				}
			}
			return aux;
		}

		public void start(){

			score= new Score();
			loadRooms(4);
			sendImagesToGUI();
			gui.addImage(hero);
			hero.getHealthbar().loadhealthBar();
			gui.setStatusMessage("ROGUE Starter Package - Turns:" + turns + " - Health: "+ hero.getHealth() +"  - Score: "+ counter+ " - Jogador: " + score.getJogador());
			gui.update();
		}

		public void openDoors() {
			for(Room r: rooms) {
				for(GameElement a: r.getTileList()) {
					if(a instanceof Door) {
						for(GameElement b: hero.getInventory()) {
							if(b instanceof Key) {
								if(((Key) b).getID().equals(((Door) a).getKeyID()) && ((Key) b).isUsed()) 
									((Door) a).setOpen();
							}
						}
					}
				}
			}
		}



		public void setRoom(String room) {
			this.room="rooms/"+room+".txt";
		}

		public void loadRooms(int nRooms){

			for(int i=0; i<=nRooms-1; i++) {

				String roomName= "rooms/room" + i + ".txt";

				List<GameElement> elements = new ArrayList<>();

				for (int x=0; x!=10; x++)
					for (int y=0; y!=10; y++)
						elements.add(new Floor(new Point2D(x,y)));

				try {
					Scanner sc= new Scanner(new File(roomName));
					int linha=0;
					while(sc.hasNextLine()){
						if(linha<10) {
							String line=sc.nextLine();
							for (int x=0; x<line.length(); x++){
								if(line.charAt(x) == '#')
									elements.add(new Wall(new Point2D(x,linha)));
							}
							linha++;
						}

						if(linha==10) {
							sc.nextLine();
							linha++;
						}

						if(linha>=10) {

							String parts[]= sc.nextLine().split(",");
							String nome = parts[0];
							int posx= Integer.parseInt(parts[1]);
							int posy= Integer.parseInt(parts[2]);

							if(nome.equals("Door")) {

								if(parts.length==7) 
									elements.add(new Door(Integer.parseInt(parts[1]),Integer.parseInt(parts[2]),parts[3],Integer.parseInt(parts[4]),Integer.parseInt(parts[5]),parts[6]));
								else 
									elements.add(new Door(Integer.parseInt(parts[1]),Integer.parseInt(parts[2]),parts[3],Integer.parseInt(parts[4]),Integer.parseInt(parts[5]),""));
							}


							if(nome.equals("Bat")) 
								elements.add(new Bat(new Point2D(posx,posy)));

							if(nome.equals("HealingPotion")) 
								elements.add(new HealingPotion(new Point2D(posx,posy)));

							if(nome.equals("Armor")) 
								elements.add(new Armor(new Point2D(posx,posy)));

							if(nome.equals("Skeleton")) 
								elements.add(new Skeleton(new Point2D(posx,posy)));

							if(nome.equals("Sword")) 
								elements.add(new Sword(new Point2D(posx,posy)));

							if(nome.equals("Treasure")) 
								elements.add(new Treasure(new Point2D(posx,posy)));

							if(nome.equals("Key")) 
								elements.add(new Key(posx,posy,parts[3]));

							if(nome.equals("Thug")) 
								elements.add(new Thug(new Point2D(posx,posy)));

							if(nome.equals("Scorpio")) 
								elements.add(new Scorpio(new Point2D(posx,posy)));

							if(nome.equals("Thief")) 
								elements.add(new Thief(new Point2D(posx,posy)));

						}
					}

					sc.close();

					for(int a=0;a<3;a++) {
						elements.add(new Color(new Point2D(7+a,10),"Black"));
					}


				}catch(FileNotFoundException e){
					System.err.println("Impossivel abrir o ficheiro de texto");
				}

				this.rooms.add(new Room(roomName,elements));
			}
		}

		public List<GameElement> getElementsInPos (Point2D pos){
			ArrayList<GameElement> elements = new ArrayList<>();
			for(GameElement a : getCurRoom().getTileList()){			
				if(a.getPosition().equals(pos)) 
					elements.add(a);
			}
			return elements;		
		}



		public void sendImagesToGUI() {

			gui.clearImages();
			gui.addImage(hero);

			for (Room a : rooms){
				if(a.getName().equals(room)){
					for (GameElement gameElement : a.getTileList()){
						gui.addImage(gameElement);
					}

					for(GameElement b: hero.getInventory()) {
						if(b!=null)
							gui.addImage(b);
					}
				}
			}

			gui.update();
		}



		public void addMultipleElements(List<GameElement> aAdicionar){
			for (GameElement gameElement : aAdicionar) {
				addElement(gameElement);
			}
			gui.update();
		}


		public void addListElement(GameElement a) {

		}

		public void removeListElement(GameElement a) {
			getCurRoom().getTileList().remove(a);
		}

		public void addGuiElement(GameElement game){
			gui.addImage(game);
			gui.update();
		}

		public void removeGuiElement(GameElement game){
			gui.removeImage(game);
			gui.update();
		}

		public void updateInvItems(GameElement a) {
			if(hero.getInventory().size()<=3) {
				for(int i=0; i<3; i++) {
					if(hero.getInventory().get(i)==null && !(a instanceof Floor)) {
						hero.getInventory().set(i,a);
						a.setPosition(new Point2D(7+i,10));
						break;
					}
				}
			}
		}

		public void removeElement(GameElement a) {
			gui.removeImage(a);
			getCurRoom().getTileList().remove(a);
		}

		public void addElement(GameElement a) {
			gui.addImage(a);
			getCurRoom().getTileList().add(a);
		}


		public void RemoveMultipleElements(List<GameElement> aRemover){
			for (GameElement gameElement : aRemover) {
				removeElement(gameElement);
			}
			gui.update();   
		}

		public void RemoveMultipleGuiElements(List<GameElement> aRemover){
			for (GameElement gameElement : aRemover) {
				gui.removeImage(gameElement);
			}
			gui.update();   
		}

		public void addMultipleGuiElements(List<GameElement> aAdicionar){
			for (GameElement gameElement : aAdicionar) {
				addGuiElement(gameElement);
			}
			gui.update();
		}

		public void winGame() {
			updateScore(score.getPontos());
			ImageMatrixGUI.getInstance().setMessage("Ganhaste o jogo");
			ImageMatrixGUI.getInstance().dispose();
			ImageMatrixGUI.getInstance().update();
		}


		public void endGame() {
			ImageMatrixGUI.getInstance().setMessage("Perdeste o jogo");
			ImageMatrixGUI.getInstance().dispose();
			ImageMatrixGUI.getInstance().update();
		}

		public void kill() {

			List<GameElement> aRemover = new ArrayList<>();
			GameElement StolenItem= null;

			for(GameElement a: getCurRoom().getTileList()) {

				if(a instanceof Damageable && ((Damageable) a).getHealth()<=0) {

					aRemover.add(a);
					counter++;

					if(a instanceof Thief && ((Thief) a).getStolenItem()!=null) {
						aRemover.add(a);
						((Thief) a).getStolenItem().setPosition((a.getPosition()));
						StolenItem=((Thief) a).getStolenItem();
					}


				}
			}

			if(hero.getHealth()<=0) 
				endGame();

			if(StolenItem!=null)
				addElement(StolenItem);

			RemoveMultipleElements(aRemover);

		}

		public void usePot() {
			if(hero.getInventory().size()>=0)
				for(GameElement a : hero.getInventory()) {
					if(a instanceof HealingPotion) {
						hero.heal();
						gui.removeImage(a);
						a=null;
					}
				}
			hero.getHealthbar().updateHealthBar((int)hero.getHealth());
		}
		
		public void updateScore(int score) {
			 List<Integer> numbers = new ArrayList<>();
		        try (BufferedReader reader = new BufferedReader(new FileReader("Scores.txt"))) {
		            String line;
		            while ((line = reader.readLine()) != null) {
		                numbers.add(Integer.parseInt(line));
		            }
		        } catch (Exception e) {
		            e.printStackTrace();
		        }
		        
		        numbers.add(score);

		        Collections.sort(numbers, Collections.reverseOrder());
		        
		        try (PrintWriter pw = new PrintWriter("Scores.txt")) {
		            for (int number : numbers) {
		                pw.println(number);
		            }
		            pw.close();
		        } catch (Exception e) {
		            e.printStackTrace();
		        }

		        
		        // Print the sorted numbers
		        for (int number : numbers) {
		            System.out.println(number);
		        }
		    }
		

		public void removeInvItem(int i){
			if(hero.getInventory().get(i)!=null) {
				if(i<hero.getInventory().size()) {
					hero.getInventory().get(i).setPosition(hero.getPosition());
					getCurRoom().getTileList().add(hero.getInventory().get(i));
					gui.addImage(hero.getInventory().get(i));
					hero.getInventory().set(i,null);
				}
				if(hero.getInventory().get(i) instanceof Sword) {
					hero.setDamage(1);
				}

				if(hero.getInventory().get(i) instanceof Armor) {
					hero.setShield(1);
				}
			}
		}

		@Override
		public void update(Observed source) {

			int key = ((ImageMatrixGUI) source).keyPressed();

			if(Direction.isDirection(key)){

				Direction direction = Direction.directionFor(key);

				if (key >= KeyEvent.VK_LEFT || key <= KeyEvent.VK_RIGHT) {
					hero.move(direction);
					hero.getHealthbar().updateHealthBar((int)hero.getHealth());
					kill();
					turns++;

				}
			}

			else if(key == KeyEvent.VK_E)
				removeInvItem(2);
			else if(key == KeyEvent.VK_Q)
				removeInvItem(0);
			else if(key == KeyEvent.VK_W)
				removeInvItem(1);
			else if(key == KeyEvent.VK_1 || key == KeyEvent.VK_2 || key == KeyEvent.VK_3)
				usePot();



			gui.setStatusMessage("ROGUE Starter Package - Turns:" + turns + " - Health: "+ hero.getHealth() +"  - Score: "+ counter+ " - Jogador: " + score.getJogador());
			gui.update();
		}

	}
