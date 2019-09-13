package TDAMapeo;

import java.util.Iterator;
import TDALista.InvalidPositionException;
import TDALista.ListaDEnlace;
import TDALista.PositionList;
import Super.Position;

/**
 * TDAMap, implementacion: Usando una tabla hash abierta donde cada bucket hace referencia a una coleccion de entradas.
 * @author Jerez, M.
 */

public class MapeoHashAbierto<K, V> implements Map<K, V>{
	protected PositionList<Entrada<K,V>>[] bucket;
	protected int capacidad, size;
	protected static final double factorCarga= (0.9);
	
	/**
	 * Se instancia la tabla hash vacia y se inicializan sus atributos correspondientes
	 */
	public MapeoHashAbierto() {
		capacidad= 13;
		size=0;
		bucket=(PositionList<Entrada<K,V>>[]) new PositionList[capacidad];
		for(int i=0; i<bucket.length; i++) {
			bucket[i]= new ListaDEnlace<Entrada<K,V>>();
		}
		
	}
	
	/**
	 * Consulta la ubicacion del bucket a la cual debera ir la clave parametrizada.
	 * @param key Clave a procesar.
	 * @return Un entero que indicara la ubicacion del bucket.
	 */
	protected int hash(K key) {
		return  Math.abs( key.hashCode() ) % capacidad;
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
	public V get(K key) throws InvalidKeyException {
		checkKey(key);
		int posBucket= hash(key);
		V retValue= null;
		boolean seguir= (bucket[posBucket].isEmpty()) ? false : true;
		
		if(seguir) {//Si la lista posicionada en el bucket indicado posee elementos, entonces busco si existe la clave que busco
			Iterator<Entrada<K,V>> it= bucket[posBucket].iterator();
			Entrada<K,V> entradaActual= null;
			
			while(seguir && it.hasNext()) {
				entradaActual= it.next();
				if(entradaActual.getKey().equals(key)) {//Si encuentro una entrada que tenga la misma clave que la buscada entonces guardo el valor
					retValue= entradaActual.getValue();
					seguir= false;
				}
			}
		}
		
		return retValue;
	}

	@Override
	public V put(K key, V value) throws InvalidKeyException {
		checkKey(key);
		V retValue= null;
		int posBucket= hash(key);
		boolean seguir= (bucket[posBucket].isEmpty()) ? false : true;
		Entrada<K,V> entradaNueva= new Entrada<K,V>(key, value);
		
		if(seguir) {//Si la lista posicionada en el bucket indicado posee elementos, entonces busco si ya existe la clave parametrizada
			Iterator<Position<Entrada<K,V>>> it= (bucket[posBucket].positions()).iterator();
			Position<Entrada<K,V>> entradaActual= null;
			
			while(seguir && it.hasNext()) {
				entradaActual= it.next();
				if(entradaActual.element().getKey().equals(key)) {
					try {
						retValue= entradaActual.element().getValue();//Guardo ref del valor anterior
						bucket[posBucket].set(entradaActual, entradaNueva);//Setteo el nuevo valor
						seguir= false;
					} catch(InvalidPositionException e) {
						e.printStackTrace();
					}
				}
			}
			
			if(seguir) {//Si no se encontro una entrada con la misma clave anteriormente entonces agrego la nueva entrada
				bucket[posBucket].addLast(entradaNueva);
				size++;
			}
		}
		else {
			bucket[posBucket].addLast(entradaNueva);
			size++;
		}
		
		if(size/capacidad >= factorCarga)// n/N >= 0.9
			rehash();
		
		return retValue;
	}

	@Override
	public V remove(K key) throws InvalidKeyException {
		checkKey(key);
		V retValue= null;
		int posBucket= hash(key);
		boolean seguir= (bucket[posBucket].isEmpty()) ? false : true;
		
		if(seguir) {//Si la lista posicionada en el bucket indicado posee elementos, entonces busco si ya existe la clave parametrizada
			Iterator<Position<Entrada<K,V>>> it= (bucket[posBucket].positions()).iterator();
			Position<Entrada<K,V>> entradaActual= null;
			
			while(seguir && it.hasNext()) {
				entradaActual= it.next();
				if(entradaActual.element().getKey().equals(key)) {//Si encontre la entrada con la clave que buscaba
					seguir= false;
					size--;
					retValue= entradaActual.element().getValue();//Guardo su valor
					try {
						bucket[posBucket].remove(entradaActual);//y la elimino de la tabla
					} catch (InvalidPositionException e) {
						e.printStackTrace();
					}
				}
			}
		}	
		
		return retValue;
	}

	@Override
	public Iterable<K> keys() {
		PositionList<K> keysList= new ListaDEnlace<K>();
		
		for(int i=0; i<bucket.length; i++) {
			for(Entrada<K,V> entrada: bucket[i]) {
				keysList.addLast(entrada.getKey());
			}
		}
		
		return keysList;
	}

	@Override
	public Iterable<V> values() {
		PositionList<V> valuesList= new ListaDEnlace<V>();
		
		for(int i=0; i<bucket.length; i++) {
			for(Entrada<K,V> entrada: bucket[i]) {
				valuesList.addLast(entrada.getValue());
			}
		}
		
		return valuesList;
	}

	@Override
	public Iterable<Entry<K, V>> entries() {
		PositionList<Entry<K,V>> entriesList= new ListaDEnlace<Entry<K,V>>();
		
		for(int i=0; i<bucket.length; i++) {
			for(Entrada<K,V> entrada: bucket[i]) {
				entriesList.addLast(entrada);
			}
		}
		
		return entriesList;
	}
	
	@Override
	public String entradasPorBucket() {
		String toRet="";
		
		for(int i=0; i<bucket.length ; i++) {
			if(i%4==0)
				toRet+="\n";
			toRet+="Bucket["+i+"]: "+bucket[i].size()+"; ";
		}
		
		return toRet;
	}
	
	/**
	 * Se encarga de redimensionar la tabla manteniento en el proceso las entradas actuales
	 */
	protected void rehash() {
		capacidad= proximoPrimo(capacidad*2);
		PositionList<Entrada<K,V>>[] vieja= bucket;
		bucket= (PositionList<Entrada<K,V>>[]) new PositionList[ capacidad ];
		for(int i=0; i<bucket.length; i++) {
			bucket[i]= new ListaDEnlace<Entrada<K,V>>();
		}
		
		for(int i=0; i< vieja.length; i++) {
			PositionList<Entrada<K,V>> listaVieja= vieja[i];
			try {
				for(Position<Entrada<K,V>> viejaEntrada: listaVieja.positions()) {
					Entrada<K,V> nuevaEntrada= listaVieja.remove(viejaEntrada);
					int posBucket= hash(nuevaEntrada.getKey());
					bucket[posBucket].addLast(nuevaEntrada);
				}
			} catch(InvalidPositionException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * Consulta el numero primo mas cercano al numero parametrizado.
	 * @param primo Numero a consultar.
	 * @return El numero primo mas cercano al numero parametrizado, en caso de que el numero pasado por parametro sea primo entonces se retornara el mismo.	
	 */
	protected int proximoPrimo(int primo) {
		boolean encontre = false;
		
		while(!encontre) {
			if(esPrimo(primo))
				encontre = true;
			else
				primo++;
		}
		
		return primo;
	}
	
	/**
	 * Consulta si el numero parametrizado es primo.
	 * @param primo Numero a verificar.
	 * @return Verdadero en caso de que el numero parametrizado sea primo, falso en caso contrario.
	 */
	protected boolean esPrimo(int primo) {
		boolean encontre = true;
		
		for(int i = 2; i<primo && encontre; i++) {
			if((primo % i) == 0)
				encontre = false;
		}
		
		return encontre;
	}
	
	/**
	 * Consulta si la clave parametrizada es invalida.
	 * @param key Clave a consultar.
	 * @throws InvalidKeyException Se lanza si la clave pasada por parametro es invalida.
	 */
	protected void checkKey(K key) throws InvalidKeyException {
		if(key==null)
			throw new InvalidKeyException("Clave invalida.");
	}
}
