package TDAGrafo;

import java.util.Iterator;

import TDALista.EmptyListException;
import TDALista.InvalidPositionException;
import TDALista.ListaDEnlace;
import TDALista.PositionList;
import Super.Position;

/**
 * TDAGrafo NO dirigido, implementacion: Adjacency List Structure.
 * @author Jerez, M.
 */

public class GrafoListaAdy<V,E> implements Graph<V,E>{
	protected PositionList<VerticeAdy<V,E>> vertexList;
	protected PositionList<ArcoAdy<V,E>> edgeList;
	
	/**
	 * Se inicializa el grafo vacio con sus atributos correspondientes.
	 */
	public GrafoListaAdy() {
		vertexList= new ListaDEnlace<VerticeAdy<V,E>>();
		edgeList= new ListaDEnlace<ArcoAdy<V,E>>();
	}
	
	
	@Override
	public Iterable<Vertex<V>> vertices() {
		PositionList<Vertex<V>> list= new ListaDEnlace<Vertex<V>>();
		
		for(Vertex<V> v: vertexList) 
			list.addLast(v);
		
		
		return list;
	}

	@Override
	public Iterable<Edge<E>> edges() {
		PositionList<Edge<E>> list= new ListaDEnlace<Edge<E>>();
		
		for(ArcoAdy<V,E> e: edgeList)
			list.addLast(e);
			
		return list;
	}

	@Override
	public Iterable<Edge<E>> incidentEdges(Vertex<V> v) throws InvalidVertexException {
		VerticeAdy<V,E> vertice= checkVertex(v);
		PositionList<Edge<E>> list= new ListaDEnlace<Edge<E>>();
		
		for(Edge<E> e: vertice.getAdyacentes())
			list.addLast(e);
		
		return list;
	}

	@Override
	public Vertex<V> opposite(Vertex<V> v, Edge<E> e) throws InvalidVertexException, InvalidEdgeException {
		VerticeAdy<V,E> vertice= checkVertex(v);
		ArcoAdy<V,E> arco= checkEdge(e);
		Vertex<V> retVertex= null;
		
		if(arco.getPredecesor()==vertice)
			retVertex= arco.getSucesor();
		else if(arco.getSucesor()==vertice)
			retVertex= arco.getPredecesor();
		else
			throw new InvalidEdgeException("El arco no esta conectado con el vertice parametrizado.");
		
		return retVertex;
	}

	@Override
	public Vertex<V>[] endvertices(Edge<E> e) throws InvalidEdgeException {
		ArcoAdy<V,E> arco= checkEdge(e);
		Vertex<V>[] array=(Vertex<V>[]) new Vertex[2];
		
		array[0]= arco.getPredecesor();
		array[1]= arco.getSucesor();
		
		return array;
	}

	@Override
	public boolean areAdjacent(Vertex<V> v, Vertex<V> w) throws InvalidVertexException {
		VerticeAdy<V,E> v1= checkVertex(v);
		VerticeAdy<V,E> v2= checkVertex(w);
		boolean sonAdy= false;
		
		Iterator<ArcoAdy<V,E>> itV1= v1.getAdyacentes().iterator();
		ArcoAdy<V,E> arcoActual= null;
		
		while(itV1.hasNext() && !sonAdy) {
			arcoActual= itV1.next();
			try {
				if( v2.equals( opposite(v1, arcoActual) ) )//Si el vertice opuesto a v con el arco actual es igual a w
					sonAdy=true;
			} catch(InvalidEdgeException | InvalidVertexException e) {
				e.printStackTrace();
			}
		}
		
		return sonAdy;
	}

	@Override
	public V replace(Vertex<V> v, V x) throws InvalidVertexException {
		VerticeAdy<V,E> vertice= checkVertex(v);
		V toRet= vertice.element();
		
		vertice.setRotulo(x);
		
		return toRet;
	}

	@Override
	public Vertex<V> insertVertex(V x) {
		VerticeAdy<V,E> nuevo= new VerticeAdy<V,E>(x);
		Position<VerticeAdy<V,E>> posList= null;
		
		try {
			
			vertexList.addLast(nuevo);
			posList= vertexList.last();
			nuevo.setPosVertexList(posList);
			
		} catch(EmptyListException e) {
			e.printStackTrace();
		}
		
		return nuevo;
	}

	@Override
	public Edge<E> insertEdge(Vertex<V> v, Vertex<V> w, E e) throws InvalidVertexException {
		VerticeAdy<V,E> vertice1= checkVertex(v);
		VerticeAdy<V,E> vertice2= checkVertex(w);
		ArcoAdy<V,E> arco= new ArcoAdy<V,E>(e, vertice1, vertice2);
		Position<ArcoAdy<V,E>> posAdyVertice1= null;
		Position<ArcoAdy<V,E>> posAdyVertice2= null;
		Position<ArcoAdy<V,E>> posEdgeList= null;
		
		vertice1.getAdyacentes().addLast(arco);
		vertice2.getAdyacentes().addLast(arco);
		edgeList.addLast(arco);
		
		try {
			posAdyVertice1= vertice1.getAdyacentes().last();
			posAdyVertice2= vertice2.getAdyacentes().last();
			posEdgeList= edgeList.last();
		} catch(EmptyListException t) {
			t.printStackTrace();
		}
		
		arco.setPosEdgeList(posEdgeList);
		arco.setPosPred(posAdyVertice1);
		arco.setPosSuces(posAdyVertice2);
		
		return arco;
	}
	
	/**
	 * Consulta que el Edge pasada por parametro sea valido y retorna el arco asociado al mismo.
	 * @param e Edge a verificar.
	 * @return El arco asociado al Edge pasado por parametro.
	 * @throws InvalidEdgeException si la Edge pasado por parametro es invalido.
	 */
	protected ArcoAdy<V,E> checkEdge(Edge<E> e) throws InvalidEdgeException {
		try {
			if( e == null )
				throw new InvalidEdgeException("Arco invalido.");
			
			return (ArcoAdy<V,E>) e;
		} catch(ClassCastException p) {
			throw new InvalidEdgeException("Arco invalido.");
		}
	}
	
	/**
	 * Consulta que el Vertex pasada por parametro sea valido y retorna el vertice asociado al mismo.
	 * @param v Vertex a verificar.
	 * @return El vertice asociado al Vertex pasado por parametro.
	 * @throws InvalidVertexException si la Vertex pasado por parametro es invalido.
	 */
	protected VerticeAdy<V,E> checkVertex(Vertex<V> v) throws InvalidVertexException {
		try {
			if( v == null )
				throw new InvalidVertexException("Vertice invalido.");
			
			return (VerticeAdy<V,E>) v;
		} catch(ClassCastException p) {
			throw new InvalidVertexException("Vertice invalido.");
		}
	}
}
