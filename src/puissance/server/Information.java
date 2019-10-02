package puissance.server;

import java.rmi.*;

public interface Information extends Remote {

   public String getInformation() throws RemoteException;
   public String somme(int x, int y) throws RemoteException;
   
   public void updateJeu() throws RemoteException;
   public void quitterJeu(String name) throws RemoteException;
}