package cgf;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import cgf.Constantes.Naipes;
import cgf.Constantes.Valores;
import cgf.controle.Controle;
import cgf.estado.CartaBaralho;
import cgf.estado.Zona;
import cgf.estado.Zona.VISIBILIDADE;
import cgf.rmi.IPlayer;
import cgf.rmi.Player;

/**
 * Constr�i zonas com cartas pr�definidas ou aleat�rias.
 * 
 * @author 99689650068
 */
public class ZonaBuilder {
	// private int numDecks;

	// private List<Valores> removidas;

	private static ZonaBuilder zonaBuilder;

	//
	// TODO Singleton?
	public static ZonaBuilder getIntancia() {
		if (zonaBuilder == null) {
			zonaBuilder = new ZonaBuilder();
		}
		return zonaBuilder;
	}

	private ZonaBuilder() {
	}

	/**
	 * Cria uma zona com "qntCartas" cartas caso o estado passado n�o seja nulo
	 * e possua um deck.
	 * 
	 * @param donos
	 *            Donos da zona criada.
	 * @param estado
	 *            Estado que possui o deck fonte de onde ser�o tiradas as cartas
	 *            para dar conteudo � zona criada.
	 * @param qntCartas
	 *            Quantidade de cartas tiradas da fonte.
	 * @param tipoZona
	 *            Define quem ser�o os donos e por quem a zona sera visivel.
	 * @return
	 */
	public final Zona buildZona(Zona origem, String nome, Integer[] donos, int qntCartas, VISIBILIDADE visivelPor,
			boolean movivel) {
		Zona zona = null;
		zona = new Zona(nome, donos, visivelPor, movivel);
		// zona.setDonos(donos);
		for (int i = 0; i < qntCartas; i++) {
			zona.add(origem.getComponent(0));
		}
		/*
		 * switch (tipoZona) { case PROTEGIDA: break; case PRIVADA:
		 * zona.setDonos(new String[] { estado.playerVez }); break; case
		 * PUBLICA: zona.setDonos(estado.getPlayerNames()); break; }
		 */
		return zona;
	}

	/**
	 * Monta o baralho de jogo em uma zona.
	 * 
	 * @param numDecks
	 *            Numero de baralhos usados para montar o baralho de jogo.
	 * @param removidas
	 *            Indica quais cartas devem ser removidas do baralho de jogo.
	 * @return Zona contendo o baralho gerado.
	 */
	public static Zona buildDeck(int numDecks, Valores[] removidas) {
		List<Zona> deck = new ArrayList<Zona>();
		for (int i = 0; i < numDecks; i++) {
			for (Valores valor : Valores.values()) {
				if (!Arrays.asList(removidas).contains(valor)) {
					if (valor != Valores.CORINGA) {
						for (Naipes naipe : Naipes.values()) {
							CartaBaralho carta = new CartaBaralho(valor, naipe);
							deck.add(carta);
						}
					} else {
						CartaBaralho carta = new CartaBaralho(valor, null);
						deck.add(carta);
					}
				}
			}
		}
		Collections.shuffle(deck);
		Zona monte = new Zona("Monte", null, VISIBILIDADE.PRIVADA, false);
		for (Zona zona : deck) {
			monte.add(zona);
		}
		return monte;
	}

	public final Zona buildHand(Zona origem, String pname, int dono, int qntCartas) throws RemoteException {
		return buildZona(origem, "Mao" + pname, new Integer[] { dono }, qntCartas,
				VISIBILIDADE.PRIVADA, false);
	}

	public final Zona buildMesa(Zona origem, int qntCartas, Integer[] donos) {
		return buildZona(origem, "Mesa", donos, qntCartas, VISIBILIDADE.PUBLICA, false);
	}
}