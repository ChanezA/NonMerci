package puissance.server;

import puissance.client.JoueurRemote;

public class Joueur {
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
