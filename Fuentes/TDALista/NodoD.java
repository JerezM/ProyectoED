package TDALista;

import Super.Position;

/**
 * Implementacion de la clase NodoD.
 * @author Jerez, M.
 */

public class NodoD<E> implements Position<E>{
	private NodoD<E> next,prev;
	private E item;
	
	/**
	 * Se inicializa el nodo con sus atributos correspondientes.
	 * @param item Valor del rotulo.
	 * @param next Referencia a el nodo siguiente.
	 * @param prev Referencia a el nodo anterior.
	 */
	public NodoD(NodoD<E> prev, E item, NodoD<E> next) {
		this.item= item;
		this.prev= prev;
		this.next= next;
	}
	
	/**
	 * Se inicializa el nodo con sus atributos correspondientes.
	 * @param item Valor del rotulo.
	 */
	public NodoD(E item) {
		this(null, item, null);
	}

	public NodoD<E> getNext() {
		return next;
	}

	public void setNext(NodoD<E> next) {
		this.next = next;
	}

	public NodoD<E> getPrev() {
		return prev;
	}

	public void setPrev(NodoD<E> prev) {
		this.prev = prev;
	}

	public E getItem() {
		return item;
	}

	public void setItem(E item) {
		this.item = item;
	}

	@Override
	public E element() {
		return item;
	}
	
	
}
