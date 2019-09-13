package Controlador;

import java.util.Iterator;

import TDAGrafo.*;
import TDALista.*;
import TDAMapeo.*;

/**
 * Clase logica del Proyecto para examen libre(23-08-2019)
 * @author Jerez, M.
 */

public class Logica {
	private Map<String, Integer> mapeo;
	private Graph<String, Double> grafo;
	
	/**
	 * Se encarga de inicializar el mapeo.
	 */
	public void generarMapeo(){
		mapeo= new MapeoHashAbierto<String, Integer>();
	}
	
	/**
	 * Inserta una nueva entrada en el mapeo.
	 * @param clave Clave de la entrada a insertar.
	 * @param valor Valor de la entrada a insertar.
	 * @throws InvalidKeyException Si la clave pasada por parametro es invalida.
	 */
	public void insertarEntrada(String clave, Integer valor) throws InvalidKeyException{
		mapeo.put(clave, valor);
	}
	
	/**
	 * Remueve la entrada con la clave dada en el mapeo.
	 * @param clave Entrada a remover.
	 * @throws InvalidKeyException si la clave pasada por parametro es invalida.
	 */
	public void eliminarEntrada(String clave) throws InvalidKeyException{
		Integer valor= mapeo.remove(clave);
		
		if(valor==null)
			throw new InvalidKeyException("No se ha encontrado una entrada con la clave indicada.");
	}
	
	/**
	 * Busca una entrada con clave igual a una clave dada y devuelve el valor asociado.
	 * @param clave Clave a buscar.
	 * @return Valor de la entrada encontrada.
	 * @throws InvalidKeyException Si la clave pasada por parametro es invalida.
	 */
	public String buscarClave(String clave) throws InvalidKeyException{
		Integer	valor= mapeo.get(clave);
		String toRet= "";
		
		if(valor==null)
			throw new InvalidKeyException("No se pudo encontrar un valor asociado a la clave indicada.");
		
		toRet+= "El valor de la clave buscada es: "+valor;
		
		return toRet;
	}
	
	/**
	 * Recorre el mapeo y almacena en una cadena de texto todas las entradas.
	 * @return Un String que contiene todas las entradas del mapeo.
	 */
	public String listarEntradas() {
		String toRet= "M:{ ";
		
		if(!mapeo.isEmpty()) {
			Iterator<Entry<String, Integer>> it= mapeo.entries().iterator();
			Entry<String, Integer> entrada= null;
		
			while(it.hasNext()) {
				entrada= it.next();
				toRet+= "("+entrada.getKey()+", "+entrada.getValue()+") ";
			}
		}
		
		toRet+= "}"; 
		
		return toRet;
	}
	
	/**
	 * Recorre el mapeo y almacena en una cadena de texto la cantidad de entradas que hay en cada bucket.
	 * @return Un String con la cantidad de entradas por bucket.
	 */
	public String entradasBucket() {
		return mapeo.entradasPorBucket();
	}
	
	/**
	 * Se encarga de inicializar el grafo.
	 */
	public void generarGrafo() {
		grafo= new GrafoListaAdy<String, Double>();
	}
	
	/**
	 * Inserta un nuevo vertice en el grafo.
	 * @param rotulo Rotulo del nuevo vertice.
	 */
	public void insertarVertice(String rotulo) {
		grafo.insertVertex(rotulo);
	}
	
	/**
	 * Inserta un nuevo arco con rotulo rotuloArco y con vertices extremos v1, v2.
	 * @param rotuloV1 El vertice origen.
	 * @param rotuloV2 El vertice destino.
	 * @param rotuloArco Rotulo del nuevo arco.
	 * @throws InvalidVertexException si uno de los vertices es invalido.
	 */
	public void insertarArco(String rotuloV1, String rotuloV2, double rotuloArco) throws InvalidVertexException{
		Vertex<String> vertice1= null; Vertex<String> vertice2= null;
		Vertex<String>[] vertices= buscarVertices(rotuloV1, rotuloV2);
		
		vertice1= vertices[0];
		vertice2= vertices[1];
		
		grafo.insertEdge(vertice1, vertice2, rotuloArco);
	}
	
