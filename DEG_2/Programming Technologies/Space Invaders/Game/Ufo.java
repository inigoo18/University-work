package Game;

import Interfaces.IExecuteRandomActions;

public class Ufo extends EnemyShip implements IExecuteRandomActions {
	
	public static boolean exists;
	public static final int SCORE = 25;
	
	Ufo(Game game, int posX, int posY, int lives) {
		super(game, posX, posY, lives);
	}
	
	public String toString() {
		return "<(+)>";
	}
	
	public void computerAction() {
		if (IExecuteRandomActions.canGenerateUfo(game) && !exists) {
			posX = Game.DIM_X - 1;
			posY = 0;
			exists = true;
		}
	}
	public void onDelete() {
		exists = false;
		game.receivePoints(SCORE);
		game.enableShockWave();
		posY = -1;
	}

	public void move() {
		if (game.isOnBoard(posX, posY)) {
			posX--;
		}
		else {
			exists = false;
		}
	}
	
	public boolean receiveMissileAttack(int damage) {
		boolean result;
		if (shield > 0) {
			shield++;
			onDelete();
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

	public boolean receiveShockWaveAttack(int damage) {
		boolean result;
		if (shield > 0) {
			onDelete();
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
		result = "U " + posX + ", " + posY + ", " + shield;
		return result;
	}
}