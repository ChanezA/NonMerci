package puissance.server;

import puissance.client.JoueurRemote;

public class Joueur {
	public String name;
	public JoueurRemote joueur;
	
	public Joueur(String name, JoueurRemote joueur) {
		this.name = name;
		this.joueur = joueur;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public JoueurRemote getJoueur() {
		return joueur;
	}

	public void setJoueur(JoueurRemote joueur) {
		this.joueur = joueur;
	}
	
}
