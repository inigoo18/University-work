
public class LockRompeEmpate extends Locks {
	
	int M;
	private volatile int in[], last[];
	
	public LockRompeEmpate(int M) {
		this.M = M;
		in = new int[M * 2 + 1];
		last = new int[M * 2 + 1];
		
		for (int i = 0; i <= M*2; i++) {
			in[i] = 0;
			last[i] = 0;
		}
		
		in = in;
		last = last;
	}
	
	public void takeLock(int id) {
		for(int j = 1; j <= M * 2; j++) {
			in[id] = j;
			in = in;
			last[j] = id;
			last = last;
			for(int k = 1; (k <= M * 2); k++) {
				if (k != id) {
					while((in[k] >= in[id]) && (last[j] == id));
				}
			}
		}
	}
	
	public void releaseLock(int id) {
		in[id] = 0;
		in = in;
	}

}
