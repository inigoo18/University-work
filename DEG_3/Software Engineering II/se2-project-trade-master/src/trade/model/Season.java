package trade.model;

import java.util.Random;

public enum Season {

	SPRING(), WINTER(), SUMMER(), FALL();
	
	private static final int NUM_SEASONS = 4;
	
	private Season() {
		
	}
	
	public static Season randomSeason() {
		Random rand = new Random();
		int nextSeason = rand.nextInt(NUM_SEASONS);
		return intToSeason(nextSeason);
	}
	
	private static Season intToSeason(int i) {
		Season seasons[] = Season.values();
		return seasons[i];
	}
	
	public Season nextSeason() {
		if (this.equals(Season.SPRING)) {
			return Season.SUMMER;
		}
		else if (this.equals(Season.SUMMER)) {
			return Season.FALL;
		}
		else if (this.equals(Season.FALL)) {
			return Season.WINTER;
		}
		else if (this.equals(Season.WINTER)) {
			return Season.SPRING;
		}
		return null;
	}
	
}