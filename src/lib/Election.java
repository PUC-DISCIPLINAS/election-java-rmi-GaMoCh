package lib;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Election extends Remote {
    String vote(String voter, String candidate) throws RemoteException;

    String result(String candidate) throws RemoteException;
}
