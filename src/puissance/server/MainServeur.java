package puissance.server;


import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.UnknownHostException;
import java.rmi.AlreadyBoundException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;
import java.util.Vector;

public class MainServeur {
	

	public static void main(String[] args) {
		try {
		      LocateRegistry.createRegistry(8080);

		      Information information = new InformationImpl();

		      Naming.bind("//localhost:8080/TestRMI", information);
		      
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

}
