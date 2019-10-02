package puissance.server;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Vector;

public class InformationImpl extends UnicastRemoteObject implements Information {

	private Vector<Joueur> joueur;

	private static final long serialVersionUID = 2674880711467464646L;
	
	public InformationImpl() throws RemoteException {
		super();
		joueur = new Vector<Joueur>(2 ,1);
	}


  public String getInformation() throws RemoteException {
    System.out.println("Invocation de la méthode getInformation()");
    return "bonjour";
  }
  
  public String somme(int x, int y) {
	  return ("result : " + (x+y));
  }

	@Override
	public void updateJeu() throws RemoteException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void quitterJeu(String name) throws RemoteException {
		// TODO Auto-generated method stub
		
	}
  
  
}