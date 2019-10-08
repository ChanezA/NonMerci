package puissance.server;

import java.io.Serializable;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import puissance.client.JoueurRemote;

public class Joueur {
	private String name;
	private int nbJetons;
	private SortedSet<Integer> cartes;


	public Joueur(String name) {
		this.name = name;
		
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void initJoueur() {
		nbJetons = 11;
		cartes = new TreeSet<Integer>();
	}

	
}
