package cgf;

import java.net.MalformedURLException;
import java.rmi.AccessException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RMISecurityManager;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import cgf.rmi.IPlayer;

public class Util {
	public static Registry getRegistry(String host) {
		// Create and install a security manager
		if (System.getSecurityManager() == null) {
			System.setSecurityManager(new RMISecurityManager());
		}

		if (host == null || host.equals("") || host.equals("127.0.0.1")) {
			try {
				return LocateRegistry.createRegistry(1099);
			} catch (RemoteException e) {
				// Registro jah rodando
				try {
					return LocateRegistry.getRegistry();
				} catch (RemoteException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		} else {
			try {
				return LocateRegistry.getRegistry(host);
			} catch (RemoteException e) {
				System.err.println("Nao achou o IP");
				e.printStackTrace();
			}
		}
		return null;
	}

	public static IPlayer getRemotePlayer(String ip, String remoteObj) {
		Registry registry = getRegistry(ip);
		try {
			return (IPlayer) registry.lookup(remoteObj);
		} catch (AccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NotBoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public static void cadastra(IPlayer player) {
		try {
			Registry r = getRegistry("127.0.0.1");
			String playerName = Constantes.REMOTE_PLAYER + r.list().length;
			Naming.rebind(playerName, player);
		} catch (MalformedURLException e) {
			System.err.println("Erro: " + e.getMessage());
			e.printStackTrace();
			System.exit(1);
		} catch (RemoteException e) {
			e.printStackTrace();
			System.exit(1);
		}
	}
}