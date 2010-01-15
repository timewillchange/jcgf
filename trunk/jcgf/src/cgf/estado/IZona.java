package cgf.estado;

import java.awt.Component;
import java.awt.Graphics;
import java.util.List;

public interface IZona {
	// public Zona(String nome, String[] donos, /* Zona conteudo, */VISIBILIDADE
	// visivelPor, boolean movivel);

	public void reorganiza();

	// @Override
	public Component add(Component comp);

	// @Override
	public Component add(Component comp, int index);

	// @Override
	public void remove(Component comp);

	// public static void criaBorda(Zona zona, boolean cria);

	public void setDonos(List<String> donos);

	public void setDonos(String[] donos);

	public boolean isSelecionada();

	public void setSelecionada(boolean selecionada);

	// @Override
	public void paint(Graphics g);

	public boolean isVisivelPossui(String pName);

	public boolean possuida(String pName);

	// @Override
	public String toString();
}
