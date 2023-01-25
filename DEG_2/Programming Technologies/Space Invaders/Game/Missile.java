package Game;

public class Missile extends Weapon {
	
	public static final int DAMAGE = 1;
	
	public Missile(Game game, int posX, int posY, int lives) {
		super(game, posX, posY, lives);
		// TODO Auto-generated constructor stub
	}
	
	public void move() {
		posY--;
		if (!game.isOnBoard(posX, posY)) {
			shield = 0;
		}
	}

	public void computerAction() {
		
	}

	public void onDelete() {
		game.enableMissile();
		
	}

	public String toString() {
		return "^";
	}
	
	public boolean performAttack(GameElement other) {
		boolean result = false;
		if (other.posY == posY - 1  && other.posX == posX && shield > 0) {
			result = true;
			shield = 0;
		}
		return result;
	}
	
	public boolean receiveBombAttack(int damage) {
		boolean result;
		if (shield > 0) {
			shield = 0;
			result = true;
		}
		else {
			result = false;
		}
		return result;
	}
	
	public String stringReport() {
		String result = "";
		result = "M " + posX + ", " + posY;
		return result;
	}
	
}