package modelo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

import modelo.AbsCommand.COMMAND;
import cgf.Constantes.Valores;
import cgf.controle.Controle;
import cgf.estado.EstadoJogo;
import cgf.estado.Zona;

public abstract class Jogo {
	protected Controle controle;

	protected Zona deck;

	protected Map<Valores, Integer> valores;

	protected Stack<EstadoJogo> mementos;

	protected abstract Zona defineDeck();

	protected abstract List<Zona> defineZonas();

	public Jogo(/* Controle controle/* EstadoJogo estado */) {
		// new Controle(this, estado);
		this.controle = Controle.getInstancia();
		valores = defineValores();
		deck = defineDeck();
	}

	/**
	 * Valida a ação de mover retornando msg de erro quando invalida. Pode ser
	 * sobreescrito invocando o super para adicionar novas msgs.
	 * 
	 * @return Msg de erro mostrada ao user.
	 */
	private AbsCommand validaMove(AbsCommand command) {
		List<Zona> origens = command.getOrigens();
		Zona destino = command.getDestino();

		EstadoJogo ej = controle.getEstadoJogo();
		String nomePlayer = controle.getNomePlayer();
		for (Zona origem : origens) {
			if (origem == null) {
				command.appendMsg("Zona origem nula\n");
			}
			if (origem == null) {
				command.appendMsg("Zona destino nula\n");
			}
			if (origem.equals(destino)) {
				command.appendMsg("Zona origem igual à destino: " + origem.getName() + "\n");
			}
			if (!origem.possuida(nomePlayer)) {// donos.contains(Player.getInstancia().getNome()))
				command.appendMsg("Zona origem não pertence ao player: " + origem.getName() + "\n");
			}
			if (!destino.possuida(nomePlayer)) {
				command.appendMsg("Zona destino não pertence ao player: " + destino.getName() + "\n");
			}
			if (!ej.getPlayerVez().equals(nomePlayer)) {
				command.appendMsg("Não é sua vez\n");
			}
		}
		command = aposValidaMove(command);
		if (/* validar */true) {
			// executa(new AbsCommand(COMMAND.VALIDATE, origens, destino));
			if (command.getMsg().length() != 0) {
				for (Zona origem : origens) {
					if (origem.getParent() instanceof Zona) {
						((Zona) origem.getParent()).reorganiza();
					}
				}
				return command;
			}
		}
		return command;
	}

	protected AbsCommand aposValidaMove(AbsCommand command) {
		return command;
	}

	protected AbsCommand antesExecuta(AbsCommand command) {
		// List<AbsCommand> comandos = new ArrayList<AbsCommand>();
		// comandos.add(command);
		return command;
	}

	public final AbsCommand executa(AbsCommand command) {
		// List<AbsCommand> comandos =
		command = antesExecuta(command);
		// for (AbsCommand absCommand : comandos)
		{
			if (COMMAND.MOVE == command.getCommand()) {
				command = validaMove(command);
				if (command.getMsg().length() == 0) {
					move(command);
				}
			} /*
			 * else if (COMMAND.VALIDATE == command.getCommand()) {
			 * validaMove(command); }
			 */else if (COMMAND.DRAW == command.getCommand()) {
				draw(command);
			} else if (COMMAND.PASS == command.getCommand()) {
				passaVez();
			} else if (COMMAND.UNDO == command.getCommand()) {
				// TODO passar command?
				undo();
			}
		}
		return aposExecuta(command);
	}

	protected AbsCommand aposExecuta(AbsCommand command) {
		return command;
	}

	private AbsCommand draw(AbsCommand command) {
		// List<Zona> origens = command.getOrigens();
		// Zona destino = command.getDestino();
		List<Zona> origens = new ArrayList<Zona>();
		origens.add(deck);
		Zona destino = controle.getEstadoJogo().getZonaByName("Mao" + command.getNomePlayer());
		for (int i = 0; i < command.getQnt(); i++) {
			executa(new AbsCommand(COMMAND.MOVE, origens, destino));
		}
		return command;
	}