	/**
	 * Se encarga de buscar en el grafo los vertices que posean como rotulo los valores parametrizados y retorna los mismos almacenados en una coleccion.
	 * @param rotuloV1 Rotulo de uno de los vertices a buscar.
	 * @param rotuloV2 Rotulo de uno de los vertices a buscar.
	 * @return Una coleccion la cual almacena los vertices encontrados.
	 * @throws InvalidVertexException Si no se encuentra algunos de los vertices en el grafo.
	 */
	private Vertex<String>[] buscarVertices(String rotuloV1, String rotuloV2) throws InvalidVertexException{
		boolean encontreV1= false; boolean encontreV2= false;
		Vertex<String>[] vertices=(Vertex<String>[])new Vertex[2];
		
		Iterator<Vertex<String>> it= grafo.vertices().iterator();
		Vertex<String> aux= null;
		
		while(it.hasNext() && !encontreV1 && !encontreV2) {
			aux= it.next();
			
			if(aux.element().equals(rotuloV1)) {//Si el rotulo del vertice actual es igual al rotulo1 parametrizado
				encontreV1= true;
				vertices[0]= aux;
				if(rotuloV1.equals(rotuloV2)) {
					vertices[1]= aux;
					encontreV2= true;
				}
			}
			else if(aux.element().equals(rotuloV2)) {//Si el rotulo del vertice actual es igual al rotulo2 parametrizado
				encontreV2= true;
				vertices[1]= aux;
			}
		}
		
		if(!encontreV1 && !encontreV2) 
			throw new InvalidVertexException("Error: Los vertices ingresados son invalidos.");
		
		while(!encontreV1 && it.hasNext()) {
			aux= it.next();
			
			if(aux.element().equals(rotuloV1)) {
				encontreV1= true;
				vertices[0]= aux;
			}
		}
		
		if(!encontreV1)
			throw new InvalidVertexException("Error: El primer vertice ingresado es invalido.");
		
		while(!encontreV2 && it.hasNext()) {
			aux= it.next();
			
			if(aux.element().equals(rotuloV2)) {
				encontreV2= true;
				vertices[1]= aux;
			}
		}
		
		if(!encontreV2)
			throw new InvalidVertexException("Error: El segundo vertice ingresado es invalido.");
		
		
		return vertices;
	}
	
	/**
	 * Se encarga de almacenar en una cadena de caracteres los rotulos de los vertices y los arcos actuales.
	 * @return Un String almacenando todos los rotulos de los vertices y ademas un trio indicando con que vertices estan conectados los arcos.
	 * @throws InvalidEdgeException Si un arco es invalido.
	 */
	public String listarVerticesYArcos() throws InvalidEdgeException{
		String toRet="Vertices: ";
		
		Iterator<Vertex<String>> itVertices= grafo.vertices().iterator();
		
		while(itVertices.hasNext()) {//Almaceno los vertices
			toRet+= itVertices.next().element()+" ";
		}
		toRet+= ";\nArcos: ";
		
		Iterator<Edge<Double>> itArcos= grafo.edges().iterator();
		Vertex<String> v1= null; Vertex<String> v2= null;
		Edge<Double> arcoActual= null;
		Vertex<String>[] vertices= null;
		
		while(itArcos.hasNext()) {//Almaceno los trios de forma => (Vertice1, PesoArco, Vertice2)
			arcoActual= itArcos.next();
			vertices= grafo.endvertices(arcoActual);
			
			v1= vertices[0]; v2= vertices[1];
			toRet+= "("+v1.element()+", "+arcoActual.element()+", "+v2.element()+") ";
		}
		toRet+= ";";
		
		return toRet;
	}
	
	/**
	 * Dados dos vértices, halla y retorna un camino con su costo entre tales vértices.
	 * @param rotulov1 Uno de los vertices a los cuales se va a encontrar el camino.
	 * @param rotulov2 Uno de los vertices a los cuales se va a encontrar el camino.
	 * @return Un String con el camino y el costo entre tales vértices.
	 * @throws InvalidVertexException Si no se encuentra algunos de los vertices en el grafo.
	 */
	public String hallarCamino(String rotulov1, String rotulov2) throws InvalidVertexException{
		PositionList<Vertex<String>> path= new ListaDEnlace<Vertex<String>>();
		Map<Vertex<String>, Boolean> estadoVertices= new MapeoHashAbierto<Vertex<String>, Boolean>();
		Vertex<String>[] vertices;
		Vertex<String> v1, v2;   v1= v2= null;
		boolean existeCamino; String toRet="";
			
		vertices= buscarVertices(rotulov1, rotulov2);
		v1= vertices[0];
		v2= vertices[1];
		
		existeCamino= DFSstShell(v1, v2, path, estadoVertices);
		
		if(existeCamino) {
			toRet= "Origen: "+v1.element()+";\nDestino: "+v2.element()+";\nCamino: ";
			toRet+= dibujarCamino(path);
		}
		else {
			toRet= "No se ha encontrado un camino entre los vertices seleccionados.";
		}
		
		return toRet;
	}
	
