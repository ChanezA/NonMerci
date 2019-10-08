package puissance.client;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface JoueurRemote extends Remote {
	public void messageFromServer(String message) throws RemoteException;
	public void updateJoueurList(String[] joueurs) throws RemoteException;
}
