package puissance.server;

import java.io.Serializable;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import puissance.client.JoueurRemote;

public class Joueur {
	private String name;
	private int nbJetons;
	private SortedSet cartes;
	private JoueurRemote joueurRemote;

	public Joueur(String name, JoueurRemote joueurRemote) {
		this.name = name;
		this.joueurRemote = joueurRemote;
		this.cartes = new TreeSet();
	}

	public JoueurRemote getJoueurRemote() {
		return joueurRemote;
	}

	public String getName() {
		return name;
	}

	public int getNbJetons() {
		return nbJetons;
	}

	public void setNbJetons(int nbJetons) {
		this.nbJetons = nbJetons;
	}
	
	public void addCarte(String carte) {
		cartes.add(carte);
	}

	public void initJoueur() {
		nbJetons = 11;
		cartes = new TreeSet<Integer>();
	}

	public SortedSet getCartes() {
		return cartes;
	}

	
}
