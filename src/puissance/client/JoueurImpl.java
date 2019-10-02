package puissance.client;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import puissance.server.Information;

public class JoueurImpl extends UnicastRemoteObject implements JoueurRemote{

	private String name;
	protected Information information;
	JoueurGUI joueurGUI;
	
	protected JoueurImpl(JoueurGUI joueurGUI, String userName) throws RemoteException {
		super();
		// TODO Auto-generated constructor stub
		this.joueurGUI = joueurGUI;
		this.name = userName;
	}

	@Override
	public void messageFromServer() throws RemoteException {
		// TODO Auto-generated method stub.
		
	}

	@Override
	public void updateJoueurList(String[] joueurs) throws RemoteException {
		joueurGUI.userPanel.remove(joueurGUI.clientPanel);
		joueurGUI.setClientPanel(joueurs);
		joueurGUI.clientPanel.repaint();
		joueurGUI.clientPanel.revalidate();
	}
	

	public void startClient() throws RemoteException {		
		String[] details = {name, hostName, clientServiceName};	

		try {
			Naming.rebind("rmi://" + hostName + "/" + clientServiceName, this);
			serverIF = ( ChatServerIF )Naming.lookup("rmi://" + hostName + "/" + serviceName);	
		} 
		catch (ConnectException  e) {
			JOptionPane.showMessageDialog(
					chatGUI.frame, "The server seems to be unavailable\nPlease try later",
					"Connection problem", JOptionPane.ERROR_MESSAGE);
			connectionProblem = true;
			e.printStackTrace();
		}
		catch(NotBoundException | MalformedURLException me){
			connectionProblem = true;
			me.printStackTrace();
		}
		if(!connectionProblem){
			registerWithServer(details);
		}	
		System.out.println("Client Listen RMI Server is running...\n");
	}

}
