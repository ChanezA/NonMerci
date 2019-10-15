package nonMerci.client;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.SortedSet;

public interface JoueurRemote extends Remote {
	public void updateJoueurList(String[] joueurs) throws RemoteException;
	public void updatePlateau(String[] data) throws RemoteException;
	public void activerBouton() throws RemoteException;
	public void desactiverBouton() throws RemoteException;
	public void updateJetonJoueur(int nbJetons) throws RemoteException;
	public void updateCartesJoueurs(SortedSet cartes, String string) throws RemoteException;
}
