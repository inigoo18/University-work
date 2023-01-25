package Parte2;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Semaphore;
import java.util.LinkedList;

public class Init implements Almacen {
	
	private int N;
	private int M;
	private volatile List<Producto> buffer;
	
	private volatile Semaphore full;
	private volatile Semaphore empty;
	private volatile Semaphore mutexp, mutexc;
	

	public Init(int N, int M, int numProductos) {
		this.N = N;
		this.M = M;
		full = new Semaphore(0);
		empty = new Semaphore(numProductos);
		mutexp = new Semaphore(1);
		mutexc = new Semaphore(1);
	}
	
	public int comenzarProceso() throws InterruptedException {
		List<Thread> threads= new ArrayList<>();
		buffer = new LinkedList<>();
			
		for(int i = 1; i <= M * 2; i = i + 2) {
			Productor p1 = new Productor(i, N, full, empty, mutexp, this);
			Consumidor p2 = new Consumidor(i+1, N, full, empty, mutexc, this);
			threads.add(p1);
			threads.add(p2);
		}
		
		for(Thread thread : threads) {
			thread.start();
		}
		
		for(Thread thread : threads) {
			thread.join();
		}
		return 1;
		//return num;
	}

	// Init:
		// ini y fin tienen que estar todo el rato en Init pueden ser incrementados y decrementados desde productor/consumidor
		// Implementamos almacén en Init. Tenemos un buffer compartido entre productor y consumidor donde 
		// los procesos productores van guardando elementos dentro del buffer y los consumidores van sacando.
		
		
	
	@Override
	public void almacenar(Producto producto) { // mete al final de la lista
		System.out.println("PRODUCIMOS el producto " + producto.getID());
		buffer.add(producto);
		buffer = buffer;
	}

	@Override
	public Producto extraer() { // extrae desde el inicio de la lista
        Producto prod = buffer.get(0);
        buffer = buffer;
        buffer.remove(0);
        buffer = buffer;
        return prod;
	}
	
}
