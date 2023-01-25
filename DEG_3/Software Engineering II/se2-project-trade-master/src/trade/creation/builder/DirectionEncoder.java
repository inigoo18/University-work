package trade.creation.builder;

import trade.model.Direction;

public class DirectionEncoder {

	public static Direction convertDirection(String direction) {
		
		Direction dir = null;
		
		if (direction.equalsIgnoreCase("north") || direction.equalsIgnoreCase("n")){
			dir = Direction.NORTH;
		}
		else if (direction.equalsIgnoreCase("south") || direction.equalsIgnoreCase("s")){
			dir = Direction.SOUTH;
		}
		else if (direction.equalsIgnoreCase("east") || direction.equalsIgnoreCase("e")) {
			dir = Direction.EAST;
		}
		else {
			dir = Direction.WEST;
		}
		
		return dir;
	}
	
public static Direction convertOppositeDirection(String direction) {
		
		Direction dir = null;
		
		if (direction.equalsIgnoreCase("north") || direction.equalsIgnoreCase("n")){
			dir = Direction.SOUTH;
		}
		else if (direction.equalsIgnoreCase("south") || direction.equalsIgnoreCase("s")) {
			dir = Direction.NORTH;
		}
		else if (direction.equalsIgnoreCase("east") || direction.equalsIgnoreCase("e")) {
			dir = Direction.WEST;
		}
		else {
			dir = Direction.EAST;
		}
		
		return dir;
	}
	
	
}
