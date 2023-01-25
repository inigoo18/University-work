import java.util.ArrayList;
import java.util.List;

public class Init {
	
	private int N;
	private int M;
	private volatile int num = 0;
	

	public Init(int N, int M) {
		this.N = N;
		this.M = M;
	}
	
	public int comenzarProceso() throws InterruptedException {
		List<Thread> threads= new ArrayList<>();
		//LockRompeEmpate lock = new LockRompeEmpate(M);
		LockTicket lock = new LockTicket(M);
		//Locks lock = new LockBakery(M);
		for(int i = 1; i <= M * 2; i = i + 2) {
			Incrementa p1 = new Incrementa(i, N, lock, this);
			Decrementa p2 = new Decrementa(i+1, N, lock, this);
			threads.add(p1);
			threads.add(p2);
		}
		
		for(Thread thread : threads) {
			thread.start();
		}
		
		for(Thread thread : threads) {
			thread.join();
		}

		return num;
	}
	
	public void incrementNum(int acc) {
		num += acc;
	}
	
	public int getNum() {
		return num;
	}
	
}