	/**
	 * @param origens
	 *            Zonas a serem movida.
	 * @param destino
	 *            Zona à qual a zona origem sera adicionada.
	 * @param validar
	 *            Se deve validar a jogada. Caso a validação falhar não move.
	 * @return Se moveu com sucesso.
	 */
	private final AbsCommand move(AbsCommand command) {
		List<Zona> origens = command.getOrigens();
		Zona destino = command.getDestino();
		for (Zona origem : origens) {
			destino.add(origem);
			if (origem.getParent() instanceof Zona) {
				((Zona) origem.getParent()).reorganiza();
			}
		}
		EstadoJogo estado = (EstadoJogo) controle.getEstadoJogo();
		estado.setMoveu(true);
		// Cria o memento apos o move
		addMemento(estado);
		// estado.firePropertyChange("moveu", antesMoves, estado);
		return command;
	}

	private final void passaVez() {
		EstadoJogo estado = controle.getEstadoJogo();
		estado.setPlayerVez(nextPlayer());
		estado.setMoveu(false);
		setMemento(estado);
		// EstadoJogo clone;
		// try {
		// clone = (EstadoJogo) estado.clone();
		// clone.setPlayerVez(nextPlayer());
		// clone.setMoveu(false);
		// addMemento(clone);
		// } catch (CloneNotSupportedException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }
	}

	/**
	 * Invocado pelo clique do botão, define o próximo jogador a jogar. Pode ser
	 * sobrescrito para definir uma ordem diferente de acordo com as regras do
	 * jogo.
	 * 
	 * @return
	 */
	private String nextPlayer() {
		EstadoJogo estado = controle.getEstadoJogo();
		String vez = null;
		// Circular
		String[] playerNames = estado.getPlayerNames();
		for (int i = 0; i < playerNames.length; i++) {
			if (estado.getPlayerVez().equals(playerNames[i])) {
				if (i == playerNames.length - 1) {
					vez = playerNames[0];
				} else {
					vez = playerNames[i + 1];
				}
			}
		}
		return vez;
	}

	// public void setNomePlayer(String nome) {
	// this.nomePlayer = nome;
	// }
	//
	// public String getNomePlayer() {
	// return nomePlayer;
	// }

	private final Map<Valores, Integer> defineValores() {
		Map<Valores, Integer> valores = new HashMap<Valores, Integer>();
		for (int i = 0; i < Valores.values().length; i++) {
			valores.put(Valores.values()[i], i);
		}
		aposDefineValores(valores);
		return valores;
	}

	protected void aposDefineValores(Map<Valores, Integer> valores) {

	}

	final EstadoJogo undo() {
		return mementos.pop();
	}

	private final void addMemento(EstadoJogo estado) {
		/*
		 * try { estado = (EstadoJogo) estado.clone(); } catch
		 * (CloneNotSupportedException e) { // TODO Auto-generated catch block
		 * e.printStackTrace(); }
		 */
		mementos.add(estado);
	}

	public final void setMemento(EstadoJogo estado) {
		if (mementos == null) {
			mementos = new Stack<EstadoJogo>();
		} else {
			mementos.clear();
		}
		addMemento(estado);
	}

	/**
	 * "Host" configura o estado do jogo ao clicar play. Define os nomes dos
	 * jogadores as zonas e notifica-os.
	 * 
	 * @param playerNames
	 * 
	 */
	public final EstadoJogo configuraEstado(String[] playerNames) {
		// Se atingiu numero de jogadores.
		EstadoJogo estado = controle.getEstadoJogo();
		estado.setPlayerVez(controle.getNomePlayer());
		estado.setPlayerNames(playerNames);
		// TODO nullpointer se nao definiu zonas
		for (Zona zona : defineZonas()) {
			controle.getEstadoJogo().add(zona);
		}
		// setNumPlayers(numPlayers);
		// passaVez();
		return estado;
	}
}