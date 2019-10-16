package nonMerci.client;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.SortedSet;

import nonMerci.server.IServeur;
import nonMerci.server.Joueur;

public class ClientImpl extends UnicastRemoteObject implements IClient{

	private String name;
	protected IServeur serveur;
	ClientGUI joueurGUI;
	
	protected boolean connectionProblem = false;
	
	protected ClientImpl(ClientGUI joueurGUI, String name) throws RemoteException {
		super();
		// TODO Auto-generated constructor stub
		this.joueurGUI = joueurGUI;
		this.name = name;
	}

	public void start() {
		try {
			//IClient joueurRemote =  ( IClient ) Naming.lookup("rmi://localhost:8080/TestRMI");
			//Naming.rebind("//localhost:8080/TestRMI"+name, this);
			serveur = ( IServeur ) Naming.lookup("rmi://localhost:8080/TestRMI");
			
			serveur.saveJoueur(this);
		} catch (MalformedURLException e) {
		      e.printStackTrace();
	    } catch (RemoteException e) {
	      e.printStackTrace();
	    } catch (NotBoundException e) {
	      e.printStackTrace();
	    }
		System.out.println("RMI connect");
		
	}

	public void quitter() {
		try {
			serveur.removeJoueur(this);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}


	public void acceptJoueur() {
		try {
			serveur.acceptJoueur(this);
		} catch (RemoteException e) {
			e.printStackTrace();
		}	
	}

	public void passJoueur() {
		try {
			serveur.passJoueur(this);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void updateJoueurList(String[] joueurs) throws RemoteException {
		joueurGUI.userPanel.remove(joueurGUI.clientPanel);
		joueurGUI.setUsersPanel(joueurs);
		joueurGUI.clientPanel.repaint();
		joueurGUI.clientPanel.revalidate();
	}
	

	@Override
	public void updatePlateau(String[] data) throws RemoteException {
		joueurGUI.textArea.setText("");
		joueurGUI.setPlateau(data);
		joueurGUI.clientPanel.repaint();
		joueurGUI.clientPanel.revalidate();
	}

	@Override
	public void activerBouton() throws RemoteException {
		joueurGUI.boutonPass.setEnabled(true);
		joueurGUI.boutonAccept.setEnabled(true);
	}

	@Override
	public void desactiverBouton() throws RemoteException {
		joueurGUI.boutonPass.setEnabled(false);
		joueurGUI.boutonAccept.setEnabled(false);
		
	}

	@Override
	public void updateJetonJoueur(int nbJetons) throws RemoteException {
		joueurGUI.textArea.append("Vos jetons : " + nbJetons +"\n");
		joueurGUI.clientPanel.repaint();
		joueurGUI.clientPanel.revalidate();
		
	}

	@Override
	public void updateCartesJoueurs(SortedSet cartes, String name) throws RemoteException {
		if(cartes.size() != 0) {
			joueurGUI.textArea.append("Cartes de " + name + " : " + cartes + "\n");
		} else {

			joueurGUI.textArea.append("Cartes de " + name + " : Pas de cartes\n");
		}
		joueurGUI.clientPanel.repaint();
		joueurGUI.clientPanel.revalidate();
	}

	@Override
	public String getName() throws RemoteException {
		return name;
	}

	@Override
	public void updatePlateauFin(IClient client) throws RemoteException {
		
		joueurGUI.textArea.setText("Le gagnant est " + client.getName());
		
		joueurGUI.clientPanel.repaint();
		joueurGUI.clientPanel.revalidate();
		
	}

}
