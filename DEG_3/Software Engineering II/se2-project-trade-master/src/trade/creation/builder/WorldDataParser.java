package trade.creation.builder;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import misc.Pair;
import trade.model.Direction;
import trade.model.PlaceList;
import world.creation.factory.ElementFactory;
import world.creation.factory.ElementFactoryImpl;

// Director

public class WorldDataParser {
	
	WorldBuilder builder;
	
	ElementFactory elementFactory;
	
	List<PlaceList> map;
	
	public WorldDataParser() {
		map = new ArrayList<PlaceList>();
		
		elementFactory = new ElementFactoryImpl();
	}
	
	public WorldDataParser(WorldBuilder build) {
		this();
		builder = build;
	}
	
	
	public void worldCreator(int seed) {
		Random rand = new Random(seed);
		PlaceList[] availablePlaces = PlaceList.values();
		
		int length = rand.nextInt(4) + 5;
		
		map.add(PlaceList.CITY);
		for (int i = 1; i < length; i++) {
			int nextInt = rand.nextInt(availablePlaces.length);
			map.add(availablePlaces[nextInt]);
		}
		for (int i = 0; i < map.size(); i++) {
			builder.buildPlace(map.get(i));
			builder.addFood(elementFactory.buildFood(map.get(i).getFood()), i);
		}
		Direction lastDir = null;
		Direction dir;
		for (int i = 0; i < map.size()-2; i++) {
			do{
				dir = Direction.randomDir();
			}while(dir == lastDir);
			lastDir = DirectionEncoder.convertOppositeDirection(dir.name());
			builder.buildExit(i, dir.name(), i+1);
		}
		for (int i = map.size() - 2; i < map.size(); i++) {
			dir = Direction.randomDir();
			builder.buildExit(i, dir.name(), -1);
		}
	}
	
	public void buildPlayer(String name, int ID) {
		builder.buildPlayer(name, ID);
	}
	
}