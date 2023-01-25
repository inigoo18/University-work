package concurrencia;

import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

import Parte2.Fichero;
import Parte2.Usuario;
import misc.Pair;

public class Monitor {

	private List<Usuario> listaUsuarios;
	
	
	public Monitor() {
		listaUsuarios = new ArrayList<>();
	}
	
	
	public synchronized List<Usuario> getListaUsuarios(){	
		return listaUsuarios;
	}
	
	public synchronized void addUserInfo(Usuario user) {
		listaUsuarios.add(user);
	}
	
	public synchronized void deleteUserInfo(Usuario user) {
		listaUsuarios.remove(user);
	}
	
	public synchronized void addFicheroToUser(Fichero fichero, String usuario) { 
    	//añadir fichero a usuario
    	for (Usuario u: listaUsuarios) {
			if(u.getName().equals(usuario)) {
				u.addFichero(fichero);
			}
		}
    }
	
	
	
}
