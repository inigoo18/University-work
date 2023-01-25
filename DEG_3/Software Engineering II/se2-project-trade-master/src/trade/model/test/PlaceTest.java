package trade.model.test;

import org.junit.*;
import static org.junit.Assert.*;

import trade.model.Direction;
import trade.model.Inventory;
import trade.model.Place;
import trade.model.PlaceList;

public class PlaceTest {

	@Test
	public void test() {
		Place place = new Place(PlaceList.CITY);
		Place place2 = new Place(PlaceList.BEACH);
		Inventory inv = new Inventory("PlaceTester", 1);
		
		place.setExitDestination(Direction.NORTH, place2);
		
		inv.setCurrentPlace(place2);
		assertTrue(place.add(inv));
		assertTrue(place.remove(inv));
		
		place.add(inv);
		
		
		assertTrue(place.getExitDestination(Direction.NORTH).equals(place2));
	
		inv.setDir(Direction.NORTH);
		inv.setMove(true);
		inv.move();
		assertEquals(inv.getCurrentPlace(), place2);
	
	}
	
}
