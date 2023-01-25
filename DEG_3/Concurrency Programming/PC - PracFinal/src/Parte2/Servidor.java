// ALUMNOS: Iñigo SANZ y Berta DE PABLO

package Parte2;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import concurrencia.LockBakery;
import concurrencia.Monitor;
import misc.Pair;

public class Servidor {
	
	//private List<Usuario> listaUsuarios;
	private List<Pair<Usuario, ObjectOutputStream>> listaFlujos;
	private InetAddress IP;
	private int puertoServidor;
	private ServerSocket listen;
	private Monitor monitor;
	private LockBakery lock;
	
	public Servidor() {
		//cargar listaUsuarios y listaFicheros
		//listaUsuarios = new ArrayList<>();
		listaFlujos = new ArrayList<>();
		puertoServidor = 3001;
		try {
			System.out.println("Creamos socket con num puerto: "+ puertoServidor);
			listen = new ServerSocket(puertoServidor);
		} catch (IOException e) {
			System.out.println("Error creando el ServerSocket.");
		}
		monitor = new Monitor();
		lock = new LockBakery();
	}
	
	public void conexionClientes() {
		while(true) {
			try {
				Socket s = listen.accept();
				ObjectOutputStream sout = new ObjectOutputStream(s.getOutputStream());
				ObjectInputStream sin = new ObjectInputStream(s.getInputStream());
				OyenteCliente oc = new OyenteCliente(sin, sout, this, monitor, lock);
				oc.start();
			} catch (IOException e) {
				System.out.println("Error en el Servidor esperando la conexión con el cliente");
			}
		}
	}
	
	public void addFlujo(Usuario user, ObjectOutputStream out) {
		Pair<Usuario, ObjectOutputStream> par = new Pair<Usuario, ObjectOutputStream>(user, out);
		listaFlujos.add(par);
	}
	
	public void deleteFlujo(Usuario user) {
		for (Pair<Usuario, ObjectOutputStream> par : listaFlujos) {
			if (user.equals(par.getFirst())) {
				listaFlujos.remove(par);
			}
		}
	}
	
	public ObjectOutputStream buscarFichero(String nFichero) {
        // buscar usuario que contiene nFichero
		// devolver objectoutpustream del socket de ese usuario
		List<Usuario> listaUsuarios = monitor.getListaUsuarios();
		for (Usuario u: listaUsuarios) {
			List<Fichero> ficheroList = u.getList();
			for (Fichero f: ficheroList) {
				if (f.getName().equals(nFichero)) {
					for (Pair<Usuario, ObjectOutputStream> par: listaFlujos) {
						if (u.equals(par.getFirst())){
							return par.getSecond();
						}
					}
				}
			}
		}
		return null;
		
    }

    public String buscarNombreUsuario(String nFichero) {
        //devolver el nombre del usuario que tiene el fichero nFichero
    	List<Usuario> listaUsuarios = monitor.getListaUsuarios();
    	for (Usuario u: listaUsuarios) {
			List<Fichero> ficheroList = u.getList();
			for (Fichero f: ficheroList) {
				if (f.getName().equals(nFichero)) {
					return u.getName();
				}
			}
		}
		return null;
    }

    public ObjectOutputStream buscarFlujo(String usuarioDestino) {
    	for (Pair<Usuario, ObjectOutputStream> par: listaFlujos) {
			if (par.getFirst().getName().equals(usuarioDestino)) {
				return par.getSecond();
			}
		}
		return null;
    }
    
	
}
