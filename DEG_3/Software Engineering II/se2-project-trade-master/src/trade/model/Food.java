package trade.model;


public abstract class Food{
	
	protected String name;
	protected String type;
	protected int cost;
	protected int maxExpiration; // if food's expiration goes down to 0, the food is destroyed
	protected int expiration;
	protected Season goodSeason, badSeason; // Good season +100% profit, bad season -50% profit
	
	
	public Food(FoodList foodName) {
		if (foodName != null) {
			name = foodName.getName();
			type = foodName.getType();
			cost = foodName.getCost();
			this.maxExpiration = foodName.getExpiration();
			expiration = maxExpiration;
		}
	}
	
	public int getCost(Season season) {
		if (goodSeason == season) {
			return cost * 2;
		}
		else if (badSeason == season) {
			return cost / 2;
		}
		return cost;
	}
	
	public String getType() {
		return type;
	}
	
	public void expire() {
		expiration--;
	}
	
	public String toString() {
		return name;
	}
	
	public int getExpiration() {
		return expiration;
	}
	
	public String getName() {
		return name;
	}
	
	public Season getGoodSeason() {
		return goodSeason;
	}
	
	public Season getBadSeason() {
		return badSeason;
	}
	
}