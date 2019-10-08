package puissance.server;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.server.RemoteRef;
import java.rmi.server.UnicastRemoteObject;
import java.util.Date;
import java.util.Vector;

import puissance.client.JoueurRemote;

public class InformationImpl extends UnicastRemoteObject implements Information {

	String line = "---------------------------------------------\n";
	private Vector<Joueur> joueurs;

	private static final long serialVersionUID = 2674880711467464646L;
	
	public InformationImpl() throws RemoteException {
		super();
		joueurs = new Vector<Joueur>(2 ,1);
	}

	public void saveJoueur(String username) throws RemoteException {
		System.out.println("Invocation de la méthode saveJoueur()");
		joueurs.addElement(new Joueur(username));
		System.out.println(joueurs);
	}
	

	public void removeJoueur(String username) throws RemoteException {
		System.out.println("Invocation de la méthode removeJoueur()");
		for(Joueur j : joueurs){
			if(j.getName().equals(username)){
				joueurs.remove(j);
				break;
			}
		}	
		System.out.println(joueurs);
	}
  	
	@Override
	public void updateJeu(String name, String nextPost) throws RemoteException {
		String message =  name + " : " + nextPost + "\n";
		//sendToAll(message);
	}

	@Override
	public void quitterJeu(String name) throws RemoteException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String[] getJoueurList() throws RemoteException {
		System.out.println("Invocation de la méthode getJoueurList()");
		String[] joueursList = new String[joueurs.size()];
		for(int i = 0; i< joueursList.length; i++){
			joueursList[i] = joueurs.elementAt(i).getName();
		}
		return joueursList;
	}

	@Override
	public boolean joueurExistant(String username) throws RemoteException {
		System.out.println("Invocation de la méthode joueurExistant()");
		boolean test = false;
		for(Joueur j : joueurs){
			if(j.getName().equals(username)){
				test = true;
				break;
			}
		}
		return test;
	}  
}