package puissance.server;

import java.io.Serializable;

import puissance.client.JoueurRemote;

public class Joueur implements Serializable {
	public String name;

    private  static  final  long serialVersionUID =  1350092881346723535L;
    
	public Joueur(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	
}
