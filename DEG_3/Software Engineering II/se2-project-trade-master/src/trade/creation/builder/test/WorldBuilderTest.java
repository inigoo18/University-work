package trade.creation.builder.test;

import static org.junit.Assert.*;

import org.junit.Test;

import trade.creation.builder.WorldBuilder;
import trade.creation.builder.WorldBuilderImpl;
import trade.model.Direction;
import trade.model.Place;
import trade.model.PlaceList;
import trade.model.World;

public class WorldBuilderTest {
	

	//public static void main(String[] args) {
	@Test
	public void test() {
		
		WorldBuilder builder = new WorldBuilderImpl();
		
		builder.buildPlace(PlaceList.CITY);
		builder.buildPlace(PlaceList.BEACH);
		builder.buildExit(0, "North", 1);
		
		World world = builder.getWorld();
		
		String type = "City";
		Place foundPlace = world.findPlace(type);
		assertTrue(foundPlace.getType().equalsIgnoreCase(type));
		assertTrue(foundPlace.getExitDestination(Direction.NORTH).getType().equalsIgnoreCase("beach"));
		
	}
}
