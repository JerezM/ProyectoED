package TDALista;

public class EmptyListException extends Exception{
	
	/**
	 * Modela una excepcion cuando la lista se encuentra vacia.
	 * @param msg Mensaje que se mostrara por consola.
	 */
	public EmptyListException(String msg) {
		super(msg);
	}
}
