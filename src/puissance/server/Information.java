package puissance.server;

import java.rmi.*;
import java.rmi.server.RemoteRef;

public interface Information extends Remote {

	public void saveJoueur(String username) throws RemoteException;
	public void removeJoueur(String username) throws RemoteException;
	public boolean joueurExistant(String username) throws RemoteException;
	public String[] getJoueurList() throws RemoteException;
	public void passJoueur(String name) throws RemoteException;
	public void acceptJoueur(String name) throws RemoteException;
	
}