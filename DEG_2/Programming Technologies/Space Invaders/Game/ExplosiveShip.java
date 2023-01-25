package Game;

public class ExplosiveShip extends AlienShip {
	public static final int SCORE = 5;
	
	ExplosiveShip(Game game, int x, int y, int lives, boolean dir, int cyclesNextMove) {
		super(game, x, y, lives, cyclesNextMove); // LLAMA AL CONSTRUCTOR DEL SHIP
		this.dir = dir;
	}
	
	public String toString() {
		return "*<"+ shield +">*";
	}
	
	public void computerAction() {
		if (timesDown == 0 && onEdge()) {
			timesDown = numShips;
		}
	}

	public void onDelete() {
		AlienShip.numShips--;
		game.receivePoints(SCORE);
	}
	
	public boolean performAttack(GameElement other) {
		boolean result = false;
		if (Math.abs(other.posY - posY) <= 1  && Math.abs(other.posX - posX) <= 1 && shield == 0) {
			result = true;
		}
		return result;
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
		result = "E " + posX + ", " + posY + ", " + shield + ", " + cyclesNextMove + ", " + dir;
		return result;
	}
}
