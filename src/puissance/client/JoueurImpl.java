package puissance.client;

import java.net.MalformedURLException;
import java.rmi.ConnectException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import javax.swing.JOptionPane;

import puissance.server.Information;
import puissance.server.InformationImpl;

public class JoueurImpl extends UnicastRemoteObject implements JoueurRemote{

	private String name;
	protected Information information;
	JoueurGUI joueurGUI;
	
	protected boolean connectionProblem = false;
	
	protected JoueurImpl(JoueurGUI joueurGUI, String name) throws RemoteException {
		super();
		// TODO Auto-generated constructor stub
		this.joueurGUI = joueurGUI;
		this.name = name;
	}

	public void start() throws RemoteException {
		try {
			Naming.rebind("//localhost:8080/TestRMI"+name, this);
			information = ( Information ) Naming.lookup("rmi://localhost:8080/TestRMI");
			
			information.saveJoueur(name);
		}  catch (MalformedURLException e) {
		      e.printStackTrace();
	    } catch (RemoteException e) {
	      e.printStackTrace();
	    } catch (NotBoundException e) {
	      e.printStackTrace();
	    }
		System.out.println("RMI connect");
		
	}


	@Override
	public void updateJoueurList(String[] joueurs) throws RemoteException {
		joueurGUI.userPanel.remove(joueurGUI.clientPanel);
		joueurGUI.setClientPanel(joueurs);
		joueurGUI.clientPanel.repaint();
		joueurGUI.clientPanel.revalidate();
	}
	
	

}
