package TDAMapeo;

public class InvalidKeyException extends Exception{
	/**
	 * Modela una excepcion cuando una clave es invalida.
	 * @param msg Mensaje que se mostrara por consola.
	 */
	public InvalidKeyException(String msg) {
		super(msg);
	}
}
