package trade.model;

import java.util.ArrayList;
import java.util.List;

public class World implements Observable<TradeObserver> {
	
	private transient static World instance = new World();
	
	
	private boolean isFinished;
	private boolean done;
	private List<Inventory> people;
	private List<Place> worldPlaces;
	private Season actualSeason;
	private List<TradeObserver> observers;
	
	private World() {
		people = new ArrayList<Inventory>();
		worldPlaces = new ArrayList<Place>();
		isFinished = false;
		done = false;
		actualSeason = Season.randomSeason();
		observers = new ArrayList<TradeObserver>();
	}
	
	public static World getInstance() {
		if (instance == null) {
			instance = new World();
		}
		return instance;
	}
	
	public boolean addToWorld(Inventory player) {
		boolean result = people.add(player);
		if (result) {
			player.setCurrentPlace(findPlace("City"));
		}
		return result;
	}
	
	public boolean addToWorld(Place place) {
		boolean result = worldPlaces.add(place);
		return result;
	}
	
	public void pickUp() {
		for (Inventory p: people) {
			if (p.pickingUp()) {
				Place place = p.getCurrentPlace();
				if (place.getQuantity() > 0) {
					p.addItem(place.getFood());
				}
				p.setPickingUp(false);
			}
		}
	}
	
	public void AI() {
		for (Inventory p : people) {
			if (p.getAI()) {
				p.AI();
			}
		}
	}
	
	
	public boolean isDone() {
		return done;
	}
	
	public void advance() {
		if (!done) {
			if (!isFinished) {
				notifyAdvance();
				AI();
				move();
				checkSelling();
				pickUp();
				checkIfAlive(); 
				expireFoods();
				actualSeason = actualSeason.nextSeason();
			}
			if (isFinished) { // gotta rethrow the if statement for the last player
				String output = "";
				output += "RANKINGS" + "\n";
				String winnerName = "";
				int maxPrice = 0;
				for (Inventory p: people) {
					int pMoney = p.getCash();
					output += (p.getName() + " - " + pMoney) + "\n";
					if (pMoney > maxPrice) {
						winnerName = p.getName();
						maxPrice = pMoney;
					}
				}
				output += "Winner is " + winnerName;
				sendMsg(output);
				done = true;
			}
		}
	}
	
	public void checkIfAlive() {
		int counter = 0;
		for (Inventory p: people) {
			if (p.getEnergy() <= 0) {
				counter++;
			}
		} 
		if (counter == people.size()) {
			isFinished = true;
		}
	}
	
	public void addPerson(Inventory inventory) {
		people.add(inventory);
		Place place = findPlace("City");
		inventory.setCurrentPlace(place);
	}
	
	public void removePerson(Inventory inventory) {
		people.remove(inventory);
	}
	
	public void clear() {
		people.clear();
	}
	
	public void move() {
		for (Inventory p: people) {
			boolean bool;
			bool = p.move();
			if (bool) {
				sendMsg(p.getName() + " has fallen and can't get up!");
			}
		}
	}
	
	public void checkSelling() {
		for (Inventory p : people) {
			if (p.getCurrentPlace().getType().equalsIgnoreCase("City")) {
				p.sumUp(actualSeason);
			}
		}
	}
	
	public void sendMsg(String string) {
		notifyMessagers(string);
	}
	
	public void expireFoods() {
		for (Inventory i : people) {
			i.expireFood();
		}
	}
	
	public Place findPlace(String name) {
		for (int i = 0; i < worldPlaces.size(); i++) {
			if (worldPlaces.get(i).getType().equalsIgnoreCase(name)) {
				return worldPlaces.get(i);
			}
		}
		return null;
	}
	
	public String report() {
		String res = "";
		res += "Total num of Places: " + worldPlaces.size() + "\n";
		for (Place p : worldPlaces) {
			res += p.getName() + " - " + p.getExits();
			res += "\n";
		}
		
		res += "Players: " + "\n";
		for (Inventory inv : people) {
			res += inv.getName() + "\n";
		}
		return res;
	}

	@Override
	public void addObserver(TradeObserver o) {
		observers.add(o);
		notifyRegister();
		
	}

	@Override
	public void removeObserver(TradeObserver o) {
		observers.remove(o);
		
	}
	
	private void notifyAdvance() {
		for (TradeObserver obs: observers) {
			obs.onAdvance(people, worldPlaces, actualSeason);
		}
	}
	
	private void notifyRegister() {
		for (TradeObserver obs: observers) {
			obs.onRegister(people, worldPlaces, actualSeason);
		}
	}
	
	private void notifyMessagers(String str) {
		for (TradeObserver obs: observers) {
			obs.onMessage(str);
		}
	}
	
}