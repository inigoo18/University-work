package world.creation.factory;

import trade.model.Cereal;
import trade.model.Fish;
import trade.model.Food;
import trade.model.FoodList;
import trade.model.Fruit;
import trade.model.Meat;

public class ElementFactoryImpl implements ElementFactory {

	public ElementFactoryImpl() {
		
	}
	
	@Override
	public Food buildFood(FoodList elem) {
		Food food = null;
		if (elem != null) {
			String type = elem.getType();
			if (type.equalsIgnoreCase("Meat")){
				food = new Meat(elem);
			}
			else if (type.equalsIgnoreCase("Fish")) {
				food = new Fish(elem);
			}
			else if (type.equalsIgnoreCase("Fruit")) {
				food = new Fruit(elem);
			}
			else if (type.equalsIgnoreCase("Cereal")) {
				food = new Cereal(elem);
			}
		}
		return food;
	}

	
	
}
