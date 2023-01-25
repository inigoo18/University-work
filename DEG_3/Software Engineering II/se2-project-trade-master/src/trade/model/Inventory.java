package trade.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Inventory {
	
	private String name;
	private int ID;
	private int money;
	private List<Food> inventory;
	private Place currentPlace;
	private int energy;
	private int maxEnergy;
	private boolean alive;
	private boolean AI;
	private boolean move;
	private boolean pickingUp;
	private Direction dir;
	
	public Inventory(String name, int ID) {
		this.name = name;
		money = 0;
		this.ID = ID;
		inventory = new ArrayList<Food>();
		maxEnergy = 15;
		energy = maxEnergy;
		alive = true;
		AI = false;
		move = true;
		pickingUp = false;
	}
	
	public void addItem(Food food) {
		if (energy > 0) {
			//inventory[inventoryCounter] = food;
			inventory.add(food);
			if (currentPlace != null) {
				currentPlace.pickedUp();
			}	
		}
	}
	
	public int getID() {
		return ID;
	}
	
	public void setAI(boolean bool) {
		AI = bool;
	}
	
	public boolean getAI() {
		return AI;
	}
	
	public void loseEnergy(int energyLost) {
		energy -= energyLost;
	}
	
	public int getCash() {
		return money;
	}
	
	public int getEnergy() {
		return energy;
	}
	
	public void removeItem(Food item) {
		inventory.remove(item);
	}
	
	public Food findItem(String name) {
		for (int i = 0; i < inventory.size(); i++) {	
			if (inventory.get(i).name.equalsIgnoreCase(name)) {
				return inventory.get(i);
			}
		}
		return null;
	}
	
	public String print() {
		String res = "";
		res += ("INVENTORY: " + name + " / Cash: " + money);
		res += "\n";
		for (Food f: inventory) {
			res += (" - " + f.name + "(" + f.expiration + ")");
			res += "\n";
		}
		res += ("Located in: " + currentPlace.getName());
		res += "\n";
		res += ("Energy: " + energyToText());
		res += "\n";
		res += (getExits());
		res += "\n";
		return res;
	}
	
	private String energyToText() {
		String result = "[";
		if (energy <= 0) {
			result = "[DONE";
		}
		else {
			for (int i = 1; i <= maxEnergy; i++) {
				if (i <= energy) {
					result = result + "|";
				}
				else {
					result = result + " ";
				}
			}
		}
		result = result + "]";
		return result;
	}
	
	public boolean move() { // put test here
		if (move) {
			if (energy > 0) {
				Place temp = currentPlace;
				Place dest = currentPlace.getExitDestination(dir);
				if (dest != null && dest != temp) {
					if (dest != temp) {
						dest.add(this);
						currentPlace = dest;
					}
					energy -= dest.getEnergyLoss();
				}
			}
			else {
				if (alive) {
					alive = false;
					energy = 0;
					return true;
				}
			}
		}
		return false;
	}
	
	public boolean pickingUp() {
		if (pickingUp) {
			loseEnergy(1);
		}
		return pickingUp;
	}
	
	public void setPickingUp(boolean bool) {
		pickingUp = bool;
	}
	
	public int getMaxEnergy() {
		return maxEnergy;	
	}
	
	public String getExits() {
		return currentPlace.getExits();
	}
	
	public Place getCurrentPlace() {
		return currentPlace;
	}
	
	public void setCurrentPlace(Place place) {
		place.add(this);
		currentPlace = place;
	}
	
	public boolean isAlive() {
		return alive;
	}
	
	public void sumUp(Season season) {
		for (Food f: inventory) {
			money += f.getCost(season);
		}
		inventory.clear();
	}
	
	public String getName() {
		return name;
	}
	
	public List<Food> getInventory(){
		return inventory;
	}
	
	public void expireFood() {
		for (Food f: inventory) {
			f.expire();
		}
		for (int i = 0; i < inventory.size(); i++) {
			if ( inventory.get(i).getExpiration() <= 0) {
				removeItem(inventory.get(i));
			}
		}
	}
	
	public boolean getMove() {
		return move;
	}
	
	public void setMove(boolean bool) {
		move = bool;
	}
	
	public void setDir(Direction dir) {
		this.dir = dir;
	}
	
	public Direction getDir() {
		return dir;
	}
	
	public void AI() {
		if (getAI()) {
			if (currentPlace.getType().equalsIgnoreCase("city") || currentPlace.getQuantity() <= 0) {
				move = true;
				pickingUp = false;
			}
			else {
				Random rand = new Random();
				int prob = rand.nextInt(2);
				if (prob == 1) {
					move = false;
					pickingUp = true;
				}
				else {
					move = true;
					pickingUp = false;
				}
			}
			if (move) {
				AIDir();
			}
		}
	}
	
	public Direction AIDir() {
		Direction dir = null;
		if (AI && move) {
			Place placeToGo = null;
			do {
				dir = Direction.randomDir();
				placeToGo = getCurrentPlace().getExitDestination(dir);
			}while(placeToGo == null);
			setDir(dir);
		}
		return dir;
	}
	
}