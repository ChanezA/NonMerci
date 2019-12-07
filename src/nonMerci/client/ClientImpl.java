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
		// TODO Auto-generated constructor stub
		this.joueurGUI = joueurGUI;
		this.name = name;
	}

	public void start() {
		try {
			//IClient joueurRemote =  ( IClient ) Naming.lookup("rmi://localhost:8080/TestRMI");
			//Naming.rebind("//localhost:8080/TestRMI"+name, this);
			serveur = ( IServeur ) Naming.lookup("rmi://localhost:8080/TestRMI");
			
			try {
				serveur.saveJoueur(this);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
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
		//joueurGUI.textArea.setText("");
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
		//joueurGUI.conteneur1.add(label2);
	    joueurGUI.affichage.add(label2);
		//joueurGUI.textArea.append("Vos jetons : " + nbJetons +"\n");
		joueurGUI.clientPanel.repaint();
		joueurGUI.clientPanel.revalidate();
		
	}

	@Override
	public void updateCartesJoueurs(SortedSet cartes, String name) throws RemoteException {
		
		
		JLabel label1 = new JLabel();
	    label1.setBounds(0, 20, 300, 200);
		
		if(cartes.size() != 0) {
			//joueurGUI.label3.setText("Cartes de " + name + " : " + cartes + "\n");
			//joueurGUI.textArea.append("Cartes de " + name + " : " + cartes + "\n");
			label1.setText("Cartes de " + name + " : " + cartes + "\n");
			//joueurGUI.conteneur1.add(label1);
			joueurGUI.affichage.add(label1);
		} else {
			//joueurGUI.label3.setText("Cartes de " + name + " : Pas de cartes\n");

			label1.setText("Cartes de " + name + " : Pas de cartes\n"+"\n");
			//joueurGUI.conteneur1.add(label1);
			joueurGUI.affichage.add(label1);
			//joueurGUI.label.setText("Cartes de " + name + " : Pas de cartes\n");
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
		//joueurGUI.affichage.removeAll();
		joueurGUI.jeux.removeAll();
		//joueurGUI.textArea.setText("Le gagnant est " + client.getName());
		joueurGUI.jeux.add(new JLabel("Le gagnant est " + client.getName()+"\n"));
		for(Entry<IClient, Integer> entry : lesjoueurs.entrySet()) {
			IClient cle = entry.getKey();
			Integer valeur = entry.getValue();
		    // traitements
			/*if(cle.getName().equals(client.getName())) {
				//
			}*/
			System.out.println(cle.getName()+" a eu "+ valeur+ "points");
			joueurGUI.jeux.add(new JLabel(cle.getName()+" a eu "+ valeur+ "points"+"\n"));
			
			//System.out.println(cle.getName()+" a eu "+ valeur+ "points");

		}
		
		joueurGUI.clientPanel.repaint();
		joueurGUI.clientPanel.revalidate();
		
	}

}
