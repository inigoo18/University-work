package Parte2;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;

import mensajes.MENSAJE_FICHERO_RECIBIDO;
import mensajes.Mensaje;

public class Receptor extends Thread{
	
	private Cliente cliente;
	private ObjectOutputStream rout;
	private String usuarioOrigen;
	
	public Receptor(Cliente cliente, ObjectOutputStream sout, String origen) {
		this.cliente = cliente;
		rout = sout;
		usuarioOrigen = origen;
	}

    public void connectTo(InetAddress IPClienteAConectar, int puertoAConectar) {
        try {
            Socket socket = new Socket((InetAddress) IPClienteAConectar, puertoAConectar); //crear socket con otro cliente
            ObjectInputStream rin = new ObjectInputStream(socket.getInputStream());
            try {
				Fichero ficheroRecibido = (Fichero) rin.readObject();
				cliente.getUsuario().addFichero(ficheroRecibido);
				Mensaje msjFicheroRecibido = new MENSAJE_FICHERO_RECIBIDO(usuarioOrigen, cliente.getUsuario().getName());
				msjFicheroRecibido.setnFichero(ficheroRecibido);
				rout.writeObject(msjFicheroRecibido);
			} catch (ClassNotFoundException e) {
				System.out.println("Error al recibir fichero en receptor");
			}
            //ACCEDER AL FICHERO QUE NOS HAYAN MANDADO
        } catch (IOException e) {
            System.out.println("IOException en Receptor (ERROR al conectarnos con otro cliente)");
        }
    }

}