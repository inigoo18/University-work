package trade.creation.builder.test;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import trade.creation.builder.WorldBuilder;
import trade.creation.builder.WorldBuilderImpl;
import trade.creation.builder.WorldDataParser;
import trade.model.Place;
import trade.model.World;

public class WorldRandomGenerationTest {
	
	@Test
	public void test() {
		
		WorldBuilder builder = new WorldBuilderImpl();
		
		WorldDataParser dataParser = new WorldDataParser(builder);
		
		dataParser.worldCreator(0);
		
		World world = builder.getWorld();
		
		Place cityPlace = world.findPlace("City"); // Must at least be 1 city
		
		assertTrue(cityPlace.getType().equalsIgnoreCase("city"));
		
		System.out.println("-=-=-=- TEST -=-=-=-");
		
		System.out.println(world.report()); // prints how the different places have been generated. This method won't be used outside of this class.
		
	}
}
