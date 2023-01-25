package Parte2;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Semaphore;
import Parte2.Init;

public class Productor extends Thread {

	private int numProceso;
	private Semaphore full, empty, mutexp;
	private int N;
	private Init init;
	
	public Productor(int i, int N, Semaphore full, Semaphore empty, Semaphore mutexp, Init init) {
		numProceso = i;
		this.full = full;
		this.empty = empty;
		this.N = N;
		this.init = init;
		this.mutexp = mutexp;
	}
	
	@Override
	public void run() {
		for (int i = 0; i < N; i++) {
			try {
				empty.acquire();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			try {
				mutexp.acquire();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			Producto newProd = new Producto(numProceso);
			init.almacenar(newProd);
			mutexp.release();
			full.release();
		}
	}

	
}
