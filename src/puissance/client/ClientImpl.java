package puissance.client;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import puissance.server.Information;

public class ClientImpl extends UnicastRemoteObject implements ClientRemote{

	private String name;
	protected Information information;
	
	protected ClientImpl(String userName) throws RemoteException {
		super();
		// TODO Auto-generated constructor stub
		// this.gui = gui;
		this.name = userName;
	}

	@Override
	public void messageFromServer() throws RemoteException {
		// TODO Auto-generated method stub.
		
	}

	@Override
	public void updateJoueurList() throws RemoteException {
		// TODO Auto-generated method stub
		
	}

}
