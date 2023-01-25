package Parte2;

import java.io.File;
import java.io.Serializable;

public class Fichero implements Serializable {
	
    private String name;
    private String content;
    
    public Fichero(String name, String content) {
    	this.content = content;
    	this.name = name;
    }
    
    public String getName() {
    	return name;
    }
    
    public String getFile() {
    	return content;
    }

}
