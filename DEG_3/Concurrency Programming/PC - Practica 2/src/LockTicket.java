import java.util.concurrent.atomic.AtomicInteger;

public class LockTicket extends Locks {
	
	private int M; /*número de procesos creados*/
	
	private volatile int number = 1;
	private volatile int next = 1, turn[];
	
	public LockTicket(int M) {
		this.M = M;
		turn = new int[this.M * 2 + 1];
		turn = turn;
		for(int i = 0; i <= this.M * 2; i++) { //turno[i] = 0 -> el proceso no quiere entrar a la sc
			turn[i] = 0;
		}
		turn = turn;
	}
	
	
	
	public void takeLock(int id) {
		//turn[id] = number;
		//turn = turn;
		
		turn[id] = fetch_and_add(number, 1);
		turn = turn;
		//number = number + 1;
		
		while(turn[id] != next);
	}
	
	public void releaseLock(int id) {
		next = next + 1;
	}
	
	private int fetch_and_add(int var, int incr) {
		AtomicInteger aux = new AtomicInteger(var);

		int num = aux.getAndAdd(incr);
		number = aux.get();
		return num;
	}
	
}