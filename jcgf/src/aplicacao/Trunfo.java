package aplicacao;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

import modelo.AbsCommand;
import modelo.Jogo;
import modelo.AbsCommand.COMMAND;
import cgf.Constantes;
import cgf.ZonaBuilder;
import cgf.Constantes.Valores;
import cgf.controle.Controle;
import cgf.estado.EstadoJogo;
import cgf.estado.Zona;
import cgf.estado.Zona.VISIBILIDADE;
import cgf.rmi.IPlayer;

public class Trunfo extends Jogo {
	@Override
	protected Zona defineDeck() {
		return ZonaBuilder.buildDeck(2, new Constantes.Valores[] { Valores.CORINGA });
	}

	@Override
	protected List<Zona> defineZonas(List<IPlayer> players) {
		List<Zona> zonas = new ArrayList<Zona>();
		for (IPlayer player : players) {
			String playerName;
			try {
				playerName = Controle.nomePlayer;
				zonas.add(ZonaBuilder.getIntancia().buildHand(deck, player, 5));
				zonas.add(ZonaBuilder.getIntancia().buildZona(deck, "Monte" + playerName, new IPlayer[] { player }, 0,
						VISIBILIDADE.PRIVADA, false));
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		zonas.add(ZonaBuilder.getIntancia().buildMesa(deck, 0, players.toArray(new IPlayer[players.size()])));
		zonas.add(ZonaBuilder.getIntancia().buildZona(deck, "Trunfo", null, 1, VISIBILIDADE.PUBLICA, false));
		zonas.add(deck);
		return zonas;
	}

	@Override
	protected int nextPlayer() {
		Zona mao = estado.getZonaByName("Mao" + controle.getNomePlayer());
		if (mao.lenght() == 0) {
			for (String nomePlayer : estado.getPlayerNames()) {
				new AbsCommand(COMMAND.DRAW, mao, 3);
			}
		}
	}

	@Override
	protected AbsCommand aposValidaMove(AbsCommand command) {
		List<Zona> origens = command.getOrigens();
		if (origens.size() != 1) {
			command.appendMsg("Origem deve ser uma única carta da mão.\n");
		}
		Zona o = origens.get(0);
		if (o.getZonaPrimaria(o).getName().startsWith("Trunfo")) {
			command.appendMsg("Origem não pode pertencer a zona do Trunfo.\n");
		}
		Zona destino = command.getDestino();
		if (!destino.getZonaPrimaria(destino).getName().startsWith("Mesa")) {
			command.appendMsg("Destino inválido.\n");
		}
		return command;
	}

	public static void main(String[] args) {
		new Controle(Trunfo.class, EstadoJogo.class);
	}
}