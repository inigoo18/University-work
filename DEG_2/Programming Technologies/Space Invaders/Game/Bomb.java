package Game;

public class Bomb extends Weapon {
	public static final int DAMAGE = 1;
	private Destroyer destroyer;
	
	public Bomb(Game game, int posX, int posY, int lives, Destroyer destroyer) {
		super(game, posX, posY, lives);
		this.destroyer = destroyer;
	}
	
	public void move() {
		posY++;
		if (!game.isOnBoard(posX, posY)) {
			shield = 0;
		}
	}

	@Override
	public void computerAction() {
		
	}

	@Override
	public void onDelete() {
		destroyer.setCanShoot(true);
	}

	public String toString() {
		return "!";
	}
	
	public boolean performAttack(GameElement other) {
		boolean result = false;
		if (other.posY == posY + 1  && other.posX == posX && shield > 0) {
			result = true;
			shield--;
		}
		return result;
	}

	public boolean receiveMissileAttack(int damage) {
		boolean result;
		if (shield > 0) {
			shield -= damage;
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
		result = "B " + posX + ", " + posY;
		return result;
	}
}