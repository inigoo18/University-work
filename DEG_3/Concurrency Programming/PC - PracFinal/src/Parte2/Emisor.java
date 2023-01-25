package Parte2;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Emisor extends Thread{

    private ServerSocket listen;
    private String nFichero;
    private Cliente cliente;

    public Emisor(int puerto, String nFichero, Cliente cliente) {
        try {
            listen = new ServerSocket(puerto);
        } catch (IOException e) {
            System.out.println("Error creando el ServerSocket del Emisor");
        }
        this.nFichero = nFichero;
        this.cliente = cliente;
    }

    @Override
    public void run() {
        while(true) {
            try {
                Socket s = listen.accept();
                ObjectOutputStream eout = new ObjectOutputStream(s.getOutputStream());
                Usuario usr = cliente.getUsuario();
                Fichero ficheroAEnviar = usr.getFichero(nFichero);
                eout.writeObject(ficheroAEnviar); //ESCRIBIR FICHERO AQUI
                cliente.setPuerto(cliente.getPuerto()+1);
            } catch (IOException e) {
                System.out.println("Error en la escucha del canal del Emisor.");
            }
            //esperar a que se conecte otro cliente conmigo
        }
    }

}