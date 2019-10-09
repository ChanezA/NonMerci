package puissance.client;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface JoueurRemote extends Remote {
	public void updateJoueurList(String[] joueurs) throws RemoteException;
}
