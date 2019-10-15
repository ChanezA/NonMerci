package nonMerci.server;

import java.io.Serializable;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import nonMerci.client.IClient;

public class Joueur {
	private String name;
	private int nbJetons;
	private SortedSet cartes;
	private IClient joueurRemote;

	public Joueur(String name, IClient joueurRemote) {
		this.name = name;
		this.joueurRemote = joueurRemote;
		this.cartes = new TreeSet();
	}

	public IClient getJoueurRemote() {
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
