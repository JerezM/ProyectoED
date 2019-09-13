package TDAGrafo;

public class InvalidEdgeException extends Exception {
	/**
	 * Modela una excepcion cuando un arco es invalido.
	 * @param msg Mensaje que se mostrara por consola.
	 */
	public InvalidEdgeException(String msg){
		super(msg);
	}

}
