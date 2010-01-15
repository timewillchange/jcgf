package cgf.estado;

/** Interface de compromisso do ancestral de EstadoJogo e Zona. */
public interface IEstado {
	/**
	 * Metodo que retorna o container de mais alto nivel da hierarquia de Estado
	 * e Zonas do e stado do jogo.
	 */
	public EstadoJogo getEstado();
}
