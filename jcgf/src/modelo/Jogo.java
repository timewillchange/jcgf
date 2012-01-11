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
import cgf.estado.IEstado;
import cgf.estado.Zona;
import cgf.rmi.IPlayer;
import cgf.rmi.Player;

public abstract class Jogo {
	// protected Controle controle;

	protected Zona deck;

	protected Map<Valores, Integer> valores;

	// String nome;

	// private Map<String, IPlayer> players;

	// TODO mudar pra IEstado
	protected EstadoJogo estado;

	protected Stack<IEstado> mementos;

	protected abstract Zona defineDeck();

	protected abstract List<Zona> defineZonas(Map<String, IPlayer> players);

	public Jogo(/* Controle controle/* EstadoJogo estado */) {
		// new Controle(this, estado);
		// this.controle = Controle.getInstancia();
		valores = defineValores();
		deck = defineDeck();
		// players = new LinkedHashMap<String, IPlayer>();
	}

	/**
	 * Valida a a��o de mover retornando msg de erro quando invalida. Pode ser
	 * sobreescrito invocando o super para adicionar novas msgs.
	 * 
	 * @return Msg de erro mostrada ao user.
	 */
	private AbsCommand validaMove(AbsCommand command) {
		List<Zona> origens = command.getOrigens();
		Zona destino = command.getDestino();

		//String nomePlayer = Controle.nomePlayer;
		for (Zona origem : origens) {
			if (origem == null) {
				command.appendMsg("Zona origem nula\n");
			}
			if (origem == null) {
				command.appendMsg("Zona destino nula\n");
			}
			if (origem.equals(destino)) {
				command.appendMsg("Zona origem igual � destino: " + origem.getName() + "\n");
			}
//			if (!origem.possuida(id)) {// donos.contains(Player.getInstancia().getNome()))
//				command.appendMsg("Zona origem n�o pertence ao player: " + origem.getName() + "\n");
//			}
//			if (!destino.possuida(id)) {
//				command.appendMsg("Zona destino n�o pertence ao player: " + destino.getName() + "\n");
//			}
//			if (estado.getPlayerVez()!=) {
//				if (!((Player) players.get(estado.getPlayerVez())).getNomePlayer().equals(nomePlayer);
//				command.appendMsg("N�o � sua vez\n");
//			}
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
		Zona destino = command.getDestino();
		List<Zona> origens = new ArrayList<Zona>();
		origens.add(deck);
		for (int i = 0; i < command.getQnt(); i++) {
			executa(new AbsCommand(COMMAND.MOVE, origens, destino));
		}
		return command;
	}

	/**
	 * @param origens
	 *            Zonas a serem movida.
	 * @param destino
	 *            Zona � qual a zona origem sera adicionada.
	 * @param validar
	 *            Se deve validar a jogada. Caso a valida��o falhar n�o move.
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
		estado.setMoveu(true);
		// Cria o memento apos o move
		addMemento(estado);
		// estado.firePropertyChange("moveu", antesMoves, estado);
		return command;
	}

	public final void passaVez(/*Map<String, IPlayer> players*/) {
		estado.setPlayerVez(nextPlayer(/*players*/));
		estado.setMoveu(false);
		setMemento();
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
	 * Invocado pelo clique do bot�o, define o pr�ximo jogador a jogar. Pode ser
	 * sobrescrito para definir uma ordem diferente de acordo com as regras do
	 * jogo.
	 * 
	 * @return
	 */
	protected int nextPlayer(/* Map<String, IPlayer> players */) {
		// IPlayer next = null;
		// String[] playerNames = estado.getPlayerNames();
		// for (int i = 0; i < players.size(); i++) {
		// if (estado.getPlayerVez().equals(players.get(i))) {
		// if (i == players.size() - 1) {
		// next = players.get(0);
		// } else {
		// next = players.get(i + 1);
		// }
		// }
		// }
		// return next;
		
		// Circular
		estado.setPlayerVez(estado.getPlayerVez() + 1);
		if (estado.getPlayerVez() == estado.getnPlayers()) {
			estado.setPlayerVez(0);
		}
		return estado.getPlayerVez();
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

	final IEstado undo() {
		return mementos.pop();
	}

	private final void addMemento(IEstado estado) {
		/*
		 * try { estado = (EstadoJogo) estado.clone(); } catch
		 * (CloneNotSupportedException e) { // TODO Auto-generated catch block
		 * e.printStackTrace(); }
		 */
		mementos.add(estado);
	}

	public final void setMemento() {
		if (mementos == null) {
			mementos = new Stack<IEstado>();
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
	public final EstadoJogo configuraEstado(Map<String, IPlayer> players) {
		// Se atingiu numero de jogadores.
		estado.setPlayerVez(0);
		// estado.setPlayerNames(playerNames);
		// TODO nullpointer se nao definiu zonas
		for (Zona zona : defineZonas(players)) {
			estado.add(zona);
		}
		// setNumPlayers(numPlayers);
		// passaVez();
		return estado;
	}

	public EstadoJogo getEstadoJogo() {
		return estado;
	}

	public IEstado criaEstado(Class classEstado, Integer valor) {
		try {
			this.estado = (EstadoJogo) classEstado.newInstance();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.estado.setnPlayers(valor);
		return this.estado;
	}
	
	public void vez(){
		
	}

	public void setEstadoJogo(EstadoJogo estado) {
		this.estado = estado;
	}
}