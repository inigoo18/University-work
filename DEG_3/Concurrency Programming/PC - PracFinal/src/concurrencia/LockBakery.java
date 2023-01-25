package concurrencia;

public class LockBakery extends Lock {
	
	private volatile int turn[];
	private static final int MAX_SIZE = 20;
	
	public LockBakery() {
		turn = new int[MAX_SIZE + 1];
		
		for (int i = 0; i <= MAX_SIZE; i++) {
			turn[i] = 0;
		}
	}
	
	public void takeLock(int id) {
		
		turn[id] = 1;
		turn[id] = max(turn) + 1;
		for (int j = 1; j <= MAX_SIZE; j++) {
			if (j != id) {
				while ((turn[j] != 0) && operador(turn[id], id, turn[j], j));
			}
		}
	}
	
	public void releaseLock(int id) {
		turn[id] = 0;
		turn = turn;
	}
	
	private int max(int array[]) {
		int max = 0;
		for (int i = 1; i <= MAX_SIZE; i++) {
			if (array[i] > max) {
				max = array[i];
			}
		}
		return max;
	}
	
	private boolean operador(int a, int b, int c, int d) {
		return (a > c) || ((a == c) && (b > d));
	}

}