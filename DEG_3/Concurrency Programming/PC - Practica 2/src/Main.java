

public class Main {
	
	
	public static void main(String[] args) throws InterruptedException {
		
		int n = 5;
		int m = 20;
		
		Init init = new Init(n, m);
		int result = init.comenzarProceso();
		System.out.println(result);
		
	}
	
}
