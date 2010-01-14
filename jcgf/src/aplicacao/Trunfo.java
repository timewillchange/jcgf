package aplicacao;

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

public class Trunfo extends Jogo {
	@Override
	protected Zona defineDeck() {
		return ZonaBuilder.buildDeck(2, new Constantes.Valores[] { Valores.CORINGA });
	}

	@Override
	protected List<Zona> defineZonas() {
		List<Zona> zonas = new ArrayList<Zona>();
		for (String playerName : controle.getEstadoJogo().getPlayerNames()) {
			zonas.add(ZonaBuilder.getIntancia().buildHand(deck, playerName, 5));
			zonas.add(ZonaBuilder.getIntancia().buildZona(deck, "Monte" + playerName, new String[] { playerName }, 0,
					VISIBILIDADE.PRIVADA, false));
		}
		zonas.add(ZonaBuilder.getIntancia().buildMesa(deck, 0));
		zonas.add(ZonaBuilder.getIntancia().buildZona(deck, "Trunfo", null, 1, VISIBILIDADE.PUBLICA, false));
		zonas.add(deck);
		return zonas;
	}

	@Override
	protected AbsCommand antesExecuta(AbsCommand command) {
		COMMAND cmd = command.getCommand();
		// TODO pass???
		if (COMMAND.PASS == cmd) {
			Zona mao = controle.getEstadoJogo().getZonaByName("Mao" + controle.getNomePlayer());
			if (mao.lenght() == 0) {
				for (String nomePlayer : controle.getEstadoJogo().getPlayerNames()) {
					new AbsCommand(COMMAND.DRAW, nomePlayer, 3);
				}
			}
		}
		// List<AbsCommand> comandos = new ArrayList<AbsCommand>();
		// comandos.add(command);
		return command;
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