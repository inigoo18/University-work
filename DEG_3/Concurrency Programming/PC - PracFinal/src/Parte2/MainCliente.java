package Parte2;

import java.util.Random;
import java.util.Scanner;

import Parte2.Cliente;

public class MainCliente {
	
	private static int puerto = 3001;
	
	private static Random random;
	
	
	public static void main(String[] args) {
		random = new Random();
		Scanner leer = new Scanner(System.in);
		int newPuerto = random.nextInt(3000);
		Cliente cliente = new Cliente(puerto, leer, newPuerto);
		
		cliente.crearComunicacionConServidor();
		int opt = -1;
		while (opt != 3) { // 3 es la opción para salir.
			opt = cliente.menu();
			cliente.enviarMensajeSeleccionado(opt);
		}
		leer.close();
	}
	
	
	

}
