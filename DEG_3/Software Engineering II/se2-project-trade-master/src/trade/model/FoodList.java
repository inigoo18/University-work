package trade.model;

public enum FoodList {
	
	PAPAYA("Papaya", 3, 3, "Fruit"),
	
	//Meat
	CHICKEN("Chicken", 4, 3, "Meat"),
	COW("Cow", 8, 3, "Meat"),
	OX("Ox", 12, 3, "Meat"),
	//Fish
	HAKE("Hake", 4, 3, "Fish"),
	SALMON("Salmon", 8, 3, "Fish"),
	CRAYFISH("Crayfish", 12, 3, "Fish"),
	//Fruit
	APPLE("Apple", 2, 5, "Fruit"),
	PITAYA("Pitaya", 5, 5, "Fruit"),
	//Cereal
	WHEAT("Wheat", 2, 5, "Cereal"),
	QUINUA("Quinua", 5, 5, "Cereal");
	
	
	
	protected String name;
	protected String type;
	protected int cost;
	protected int expiration; // if food's expiration goes down to 0, the food is destroyed
	protected Season goodSeason, badSeason; // Good season +50% profit, bad season -50% profit
	
	private FoodList(String name, int price, int expiration, String type) {
		this.name = name;
		this.type = type;
		cost = price;
		this.expiration = expiration + 1;
	}
	
	public String getName() {
		return name;
	}
	
	public String getType() {
		return type;
	}
	
	public int getCost() {
		return cost;
	}
	
	public int getExpiration() {
		return expiration;
	}
	
	public Season getGoodSeason() {
		return goodSeason;
	}
	
	public Season getBadSeason() {
		return badSeason;
	}
	
	public static FoodList parse(String inputString) {
		for (FoodList food : FoodList.values())
			if (food.name().equalsIgnoreCase(inputString))
				return food;
		return null;
	}
	
}