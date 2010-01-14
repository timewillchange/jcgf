package cgf;

public class Constantes {
	public static final String REMOTE_PLAYER = "RemotePlayer";

	public static final String BARALHOS = "Baralhos/";

	// TODO nao sera uma constante, sera definida pelo tipo de deck.
	public static final String DECK_BACK = "b1fv.gif";

	public static enum Naipes {
		OUROS, PAUS, COPAS, ESPADAS;
	}

	public static enum Valores {
		CORINGA, AS, DOIS, TRES, QUATRO, CINCO, SEIS, SETE, OITO, NOVE, DEZ, VALETE, DAMA, REI;
	}
}