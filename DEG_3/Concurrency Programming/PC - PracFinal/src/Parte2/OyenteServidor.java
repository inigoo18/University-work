package Parte2;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Random;

import mensajes.MENSAJE_CONEXION;
import mensajes.MENSAJE_PREPARADO_CLIENTESERVIDOR;
import mensajes.Mensaje;

public class OyenteServidor extends Thread{
	
	private ObjectInputStream sin;
	private ObjectOutputStream sout;
	private Cliente cliente;
	
	public OyenteServidor(ObjectInputStream cin, ObjectOutputStream cout, Cliente cliente) {
		sin = cin;
		sout = cout;
		this.cliente = cliente;
		Mensaje MsjConexion = new MENSAJE_CONEXION("Cliente" , "Servidor");
		MsjConexion.setUser(cliente.getUsuario());
		try {
			sout.writeObject(MsjConexion);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("Problema mandando Mensaje Conexión al Server");
		}
	}
	
	@Override
	public void run() {
		Mensaje m;
		
		while(true) {
			m = leerMensaje();
			String tipo = m.getTipo();
			System.out.println("Recibimos mensaje: " + tipo);
			if (tipo.equals("MENSAJE_CONFIRMACION_CONEXION")) {
				System.out.println("- CONEXIÓN ESTABLECIDA: Elija un comando -");
			}
			else if (tipo.equals("MENSAJE_CONFIRMACION_LISTA_USUARIOS")) {
				// imprimir lista usuarios por standard output
				String res = m.getInfo();
				System.out.print(res);
			} 
			else if (tipo.equals("MENSAJE_EMITIR_FICHERO")) {
				// nos llega el nombre del cliente C1 e informacion pedida 3 (3??)
				// enviar MENSAJE_PREPARADO_CLIENTESERVIDOR a mi oyente
				//crear proceso EMISOR y esperar en accept la conexion
				System.out.println("El cliente que tiene el fichero ha recibido mensaje de servidor");
                try {
                	Mensaje msgPreparadoClienteS = new MENSAJE_PREPARADO_CLIENTESERVIDOR(m.getOrigen(), m.getDestino());
                	msgPreparadoClienteS.setIP(cliente.getIP());
                	msgPreparadoClienteS.setPuerto(cliente.getPuerto());
                	msgPreparadoClienteS.setUser(cliente.getUsuario());
                	sout.writeObject(msgPreparadoClienteS); // enviar MENSAJE_PREPARADO_CLIENTESERVIDOR a mi oyente
               
	                String nFichero = m.getInfo();
					Emisor emisor = new Emisor(cliente.getPuerto(), nFichero, cliente);
	                emisor.start(); //crear proceso EMISOR y esperar en accept la conexion
                } catch (IOException e) {
                    System.out.println("IOException en OyenteServidor (ERROR mandando MENSAJE_PREPARADO_CLIENTESERVIDOR)");
                }
			}
			else if (tipo.equals("MENSAJE_PREPARADO_SERVIDORCLIENTE")) {
				//llega direccion IP y puerto del propietario de fichero
				// crear proceso RECEPTOR
				new Receptor(cliente, sout, m.getOrigen()).connectTo(m.getIP(), m.getPuerto());
			}
			else if (tipo.equals("MENSAJE_CONFIRMACION_CERRAR_CONEXION")) {
				// imprimir adios por standard output.
				System.out.println("- CONEXIÓN TERMINADA -");
			}
		}
		
	}
	
	private Mensaje leerMensaje() {
		Mensaje m = null;
		try {
			m = (Mensaje) sin.readObject();
		} catch (ClassNotFoundException e) {
			System.out.println("ClassNotFoundException en OyenteServidor (ERROR leyendo el mensaje)");
		} catch (IOException e) {
			System.out.println("IOException en OyenteServidor (ERROR leyendo el mensaje)");
			e.printStackTrace();
		}
		return m;
	}

}
