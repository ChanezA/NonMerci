package nonMerci.server;

import java.util.Iterator;
import java.util.SortedSet;
import java.util.TreeSet;

import nonMerci.client.IClient;

public class Joueur {
	private int nbJetons;
	private SortedSet cartes;
	private IClient client;

	public Joueur(IClient client) {
		this.client = client;
		this.cartes = new TreeSet();
	}

	public IClient getClient() {
		return client;
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

	public int calculPoint() {
		int points;
		if(cartes.size() != 0) {
		    Iterator iterator = cartes.iterator();
		    int[] cartes_entier = new int[cartes.size()];
		    int i =0;
		    while(iterator.hasNext()) {
		    	cartes_entier[i]= Integer.parseInt(iterator.next().toString());
		        i++;
		    }
		    points = cartes_entier[0];
		    for(int j=1;j<cartes_entier.length;++j) {
		        if(cartes_entier[j-1]+1 != cartes_entier[j]) {
		        	points += cartes_entier[j];
		        }
		    }
		} else {
			points = 0;
		}
	    return (points- nbJetons);
	}
}
