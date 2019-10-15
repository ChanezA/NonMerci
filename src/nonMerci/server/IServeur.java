package nonMerci.server;

import java.rmi.*;
import java.rmi.server.RemoteRef;

import nonMerci.client.IClient;

public interface IServeur extends Remote {

	public void saveJoueur(IClient joueur) throws RemoteException;
	/*public void removeJoueur(String username) throws RemoteException;
	public boolean joueurExistant(String username) throws RemoteException;
	public String[] getJoueurList() throws RemoteException;
	public void passJoueur(String name) throws RemoteException;
	public void acceptJoueur(String name) throws RemoteException;*/
	
}