package Parte2;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.List;
import java.util.Scanner;

import mensajes.MENSAJE_CERRAR_CONEXION;
import mensajes.MENSAJE_CONEXION;
import mensajes.MENSAJE_LISTA_USUARIOS;
import mensajes.MENSAJE_PEDIR_FICHERO;
import mensajes.Mensaje;

public class Cliente {
	
	private String nombre;
	private InetAddress IP;
	private Socket serverSocket;
	private ObjectInputStream cin;
	private ObjectOutputStream cout;
	private Usuario user;
	private String nFichero;
	private int miPuerto;
	
	private int puertoServidor;
	Scanner scanner;
	
	public Cliente(int ps, Scanner scanner, int puerto) {
		try {
			IP = InetAddress.getLocalHost();
		} catch (UnknownHostException e) {
			System.out.println("Error creando la IP del cliente " + nombre + ".");
		}
		miPuerto = puerto;
		puertoServidor = ps;
		this.scanner = scanner;
		user = new Usuario();
		init();
	}
	
	public void crearComunicacionConServidor() {
		try {
			System.out.println("Conexión con el servidor...");
			serverSocket = new Socket(IP, puertoServidor); //crear socket con servidor
			System.out.println("Creamos canal de comunicación entre usuario " + nombre + " y Servidor.");
			System.out.println("Tenemos puerto " + puertoServidor);
			cout = new ObjectOutputStream(serverSocket.getOutputStream());
			cin = new ObjectInputStream(serverSocket.getInputStream());
			
			System.out.println("Creamos un nuevo hilo OyenteServidor para el usuario " + nombre);
			OyenteServidor os = new OyenteServidor(cin, cout, this); //crear nuevo thread OyenteServidor para leer el socket
			os.start(); //escucha contínua en el canal de comunicación con el servidor
		} catch (IOException e) {
			System.out.println("Error del cliente " + nombre + " creando el canal de conexión con el Servidor.");
		}
	}
	
	public void mensajeConexion() {
		System.out.println("Creamos Mensaje de Conexión para el usuario " + nombre);
		Mensaje m = new MENSAJE_CONEXION(nombre, "Servidor");
		try {
			System.out.println("Mandamos Mensaje de Conexión de usuario " + nombre + " a Servidor.");
			cout.writeObject(m); //enviar mensaje de conexión con el servidor
		} catch (IOException e) {
			System.out.println("Error escribiendo un Mensaje en el canal del cliente " + nombre + " y el Servidor.");
		}
	}
	
	private boolean clientHasFichero(String nFichero) {
		List<Fichero> listFicheros = user.getList();
		for (Fichero f: listFicheros) {
			if (f.getName().equals(nFichero)) {
				return true;
			}
		}
		return false;
	}
	
	public int menu() {
		int menuOp = -1;
		nFichero = null;
		while(menuOp < 0 || menuOp > 3) { //menu con usuario
			System.out.println("Seleccione una opción del menú: ");
			System.out.println("1- Consultar lista de usuarios.\n"
							 + "2- Pedir fichero.\n"
							 + "3- Salir.");
			menuOp = scanner.nextInt();
			scanner.nextLine(); // IMPORTANTE PARA CUANDO DAS ENTER
			if(menuOp == 2) {
                System.out.println("Introduzca el nombre del fichero que desea: ");
                nFichero = scanner.next();
                if (clientHasFichero(nFichero)) {
                	
                	System.out.println("Ya tienes ese fichero!");
                	menuOp = -1;
                }
            }
		}
		return menuOp;
	}
	
	public void enviarMensajeSeleccionado(int op) {
		Mensaje m = null;
	
		if (op == 1) {
			m = new MENSAJE_LISTA_USUARIOS(nombre, "Servidor");
		}
		else if (op == 2){
			m = new MENSAJE_PEDIR_FICHERO(nombre, "Servidor");
		}
		else if (op == 3) {
			m = new MENSAJE_CERRAR_CONEXION(nombre, "Servidor");
			m.setUser(user);
		}
		
		System.out.println("Usuario " + nombre + " ha elegido el Mensaje de tipo: " + m.getTipo());
        try {
            cout.writeObject(m);
            if(op == 2) {
                cout.writeObject(nombre);
                cout.writeObject(nFichero);
            }
        } catch (IOException e) {
            System.out.println("Error escribiendo un mensaje en el canal del cliente " + nombre + " y el Servidor.");
        }

	}
	
	public InetAddress getIP() {
		return IP;
	}
	
	public int getPuerto() {
		return miPuerto;
	}
	
	public Usuario getUsuario() {
		return user;
	}
	
	private String loadFile(String name) {
		Scanner in;
		StringBuilder sb = new StringBuilder();
		try {
			in = new Scanner(new FileReader(name));
			while(in.hasNext()) {
			    sb.append(in.next());
			}
			in.close();
		} catch (FileNotFoundException e) {
			System.out.println("Problema leyendo fichero");
			return null;
		}
		return sb.toString();
	}
	
	
	private void init() {
		System.out.println("Introduzca su nombre:");
		String nombre = scanner.next();
		this.nombre = nombre;
		user.setUserName(this.nombre);
		System.out.println("Quiere añadir algun fichero de la carpeta resources? (S: 0/N: 1)");
		int opt = scanner.nextInt();
		scanner.nextLine();
		if (opt == 0) {
			boolean stop = false;
			while (!stop) {
				System.out.println("Introduzca el nombre del fichero que quiere cargar");
				String fileName = "resources/";
				String realName;
				realName = scanner.next();
				fileName = fileName.concat(realName);
				String data = loadFile(fileName);
				if (clientHasFichero(realName)) {
                	System.out.println("Ya tienes ese fichero!");
                }
				else {
					if (data != null) {
						Fichero fich = new Fichero(realName, data);
						user.addFichero(fich);
					}
				}

				System.out.println("Quiere continuar añadiendo ficheros? (S: 0/N: 1)");
				opt = scanner.nextInt();
				scanner.nextLine();
				if (opt == 1) {
					stop = true;
				}
			}
			
		}
		else {
			
		}
	}
	
	public void setPuerto(int p) {
		miPuerto = p;
	}
	
}
