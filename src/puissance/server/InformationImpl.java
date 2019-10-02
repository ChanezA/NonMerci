package puissance.server;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Vector;

public class InformationImpl extends UnicastRemoteObject implements Information {

	private Vector<Joueur> joueurs;

	private static final long serialVersionUID = 2674880711467464646L;
	
	public InformationImpl() throws RemoteException {
		super();
		joueurs = new Vector<Joueur>(2 ,1);
	}


	public String getInformation() throws RemoteException {
		System.out.println("Invocation de la méthode getInformation()");
		return "bonjour";
	}
  
  	
	@Override
	public void updateJeu() throws RemoteException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void quitterJeu(String name) throws RemoteException {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void registerListener(String[] details) throws RemoteException {
		// TODO Auto-generated method stub
		
	}
	

	private void updateUserList() {
		String[] currentUsers = getUserList();	
		for(Joueur c : joueurs){
			try {
				c.getJoueur().updateJoueurList(currentUsers);
			} 
			catch (RemoteException e) {
				e.printStackTrace();
			}
		}	
	}
	

	private String[] getUserList(){
		// generate an array of current users
		String[] allUsers = new String[joueurs.size()];
		for(int i = 0; i< allUsers.length; i++){
			allUsers[i] = joueurs.elementAt(i).getName();
		}
		return allUsers;
	}
  
}