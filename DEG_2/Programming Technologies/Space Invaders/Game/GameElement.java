package Game;

import Interfaces.IAttack;

public abstract class GameElement implements IAttack {
	protected int posX, posY, shield;
	protected Game game;

	public GameElement( Game game, int X, int Y, int lives) {
		this.game = game;
		posX = X;
		posY = Y;
		shield = lives;
	}

	public boolean isAlive() {
		return shield > 0;
	}

	public int getShield() {
		return shield;
	}

	public boolean isOnPosition( int X, int Y ) {
		return (X == posX && Y == posY);
	}

	public void getDamage(int damage) {
		shield = (damage >= shield ? 0 : shield - damage);
	}

	public boolean isOut() {
		return !game.isOnBoard(posX, posY);
	}

	public abstract void computerAction();

	public abstract void onDelete();

	public abstract void move();

	public abstract String toString();
	
	public abstract String stringReport();
}