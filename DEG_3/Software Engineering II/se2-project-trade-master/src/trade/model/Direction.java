package trade.model;

import java.util.Random;

public enum Direction {
	NORTH, EAST, SOUTH, WEST;
	
	
	public static Direction randomDir() { // for test purposes
		Random rand = new Random();
		int num = rand.nextInt(3);
		return values()[num];
	}
}
