package nonMerci.server;

import java.rmi.*;

import nonMerci.client.IClient;

public interface IServeur extends Remote {

	public void saveJoueur(IClient joueur) throws RemoteException, InterruptedException;
	public void removeJoueur(IClient joueur) throws RemoteException;
	public boolean joueurExistant(String username) throws RemoteException;
	/*	public String[] getJoueurList() throws RemoteException;*/
	public void passJoueur(IClient joueur) throws RemoteException;
	public void acceptJoueur(IClient joueur) throws RemoteException;
	
}