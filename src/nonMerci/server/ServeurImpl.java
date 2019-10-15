package nonMerci.server;

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
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.Vector;

import nonMerci.client.IClient;

public class ServeurImpl extends UnicastRemoteObject implements IServeur {

	String line = "---------------------------------------------\n";
	private Vector<Joueur> joueurs;
	private List<Integer> cartes;
	private boolean partieLancee;
	private String[] details = {"0", "0"}; //carte tirée, jeton pour la carte
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
		
		//12 pour test, remettre a 35
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
		if(cartes.size() != 0) {
			Random r = new Random();
			int rand = r.nextInt(cartes.size());
			details[0] = Integer.toString(cartes.get(rand));
			cartes.remove(cartes.get(rand));
		} else {
			//fin partie
			calculGagnant();
			System.out.println("FIN");
		}
	}
	
	private void calculGagnant() {

		Joueur joueurGagnant = null;
		int min = 9999;
		int points;
		for(Joueur j : joueurs){
			points = calculPoint(j);
	        if(points < min) {
	        	joueurGagnant = j;
	        	min = points;
	        }
		}	
	    updatePlateauFin(joueurGagnant);
	}

	private int calculPoint(Joueur joueur) {
		// TODO Auto-generated method stub
	
	        Iterator iterator = cartes.iterator();
	        int[] cartes_entier = new int[cartes.size()];
	        int i =0;
	        while(iterator.hasNext()) {
	        	cartes_entier[i]= Integer.parseInt(iterator.next().toString());
	            i++;
	        }
	        int points = cartes_entier[0];
	        for(int j=1;j<cartes_entier.length;++j) {
	            if(cartes_entier[j-1]+1 != cartes_entier[j]) {
	            	points += cartes_entier[j];
	            }
	        }
	        return (points- joueur.getNbJetons());
	}

	public void saveJoueur(String name) throws RemoteException {
		System.out.println("Invocation de la méthode saveJoueur()");
		if(!partieLancee) {
			try {
				IClient joueurRemote =  ( IClient ) Naming.lookup("rmi://localhost:8080/TestRMI"+name);
			
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
				System.out.println(joueurCourant);
				if(j == joueurs.get(joueurCourant)) {
					j.getJoueurRemote().activerBouton();
				} else {
					j.getJoueurRemote().desactiverBouton();
				}
				for(Joueur player : joueurs) {
					j.getJoueurRemote().updateCartesJoueurs(player.getCartes(), player.getName());
					
				}
			} 
			catch (RemoteException e) {
				e.printStackTrace();
			}
		}	
	}
	
	private void updatePlateauFin(Joueur joueur) {
		// TODO Auto-generated method stub
		
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
				joueurCourant = (joueurCourant + 1) % joueurs.size();
				break;
			}
		}
		updatePlateau();
	}
	
	public void passJoueur(String name) throws RemoteException {
		System.out.println("Invocation de la méthode passJoueur()");
		for(Joueur j : joueurs){
			if(j.getName().equals(name)){
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