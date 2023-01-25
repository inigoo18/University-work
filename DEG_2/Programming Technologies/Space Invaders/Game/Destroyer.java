package Game;

import Interfaces.IExecuteRandomActions;

public class Destroyer extends AlienShip implements IExecuteRandomActions {
	
	private boolean canShoot;
	public static final int SCORE = 10;
	
	Destroyer(Game game, int posX, int posY, int lives, int cyclesNextMove) {
		super(game, posX, posY, lives, cyclesNextMove);
		AlienShip.numShips++;
		canShoot = true;
	}
	
	public void setCanShoot(boolean arg) { canShoot = arg; }
	public boolean getCanShoot() { return canShoot; }
	
	public String toString() {
		return "!<"+ shield + ">!";
		
	}
	
	
	public void computerAction() {
		if (timesDown == 0 && onEdge()) {
			timesDown = numShips;
		}
		if (IExecuteRandomActions.canGenerateBomb(game) && canShoot) {
			canShoot = false;
			Bomb bomb = new Bomb(game, posX, posY, 1, this);
			game.addObject(bomb);
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
		result = "D " + posX + ", " + posY + ", " + shield + ", " + cyclesNextMove + ", " + dir;
		return result;
	}
}