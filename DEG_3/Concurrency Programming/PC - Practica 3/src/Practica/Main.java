package Practica;

public class Main {
	public static void main(String[] args) throws InterruptedException {
		
		int n = 35;
		int m = 5;
		
		Init init = new Init(n, m);
		int result = init.comenzarProceso();
		System.out.println(result);
		
	}
	
}
