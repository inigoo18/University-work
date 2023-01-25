package Parte2;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.OutputStream;
import java.io.Serializable;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Usuario implements Serializable{
	
	private int id;
	private String name;
	private InetAddress IP;
	private List<Fichero> listFicheros;
	
	public Usuario() {
		listFicheros = new ArrayList<>();
		try {
			IP = InetAddress.getLocalHost();
		} catch (UnknownHostException e) {
			System.out.println("Error creando la IP del usuario con id = " + this.id);
		}
	}
	
	public String getName() {
		return name;
	}
	
	public List<Fichero> getList() {
		return listFicheros;
	}
	
	public void setUserName(String str) {
		name = str;
	}
	
	public void addFichero(Fichero f) {
		listFicheros.add(f);
	}
	
	
	public Fichero getFichero(String name) {
		for (Fichero f: listFicheros) {
			if (f.getName().equals(name)) {
				return f;
			}
		}
		return null;
	}
	
	public InetAddress getIP() {
		return IP;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public int getId() {
		return id;
	}
	
	
}
