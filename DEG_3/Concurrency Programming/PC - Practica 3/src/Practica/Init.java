package Practica;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Semaphore;

public class Init {
	
	private int N;
	private int M;
	private volatile int num = 0;
	private Semaphore sem;
	

	public Init(int N, int M) {
		this.N = N;
		this.M = M;
		sem = new Semaphore(1);
	}
	
	public int comenzarProceso() throws InterruptedException {
		List<Thread> threads= new ArrayList<>();
				
		for(int i = 1; i <= M * 2; i = i + 2) {
			Incrementa p1 = new Incrementa(i, N, sem, this);
			Decrementa p2 = new Decrementa(i+1, N, sem, this);
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
