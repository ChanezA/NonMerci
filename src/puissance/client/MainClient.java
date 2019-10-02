package puissance.client;

import java.net.InetAddress;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;

import puissance.server.Information;


public class MainClient {
	public static void main(String[] argv) {

		try {
			Information info = (Information) Naming.lookup("//localhost:8080/TestRMI");
			System.out.println("truc");
			System.out.println(info.getInformation());
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