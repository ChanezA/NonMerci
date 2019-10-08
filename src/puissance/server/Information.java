package puissance.server;

import java.rmi.*;
import java.rmi.server.RemoteRef;

public interface Information extends Remote {

	public void updateJeu(String name, String nextPost) throws RemoteException;
	public void quitterJeu(String name) throws RemoteException;
	public void saveJoueur(Joueur joueur) throws RemoteException;
	public void removeJoueur(Joueur joueur) throws RemoteException;
	public String[] getJoueurList() throws RemoteException;
}