package TDALista;

public class BoundaryViolationException extends Exception{
	
	/**
	 * Modela una excepcion cuando se sobrepasan los limites de la estructura.
	 * @param msg Mensaje que se mostrara por consola.
	 */
	public BoundaryViolationException(String msg) {
		super(msg);
	}
}
