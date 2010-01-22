package aplicacao;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import modelo.AbsCommand;
import modelo.Jogo;
import modelo.AbsCommand.COMMAND;
import cgf.Constantes;
import cgf.ZonaBuilder;
import cgf.Constantes.Valores;
import cgf.controle.Controle;
import cgf.estado.CartaBaralho;
import cgf.estado.EstadoJogo;
import cgf.estado.Zona;
import cgf.estado.Zona.VISIBILIDADE;
import cgf.rmi.IPlayer;

public class Escova extends Jogo {
	@Override
	protected Zona defineDeck() {
		return ZonaBuilder.buildDeck(1, new Constantes.Valores[] { Valores.OITO, Valores.NOVE, Valores.DEZ,
				Valores.CORINGA });
	}

	@Override
	protected void aposDefineValores(Map<Valores, Integer> valores) {
		valores.put(Valores.DAMA, 8);
		valores.put(Valores.VALETE, 9);
		valores.put(Valores.REI, 10);
	}

	@Override
	protected List<Zona> defineZonas(List<IPlayer> players) {
		List<Zona> zonas = new ArrayList<Zona>();
		for (IPlayer player : players) {
			String playerName;
			try {
				playerName = Controle.nomePlayer;
				zonas.add(ZonaBuilder.getIntancia().buildHand(deck, player, 3));
				zonas.add(ZonaBuilder.getIntancia().buildZona(deck, "Monte" + playerName, new IPlayer[] { player }, 0,
						VISIBILIDADE.NINGUEM, false));
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		zonas.add(ZonaBuilder.getIntancia().buildMesa(deck, 4, players.toArray(new IPlayer[players.size()])));
		zonas.add(deck);
		return zonas;
	}

	@Override
	protected AbsCommand antesExecuta(AbsCommand command) {
		Zona destino = command.getDestino();
		COMMAND cmd = command.getCommand();
		if (COMMAND.MOVE == cmd) {
			command.setDestino(destino.getZonaPrimaria(destino));
		}
		// List<AbsCommand> comandos = new ArrayList<AbsCommand>();
		// comandos.add(command);
		return command;
	}

	@Override
	protected AbsCommand aposValidaMove(AbsCommand command) {
		List<Zona> origens = command.getOrigens();
		Zona destino = command.getDestino();
		int soma = 0;
		boolean so1Mao = false;
		if (estado.isMoveu()) {
			command.appendMsg("Voce já jogou nesta rodada!\n");
		}
		if (destino.getName().equals("Mesa")) {
			if (!(origens.get(0) instanceof CartaBaralho)) {
				command.appendMsg("Origem deve ser uma carta.\n");
			}
			if (origens.size() != 1) {
				command.appendMsg("Só pode por uma carta na mesa.\n");
			}
		} else {
			for (Zona origem : origens) {
				if (origem instanceof CartaBaralho) {
					CartaBaralho carta = (CartaBaralho) origem;
					soma += valores.get(carta.getValor());
					if (carta.getParent().getName().startsWith("Mao")) {
						if (so1Mao == true) {
							command.appendMsg("Só vale usar uma carta da mão.\n");
						}
						so1Mao = true;
					}
				} else {
					command.appendMsg("Origem " + origem.getName() + "não é uma carta.\n");
				}
			}
			if (soma != 15 && estado.getZonaByName("Monte" + Controle.nomePlayer).equals(destino)) {
				command.appendMsg("Esta jogada não soma 15.\n");
			}
		}
		return command;
	}

	public static void main(String[] args) {
		// Controle.getInstancia().setJogo(new JogoEscova(new EstadoJogo()));
		// new JogoEscova();
		new Controle(Escova.class, EstadoJogo.class);
	}
}