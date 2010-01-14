package cgf.estado;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.LayoutManager;

import javax.swing.JPanel;

import cgf.rmi.IPlayer;
import cgf.visao.CircularLayoutStrategy;
import cgf.visao.ILayoutStrategy;

//TODO Talvez nao extender JPanel e sim ter um. Vantagem: nao mostrar metodos desnecessarios ao usuario.
public class EstadoJogo extends JPanel implements ILayoutStrategy, IEstado, Cloneable {
	// OBS: Só pra lembrar variaveis static obviamente não são serializadas!
	// TODO String ou Player? Deve ser salvo!
	protected String playerVez;

	protected boolean moveu = false;

	protected String[] playerNames;

	protected int nRound;

	public int getnRound() {
		return nRound;
	}

	public void incNRound() {
		this.nRound++;
	}

	public EstadoJogo(/* ILayoutStrategy layoutStrategy */) {
		// super(layoutStrategy);
		setBackground(Color.GREEN);
		setPreferredSize(new Dimension(400, 400));
		setMinimumSize(new Dimension(400, 400));
	}

	public void setPlayerNames(String[] playerNames) {
		this.playerNames = playerNames;
	}

	public void printEstado() {
		for (Component zona : getComponents()) {
			System.out.println(zona.getName() + ((Container) zona).getComponents().length);
		}
	}

	public String[] getPlayerNames() {
		return playerNames;
	}

	public void setPlayerVez(String playerVez) {
		String old = this.playerVez;
		this.playerVez = playerVez;
		firePropertyChange("playerVez", old, playerVez);
	}

	public final Zona getZonaByName(String nomeZona) {
		return getZonaByNameRecursivo(nomeZona, this);
	}

	private Zona getZonaByNameRecursivo(String nomeZona, Container origem) {
		if (nomeZona != null) {
			if (nomeZona.equals(origem.getName())) {
				return (Zona) origem;
			}
			for (Component c : origem.getComponents()) {
				Zona zona = getZonaByNameRecursivo(nomeZona, (Container) c);
				if (zona != null) {
					return zona;
				}
			}
		}
		return null;
	}

	public EstadoJogo getEstado() {
		// TODO inutil
		return this;
	}

	@Override
	public Object clone() throws CloneNotSupportedException {
		// TODO Rever clone.
		return super.clone();
	}

	public String getPlayerVez() {
		return playerVez;
	}

	public boolean isMoveu() {
		return moveu;
	}

	public void setMoveu(boolean moveu) {
		this.moveu = moveu;
	}

	// TODO pensar geito melhor
	@Override
	public LayoutManager getLayout() {
		if (getLayout() == null) {
			setLayout(new CircularLayoutStrategy());
		}
		return super.getLayout();
	}

	@Override
	public void reorganiza(IPlayer modelo) {
		// TODO ninguem chama
	}
}