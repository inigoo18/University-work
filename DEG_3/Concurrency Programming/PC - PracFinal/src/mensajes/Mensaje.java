package mensajes;

import java.io.Serializable;
import java.net.InetAddress;

import Parte2.Fichero;
import Parte2.Usuario;

public abstract class Mensaje implements Serializable {
	
	protected String tipo;
	protected String origen;
	protected String destino;
	protected String info;
	protected Usuario user;
	protected InetAddress IP;
	protected int puerto;
	protected Fichero nFichero;


	public Mensaje(String o, String d) {
		origen = o;
		destino = d;
	}
	
	public String getTipo() {
		return tipo;
	}
	
	public String getOrigen() {
		return origen;
	}
	
	public String getDestino() {
		return destino;
	}
	
	public String getInfo() {
		return info;
	}
	
	public void setInfo(String inf) {
		info = inf;
	}
	
	public void setUser(Usuario user) {
		this.user = user;
	}
	
	public Usuario getUser() {
		return user;
	}
	
	public int getPuerto() {
		return puerto;
	}
	
	public void setPuerto(int num) {
		puerto = num;
	}
	
	public InetAddress getIP() {
		return IP;
	}
	
	public void setIP(InetAddress num) {
		IP = num;
	}
	
	public Fichero getnFichero() {
		return nFichero;
	}
	
	public void setnFichero(Fichero nFichero) {
		this.nFichero = nFichero;
	}
	
}
