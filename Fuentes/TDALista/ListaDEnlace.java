package TDALista;

import java.util.Iterator;
import Super.Position;

/**
 * TDAPositionList, implementacion: Double linked list.
 * @author Jerez, M.
 */

public class ListaDEnlace<E> implements PositionList<E> {
	protected NodoD<E> header,trailer;
	protected int size;
	
	/**
	 * Se inicializa la coleccion vacia con sus atributos correspondientes.
	 */
	public ListaDEnlace() {
		header= new NodoD<E>(null);
		trailer= new NodoD<E>(null);
		header.setNext(trailer);
		trailer.setPrev(header);
		size= 0;
	}

	@Override
	public int size() {
		return size;
	}

	@Override
	public boolean isEmpty() {
		return size==0;
	}

	@Override
	public Position<E> first() throws EmptyListException {
		if(size==0)
			throw new EmptyListException("Lista vacia.");
		
		return header.getNext();
	}

	@Override
	public Position<E> last() throws EmptyListException {
		if(size==0)
			throw new EmptyListException("Lista vacia.");
		
		return trailer.getPrev();
	}

	@Override
	public Position<E> next(Position<E> p) throws InvalidPositionException, BoundaryViolationException {
		if(size==0)
			throw new InvalidPositionException("Lista vacia.");
		NodoD<E> nodo= checkPosition(p);
		if(nodo.getNext() == trailer)
			throw new BoundaryViolationException("Error al solicitar el sgte de la ultima posicion de la lista.");
		
		return nodo.getNext();
	}

	@Override
	public Position<E> prev(Position<E> p) throws InvalidPositionException, BoundaryViolationException {
		if(size==0)
			throw new InvalidPositionException("Lista vacia.");
		NodoD<E> nodo= checkPosition(p);
		if(nodo.getPrev() == header)
			throw new BoundaryViolationException("Error al solicitar el anterior de la primera posicion de la lista.");
		
		return nodo.getPrev();
	}

	@Override
	public void addFirst(E element) {
		NodoD<E> sgte= header.getNext();
		NodoD<E> nodo= new NodoD<E>(header, element, sgte);
		
		header.setNext(nodo);
		sgte.setPrev(nodo);
		size++;
	}

	@Override
	public void addLast(E element) {
		NodoD<E> prev= trailer.getPrev();
		NodoD<E> nodo= new NodoD<E>(prev, element, trailer);
		
		trailer.setPrev(nodo);
		prev.setNext(nodo);
		size++;
	}

	@Override
	public void addAfter(Position<E> p, E element) throws InvalidPositionException {
		if(size==0)
			throw new InvalidPositionException("Lista vacia.");
		
		NodoD<E> nodo= checkPosition(p);
		NodoD<E> sgte= nodo.getNext();
		NodoD<E> newby= new NodoD<E>(nodo, element, sgte);
		
		nodo.setNext(newby);
		sgte.setPrev(newby);
		size++;
	}

	@Override
	public void addBefore(Position<E> p, E element) throws InvalidPositionException {
		if(size==0)
			throw new InvalidPositionException("Lista vacia.");
		
		NodoD<E> nodo= checkPosition(p);
		NodoD<E> prev= nodo.getPrev();
		NodoD<E> newby= new NodoD<E>(prev, element, nodo);
		
		prev.setNext(newby);
		nodo.setPrev(newby);
		size++;
	}

	@Override
	public E remove(Position<E> p) throws InvalidPositionException {
		if(size==0)
			throw new InvalidPositionException("Lista vacia.");
		
		NodoD<E> nodo= checkPosition(p);
		NodoD<E> prev= nodo.getPrev();
		NodoD<E> sgte= nodo.getNext();
		E ret= nodo.element();
		
		prev.setNext(sgte);
		sgte.setPrev(prev);
		nodo.setItem(null);
		nodo.setNext(null);
		nodo.setPrev(null);
		size--;
		
		return ret;
	}

	@Override
	public E set(Position<E> p, E element) throws InvalidPositionException {
		if(size==0)
			throw new InvalidPositionException("Lista vacia.");
		
		NodoD<E> nodo= checkPosition(p);
		E ret= nodo.element();
		
		nodo.setItem(element);
		
		return ret;
	}

	@Override
	public Iterator<E> iterator() {
		return new ElementIterator<E>(this);
	}

	@Override
	public Iterable<Position<E>> positions() {
		PositionList<Position<E>> list= new ListaDEnlace<Position<E>>();
		if(size > 0) {
			NodoD<E> it= header.getNext();
			
			while(it.getNext() != trailer) {
				list.addLast(it);
				it= it.getNext();
			}
			list.addLast(it);
		}
		
		return list;
	}
	
	/**
	 * Consulta que la posicion pasada por parametro sea valida y retorna el nodo asociado a la misma.
	 * @param v Posicion de un nodo.
	 * @return El nodo asociado a la posicion pasada por parametro.
	 * @throws InvalidPositionException si la posicion pasada por parametro es invalida.
	 */
	protected NodoD<E> checkPosition(Position<E> p) throws InvalidPositionException {
		try {
			if( p == null || p == header || p == trailer)
				throw new InvalidPositionException("Posicion invalida.");
			
			return (NodoD<E>) p;
		} catch(ClassCastException e) {
			throw new InvalidPositionException("Posicion invalida.");
		}
	}
}
