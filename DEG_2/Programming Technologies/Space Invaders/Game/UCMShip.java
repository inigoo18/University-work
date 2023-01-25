package Game;

import Exceptions.MissileInFlightException;
import Exceptions.NoSuperException;
import Exceptions.ShockwaveException;
import Exceptions.NotEnoughScoreException;
import Exceptions.OffWorldException;

public class UCMShip extends Ship {

	private boolean canShockwave, canSuper;
	private boolean canShoot;
	int score = 0;
	
	UCMShip(Game game, int x, int y, int lives) {
		super(game, x, y, lives);
		canShoot = true;
		canSuper = false;
		canShockwave = false;
	}
	
	public void setScore(int points) {
		score = points;
	}
	
	public void setSuper(boolean arg) {
		canSuper = arg;
	}
	
	public void setCanShoot(boolean arg) {
		canShoot = arg;
	}
	
	public void canShoot() throws MissileInFlightException {
		if (!canShoot) {
			throw new MissileInFlightException("missile already on board");
		}
	}
	
	public void canSuper() throws NoSuperException{
		if (!canSuper) {
			throw new NoSuperException("no supermissile available");
		}
	}
	
	public void canShockwave() throws ShockwaveException{
		if (!canShockwave) {
			throw new ShockwaveException("no shockwave available");
		}
	}
	
	public void scoreHigher(int price) throws NotEnoughScoreException{
		if (score < price) {
			throw new NotEnoughScoreException("not enough score");
		}
	}
	
	public void canMoveSpaces(int numCells) throws OffWorldException{
		if (!(posX + numCells < Game.DIM_X && posX + numCells >= 0)) {
			throw new OffWorldException("out of bounds");
		}
	}
	
	public void setShockwave(boolean arg) {
		canShockwave = arg;
	}
	
	public boolean getShockwave() {
		return canShockwave;
	}
	
	public void addScore(int points) {
		score += points;
	}
	
	public int getScore() {
		return score;
	}
	
	public boolean getSuper() {
		return canSuper;
	}
	
	public String toString() {
		String result;
		if (shield > 0) {
			result = "/-^-\\";
		}
		else {
			result = "_+.+_";
		}
		return result;
	}
	
	public void computerAction() {
		
	}

	public void onDelete() {
		
	}

	public void move() {

	}

	public boolean receiveBombAttack(int damage) {
		boolean result;
		if (shield > 0) {
			result = true;
			if (shield < 0) {
				shield = 0;
			}
		}
		else {
			result = false;
		}
		return result;
	}
	
	public String stringReport() {
		String result = "";
		result = "P " + posX + ", " + posY + ", " + shield + ", " + score + ", " + canShockwave + ", " + canSuper;
		return result;
	}
	
}
