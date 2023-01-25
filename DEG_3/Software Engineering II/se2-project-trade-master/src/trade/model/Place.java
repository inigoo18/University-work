package trade.model;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import world.creation.factory.ElementFactory;
import world.creation.factory.ElementFactoryImpl;

public class Place {

	private String name;
	private String typeofPlace; // maybe we can work with this for a new pattern
	private Food food;
	private int quantity;
	private int energyLoss;
	private List<Inventory> players;
	private Exits exits;
	private Color color;
	private ElementFactory foodFact;
	
	
	
	public Place(PlaceList place) {
		name = place.getName();
		typeofPlace = place.getName();
		this.energyLoss = place.getEnergyLoss();
		this.quantity = place.getQuantity();
		this.exits = new Exits();
		this.players = new ArrayList<Inventory>();
		this.food = null;
		this.color = place.getColor();
		foodFact = new ElementFactoryImpl();
	}
	
	// Adds a new player to the room and removes it from the one they were currently at.
	public boolean add(Inventory player) {
		boolean result = players.add(player);
		Place room = player.getCurrentPlace();
		if (room != null) {
			room.remove(player);
		}
		return result;
	}
	
	public boolean remove(Inventory player) {
		//System.out.println(player.getName() + " removed from place: " + this.getName());
		return this.players.remove(player);
	}
	
	public List<Inventory> getPlayers(){
		return players;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public Color getColor() {
		return color;
	}
	
	public Place getExitDestination(Direction dir) {
		return exits.getDestination(dir);
	}
	
	public void setExitDestination(Direction dir, Place destination) {
		exits.setDestination(dir, destination);
	}
	
	public String getType() {
		return typeofPlace;
	}
	
	public int getEnergyLoss() {
		return energyLoss;
	}
	
	public String getName() {
		return name;
	}
	
	public void pickedUp() {
		quantity--;
	}
	
	public int getQuantity() {
		return quantity;
	}
	
	public Food getFood() { // method needed because we need to create a clone of the food.
		Food newFood = null;
		if (food != null) {
			newFood = foodFact.buildFood(FoodList.parse(food.getName()));
		}
		return newFood;
	}
	
	public String getExits() {
		return exits.getExits();
	}
	
	public int getNumExits() {
		return exits.getNumExits();
	}
	
	public Direction getExitDir() { // mainly for test purposes & NPCs
		return exits.findExit();
	}
	
	public void setFood(Food food) {
		this.food = food;
	}

}
