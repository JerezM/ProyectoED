package TDAGrafo;

import Super.Position;

/**
 * Clase ArcoAdy utilizada en el TDAGrafo implementado con lista de adyacencia.
 * @author Jerez, M.
 */

public class ArcoAdy<V,E> implements Edge<E>{
	protected E rotulo;
	protected VerticeAdy<V,E> predecesor, sucesor;
	protected Position<ArcoAdy<V,E>> posPred, posSuces, posEdgeList;
	
	
	/**
	 * Se inicializa el arco que une a los vertices parametrizados y se instancian sus atributos correspondientes.
	 * @param rot Peso del arco.
	 * @param v1 Uno de los vectores que unira el arco.
	 * @param v2 El otro vector que unira el arco.
	 */
	public ArcoAdy(E rot, VerticeAdy<V,E> v1, VerticeAdy<V,E> v2) {
		rotulo= rot;
		predecesor= v1; sucesor= v2;
		posPred= posSuces= posEdgeList= null;
	}

	@Override
	public E element() {
		return rotulo;
	}

	public VerticeAdy<V, E> getPredecesor() {
		return predecesor;
	}

	public void setPredecesor(VerticeAdy<V, E> predecesor) {
		this.predecesor = predecesor;
	}

	public VerticeAdy<V, E> getSucesor() {
		return sucesor;
	}

	public void setSucesor(VerticeAdy<V, E> sucesor) {
		this.sucesor = sucesor;
	}

	public void setRotulo(E rotulo) {
		this.rotulo = rotulo;
	}

	public Position<ArcoAdy<V, E>> getPosPred() {
		return posPred;
	}

	public void setPosPred(Position<ArcoAdy<V, E>> posPred) {
		this.posPred = posPred;
	}

	public Position<ArcoAdy<V, E>> getPosSuces() {
		return posSuces;
	}

	public void setPosSuces(Position<ArcoAdy<V, E>> posSuces) {
		this.posSuces = posSuces;
	}

	public Position<ArcoAdy<V, E>> getPosEdgeList() {
		return posEdgeList;
	}

	public void setPosEdgeList(Position<ArcoAdy<V, E>> posEdgeList) {
		this.posEdgeList = posEdgeList;
	}

}
