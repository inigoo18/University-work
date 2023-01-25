package Game;

import Interfaces.IExecuteRandomActions;

public class Carrier extends AlienShip implements IExecuteRandomActions {
	public static final int SCORE = 5;
	
	Carrier(Game game, int x, int y, int lives, int cyclesNextMove) {
		super(game, x, y, lives, cyclesNextMove); // LLAMA AL CONSTRUCTOR DEL SHIP
		AlienShip.numShips++;
	}
	
	public String toString() {
		return "-<"+ shield +">-";
	}
	
	public void computerAction() {
		if (timesDown == 0 && onEdge()) {
			timesDown = numShips;
		}
		if (IExecuteRandomActions.canGenerateExplosiveShip(game)) {
			ExplosiveShip ship = new ExplosiveShip(game, posX, posY, shield, dir, cyclesNextMove);
			game.swapObject(ship, this);
		}
	}

	public void onDelete() {
		AlienShip.numShips--;
		game.receivePoints(SCORE);
	}

	public boolean receiveMissileAttack(int damage) {
		boolean result;
		int auxShield = shield;
		if (auxShield > 0) {
			auxShield -= damage;
			result = true;
		}
		else {
			result = false;
		}
		return result;
	}

	public boolean receiveShockWaveAttack(int damage) {
		boolean result;
		int auxShield = shield;
		if (auxShield > 0) {
			auxShield -= damage;
			result = true;
		}
		else {
			result = false;
		}
		return result;
	}
	
	public String stringReport() {
		String result = "";
		result = "C " + posX + ", " + posY + ", " + shield + ", " + cyclesNextMove + ", " + dir;
		return result;
	}
	
}