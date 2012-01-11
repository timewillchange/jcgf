function defineDeck() {
	return ZonaBuilder.buildDeck(1, new Constantes.Valores[] { Valores.OITO, Valores.NOVE, Valores.DEZ,
			Valores.CORINGA });
}

function void aposDefineValores(Map<Valores, Integer> valores) {
	valores.put(Valores.DAMA, 8);
	valores.put(Valores.VALETE, 9);
	valores.put(Valores.REI, 10);
}

function List<Zona> defineZonas(Map<String, IPlayer> players) {
	List<Zona> zonas = new ArrayList<Zona>();
	int i = 0;
	for (String playerName : players.keySet()) {
		try {
			zonas.add(ZonaBuilder.getIntancia().buildHand(deck, playerName, i, 3));
			zonas.add(ZonaBuilder.getIntancia().buildZona(deck, "Monte" + playerName, new Integer[] { i }, 0,
					VISIBILIDADE.NINGUEM, false));
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		i++;
	}
	zonas.add(ZonaBuilder.getIntancia().buildMesa(deck, 4, new Integer[] { 0, 1 }));
	zonas.add(deck);
	return zonas;
}

function AbsCommand antesExecuta(AbsCommand command) {
	Zona destino = command.getDestino();
	COMMAND cmd = command.getCommand();
	if (COMMAND.MOVE == cmd) {
		command.setDestino(destino.getZonaPrimaria(destino));
	}
	// List<AbsCommand> comandos = new ArrayList<AbsCommand>();
	// comandos.add(command);
	return command;
}

function AbsCommand aposValidaMove(AbsCommand command) {
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
	} else if (("Monte" + Controle.nomePlayer).equals(destino.getName())) {
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
		if (soma != 15) {
			command.appendMsg("Esta jogada não soma 15.\n");
		}
	} else {
		command.appendMsg("Zona destino inválida.\n");
	}
	return command;
}