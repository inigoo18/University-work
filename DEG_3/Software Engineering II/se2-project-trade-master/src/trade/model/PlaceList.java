package trade.model;

import java.awt.Color;
import java.util.Random;

public enum PlaceList {
	
	CITY(1, null, 0, Color.GRAY),
	BEACH(1, FoodList.PITAYA, 5, new Color(252, 251, 229)),
	FOREST(2, FoodList.APPLE, 5, new Color(3,57,34)),
	MOUNTAIN(3, FoodList.OX, 3, Color.PINK),
	DESERT(3, FoodList.QUINUA, 5, Color.ORANGE),
	RIVER(1, FoodList.HAKE, 8, Color.CYAN),
	LAKE(2, FoodList.SALMON, 5, Color.BLUE),
	LAGOON(3, FoodList.CRAYFISH, 3, new Color(0,3,91)),
	PLAINS(1, FoodList.CHICKEN, 8, Color.GREEN),
	FARM(1, FoodList.WHEAT, 5, new Color(155,135,12)),
	SAVANNAH(2, FoodList.COW, 5, Color.RED);

	
	private int energyLoss;
	private FoodList drop;
	private int quantity;
	private Color color;
	
	private PlaceList(int energyLoss, FoodList dropName, int quantity, Color GUIColor) {
		this.energyLoss = energyLoss;
		drop = dropName;
		this.quantity = quantity;
		this.color = GUIColor;
	}
	
	public Color getColor() {
		return color;
	}
	
	public int getEnergyLoss() {
		return energyLoss;
	}
	
	public String getName() {
		return name();
	}
	
	public FoodList getFood() {
		return drop;
	}
	
	public void pickedUp() {
		quantity--;
	}
	
	public int getQuantity() {
		return quantity;
	}
}