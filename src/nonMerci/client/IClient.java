package nonMerci.client;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.SortedSet;

import nonMerci.server.Joueur;

public interface IClient extends Remote {
	public void updateJoueurList(String[] joueurs) throws RemoteException;
	public void updatePlateau(String[] data) throws RemoteException;
	public void activerBouton() throws RemoteException;
	public void desactiverBouton() throws RemoteException;
	public void updateJetonJoueur(int nbJetons) throws RemoteException;
	public void updateCartesJoueurs(SortedSet cartes, String string) throws RemoteException;
	
	public String getName() throws RemoteException;
	public void updatePlateauFin(IClient client) throws RemoteException;
}
