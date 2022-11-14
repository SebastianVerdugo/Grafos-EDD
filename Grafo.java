package GrafosEDD;

import java.util.*;

public class Grafo {
	public static final double INFINITY = Double.MAX_VALUE;
	
	public void agregarCamino (String nombreOrigen, String nombreDestino, double costo)
	{
		Nodo o= getNodo (nombreOrigen);
		Nodo d= getNodo (nombreDestino);
		o.caminos.add(new Camino (d, costo));
	}
	
	private void imprimirCamino (Nodo destino)
	{
		if(destino.anterior!=null)
		{
			imprimirCamino(destino.anterior);
			System.out.print(" to ");
		}
		System.out.println(destino.nombre);
	}
	
	public void imprimirCamino(String nombreDestino)
	{
		Nodo no = mapaNodo.get(nombreDestino);
		if(no==null)
		{
			throw new NoSuchElementException();
		}
		else if(no.distancia==INFINITY)
		{
			System.out.println(nombreDestino + " es inalcanzable");
		}
		else
		{
			System.out.print("(El costo es: "+no.distancia+") ");
			imprimirCamino(no);
			System.out.println( );
		}
	}
	
	public void noPonderado (String nombreInicio)
	{
		limpiarTodo();
		Nodo inicio = mapaNodo.get(nombreInicio);
		if(inicio==null)
		{
			throw new NoSuchElementException("Nodo de inicio no encontrado");
		}
		Queue<Nodo>q = new LinkedList<Nodo>( );
		q.add(inicio); inicio.distancia=0;
		while(!q.isEmpty())
		{
			Nodo n = q.remove();
			for(Camino c : n.caminos)
			{
				Nodo no = c.destino;
				if(no.distancia==INFINITY)
				{
					no.distancia=n.distancia+1;
					no.anterior=n;
					q.add(no);
				}
			}
		}
	}
	
	public void dijkstra (String nombreInicio)
	{
		PriorityQueue<Camino>pq= new PriorityQueue<Camino>();
		Nodo inicio = mapaNodo.get(nombreInicio);
		if(inicio == null)
		{
			throw new NoSuchElementException("Nodo inicio no encotrado");
		}
		
		limpiarTodo();
		pq.add(new Camino (inicio, 0));
		inicio.distancia=0;
		
		int nodosVistos=0;
		
		while(!pq.isEmpty() && nodosVistos < mapaNodo.size())
		{
			Camino nrec=pq.remove();
			Nodo n= nrec.destino;
			if(n.scratch!=0)
			{
				continue;
			}
			
			n.scratch=1;
			nodosVistos++;
			
			for(Camino c : n.caminos)
			{
				Nodo no = c.destino;
				double cnno = c.costo;
				if(cnno<0)
				{
					throw new ExcepcionesGrafo("El grafo tiene caminos negativos");
				}
				if(no.distancia>n.distancia+cnno)
				{
					no.distancia=n.distancia+cnno;
					no.anterior=n;
					pq.add(new Camino(no, no.distancia));
				}
			}
		}
	}
	
	public void negativo (String nombreInicio)
	{
		limpiarTodo();
		
		Nodo inicio = mapaNodo.get(nombreInicio);
		if(inicio==null)
		{
			throw new NoSuchElementException ("Nodo inicio no encontrado");
		}
		
		Queue<Nodo> q = new LinkedList<Nodo>();
		q.add(inicio);
		inicio.distancia=0;
		inicio.scratch++;
		
		while(!q.isEmpty())
		{
			Nodo n = q.remove();
			if(n.scratch++ > 2 * mapaNodo.size())
			{
				throw new ExcepcionesGrafo("Ciclo negativo detectado");
			}
			
			for(Camino e : n.caminos)
			{
				Nodo no = e.destino;
				double cnno = e.costo;
				if(no.distancia > n.distancia+cnno)
				{
					no.distancia = n.distancia+cnno;
					no.anterior = n;
					if(no.scratch++ % 2 == 0)
					{
						q.add(no);
					}
					else
					{
						no.scratch--;
					}
				}
			}
		}
	}
	
	public void aciclico (String nombreInicio)
	{
		Nodo inicio = mapaNodo.get(nombreInicio);
		if(inicio == null)
		{
			throw new NoSuchElementException ("Nodo inicio no encontrado");
		}
		limpiarTodo();
		Queue<Nodo>q = new LinkedList<Nodo>();
		inicio.distancia=0;
		
		Collection<Nodo> nodoSet = mapaNodo.values();
		
		for(Nodo n : nodoSet)
		{
			for(Camino c : n.caminos)
			{
				c.destino.scratch++;
			}
		}
		
		for(Nodo n : nodoSet)
		{
			if(n.scratch == 0)
			{
				q.add(n);
			}
		}
		
		int iteraciones;
		
		for(iteraciones = 0; !q.isEmpty(); iteraciones++)
		{
			Nodo n = q.remove();
			
			for(Camino c : n.caminos)
			{
				Nodo no = c.destino;
				double cnno = c.costo;
				
				if(--no.scratch == 0)
				{
					q.add(no);
				}
				
				if(n.distancia == INFINITY)
				{
					continue;
				}
				
				if(no.distancia > n.distancia + cnno)
				{
					no.distancia = n.distancia+cnno;
					no.anterior=n;
				}
			}
		}
		if(iteraciones != mapaNodo.size())
		{
			throw new ExcepcionesGrafo("¡El grafo tiene un ciclo!");
		}
	}
	
	private Nodo getNodo(String nombreNodo)
	{
		Nodo n = mapaNodo.get(nombreNodo);
		if(n==null)
		{
			n = new Nodo (nombreNodo);
			mapaNodo.put(nombreNodo, n);
		}
		return n;
	}
	
	private void limpiarTodo()
	{
		for(Nodo n : mapaNodo.values())
			n.Reiniciar();
	}
	
	
	private Map <String, Nodo> mapaNodo = new HashMap<String, Nodo>();
}
