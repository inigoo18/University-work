package Game;

public class Board {
	private GameElement[] elements;
	private int currentElements, width, height;
	

	public Board(int width, int height) {
		this.width = width;
		this.height = height;
		elements = new GameElement[width*height]; 
		currentElements = 0;
	}

	public void add(GameElement gameElement) {
		elements[currentElements] = gameElement;
		currentElements++;
	}
	
	public void swap(GameElement gameElement, GameElement other) {
		int idx = getIndex(other.posX, other.posY);
		elements[idx] = gameElement; 
	}

	public void update() {
		int numShips = AlienShip.numShips + 2; // + UFO + UCMShip (first elements)
		for (int i = 0; i < numShips; i++) {
			elements[i].move();
		}
		removeDead();
		for (int i = numShips; i < currentElements; i++) {
			checkAttacks(elements[i]);
			elements[i].move();
		}
		for (int i = 0; i < numShips; i++) {
			checkAttacks(elements[i]);
		}
		removeDead();
	}

	public void computerAction() {
		for (int i = 0; i < currentElements; i++) {
			elements[i].computerAction();
		}
	}

	public String toString( int x, int y ) {
		GameElement element = getObjectAt(x,y);
		if (element != null) {
			return element.toString();
		}
		else {
			return " ";
		}
	}

	private GameElement getObjectAt (int x, int y) {
		GameElement element = null;
		boolean hasFound = false;
		int i = 0;
		while (!hasFound && i < currentElements) {
			if (elements[i].isOnPosition(x, y)) {
				hasFound = true;
				element = elements[i];
			}
			else {
				i++;
			}
		}
		return element;
	}

	private int getIndex(int x, int y) {
		boolean hasFound = false;
		int i = currentElements - 1;
		while (!hasFound && i >= 0) {
			if (elements[i].isOnPosition(x, y)) {
				hasFound = true;
			}
			else {
				i--;
			}
		}
		return i;
	}

	private void checkAttacks(GameElement gameElement) {
			GameElement case1 = getObjectAt(gameElement.posX, gameElement.posY - 1); // MISSILE
			GameElement case2 = getObjectAt(gameElement.posX, gameElement.posY + 1); // BOMB
			int damage1 = gameElement.getShield();
			if (case1 != null && gameElement.performAttack(case1)) { // missile
				if (case1.receiveMissileAttack(damage1)) {
					case1.getDamage(damage1);
				}
			}
			if (case2 != null && gameElement.performAttack(case2)){ // bomb
				if (case2.receiveBombAttack(Bomb.DAMAGE)) {
					case2.getDamage(Bomb.DAMAGE);
				}
			}
			else if (gameElement.posX == -1) { // shockwave
				for (int i = 0; i < currentElements; i++) {
					if (gameElement.performAttack(elements[i])) {
						if (elements[i].receiveShockWaveAttack(Shockwave.DAMAGE)) {
							elements[i].getDamage(Shockwave.DAMAGE);
						}
					}
				}
			}
			else {
				for (int a = 0; a < currentElements; a++) { // explosive ship
					if (gameElement.performAttack(elements[a])) {
						if (elements[a].receiveMissileAttack(Missile.DAMAGE)) {
							elements[a].getDamage(Missile.DAMAGE);
						}
					}
				}
			}
	}

	private void remove(GameElement gameElement) { // GameElement[] ; GameElement
		int idx = getIndex(gameElement.posX, gameElement.posY);
		for (int i = idx; i < currentElements - 1; i++) {
			elements[i] = elements[i + 1];
		}
		currentElements--;
		
	}
	

	private int getcurrentElements() {
		int counter = 0;
		for (int i = 0; i < currentElements; i++) {
			if (elements[i].isAlive()) {
				counter++;
			}
		}
		return counter - 2;
	}

	private void removeDead() {
		for (int i = currentElements - 1; i >= 0; i--) {
			if (!elements[i].isAlive()) {
				elements[i].onDelete();
				remove(elements[i]);
			}
		}
	}
	
	public String stringify() {
		String result = "";
		
		for (int i = 0; i < currentElements; i++) {
			result += elements[i].stringReport();
			result += "\n";
		}
		
		return result;
	}
}
