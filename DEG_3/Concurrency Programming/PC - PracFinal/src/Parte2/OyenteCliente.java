package Parte2;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.util.List;

import concurrencia.LockBakery;
import concurrencia.Monitor;
import mensajes.MENSAJE_CONFIRMACION_CERRAR_CONEXION;
import mensajes.MENSAJE_CONFIRMACION_CONEXION;
import mensajes.MENSAJE_CONFIRMACION_LISTA_USUARIOS;
import mensajes.MENSAJE_EMITIR_FICHERO;
import mensajes.MENSAJE_PREPARADO_SERVIDORCLIENTE;
import mensajes.Mensaje;
import misc.Pair;

public class OyenteCliente extends Thread{
	
	private ObjectInputStream cin;
	private ObjectOutputStream cout;
	private Servidor server;
	private Monitor monitor;
	private LockBakery lock;

	public OyenteCliente(ObjectInputStream sin, ObjectOutputStream sout, Servidor server, Monitor monitor, LockBakery lock) {
		cin = sin;
		cout = sout;
		this.server = server;
		this.monitor = monitor;
		this.lock = lock;
	}
	
	@Override
	public void run() {
		Mensaje m;
		
		while(true) { 
			m = leerMensaje();
			String tipo = m.getTipo();

			if (tipo.equals("MENSAJE_CONEXION")) {
				//guardamos info del usuario    (en las tablas)
				// envio mensaje confirmacion conexion out
				
				System.out.println("Hemos recibido mensaje conexion");
				Usuario usr = m.getUser();
				
				monitor.addUserInfo(usr);
				usr.setId(monitor.getListaUsuarios().size());
				
				lock.takeLock(usr.getId());
				server.addFlujo(usr, cout);
				lock.releaseLock(usr.getId());
				
				
				Mensaje conexionConfirmada = new MENSAJE_CONFIRMACION_CONEXION("Servidor", "Cliente"); 
				try {
					cout.writeObject(conexionConfirmada);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					System.out.println("Hubo un problema mandandole el mensaje de conexion confirmada al cliente...");
				}
				
			}
			else if (tipo.equals("MENSAJE_LISTA_USUARIOS")) {
				// crear un mensaje con la info de usuarios en sistema
				//envio mensaje confirmacion lista usuarios fout
				
				Mensaje mensajeListaUsuarios = new MENSAJE_CONFIRMACION_LISTA_USUARIOS("Servidor", "Cliente");
				List<Usuario> listaUsuarios = monitor.getListaUsuarios();
				
				String lista = "";
				for (Usuario user : listaUsuarios) {
					lista += user.getName() + " : [";
					
					for (Fichero fich : user.getList()) {
						lista += fich.getName() + " ";
					}
					
					lista += "]" + "\n";
				}
				
				mensajeListaUsuarios.setInfo(lista);
				
				try {
					cout.writeObject(mensajeListaUsuarios);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					System.out.println("Hubo un problema mandandole la lista de usuarios conectados al cliente...");
				}
				
				
				
			}
			else if (tipo.equals("MENSAJE_CERRAR_CONEXION")) {
				// eliminar informacion del usuario (en las tablas)
				// envio mensaje confirmacion cerrar conexion out
				
				Usuario usuarioABorrar = m.getUser();
				monitor.deleteUserInfo(usuarioABorrar);
				
				
				Mensaje cerrarConfirmada = new MENSAJE_CONFIRMACION_CERRAR_CONEXION("Servidor", "Cliente"); 
				try {
					cout.writeObject(cerrarConfirmada);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					System.out.println("Hubo un problema mandandole el mensaje de CERRAR conexión confirmada al cliente...");
				}
				
				
			}
			else if (tipo.equals("MENSAJE_PEDIR_FICHERO")) {
				//buscar usuario que contiene el fichero y obtener fout2
				//envio mensaje MENSAJE_EMITIR_FICHERO por fout2
				
				String nFichero, nCliente;
                try {
                    nCliente = (String) cin.readObject(); //nombre del cliente que solicita el fichero
                    nFichero = (String) cin.readObject(); //nombre del fichero que solicita
                    ObjectOutputStream fout2 = server.buscarFichero(nFichero); //buscar usuario que contiene el fichero y obtener fout2
                    if (fout2 != null) {
	                    String usuarioConFichero = server.buscarNombreUsuario(nFichero);
	                    Mensaje MensajeEmitir = new MENSAJE_EMITIR_FICHERO(usuarioConFichero, nCliente);
	                    MensajeEmitir.setInfo(nFichero);
	                    fout2.writeObject(MensajeEmitir); //envio mensaje MENSAJE_EMITIR_FICHERO por fout2
                    }
                    else {
                    	System.out.println("Ese fichero no lo tiene nadie!");
                    }
                } catch (ClassNotFoundException e) {
                    System.out.println("ClassNotFoundException en OyenteCliente (ERROR en MENSAJE_PEDIR_FICHERO)");
                } catch (IOException e) {
                    System.out.println("IOException en OyenteCliente (ERROR en MENSAJE_PEDIR_FICHERO)");
                }
				
			}
			else if (tipo.equals("MENSAJE_PREPARADO_CLIENTESERVIDOR")) {
				//buscar fout1 (flujo del cliente al que hay que enviar la info)
				// envio fout1 mensaje MENSAJE_PREPARADO_SERVIDOR
				try {
					Usuario usr = m.getUser();
					lock.takeLock(usr.getId()); // protegemos la lista de flujos compartido (buscarFlujo)
					ObjectOutputStream fout1 = server.buscarFlujo(m.getDestino()); //buscar fout1 (flujo del cliente al que hay que enviar la info)
					lock.releaseLock(usr.getId());
					Mensaje mensajePrepServCli = new MENSAJE_PREPARADO_SERVIDORCLIENTE(m.getDestino(), m.getOrigen());
					mensajePrepServCli.setIP(m.getIP());
					mensajePrepServCli.setPuerto(m.getPuerto());
					fout1.writeObject(mensajePrepServCli); //envio fout1 mensaje MENSAJE_PREPARADO_SERVIDOR
					
                } catch (IOException e) {
                    System.out.println("IOException en OyenteCliente (ERROR en MENSAJE_PREPARADO_CLIENTESERVIDOR)");
                }
			}
			else if (tipo.equals("MENSAJE_FICHERO_RECIBIDO")){
				monitor.addFicheroToUser(m.getnFichero(), m.getDestino());
			}
		}
		
	}
	
	private Mensaje leerMensaje() {
		Mensaje m = null;
		try {
			m = (Mensaje) cin.readObject();
		} catch (ClassNotFoundException e) {
			System.out.println("ClassNotFoundException en OyenteCliente (ERROR leyendo el mensaje)");
		} catch (IOException e) {
			System.out.println("IOException en OyenteCliente (ERROR leyendo el Mensaje)");
			e.printStackTrace();
		}
		return m;
	}

}
