package TDALista;

import java.util.Iterator;
import java.util.NoSuchElementException;
import Super.Position;


/**
 * Implementacion de la clase ElementIterator
 * @author Jerez, M.
 */

public class ElementIterator<E> implements Iterator<E>{
	protected PositionList<E> list;
	protected Position<E> cursor;
	
	/**
	 * Se inicializa el cursor en la lista que se busca iterar.
	 * @param l lista a la cual se asociara el iterator.
	 */
	public ElementIterator(PositionList<E> l) {
		try {
			
			list= l;
			if(list != null && !list.isEmpty() )
				cursor= list.first();
			else
				cursor= null;
			
		} catch(EmptyListException e) {
			cursor= null;
		}
	}
	
	/**
	 * Verifica si quedan elementos en el iterador.
	 */
	public boolean hasNext() {
		return cursor!= null;
	}
	
	/**
	 * Consulta el elemento situado en la posicion actual del cursor y avanza el cursor en una posicion.
	 * @return E El elemento situado en la posicion actual del cursor.
	 * @throws NoSuchElementException Se lanza cuando el valor de la posicion del cursor es null.
	 */
	public E next() throws NoSuchElementException{
		if(cursor == null)
			throw new NoSuchElementException("Error: No hay siguiente.");
		
		E ret= cursor.element();
		try {
			cursor= (cursor == list.last()) ? null : list.next(cursor);
		} catch( EmptyListException | InvalidPositionException | BoundaryViolationException e) {
			e.printStackTrace();
		}
		
		return ret;
	}
}
