package Game;

public abstract class AlienShip extends EnemyShip{
	
	protected boolean dir;
	protected int cyclesNextMove;
	public static int numShips;
	public static int timesDown;
	private static boolean reachGround = false;
	
	
	AlienShip(Game game, int x, int y, int lives, int cyclesNextMove) {
		super(game, x, y, lives); // LLAMA AL CONSTRUCTOR DEL SHIP
		this.cyclesNextMove = cyclesNextMove;
		dir = true;
	}
	
	public static boolean allDead() {
		if (numShips == 0) {
			return true;
		}
		return false;
	}

	public static boolean haveLanded() {
		return reachGround;
	}
	
	protected boolean onEdge() {
		boolean result = false;
		if ((posX == 1 && dir)  || (posX == Game.DIM_X - 2 && !dir)) {
			result = true;
		}
		timesDown = 0;
		return result;
	}
	
	public void move() {
		if (game.getCycle() % cyclesNextMove == 0) {
			if (dir) {
				posX--;
			}
			else {
				posX++;
			}
			if (timesDown > 0) {
				posY++;
				dir = !dir;
				timesDown--;
				if (posY == Game.DIM_Y - 1) {
					reachGround = true;
				}
			}
		}
	}
	
	public void setDir(boolean dir) {
		this.dir = dir;
	}
	
	public abstract void computerAction();

	public abstract void onDelete();

	public abstract String toString();
	
}