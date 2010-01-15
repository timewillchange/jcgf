package cgf.rmi;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.LinkedHashMap;
import java.util.Map;

import cgf.controle.Controle;
import cgf.estado.EstadoJogo;

/**
 * Classe com metodos uteis de manipulação do estado do jogo. Usa padrão
 * Observer para conversar com os Players cadastrados por RMI.
 * 
 * @author Zidane
 * 
 */
public class Player extends UnicastRemoteObject implements IPlayer {
	// TODO public static ?
	private String nomePlayer;

	public void setNomePlayer(String nomePlayer) {
		this.nomePlayer = nomePlayer;
	}

	public String getNomePlayer() {
		return nomePlayer;
	}

	private int numPlayers;

	// private boolean changed = false;

	private Map<String, IPlayer> observers;

	private Controle controle;

	/**
	 * TODO estodo informado no construtor para dizer ao framework qual o estado
	 * do modelo. Pensar solução melhor, talvez usando factory.
	 */
	public Player() throws RemoteException {
		this.controle = Controle.getInstancia();
		observers = new LinkedHashMap<String, IPlayer>();
	}

	public synchronized void addObserver(String nomePlayer, IPlayer player) {
		if (player == null)
			throw new NullPointerException();
		if (!observers.containsValue(player)) {
			observers.put(nomePlayer, player);
		}
		// setChanged(true);
		notifyObservers(observers);
	}

	public synchronized void deleteObserver(IPlayer o) {
		observers.remove(o);
		// setChanged(true);
		notifyObservers(observers);
	}

	public void notifyObservers(Object arg) {
		if (arg instanceof EstadoJogo) {
			System.out.println("Vai mandar o estado");
		}
		// synchronized (this) {
		// if (!changed)
		// return;
		// setChanged(false);
		// }
		for (IPlayer player : observers.values()) {
			try {
				System.out.println("Notify " + player + " = " + arg);
				player.update(nomePlayer, arg);
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
	}

	/**
	 * Clears the observer list so that this object no longer has any observers.
	 */
	public synchronized void deleteObservers() {
		observers.clear();
		// setChanged(true);
		notifyObservers(observers);
	}

	// synchronized void setChanged(boolean changed) {
	// this.changed = changed;
	// }
	//
	// public synchronized boolean getChanged() {
	// return changed;
	// }

	public void addObservers(Map<String, IPlayer> obs) {
		observers.putAll((Map<String, IPlayer>) obs);
	}

	/*
	 * public synchronized Set<IPlayer> getObservers() { return observers; }
	 */

	public void update(String sender, Object valor) {
		System.out.println("Update from " + sender + " = " + valor);
		if (valor instanceof IPlayer) {
			addObserver(sender, (IPlayer) valor);
			if (observers.size() == numPlayers) {
				// Ao atingir o nro de Players
				// Jogo.getInstancia().configuraEstado();
				controle.update(sender, observers.keySet().toArray(new String[] {}));
			}
		} else if (valor instanceof Map) {
			addObservers((Map<String, IPlayer>) valor);
		} else if (valor instanceof EstadoJogo) {
			controle.update(sender, valor);
		}
	}

	public void setNumPlayers(int numPlayers) {
		this.numPlayers = numPlayers;
	}
}