public class Decrementa extends Thread{
	
	private int numProceso;
	private Locks algo;
	private int N;
	private Init init;
	
	public Decrementa(int i, int N, Locks algo, Init init) {
		numProceso = i;
		this.algo = algo;
		this.N = N;
		this.init = init;
	}
	
	@Override
	public void run() {
		for (int i = 0; i < N; i++) {
			algo.takeLock(numProceso);
			init.incrementNum(-1);
			//System.out.println("Soy el proceso (desc) " + numProceso + ", num = " + init.getNum());
			algo.releaseLock(numProceso);
		}
	}

}