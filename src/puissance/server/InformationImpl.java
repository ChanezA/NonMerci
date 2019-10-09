package puissance.server;

import java.net.MalformedURLException;
import java.rmi.AlreadyBoundException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.RemoteRef;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.Vector;

import puissance.client.JoueurRemote;

public class InformationImpl extends UnicastRemoteObject implements Information {

	String line = "---------------------------------------------\n";
	private Vector<Joueur> joueurs;
	private List<Integer> cartes;
	private int cpt;

	private static final long serialVersionUID = 2674880711467464646L;
	
	public InformationImpl() throws RemoteException {
		super();
		joueurs = new Vector<Joueur>(2 ,1);
		cartes = new ArrayList<Integer>();
		cpt = 0;
	}
	
	public static void main(String[] args) {
		try {
		      LocateRegistry.createRegistry(8080);
		      Information information = new InformationImpl();

		      Naming.bind("rmi://localhost:8080/TestRMI", information);
		      
		      System.out.println("Serveur lancé");
		    } catch (RemoteException e) {
		      e.printStackTrace();
		    } catch (MalformedURLException e) {
		      e.printStackTrace();
		    } catch (AlreadyBoundException e) {
				e.printStackTrace();
			} catch(Exception e){
				System.out.println("Problèmes Serveur");
			}	
	}
	
	public void init() throws RemoteException {
		cpt++;
	}
	
	public void initJoueurs() {
		
		for(int i=3;i<=35;++i) {
			cartes.add(i);
		}

		Random r = new Random();
		for(int i=0;i<10;++i) {
			cartes.remove(cartes.get(r.nextInt(cartes.size())));
		}
		
		for(Joueur j : joueurs){
			j.initJoueur();
		}
		
		
	}

	public void saveJoueur(String name) throws RemoteException {
		System.out.println("Invocation de la méthode saveJoueur()");
		
		try {
			JoueurRemote joueurRemote =  ( JoueurRemote ) Naming.lookup("rmi://localhost:8080/TestRMI"+name);
		
			joueurs.addElement(new Joueur(name, joueurRemote));
			
			//joueurRemote.messageFromServer("[Server] : Hello " + name + "\n");
			
			//sendToAll("[Server] : " + name + " has joined the group.\n");
			
			updateUserList();	
			
		}  catch (MalformedURLException e) {
		      e.printStackTrace();
	    } catch (RemoteException e) {
	      e.printStackTrace();
	    } catch (NotBoundException e) {
	      e.printStackTrace();
	    }
		
	}
	
	
	private void updateUserList() {
		String[] currentUsers = null;
		try {
			currentUsers = getJoueurList();
		} catch (RemoteException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		//String[] currentUsers = getJoueurList();
		for(Joueur j : joueurs){
			try {
				j.getJoueurRemote().updateJoueurList(currentUsers);
			} 
			catch (RemoteException e) {
				e.printStackTrace();
			}
		}	
	}

	
	public String[] getJoueurList() throws RemoteException {
		System.out.println("Invocation de la méthode getJoueurList()");
		String[] joueursList = new String[joueurs.size()];
		for(int i = 0; i< joueursList.length; i++){
			joueursList[i] = joueurs.elementAt(i).getName();
		}
		return joueursList;
	}
	
	
	public void removeJoueur(String username) throws RemoteException {
		System.out.println("Invocation de la méthode removeJoueur()");
		for(Joueur j : joueurs){
			if(j.getName().equals(username)){
				joueurs.remove(j);
				break;
			}
		}	
		
		updateUserList();
		
		System.out.println(joueurs);
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