	/**
	 * Recorre el grafo y verifica si existe un camino entre los vertices parametrizados, de ser asi, el camino antes mencionado se guarda en una coleccion.
	 * @param origen Vertice origen del camino.
	 * @param destino Vertice destino del camino.
	 * @param camino Coleccion donde se almacenara el camino recorrido.
	 * @param map Mapeo que se utilizara para las etiquetas de vertice(Visitado/NoVisitado).
	 * @return True en caso de que existe un camino entre los vertices seleccionados; Falso en caso contrario.
	 */
	private boolean DFSstShell(Vertex<String> origen, Vertex<String> destino, PositionList<Vertex<String>> camino, Map<Vertex<String>, Boolean> map) {
		try {
			for (Vertex<String> vert : grafo.vertices())
				map.put(vert, false);
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		}
		
		return DFSst(origen, destino, camino, map);
	}

	/**
	 * Recorre el grafo utilizando el metodo DFS y verifico si existe un camino entre los vertices parametrizados,
	 *  de ser asi, el camino antes mencionado se guarda en una coleccion.
	 * @param origen Vertice origen del camino.
	 * @param destino Vertice destino del camino.
	 * @param camino Coleccion donde se almacenara el camino recorrido.
	 * @param map Mapeo que se utilizara para las etiquetas de vertice(Visitado/NoVisitado).
	 * @return True en caso de que existe un camino entre los vertices seleccionados; Falso en caso contrario.
	 */
	private boolean DFSst(Vertex<String> origen, Vertex<String> destino, PositionList<Vertex<String>> camino, Map<Vertex<String>, Boolean> map) {
		boolean encontre= false;
		
		try {
			
			map.put(origen, true);
			camino.addLast(origen);
			if (origen == destino)
				encontre= true;
			else {
				Iterator<Edge<Double>> it= grafo.incidentEdges(origen).iterator();
				Edge<Double> arcoActual=null;
				
				while(it.hasNext() && !encontre) {//Recorro todos los arcos emergentes al origen
					arcoActual= it.next();
					Vertex<String> w = grafo.opposite(origen, arcoActual);//Grafo adyacente al origen
					if (map.get(w)== false) {
						encontre = DFSst(w, destino, camino, map);
					}
				}
				
				if(!encontre)
					camino.remove(camino.last());//BackTracking
			}
			
		} catch (InvalidEdgeException | InvalidVertexException | InvalidPositionException | EmptyListException | InvalidKeyException e) {}
		
		return encontre;
	}
	
	/**
	 * Se encarga de recorrer la coleccion contenedora del camino y la transforma a formato String para facilitar la lectura.
	 * @param path Coleccion que contiene el camino entre los vertices.
	 * @return Un String ejemplificando el camino generado.
	 */
	private String dibujarCamino(PositionList<Vertex<String>> path) {
		String camino= ""; double costeCamino= 0;
		Iterator<Vertex<String>> itCamino= path.iterator();
		Vertex<String> v1= null;
		Vertex<String> v2= null;
		Edge<Double> arcoUnion= null;
		
		
			if(path.size()==1 && itCamino.hasNext()) {
				v1= itCamino.next();
				camino= "("+v1.element()+",0,"+v1.element()+");\nCoste camino: "+costeCamino+";";
			}
			else {
				v1=(itCamino.hasNext()) ? itCamino.next() : null;
				
				while(itCamino.hasNext() && v1!=null) {
					v2= itCamino.next();
					arcoUnion= buscarArcoUnion(v1, v2);
					
					camino+= "("+v1.element()+","+arcoUnion.element()+","+v2.element()+") ";
					costeCamino+= arcoUnion.element();
					v1= v2;	
				}
				
				camino+= ";\nCoste camino: "+costeCamino+";";
			}
			
		
		
		return camino;
	}
	
	/**
	 * Consulta cual es el arco que uno a los vertices parametrizados.
	 * @param v1 Vertice origen.
	 * @param v2 Vertice destino.
	 * @return El arco que une a los vertices parametrizados.
	 */
	private Edge<Double> buscarArcoUnion(Vertex<String> v1, Vertex<String> v2) {
		boolean encontre= false;
		Iterator<Edge<Double>> itArcos= null;
		Edge<Double> arcoActual=null;
		
		try {
			
			itArcos= grafo.incidentEdges(v1).iterator();
			while(itArcos.hasNext() && !encontre) {//Recorro todos los arcos emergentes de v1
				arcoActual= itArcos.next();
				if( grafo.opposite(v1, arcoActual).equals(v2) )//Si el vertice opuesto a v1 usando el arcoActual es igual v2
					encontre= true;
			}
			
		} catch (InvalidVertexException | InvalidEdgeException e) {
			e.printStackTrace();
		}
		
		return arcoActual;
	}
}
