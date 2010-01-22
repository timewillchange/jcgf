package cgf.estado;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.image.RescaleOp;
import java.util.Arrays;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.border.TitledBorder;

import cgf.rmi.IPlayer;
import cgf.visao.GUIPreferencias;

public class Zona extends JLabel implements Cloneable {
	public static enum VISIBILIDADE {
		NINGUEM, PRIVADA, /* TODOS */PUBLICA;
	}

	protected VISIBILIDADE visivelPor;

	protected List<IPlayer> donos;

	public List<IPlayer> getDonos() {
		return donos;
	}

	protected boolean movivel;

	transient protected boolean selecionada;

	// TODO fazer as zonas terem props como minDistX e minDistY passadas no
	// contrutor
	public Zona(String nome, IPlayer[] donos, /* Zona conteudo, */VISIBILIDADE visivelPor, boolean movivel) {
		if (donos != null) {
			this.donos = Arrays.asList(donos);
		}
		// TODO remover isso???
		setName(nome);
		if (!(this instanceof CartaBaralho)) {
			setBorder(BorderFactory.createTitledBorder(nome));
		}
		this.visivelPor = visivelPor;
		this.movivel = movivel;
		setMinimumSize(new Dimension(GUIPreferencias.deckX, GUIPreferencias.deckY));
		reorganiza();
		// setLayout(new CircularLayoutStrategy());
	}

	// TODO deve ser recursivo? in-out ou out-in?
	public void reorganiza(/* Container comp */) {
		int nro = this.getComponentCount();
		// if (nro > 0)
		{
			TitledBorder t = (TitledBorder) this.getBorder();
			Dimension min = (t == null ? new Dimension() : t.getMinimumSize(this));
			Dimension pref = new Dimension((nro + 1) * GUIPreferencias.minDistX + GUIPreferencias.deckX, (nro + 2)
					* GUIPreferencias.minDistY + GUIPreferencias.deckY);
			this.setPreferredSize(new Dimension(min.width > pref.width ? min.width : pref.width,
					min.height > pref.height ? min.height : pref.height));
			for (int i = 0; i < nro; i++) {
				Zona zona = (Zona) this.getComponent(i);
				zona.setLocation((nro - i) * GUIPreferencias.minDistX, (nro - i + 1) * GUIPreferencias.minDistY);
				// setComponentZOrder(zona, nro-1);
			}
		}
		revalidate();
		repaint();
		// for (Component c : comp.getComponents()) {
		// reorganiza((Zona) c, compress);
		// }
	}

	@Override
	public Component add(Component comp) {
		return add(comp, -1);
	}

	@Override
	public Component add(Component comp, int index) {
		if (!(comp instanceof Zona)) {
			// Nao deve fazer nada
			System.err.println("Uma zona só pode ter filhos do tipo Zona!");
			return null;
		}
		// Container antesMove = comp.getParent();
		super.add(comp, index);
		Zona novaZona = (Zona) comp;
		// Quando uma zona muda de local ela muda de dono.
		novaZona.setDonos(donos);
		novaZona.visivelPor = visivelPor;
		reorganiza();
		// firePropertyChange("moveu", antesMove, this);
		return comp;
	}

	@Override
	public void remove(Component comp) {
		super.remove(comp);
		reorganiza();
		// firePropertyChange("component", 4, 5);
	}

	public static void criaBorda(Zona zona, boolean cria) {
		if (zona != null && zona.getIcon() != null) {
			if (cria) {
				zona.setSize(zona.getIcon().getIconWidth() + GUIPreferencias.larguraBorda * 2, zona.getIcon()
						.getIconHeight()
						+ GUIPreferencias.larguraBorda * 2);
				zona.setBorder(BorderFactory.createLineBorder(GUIPreferencias.corBorda, GUIPreferencias.larguraBorda));
				// EtchedBorder(EtchedBorder.RAISED));//BevelBorder(BevelBorder.RAISED));//
			} else {
				zona.setBorder(null);
				zona.setSize(zona.getIcon().getIconWidth(), zona.getIcon().getIconHeight());
			}
		}
	}

	/*
	 * public Set<String> getDonos() { return donos; }
	 * 
	 * public Set<String> getVisivelPor() { return visivelPor; }
	 */

	public void setDonos(List<IPlayer> donos) {
		this.donos = donos;
	}

	public void setDonos(IPlayer[] donos) {
		this.donos = Arrays.asList(donos);
	}

	/*
	 * public void setVisivelPor(Set<String> visivelPor) { this.visivelPor =
	 * visivelPor; }
	 */

	public boolean isSelecionada() {
		return selecionada;
	}

	public void setSelecionada(boolean selecionada) {
		this.selecionada = selecionada;
	}

	@Override
	public void paint(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		if (selecionada) {
			negative(g2, this);
		} else {
			super.paint(g);
		}
	}

	// public TipoZona getTipoZona() {
	// return tipoZona;
	// }
	//
	// public void setTipoZona(TipoZona tipoZona) {
	// this.tipoZona = tipoZona;
	// }
	//
	public boolean isVisivelPossui(String pName) {
		if (visivelPor == VISIBILIDADE.PUBLICA
				|| (visivelPor != VISIBILIDADE.NINGUEM && donos != null && donos.contains(pName))) {
			return true;
		}
		return false;
	}

	public boolean possuida(String pName) {
		return donos != null && donos.contains(pName);
	}

	private void negative(Graphics g, Zona zona) {
		RescaleOp op = new RescaleOp(-1.0f, 255f, null);
		Graphics2D g2 = (Graphics2D) g;
		BufferedImage bi = GUIPreferencias.toBufferedImage(((ImageIcon) zona.getIcon()).getImage());
		g2.drawImage(bi, op, 0, 0);
	}

	// TODO aki? Melhorar isso
	public Zona getZonaPrimaria(Zona z) {
		if (z.getParent() instanceof Zona) {
			return getZonaPrimaria((Zona) this.getParent());
		}
		return z;
	}

	// @Override
	public int lenght() {
		return getComponentCount();
	}

	//
	// public void setVisivelPor(VisivelPor visivelPor) {
	// this.visivelPor = visivelPor;
	// }
	@Override
	public String toString() {
		return getName();
	}

	public void setaFoto(String nomePlayer) {
		// TODO Auto-generated method stub
		
	}
}