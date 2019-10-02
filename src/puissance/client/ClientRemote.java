package puissance.client;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ClientRemote extends Remote {
	public void messageFromServer() throws RemoteException;
	public void updateJoueurList() throws RemoteException;
}
