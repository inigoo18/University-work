package Practica;

import java.util.concurrent.Semaphore;

public class Decrementa extends Thread{
	
	private int numProceso;
	private Semaphore sem;
	private int N;
	private Init init;
	
	public Decrementa(int i, int N, Semaphore sem, Init init) {
		numProceso = i;
		this.sem = sem;
		this.N = N;
		this.init = init;
	}
	
	@Override
	public void run() {
		for (int i = 0; i < N; i++) {
			try {
				sem.acquire();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			init.incrementNum(-1);
			sem.release();
		}
	}

}