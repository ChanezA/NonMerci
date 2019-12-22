package nonMerci.client;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Map;
import java.util.Map.Entry;
import java.util.SortedSet;

import javax.swing.JLabel;

import nonMerci.server.IServeur;

public class ClientImpl extends UnicastRemoteObject implements IClient{

	private static final long serialVersionUID = 1961866148111791669L;
	private String name;
	protected IServeur serveur;
	ClientGUI joueurGUI;
	
	protected boolean connectionProblem = false;
	
	protected ClientImpl(ClientGUI joueurGUI, String name) throws RemoteException {
		super();
		this.joueurGUI = joueurGUI;
		this.name = name;
	}

	public void start() {
		try {
			serveur = ( IServeur ) Naming.lookup("rmi://localhost:8080/TestRMI");
			try {
				serveur.saveJoueur(this);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
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
		joueurGUI.userPanel.validate();
		joueurGUI.setUsersPanel(joueurs);
		joueurGUI.clientPanel.revalidate();
		joueurGUI.clientPanel.repaint();
		joueurGUI.clientPanel.updateUI();
	}
	

	@Override
	public void updatePlateau(String[] data) throws RemoteException {
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
		joueurGUI.affichage.removeAll();
		JLabel label2 = new JLabel();
	    label2.setBounds(0, 40, 200, 50);
	    label2.setText("Vos jetons : " + nbJetons +"\n");
	    joueurGUI.affichage.add(label2);
		joueurGUI.clientPanel.repaint();
		joueurGUI.clientPanel.revalidate();
	}

	@Override
	public void updateCartesJoueurs(SortedSet cartes, String name) throws RemoteException {
		JLabel label1 = new JLabel();
	    label1.setBounds(0, 20, 300, 200);
		if(cartes.size() != 0) {
			label1.setText("Cartes de " + name + " : " + cartes + "\n");
			joueurGUI.affichage.add(label1);
		} else {
			label1.setText("Cartes de " + name + " : Pas de cartes\n"+"\n");
			joueurGUI.affichage.add(label1);
		}
		joueurGUI.clientPanel.repaint();
		joueurGUI.clientPanel.revalidate();
	}

	@Override
	public String getName() throws RemoteException {
		return name;
	}

	@Override
	public void updatePlateauFin(Map<IClient, Integer>lesjoueurs,IClient client) throws RemoteException {
		joueurGUI.jeux.removeAll();
		joueurGUI.jeux.add(new JLabel("Le gagnant est " + client.getName()+". "));
		for(Entry<IClient, Integer> entry : lesjoueurs.entrySet()) {
			IClient cle = entry.getKey();
			Integer valeur = entry.getValue();
			joueurGUI.jeux.add(new JLabel(cle.getName()+" a eu "+ valeur+ "points. "));
		}
		joueurGUI.clientPanel.repaint();
		joueurGUI.clientPanel.revalidate();
	}

}
