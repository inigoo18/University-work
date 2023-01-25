package world.creation.factory;

import trade.model.Food;
import trade.model.FoodList;

public interface ElementFactory {

	public Food buildFood(FoodList elem);
	
}
