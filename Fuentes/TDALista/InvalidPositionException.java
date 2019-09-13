package TDALista;

public class InvalidPositionException extends Exception{
	
	/**
	 * Modela una excepcion cuando una posicion es invalida.
	 * @param msg Mensaje que se mostrara por consola.
	 */
	public InvalidPositionException(String msg) {
		super(msg);
	}
}
