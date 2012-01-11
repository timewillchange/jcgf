package cgf.controle;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import modelo.Jogo;
import cgf.estado.EstadoJogo;
import cgf.rmi.IPlayer;
import cgf.rmi.Player;
import cgf.visao.Visao;

//TODO Singleton ?
public class Controle<J extends Jogo, E extends EstadoJogo> implements PropertyChangeListener {
	private static boolean notificaPlayers = true;

	// private static Controle controle;

	// TODO aki mesmo e public static?
	public static String nomePlayer;

	private ListenersVisao listenersVisao;

	private Jogo jogo;

	private Visao visao;

	private IPlayer player;

	private Map<String, IPlayer> players;

	private Class<E> classEstado;

	public Controle(Class<J> jogo, Class<E> estado) {
		try {
			// this.controle = this;
			this.classEstado = estado;
			criaVisao(estado);
			criaJogo(jogo);
			// player = new Player();
			players = new HashMap<String, IPlayer>();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void criaJogo(Class<J> jogo) throws InstantiationException, IllegalAccessException {
		this.jogo = jogo.newInstance();
		this.jogo.setMemento();
	}

	private void criaVisao(Class<E> estado) throws InstantiationException, IllegalAccessException {
		visao = new Visao();
		listenersVisao = new ListenersVisao(this);
		listenersVisao.addPlayListener();
		listenersVisao.addKeybordListener();
		listenersVisao.addVezListener();
	}

	/**
	 * Escuta altera��es no estado de jogo e manda executar as notifica��es
	 * remotas.
	 */
	public final void propertyChange(PropertyChangeEvent evt) {
		String propName = evt.getPropertyName();
		Object valor = evt.getNewValue();
		if ("nPlayers".equals(propName)) {
			visao.setEstadoJogo((EstadoJogo) jogo.criaEstado(classEstado, (Integer) valor));
		} else if ("ancestor".equals(propName)) {
			// Quando ocorre modifica��o de estado pela visao.
			if (notificaPlayers) {
				if (evt.getOldValue() != null && evt.getNewValue() != null) {
					// EstadoJogo estado = getEstadoJogo();
					// preparaEstado(estado, true);//ja eh feito no
					// broadcast
					broadcast(visao.getEstadoJogo());
				}
			}
		} else if (valor instanceof IPlayer) {
			players.put((String) evt.getSource(), (IPlayer) valor);
			if (jogo.getEstadoJogo().getnPlayers() == players.size()) {
				// Jogo vai comecar
				setNotificaPlayers(false);
				EstadoJogo estado = jogo.configuraEstado(players);
				setNotificaPlayers(true);
				// preparaEstado(estado, false); jah eh feito no broadcast
				visao.setEstadoJogo(estado);
				broadcast(estado);
			}
		} else if (valor instanceof List) {
			players = (Map) valor;
		} else if (valor instanceof EstadoJogo) {
			EstadoJogo estado = (EstadoJogo) valor;
			boolean minhaVez = estado.getPlayerVez() == ((Player) player).getId();
			if (!evt.getSource().equals(nomePlayer)) {
				listenersVisao.preparaEstado(estado, false);
				jogo.setMemento();
				visao.setEstadoJogo(estado);
				jogo.setEstadoJogo(estado);
				// jogo.aposReceberEstadoRemoto();
				// Arruma visao
				if (minhaVez) {
					// JOptionPane.showMessageDialog(visao, "� sua vez!");
					// jogo.executa(new AbsCommand(COMMAND.PASS, null, null));
				}
			}
			visao.getButtonDraw().setEnabled(minhaVez);
			visao.getButtonVez().setEnabled(minhaVez);
		}
	}

	void setNotificaPlayers(boolean notifica) {
		notificaPlayers = notifica;
	}

	Visao getVisao() {
		return visao;
	}

	// public final void update(String sender, Object valor) {
	// if (valor instanceof String[]) {
	// // Jogo vai comecar
	// setNotificaPlayers(false);
	// EstadoJogo estado = jogo.configuraEstado((String[]) valor);
	// setNotificaPlayers(true);
	// // preparaEstado(estado, false); jah eh feito no broadcast
	// visao.setEstadoJogo(estado);
	// broadcastEstado();
	// } else if (valor instanceof EstadoJogo) {
	// EstadoJogo estado = (EstadoJogo) valor;
	// boolean minhaVez = estado.getPlayerVez().equals(getNomePlayer());
	// if (!sender.equals(getNomePlayer())) {
	// listenersVisao.preparaEstado(estado, false);
	// jogo.setMemento(estado);
	// visao.setEstadoJogo(estado);
	// // jogo.aposReceberEstadoRemoto();
	// // Arruma visao
	// if (minhaVez) {
	// JOptionPane.showMessageDialog(visao, "� sua vez!");
	// jogo.executa(new AbsCommand(COMMAND.PASS, null, null));
	// }
	// }
	// visao.getButtonDraw().setEnabled(minhaVez);
	// visao.getButtonVez().setEnabled(minhaVez);
	// }
	// }

	final void broadcastEstado() {
		// TODO setChanged(true) aki?
		// player.setChanged(true);
		/*
		 * EstadoJogo es = null; try { es = (EstadoJogo) estado.clone(); } catch
		 * (CloneNotSupportedException e) { // TODO Auto-generated catch block
		 * e.printStackTrace(); }
		 */
		// TODO problema, ao passar true ele acaba com os listeners da visao
		// local.
		// jogo.inicializaMementos();
		// jogo.addMemento(estado);
		EstadoJogo estado = jogo.getEstadoJogo();
		listenersVisao.preparaEstado(estado, true);
		// player.notifyObservers(estado);
		broadcast(estado);
		listenersVisao.preparaEstado(estado, false);
	}

	Jogo getJogo() {
		return jogo;
	}

	// public EstadoJogo undo() {
	// EstadoJogo ej = jogo.undo();
	// if (ej != null) {
	// estadoJogo = ej;
	// }
	// return estadoJogo;
	// }
	final void criaPlayer(String nome) {
		try {
			player = new Player(nome, this);
			nomePlayer = nome;
			// player.addObserver(nome, player);
			addPlayer(nome, player);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// getVisao().getEstadoJogo().setNumPlayers(numPlayers);
	}

	private final void addPlayer(String nome, IPlayer player) {
		if (!players.values().contains(player)) {
			players.put(nome, player);
		}
		// setChanged(true);
		broadcast(players);
	}

	private final void broadcast(Object arg) {
		if (arg instanceof EstadoJogo) {
			System.out.println("Vai mandar o estado");
		}
		// synchronized (this) {
		// if (!changed)
		// return;
		// setChanged(false);
		// }
		for (IPlayer p : players.values()) {
			try {
				// if (!((Player) p).nomePlayer.equals(nomePlayer))
				{
					p.update(nomePlayer, arg);
				}
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			// player.notifyObserver(arg);
		}
	}

	public IPlayer getPlayer() {
		return player;
	}

	public Map<String, IPlayer> getPlayers() {
		return players;
	}
	//
	// public EstadoJogo getEstadoJogo() {
	// return visao.getEstadoJogo();
	// }
}