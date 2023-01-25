package Game;

public class Shockwave extends Weapon {
	public static final int DAMAGE = 1;
	
	
	public Shockwave(Game game, int X, int Y, int lives) {
		super(game, X, Y, lives);
	}

	public boolean performAttack(GameElement other) {
		boolean result = false;
		if (posY == other.posY){
			result = true;
		}
		return result;
	}

	@Override
	public void computerAction() {
		
	}

	@Override
	public void onDelete() {
		
	}

	@Override
	public void move() {
		shield--;
		
	}

	@Override
	public String toString() {
		return null;
	}
	
	public String stringReport() {
		return null;
	}
}
