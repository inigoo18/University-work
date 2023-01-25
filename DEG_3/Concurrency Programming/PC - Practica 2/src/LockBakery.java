
public class LockBakery extends Locks {
	
	int M;
	private volatile int turn[];
	
	public LockBakery(int M) {
		this.M = M;
		turn = new int[M * 2 + 1];
		
		for (int i = 0; i <= M*2; i++) {
			turn[i] = 0;
		}
	}
	
	public void takeLock(int id) {
		
		turn[id] = 1;
		turn[id] = max(turn) + 1;
		for (int j = 1; (j <= M * 2); j++) {
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
		for (int i = 1; i <= M*2; i++) {
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
