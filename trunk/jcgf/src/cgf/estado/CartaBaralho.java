package cgf.estado;

import java.awt.Component;
import java.awt.Image;

import javax.swing.Icon;
import javax.swing.ImageIcon;

import cgf.Constantes;
import cgf.Constantes.Naipes;
import cgf.Constantes.Valores;
import cgf.visao.GUIPreferencias;

public class CartaBaralho extends Zona {
	private Valores valor;

	private Naipes naipe;

	public CartaBaralho(Valores valor, Naipes naipe) {
		super(valor.toString() + naipe, null, VISIBILIDADE.PUBLICA, true);
		this.valor = valor;
		this.naipe = naipe;
		// setaFoto();
		// setBorder(BorderFactory.createEmptyBorder());
		// Mouse mo = new Mouse();
		// addMouseListener(mo);
		// addMouseMotionListener(mo);
	}

	public Naipes getNaipe() {
		return naipe;
	}

	public void setNaipe(Naipes naipe) {
		this.naipe = naipe;
	}

	public Valores getValor() {
		return valor;
	}

	public void setValor(Valores valor) {
		this.valor = valor;
	}

	/*
	 * @Override public void move(Zona origem, Zona destino) { // TODO ??? nao
	 * deve ter move? }
	 */

	@Override
	public Component add(Component comp) {
		if (!(comp instanceof Zona)) {
			// Nao deve fazer nada
			System.err.println("Uma zona só pode ter filhos do tipo Zona!");
			return null;
		}
		if (this.getParent() == null) {
			// Nao deve fazer nada
			System.err.println("Uma carta solta não pode receber filhos!");
			return null;
		}
		int index = this.getParent().getComponentZOrder(this);
		this.getParent().add(comp, index);
		return comp;
	}

	@Override
	public Component add(Component comp, int index) {
		if (!(comp instanceof Zona)) {
			// Nao deve fazer nada
			System.err.println("Uma zona só pode ter filhos do tipo Zona!");
			return null;
		}
		if (this.getParent() == null) {
			// Nao deve fazer nada
			System.err.println("Uma carta solta não pode receber filhos!");
			return null;
		}
		// int index = this.getParent().getComponentZOrder(this);
		this.getParent().add(comp, index);
		return comp;
	}

	/**
	 * TODO aki mesmo? Seta a foto da carta de acordo com visibilidade da mesma.
	 */
	public void setaFoto(String nomePlayer) {
		// if (zona instanceof CartaBaralho)
		{
			// CartaBaralho carta = (CartaBaralho) zona;
			CartaBaralho carta = this;
			String foto = "";
			if (!carta.isVisivelPossui(nomePlayer)) {
				// Se a carta nao é visivel por esse player sua foto sera o
				// virada.
				// g.drawImage(Constantes.DECK_BACK, 0, 0, null);
				foto = Constantes.DECK_BACK;
			} else {
				switch (carta.getNaipe()) {
				case COPAS:
					foto += "h";
					break;
				case ESPADAS:
					foto += "s";
					break;
				case OUROS:
					foto += "d";
					break;
				case PAUS:
					foto += "c";
					break;
				default:
					foto += "j";
					break;
				}
				foto += carta.getValor().ordinal() + ".gif";
			}

			Icon i0 = new ImageIcon(ClassLoader.getSystemResource(Constantes.BARALHOS + foto));
			Icon i = new ImageIcon(((ImageIcon) i0).getImage().getScaledInstance(GUIPreferencias.deckX,
					GUIPreferencias.deckY, Image.SCALE_DEFAULT));
			carta.setIcon(i);
			carta.setSize(GUIPreferencias.deckX, GUIPreferencias.deckY);
		}
	}

	// @Override
	// public String toString() {
	// String s = "";
	// if (valor != null) {
	// s += valor;
	// }
	// if (naipe != null) {
	// s += " de ";
	// s += naipe;
	// }
	// return s;
	// }
}