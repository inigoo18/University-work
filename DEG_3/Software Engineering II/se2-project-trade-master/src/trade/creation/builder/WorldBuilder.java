package trade.creation.builder;

import trade.model.Food;
import trade.model.PlaceList;
import trade.model.World;

// Builder

public interface WorldBuilder {

	public World getWorld();
	
	public void buildPlace(PlaceList place);
	
	public void buildExit(int placeFrom, String whichWay, int placeTo);
	
	public void buildPlayer(String name, int ID);
	
	public void addFood(Food food, int place);
	
	
	
}
