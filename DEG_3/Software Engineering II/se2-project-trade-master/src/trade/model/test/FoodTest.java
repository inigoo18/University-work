package trade.model.test;

import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import trade.model.Food;
import trade.model.FoodList;
import trade.model.Fruit;
import trade.model.Season;
import world.creation.factory.ElementFactory;
import world.creation.factory.ElementFactoryImpl;

public class FoodTest {
	@Test
	public void test() {
		Food food = new Fruit(FoodList.APPLE);
		int normalPrice = food.getCost(null);
		Season season = Season.SPRING;
		int changedPrice = food.getCost(season); // +50%
		assertNotEquals(normalPrice, changedPrice);
		
		int expiration = food.getExpiration();
		food.expire();
		int loweredExp = food.getExpiration();
		assertNotEquals(expiration, loweredExp);
		
		ElementFactory elem = new ElementFactoryImpl();
		Food newFood = elem.buildFood(FoodList.APPLE);
		assertTrue(newFood.getName().equalsIgnoreCase("Apple"));
		
	}
}
