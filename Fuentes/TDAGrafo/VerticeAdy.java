package TDAGrafo;

import TDALista.ListaDEnlace;
import TDALista.PositionList;
import Super.Position;

/**
 * Clase VerticeAdy utilizada en el TDAGrafo implementado con lista de adyacencia.
 * @author Jerez, M.
 */

public class VerticeAdy<V,E> implements Vertex<V>{
	protected V rotulo;
	protected PositionList<ArcoAdy<V,E>> adyacentes;
	protected Position<VerticeAdy<V,E>> posVertexList;
	
	/**
	 * Se inicializa un vertice con el rotulo parametrizado y se instancias sus atributos correspondientes.
	 * @param rotulo Valor que tendra el vertice.
	 */
	public VerticeAdy(V rotulo) {
		this.rotulo= rotulo;
		adyacentes= new ListaDEnlace<ArcoAdy<V,E>>();
		posVertexList= null;
	}

	@Override
	public V element() {
		return rotulo;
	}

	public Position<VerticeAdy<V, E>> getPosVertexList() {
		return posVertexList;
	}

	public void setPosVertexList(Position<VerticeAdy<V, E>> posVertexList) {
		this.posVertexList = posVertexList;
	}

	public PositionList<ArcoAdy<V, E>> getAdyacentes() {
		return adyacentes;
	}

	public void setRotulo(V rotulo) {
		this.rotulo = rotulo;
	}
}
