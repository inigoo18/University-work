package trade.model.test;

import static org.junit.Assert.*;

import org.junit.Test;

import trade.model.Food;
import trade.model.FoodList;
import trade.model.Fruit;
import trade.model.Inventory;
import trade.model.Meat;
import trade.model.Place;
import trade.model.PlaceList;

public class InventoryTest {
	@Test
	public void test() {
		Inventory inv = new Inventory("Test1", 0);
		Food food = new Fruit(FoodList.APPLE);
		Food food2 = new Fruit(FoodList.PITAYA);
		Food food3 = new Meat(FoodList.OX);
		
		inv.addItem(food);
		inv.addItem(food2);
		
		int res = 0;
		res += food.getCost(null);
		res += food2.getCost(null);
		
		inv.sumUp(null);

		assertEquals(inv.getCash(), res);
		
		
		inv.addItem(food3);
		
		Food findingFood = inv.findItem("Ox");
		assertEquals(findingFood, food3);
		
		inv.addItem(findingFood);
		
		int energyBefore = inv.getEnergy();
		inv.loseEnergy(1);
		assertNotEquals(inv.getEnergy(), energyBefore);
		
	}
		
}