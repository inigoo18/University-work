package Parte2;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Semaphore;
import Parte2.Init;

public class Consumidor extends Thread {

	private Semaphore full, empty, mutexc;
	private int N;
	private int numProceso;
	private Init init;
	
	public Consumidor(int i, int N, Semaphore full, Semaphore empty, Semaphore mutexc, Init init) {
		numProceso = i;
		this.full = full;
		this.empty = empty;
		this.N = N;
		this.init = init;
		this.mutexc = mutexc;
	}
	
	@Override
	public void run() {
		for (int i = 0; i < N; i++) {
			try {
				full.acquire();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			try {
				mutexc.acquire();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			Producto prod = init.extraer();
			System.out.println("Consumimos el producto: " + prod.getID());
			mutexc.release();
			empty.release();
		}
	}
	
	
}
