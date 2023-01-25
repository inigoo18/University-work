package Parte2;

public class Main {
	public static void main(String[] args) throws InterruptedException {
		
		int n = 1;
		int m = 50;
		int numProds = 5;
		
		Init init = new Init(n, m, numProds);
		int result = init.comenzarProceso();
		System.out.println(result);
		
	}
	
}
