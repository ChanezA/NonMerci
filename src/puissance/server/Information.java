package puissance.server;

import java.rmi.*;

public interface Information extends Remote {

   public String getInformation() throws RemoteException;

   public void registerListener(String[] details)throws RemoteException;
   public void updateJeu() throws RemoteException;
   public void quitterJeu(String name) throws RemoteException;
}