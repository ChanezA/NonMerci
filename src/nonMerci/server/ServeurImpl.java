package nonMerci.server;

import java.net.MalformedURLException;
import java.rmi.AlreadyBoundException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Vector;

import nonMerci.client.IClient;

public class ServeurImpl extends UnicastRemoteObject implements IServeur {

	String line = "---------------------------------------------\n";
	private Vector<Joueur> joueurs;
	private List<Integer> cartes;
	private boolean partieLancee;
	private String[] details = {"0", "0"}; //carte tir√©e, jeton pour la carte
	private int joueurCourant;
	
	private static final long serialVersionUID = 2674880711467464646L;
	
	public ServeurImpl() throws RemoteException {
		super();
		joueurs = new Vector<Joueur>(2 ,1);
		cartes = new ArrayList<Integer>();
		partieLancee = false;
	}
	
	public static void main(String[] args) {
		try {
		      LocateRegistry.createRegistry(8080);
		      IServeur information = new ServeurImpl();

		      Naming.bind("rmi://localhost:8080/TestRMI", information);
		      
		      System.out.println("Serveur lanc√©");
		    } catch (RemoteException e) {
		      e.printStackTrace();
		    } catch (MalformedURLException e) {
		      e.printStackTrace();
		    } catch (AlreadyBoundException e) {
				e.printStackTrace();
			} catch(Exception e){
				System.out.println("Probl√®mes Serveur");
			}	
	}
		
	public void initPartie() {
		// Mettre ‡ 12 pour test, remettre a 35
		for(int i=3;i<=35;++i) {
			cartes.add(i);
		}

		Random r = new Random();
		
		// Retire 9 cartes du jeu
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
		System.out.println("nb cartes : " + cartes.size());
		if(cartes.size() != 0) {
			Random r = new Random();
			int rand = r.nextInt(cartes.size());
			details[0] = Integer.toString(cartes.get(rand));
			cartes.remove(cartes.get(rand));
		} else {
			//fin partie
			partieLancee = false;
			calculGagnant();
			System.out.println("FIN");
		}
	}
	
	private void calculGagnant() {

		Joueur joueurGagnant = null;
		int min = Integer.MAX_VALUE;
		int points;
		for(Joueur j : joueurs){
			points = j.calculPoint();
			//points = calculPoint(j);
	        if(points < min) {
	        	joueurGagnant = j;
	        	min = points;
	        }
		}	
	    updatePlateauFin(joueurGagnant);
	}
/*
	public void saveJoueur(IClient joueur) throws RemoteException {
		System.out.println("Invocation de la m√©thode saveJoueur()");
		if(!partieLancee) {
			joueurs.addElement(new Joueur(joueur));
			updateUserList();	
			if(joueurs.size() == 3) {
				partieLancee = true;
				initPartie();
				updatePlateau();
			}	
		}
	}
*/	
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
				j.getClient().updateJoueurList(currentUsers);
			} 
			catch (RemoteException e) {
				e.printStackTrace();
			}
		}	
	}

	private void updatePlateau() {
		for(Joueur j : joueurs){
			try {
				j.getClient().updatePlateau(details);
				j.getClient().updateJetonJoueur(j.getNbJetons());
				if(j == joueurs.get(joueurCourant)) {
					j.getClient().activerBouton();
				} else {
					j.getClient().desactiverBouton();
				}
				for(Joueur player : joueurs) {
					j.getClient().updateCartesJoueurs(player.getCartes(), player.getClient().getName());
					
				}
			} 
			catch (RemoteException e) {
				e.printStackTrace();
			}
		}	
	}
	
	private void updatePlateauFin(Joueur joueur) {
		// TODO Auto-generated method stub
		System.out.println("Plateau Fin");

		for(Joueur j : joueurs){
			try {
				j.getClient().updatePlateauFin(joueur.getClient());
				j.getClient().desactiverBouton();
			} 
			catch (RemoteException e) {
				e.printStackTrace();
			}
		}	
	}
	
	public String[] getJoueurList() throws RemoteException {
		System.out.println("Invocation de la m√©thode getJoueurList()");
		String[] joueursList = new String[joueurs.size()];
		for(int i = 0; i< joueursList.length; i++){
			joueursList[i] = joueurs.elementAt(i).getClient().getName();
		}
		return joueursList;
	}
	
	
	public void removeJoueur(IClient joueur) throws RemoteException {
		System.out.println("Invocation de la mÈthode removeJoueur()");
		for(Joueur j : joueurs){
			if(j.getClient().equals(joueur)){
				joueurs.remove(j);
				break;
			}
		}	
		updateUserList();
	}
  	
	public void acceptJoueur(IClient joueur) throws RemoteException {
		System.out.println("Invocation de la mÈthode acceptJoueur()");
		for(Joueur j : joueurs){
			if(j.getClient().equals(joueur)){
				j.setNbJetons(j.getNbJetons() + Integer.parseInt(details[1]));
				j.addCarte(details[0]);
				details[1] = "0";
				choisirCarte();
//				joueurCourant = (joueurCourant + 1) % joueurs.size();
				break;
			}
		}
		if(partieLancee) {
			updatePlateau();
		}
	}
	
	public void passJoueur(IClient joueur) throws RemoteException {
		System.out.println("Invocation de la mÈthode passJoueur()");
		for(Joueur j : joueurs){
			if(j.getClient().equals(joueur)){
				if(j.getNbJetons()==0) {
					acceptJoueur(joueur);
					break;
				}
					j.setNbJetons(j.getNbJetons()-1);
					details[1] = Integer.toString(Integer.parseInt(details[1]) + 1);
					joueurCourant = (joueurCourant + 1) % joueurs.size();
					break;
			}
		}	
		updatePlateau();
	}

	@Override
	public boolean joueurExistant(String name) throws RemoteException {
		System.out.println("Invocation de la mÈthode joueurExistant()");
		boolean test = false;
		for(Joueur j : joueurs){
			if(j.getClient().getName().equals(name)){
				test = true;
				break;
			}
		}
		return test;
	}
	
	public void saveJoueur(IClient joueur) throws RemoteException {
		System.out.println("Invocation de la mÈthode saveJoueur()");
		if(!partieLancee) {
			if(!joueurExistant(joueur.getName())){
				joueurs.addElement(new Joueur(joueur));
				updateUserList();	
				if(joueurs.size() == 3) {
					partieLancee = true;
					initPartie();
					updatePlateau();
				}
			} else {
				// TODO : pour l'instant si on a un mÍme nom qu'un autre joueur, on se connecte pour
				// un jeu mais pas sur la mÍme partie que le joueur qui porte le mÍme nom
			}
		}
	}
	

}