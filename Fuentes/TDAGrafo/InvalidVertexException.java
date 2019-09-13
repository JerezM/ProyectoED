package TDAGrafo;


public class InvalidVertexException extends Exception{
	/**
	 * Modela una excepcion cuando un vertice es invalido.
	 * @param msg Mensaje que se mostrara por consola.
	 */
	public InvalidVertexException(String msg){
		super(msg);
	}

}
