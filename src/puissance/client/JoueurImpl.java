package puissance.client;

import java.net.MalformedURLException;
import java.rmi.ConnectException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import javax.swing.JOptionPane;

import puissance.server.Information;

public class JoueurImpl extends UnicastRemoteObject implements JoueurRemote{

	private String name;
	protected Information information;
	JoueurGUI joueurGUI;
	
	protected boolean connectionProblem = false;
	
	protected JoueurImpl(JoueurGUI joueurGUI, String userName) throws RemoteException {
		super();
		// TODO Auto-generated constructor stub
		this.joueurGUI = joueurGUI;
		this.name = userName;
	}

	@Override
	public void messageFromServer(String message) throws RemoteException {
		System.out.println( message );
		joueurGUI.textArea.append( message );
		joueurGUI.textArea.setCaretPosition(joueurGUI.textArea.getDocument().getLength());
		
	}

	@Override
	public void updateJoueurList(String[] joueurs) throws RemoteException {
		joueurGUI.userPanel.remove(joueurGUI.clientPanel);
		joueurGUI.setClientPanel(joueurs);
		joueurGUI.clientPanel.repaint();
		joueurGUI.clientPanel.revalidate();
	}
	
	

}
