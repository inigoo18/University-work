package Game;

public abstract class Ship extends GameElement {
	
	
	public Ship(Game game, int X, int Y, int lives) {
		super(game, X, Y, lives);
		// TODO Auto-generated constructor stub
	}
	
	
	
	public void setPosX(int X) { posX = X; }
	public void setPosY(int Y) { posY = Y; }
	public void setShield(int lives) { shield = lives; }
	
	public int getPosX() { return posX; }
	public int getPosY() { return posY; }
	public int getShield() { return shield; }
	
	
	public abstract void computerAction();

	public abstract void onDelete();

	public abstract void move();

	public abstract String toString();
	
}