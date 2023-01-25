package trade.model.test;

import static org.junit.Assert.*;

import org.junit.Test;

import trade.creation.builder.WorldBuilder;
import trade.creation.builder.WorldBuilderImpl;
import trade.creation.builder.WorldDataParser;
import trade.model.Inventory;
import trade.model.Place;
import trade.model.PlaceList;
import trade.model.World;


public class WorldTest {

	
	@Test
	public void test() {
		
		WorldBuilder builder = new WorldBuilderImpl();
		builder.buildPlace(PlaceList.BEACH);
		
		Inventory player = new Inventory("TestPlayer", 0);
		
		WorldDataParser dataParser = new WorldDataParser(builder);
		dataParser.worldCreator(0);
		
		World world = builder.getWorld();
		Place place = world.findPlace("Beach");
		
		world.addPerson(player);
		player.setCurrentPlace(place);
		player.setPickingUp(true);
		world.pickUp();
		
		assertEquals(player.getInventory().size(), 1);
		
	}
	
	
}
