package trade.creation.builder;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import trade.model.Direction;
import trade.model.Food;
import trade.model.Inventory;
import trade.model.Place;
import trade.model.PlaceList;
import trade.model.World;

public class WorldBuilderImpl implements WorldBuilder {

	World worldBeingBuilt;
	
	List<Place> placeSet;
	List<Place> placesAdded;
	
	List<String> desertList;
	List<String> mountainList;
	List<String> cityList;
	List<String> beachList;
	List<String> forestList;
	List<String> riverList;
	List<String> lakeList;
	List<String> plainsList;
	List<String> farmList;
	List<String> lagoonList;
	List<String> savannahList;
	
	String[] desertPlaces = {"Namib Desert", "Sahara Desert", "Thar Desert", "Sonoran Desert", "Antarctic Desert"};
	String[] mountainPlaces = {"Mount Everest", "Mount Pick", "Denali Mountain", "Kilimanjaro Mountain", "Mount Mountain"};
	String[] cityPlaces = {"Madrid", "Burgos", "Paris", "Rome", "Yaizaland"};
	String[] beachPlaces = {"Sassel Beach", "Tamanert Beach", "Sunny Beach", "Doumia Beach", "Pigeon Beach"};
	String[] forestPlaces = {"Black Forest", "Hallerbos Forest", "Stanton Woods", "Otzarreta Woods", "The Sea of Trees"};
	String[] riverPlaces = {"Danubio River", "Tamesis River", "Nilo River", "Rin River", "Misisipi River"};
	String[] lakePlaces = {"Nimrod Lake", "Leech Lake", "Lake Winnebago", "Lake Winnipesaukee", "Reservoir Lake"};
	String[] plainsPlaces = {"Grassy Plains", "Indus Valley", "Gangetic Plains", "Canterbury Plains", "Khuzestan Plains"};
	String[] farmPlaces = {"Waterfall Ranch", "Silverbell Ranch", "Cool Farm", "Pine Farm", "Berta's Farm"};
	String[] lagoonPlaces = {"Blue Lagoon", "Aitutaki Lagoon", "Nanuya Lagoon", "Laguna Colorada", "Crazy Fish Lagoon"};
	String[] savannahPlaces = {"Gonja Savanna", "Lion Savanna", "Bole Savanna", "Sawla Savanna", "Tolon Savanna"};
	
	
	public WorldBuilderImpl() {
		placeSet = new ArrayList<Place>();
		placesAdded = new ArrayList<Place>();
		
		desertList = new ArrayList<String>();
		mountainList = new ArrayList<String>();
		cityList = new ArrayList<String>();
		beachList = new ArrayList<String>();
		forestList = new ArrayList<String>();
		riverList = new ArrayList<String>();
		lakeList = new ArrayList<String>();
		plainsList = new ArrayList<String>();
		farmList = new ArrayList<String>();
		lagoonList = new ArrayList<String>();
		savannahList = new ArrayList<String>();
		
		for (int i = 0; i < desertPlaces.length; i++) {
			desertList.add(desertPlaces[i]);
			mountainList.add(mountainPlaces[i]);
			cityList.add(cityPlaces[i]);
			beachList.add(beachPlaces[i]);
			forestList.add(forestPlaces[i]);
			riverList.add(riverPlaces[i]);
			lakeList.add(lakePlaces[i]);
			plainsList.add(plainsPlaces[i]);
			farmList.add(farmPlaces[i]);
			lagoonList.add(lagoonPlaces[i]);
			savannahList.add(savannahPlaces[i]);
		}
		
		worldBeingBuilt = World.getInstance();
	}
	
	@Override
	public World getWorld() {
		return worldBeingBuilt;
	}

	@Override
	public void buildPlace(PlaceList chosenPlace) {
		Place place = new Place(chosenPlace);
		place.setName(makeName(chosenPlace));
		placeSet.add(place);
		
		
	}
	
	private String makeName(PlaceList place) {
		String name;
		Random rand = new Random();
		int nextInt;
		List<String> stringList;
		
		if (place == PlaceList.CITY) {
			stringList = cityList;
		}
		else if (place == PlaceList.BEACH) {
			stringList = beachList;
		}
		else if (place == PlaceList.DESERT) {
			stringList = desertList;
		}
		else if (place == PlaceList.FOREST) {
			stringList = forestList;
		}
		else if (place == PlaceList.RIVER) {
			stringList = riverList;
		}
		else if (place == PlaceList.LAKE) {
			stringList = lakeList;
		}
		else if (place == PlaceList.PLAINS) {
			stringList = plainsList;
		}
		else if (place == PlaceList.FARM) {
			stringList = farmList;
		}
		else if (place == PlaceList.LAGOON) {
			stringList = lagoonList;
		}
		else if (place == PlaceList.SAVANNAH) {
			stringList = savannahList;
		}
		else{
			stringList = mountainList;
		}
		nextInt = rand.nextInt(stringList.size());
		name = stringList.get(nextInt);
		stringList.remove(nextInt);
		return name;
	}

	@Override
	public void buildExit(int placeFrom, String whichWay, int placeTo) {
		
		
		Place place1 = placeSet.get(placeFrom);
		Place place2;
		if (placeTo != -1) {
			place2 = placeSet.get(placeTo);
		}
		else {
			Random rand = new Random();
			do {
			place2 = placeSet.get(rand.nextInt(placeSet.size() - 2));
			} while(place1 == place2);
			Direction randDir;
			do {
				randDir = Direction.randomDir();
				whichWay = randDir.name();
				if (place1.getExitDestination(randDir) != null || (place2.getExitDestination(DirectionEncoder.convertOppositeDirection(randDir.name())) != null)) {
					randDir = null;
				}
			} while (randDir == null);
		}
		
		
		Direction dir = DirectionEncoder.convertDirection(whichWay);
		Direction dir2 = DirectionEncoder.convertOppositeDirection(whichWay);
		place1.setExitDestination(dir, place2);
		place2.setExitDestination(dir2, place1);
		
		if (!placesAdded.contains(place1)) {
			placesAdded.add(place1);
			worldBeingBuilt.addToWorld(place1);
		}
		if (!placesAdded.contains(place2)) {
			placesAdded.add(place2);
			worldBeingBuilt.addToWorld(place2);
		}
		
	}
	
	public void buildPlayer(String name, int ID) {
		Inventory inv = new Inventory(name, ID);
		inv.setPickingUp(true);
		inv.setAI(true);
		worldBeingBuilt.addToWorld(inv);
	}
	
	public void addFood(Food food, int place) {
		placeSet.get(place).setFood(food);
	}


}
