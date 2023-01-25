package Game;

public abstract class EnemyShip extends Ship {
	
	public EnemyShip(Game game, int posX, int posY, int lives) {
		super(game, posX, posY, lives);
	}
	
	public abstract void computerAction();

	public abstract void onDelete();

	public abstract void move();

	public abstract String toString();
}