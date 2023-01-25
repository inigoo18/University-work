package trade.model;

import java.io.Serializable;

public class Exits implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private Place north;
	private Place east;
	private Place south;
	private Place west;
	
	public Exits() {
		north = null;
		east = null;
		south = null;
		west = null;
	}
	
	public void setDestination(Direction dir, Place destination) {
		if (dir == Direction.NORTH) {
			north = destination;
		}
		else if (dir == Direction.EAST) {
			east = destination;
		}
		else if (dir == Direction.SOUTH) {
			south = destination;
		}
		else if (dir == Direction.WEST) {
			west = destination;
		}
	}
	
	public Place getDestination(Direction dir) {
		if (dir == Direction.NORTH) {
			return north;
		}
		else if (dir == Direction.EAST) {
			return east;
		}
		else if (dir == Direction.SOUTH) {
			return south;
		}
		else if (dir == Direction.WEST) {
			return west;
		}
		return null;
	}
	
	public String getExits() {
		String result = "Exits: ";
		if (north != null) {
			result += "north - " + north.getName() + "\n";
		}
		if (east != null) {
			result += "east - " + east.getName() + "\n";
		}
		if (west != null) {
			result += "west - " + west.getName() + "\n";
		}
		if (south != null) {
			result += "south - " + south.getName() + "\n";
		}
		return result;
	}
	
	public int getNumExits() {
		int res = 0;
		if (north != null) {
			res++;
		}
		if (east != null) {
			res++;
		}
		if (west != null) {
			res++;
		}
		if (south != null) {
			res++;
		}
		return res;
	}
	
	public Direction findExit() {
		Direction dir = null;
		
		if (north != null) {
			dir = Direction.NORTH;
		}
		if (east != null) {
			dir = Direction.EAST;
		}
		if (west != null) {
			dir = Direction.WEST;
		}
		if (south != null) {
			dir = Direction.SOUTH;
		}
		return dir;
	}
	
}
