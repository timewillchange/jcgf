package cgf.rmi;

import java.io.Serializable;
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IRemoteObserver extends /* Observer, */Remote, Serializable {
	public void addObserver(String nomePlayer, IRemoteObserver player);

	public void update(/* IRemoteObserver */String sender, Object valor) throws RemoteException;
}