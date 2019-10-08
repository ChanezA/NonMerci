package puissance.server;

import java.io.Serializable;

import puissance.client.JoueurRemote;

public class Joueur implements Serializable {
	public String name;
	
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
