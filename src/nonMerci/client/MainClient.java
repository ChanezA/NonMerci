package nonMerci.client;

import java.net.InetAddress;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;

import nonMerci.server.IServeur;
import nonMerci.server.Joueur;


public class MainClient {
	public static void main(String[] argv) {

		try {
			IServeur info = (IServeur) Naming.lookup("//localhost:8080/TestRMI");
			info.saveJoueur("user");
			System.out.println("test");
			while(true) {
				
			}
		} catch (MalformedURLException e) {
		      e.printStackTrace();
	    } catch (RemoteException e) {
	      e.printStackTrace();
	    } catch (NotBoundException e) {
	      e.printStackTrace();
	    }
	}
}