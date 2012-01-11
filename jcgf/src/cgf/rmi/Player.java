package cgf.rmi;

import java.beans.PropertyChangeEvent;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

import cgf.Util;
import cgf.controle.Controle;
import cgf.estado.EstadoJogo;

/**
 * Classe de jogador remoto. Usa padr�o Observer para conversar com os Players
 * cadastrados por RMI.
 * 
 * @author Zidane
 * 
 */
public class Player extends UnicastRemoteObject implements IPlayer {
	private int id;
	// TODO public static ?
	private String nomePlayer;

	// private boolean changed = false;

	// private Map<String, IPlayer> observers;

	public String getNomePlayer() {
		return nomePlayer;
	}

	private Controle controle;

	public Player(String nome, Controle controle) throws RemoteException {
		this.nomePlayer = nome;
		this.controle = controle;
		Util.cadastra(this);
	}

	/*
	 * public synchronized Set<IPlayer> getObservers() { return observers; }
	 */

	public void update(String sender, Object valor) {
		System.out.println("Update from " + sender + " = " + valor);
		if (valor instanceof String || valor instanceof IPlayer || valor instanceof List || valor instanceof EstadoJogo) {
			// controle.update(sender, valor);
			controle.propertyChange(new PropertyChangeEvent(sender, "", null, valor));
		}
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
}