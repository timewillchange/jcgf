package cgf.controle;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.rmi.RemoteException;

import javax.swing.JOptionPane;

import modelo.AbsCommand;
import modelo.Jogo;
import modelo.AbsCommand.COMMAND;
import cgf.estado.EstadoJogo;
import cgf.estado.Zona;
import cgf.rmi.IPlayer;
import cgf.rmi.Player;
import cgf.visao.Visao;

//TODO Singleton ?
public class Controle<J extends Jogo, E extends EstadoJogo> implements PropertyChangeListener, IPlayer {
	private static boolean notificaPlayers = true;

	private static Controle controle;

	private Jogo jogo;

	private Player player;

	private Visao visao;

	private ListenersVisao listenersVisao;

	public EstadoJogo getEstadoJogo() {
		return visao.getEstadoJogo();
	}

	public static Controle getInstancia() {
		return controle;
	}

	public Controle(Class<J> jogo, Class<E> estado) {
		try {
			this.controle = this;
			criaVisao(estado);
			criaJogo(jogo);
			player = new Player();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void criaJogo(Class<J> jogo) throws InstantiationException, IllegalAccessException {
		this.jogo = jogo.newInstance();
		this.jogo.setMemento(getEstadoJogo());
	}

	private void criaVisao(Class<E> estado) throws InstantiationException, IllegalAccessException {
		visao = new Visao();
		listenersVisao = new ListenersVisao();
		listenersVisao.addPlayListener();
		listenersVisao.addKeybordListener();
		listenersVisao.addVezListener();
		visao.setEstadoJogo(estado.newInstance());
	}

	public String getNomePlayer() {
		return player.getNomePlayer();
	}

	/**
	 * Escuta alterações no estado de jogo e manda executar as notificações
	 * remotas.
	 */
	public final void propertyChange(PropertyChangeEvent evt) {
		if (notificaPlayers) {
			if ("ancestor".equals(evt.getPropertyName())) {
				if (evt.getOldValue() != null && evt.getNewValue() != null) {
					// EstadoJogo estado = getEstadoJogo();
					// preparaEstado(estado, true);//ja eh feito no broadcast
					broadcastEstado();
				}
			}
		}
	}

	void setNotificaPlayers(boolean notifica) {
		notificaPlayers = notifica;
	}

	Visao getVisao() {
		return visao;
	}

	public final void update(String sender, Object valor) {
		if (valor instanceof String[]) {
			// Jogo vai comecar
			setNotificaPlayers(false);
			EstadoJogo estado = jogo.configuraEstado((String[]) valor);
			setNotificaPlayers(true);
			// preparaEstado(estado, false); jah eh feito no broadcast
			visao.setEstadoJogo(estado);
			broadcastEstado();
		} else if (valor instanceof EstadoJogo) {
			EstadoJogo estado = (EstadoJogo) valor;
			boolean minhaVez = estado.getPlayerVez().equals(getNomePlayer());
			if (!sender.equals(getNomePlayer())) {
				listenersVisao.preparaEstado(estado, false);
				jogo.setMemento(estado);
				visao.setEstadoJogo(estado);
				// jogo.aposReceberEstadoRemoto();
				// Arruma visao
				if (minhaVez) {
					JOptionPane.showMessageDialog(visao, "É sua vez!");
					jogo.executa(new AbsCommand(COMMAND.PASS, null, null));
				}
			}
			visao.getButtonDraw().setEnabled(minhaVez);
			visao.getButtonVez().setEnabled(minhaVez);
		}
	}

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
		EstadoJogo estado = visao.getEstadoJogo();
		listenersVisao.preparaEstado(estado, true);
		player.notifyObservers(estado);
		listenersVisao.preparaEstado(estado, false);
	}

	Jogo getJogo() {
		return jogo;
	}

	Player getPlayer() {
		return player;
	}

	// public EstadoJogo undo() {
	// EstadoJogo ej = jogo.undo();
	// if (ej != null) {
	// estadoJogo = ej;
	// }
	// return estadoJogo;
	// }
}