package cgf.controle;

import java.awt.Component;
import java.awt.Container;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.beans.PropertyChangeListener;
import java.util.Arrays;
import java.util.EventListener;
import java.util.List;

import modelo.AbsCommand;
import modelo.AbsCommand.COMMAND;

import cgf.Constantes;
import cgf.Util;
import cgf.estado.EstadoJogo;
import cgf.estado.Zona;
import cgf.rmi.IPlayer;

public class ListenersVisao {
	// TODO usar mouselistener ou mouse ?
	private List<EventListener> myListenersList;

	ListenersVisao() {
		myListenersList = Arrays.asList(new EventListener[] { new Mouse(), Controle.getInstancia() });
	}

	void addPlayListener() {
		final Controle controle = Controle.getInstancia();
		controle.getVisao().getPlayDialog().getPlay().addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent e) {
				// Controle player = controle;
				String action = controle.getVisao().getPlayDialog().getBg().getSelection().getActionCommand();
				String nome = controle.getVisao().getPlayDialog().getNome().getText();
				controle.getPlayer().setNomePlayer(nome);
				controle.getVisao().setTitle(nome);
				if ("novo".equals(action)) {
					// host = true;
					controle.getPlayer().setNumPlayers(
							(Integer) controle.getVisao().getPlayDialog().getMinPlayer().getValue());
					controle.getPlayer().addObserver(nome, controle.getPlayer());
					Util.cadastra(controle.getPlayer());
					controle.getVisao().setExtendedState(Frame.ICONIFIED);
				} else if ("salvo".equals(action)) {
					// host = true;
				} else if ("remoto".equals(action)) {
					// host = false;
					try {
						String ip = controle.getVisao().getPlayDialog().getIp().getText();
						IPlayer server = Util.getRemotePlayer(ip, Constantes.REMOTE_PLAYER + "0");
						server.update(nome, controle.getPlayer()/* ip */);
					} catch (Exception ex) {
						ex.printStackTrace();
					}
				}
				controle.getVisao().getPlayDialog().dispose();
			}
		});
	}

	void addKeybordListener() {
		final Controle controle = Controle.getInstancia();
		controle.getVisao().addKeyListener(new KeyAdapter() {
			public void keyTyped(KeyEvent e) {
				if (e.getKeyChar() == KeyEvent.VK_ESCAPE) {
					Mouse.deselecionaTudo();
				}
			}
		});
	}

	void addVezListener() {
		final Controle controle = Controle.getInstancia();
		controle.getVisao().getButtonVez().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("passou");
				controle.getJogo().executa(new AbsCommand(COMMAND.PASS));
				controle.broadcastEstado();
			}
		});
	}

	void addMouseListeners(Zona zona) {
		/*
		 * Recoloca os listeners pois eles se perdem na serializa��o.
		 */
		for (EventListener listener : myListenersList) {
			/*
			 * TODO Mangue para evitar problema de duplica��o de listeners.
			 */
			if (listener instanceof MouseListener && !Arrays.asList(zona.getMouseListeners()).contains(listener)) {
				zona.addMouseListener((MouseListener) listener);
			}
			if (listener instanceof MouseMotionListener
					&& !Arrays.asList(zona.getMouseMotionListeners()).contains(listener)) {
				zona.addMouseMotionListener((MouseMotionListener) listener);
			}
			if (listener instanceof PropertyChangeListener
					&& !Arrays.asList(zona.getPropertyChangeListeners()).contains(listener)) {
				zona.addPropertyChangeListener((PropertyChangeListener) listener);
			}
		}
	}

	void removeMouseListeners(Zona zona) {
		for (EventListener listener : myListenersList) {
			if (listener instanceof MouseListener) {
				zona.removeMouseListener((MouseListener) listener);
			}
			if (listener instanceof MouseMotionListener) {
				zona.removeMouseMotionListener((MouseMotionListener) listener);
			}
			if (listener instanceof PropertyChangeListener) {
				zona.removePropertyChangeListener((PropertyChangeListener) listener);
			}
		}
	}
	

	final void preparaEstado(EstadoJogo estado, boolean compress) {
		// if (compress) {
		// for (PropertyChangeListener listener :
		// estado.getPropertyChangeListeners()) {
		// estado.removePropertyChangeListener(listener);
		// }
		// } else {
		// // TODO Usar mesmo property listeners???
		// estado.addPropertyChangeListener("playerVez", this);
		// estado.addPropertyChangeListener("moveu", this);
		// }
		// jogo.preparaEstado(estado, compress);
		preparaEstadoRecursivo(estado, compress);
	}

	/**
	 * Seta a foto e TODO Coloca os mouse listeners em todas as cartas do
	 * estado. Listeners parecem ser transients, nao serializ�veis, ao envia-los
	 * perde-se os listeners das cartas.
	 * 
	 * @param comp
	 */
	private final void preparaEstadoRecursivo(Container comp, boolean compress) {
		if (comp instanceof Zona) {
			Zona zona = (Zona) comp;
			// TODO fica aki fora do if?
			zona.setaFoto();
			if (compress) {
				// Apaga a foto para reduzir o tamanho seriazilado da zona.
				// TODO Com isso as Cartas nao ficam visiveis, nao adianta
				// clonar! Pensar solucao melhor!
				// zona.setIcon(null);
				removeMouseListeners(zona);
				Zona.criaBorda(zona, false);
			} else {
				// if (zona instanceof CartaBaralho)
				{
					addMouseListeners(zona);
				}
			}
		}
		for (Component c : comp.getComponents()) {
			preparaEstadoRecursivo((Zona) c, compress);
		}
	}
}