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
	private boolean partieLancee;
	private String[] details = {"0", "0"}; //carte tirée, jeton pour la carte
	private int joueurCourant;
	
	private static final long serialVersionUID = 2674880711467464646L;
	
	public InformationImpl() throws RemoteException {
		super();
		joueurs = new Vector<Joueur>(2 ,1);
		cartes = new ArrayList<Integer>();
		partieLancee = false;
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
		
	public void initPartie() {
		
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
		
		joueurCourant = r.nextInt(joueurs.size());
		choisirCarte();
		
	}
	
	public void choisirCarte() {
		Random r = new Random();
		int rand = r.nextInt(cartes.size());
		details[0] = Integer.toString(cartes.get(rand));
		cartes.remove(cartes.get(rand));
	}

	public void saveJoueur(String name) throws RemoteException {
		System.out.println("Invocation de la méthode saveJoueur()");
		if(!partieLancee) {
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
			
			if(joueurs.size() == 3) {
				partieLancee = true;
				initPartie();
				updatePlateau();
			}	
		}
	}
	
	
	private void updateUserList() {
		String[] currentUsers = null;
		try {
			currentUsers = getJoueurList();
		} catch (RemoteException e1) {
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

	private void updatePlateau() {
		for(Joueur j : joueurs){
			try {
				j.getJoueurRemote().updatePlateau(details);
				j.getJoueurRemote().updateJetonJoueur(j.getNbJetons());
				if(j == joueurs.get(joueurCourant)) {
					j.getJoueurRemote().activerBouton();
				} else {
					j.getJoueurRemote().desactiverBouton();
				}
				for(Joueur player : joueurs) {
					j.getJoueurRemote().updateCartesJoueurs(j.getCartes(), player.getName());
					
				}
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
	
	
	public void removeJoueur(String name) throws RemoteException {
		System.out.println("Invocation de la méthode removeJoueur()");
		for(Joueur j : joueurs){
			if(j.getName().equals(name)){
				joueurs.remove(j);
				break;
			}
		}	
		updateUserList();
	}
  	
	public void acceptJoueur(String name) throws RemoteException {
		System.out.println("Invocation de la méthode acceptJoueur()");
		for(Joueur j : joueurs){
			if(j.getName().equals(name)){
				j.setNbJetons(j.getNbJetons() + Integer.parseInt(details[1]));
				j.addCarte(details[0]);
				details[1] = "0";
				choisirCarte();
				joueurCourant++;
				break;
			}
		}	
	}
	
	public void passJoueur(String name) throws RemoteException {
		System.out.println("Invocation de la méthode passJoueur()");
		for(Joueur j : joueurs){
			if(j.getName().equals(name)){
				j.setNbJetons(j.getNbJetons()-1);
				details[1] = details[1] + 1;
				joueurCourant++;
				break;
			}
		}	
	}

	@Override
	public boolean joueurExistant(String name) throws RemoteException {
		System.out.println("Invocation de la méthode joueurExistant()");
		boolean test = false;
		for(Joueur j : joueurs){
			if(j.getName().equals(name)){
				test = true;
				break;
			}
		}
		return test;
	}

}