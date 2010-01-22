package cgf.rmi;

import java.io.Serializable;
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IPlayer extends /* Observer, */Remote, Serializable {
	public void update(/* IPlayer */String sender, Object valor) throws RemoteException;

	// public String getNomePlayer() throws RemoteException;

	// public void addObserver(String nomePlayer, IPlayer player);

	// public void notifyObserver(Object arg);

	// public void notifyObservers(Object arg);
}
